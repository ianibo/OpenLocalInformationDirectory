@Grapes([
    @Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.14'),
    @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.1'),
    @Grab(group='org.apache.httpcomponents', module='httpmime', version='4.1.2'),
    @Grab(group='org.apache.httpcomponents', module='httpclient', version='4.0'),
    @Grab(group='xerces', module='xercesImpl', version='2.9.1') ])


import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovyx.net.http.*
import org.apache.http.entity.mime.*
import org.apache.http.entity.mime.content.*
import java.nio.charset.Charset
import static groovy.json.JsonOutput.*
import groovy.util.slurpersupport.GPathResult
import org.apache.http.*
import org.apache.http.protocol.*
import groovy.json.*



// http://www.culturegrid.org.uk/dpp/oai?verb=listRecords&set=PN:MLAInstitutions:*&metadataPrefix=CultureGrid_Institution


def url = args[0]
def coll_shortcode = 'mlainst'

def api = new RESTClient(url)
def rest_upload_pass = ""
System.in.withReader {
  print 'upload pass:'
  rest_upload_pass = it.readLine()
}

// Add preemtive auth
api.client.addRequestInterceptor( new HttpRequestInterceptor() {
  void process(HttpRequest httpRequest, HttpContext httpContext) {
    String auth = "admin:${rest_upload_pass}"
    String enc_auth = auth.bytes.encodeBase64().toString()
      httpRequest.addHeader('Authorization', 'Basic ' + enc_auth);
    }
})
  

doSync('http://www.culturegrid.org.uk',api);


def doSync(host, uploadApi) {
  println("Get latest changes");

  def http = new HTTPBuilder( host )

  def more = true
  println("Attempt get...");
  def resumption=null

  // perform a GET request, expecting JSON response data
  while ( more ) {
    println("Make http request..");

    http.request( GET, XML ) {
      uri.path = '/dpp/oai'
      if ( resumption ) {
        uri.query = [ verb:'ListRecords', resumptionToken: resumption ]
      }
      else {
        uri.query = [ verb:'ListRecords', metadataPrefix: 'CultureGrid_Institution', set:'PN:MLAInstitutions:*' ]
      }

      // response handler for a success response code:
      response.success = { resp, xml ->
        println resp.statusLine

        xml.'ListRecords'.'record'.each { r ->
          println("Record id...${r.'header'.'identifier'}");

          def record = r.metadata
          // notificationTarget.notifyChange(dto)
          // process(record.description, uploadApi)
          upload(record.description, uploadApi)
        }

        if ( xml.'ListRecords'.'resumptionToken'.size() == 1 ) {
          resumption=xml.'ListRecords'.'resumptionToken'.text()
          println("Iterate with resumption : ${resumption}");
        }
        else {
          more = false
        }
      }

      // handler for any failure status code:
      response.failure = { resp ->
        println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
      }
    }
    println("Endloop");
  }

  println("All done");
}

def process(record, uploadApi) {
  println("\n\n");
  println(record.identifier);
  println(record.title);
  println(record.type);
  println(record.jurisdiction);
  println("Other Identifiers....");
  println("Administrative status: ${record.administrative_status}");
  println("Voice: ${record.voice}");
  println(record.email);
  println(record.address.street);
  println(record.address.locality);
  println(record.address.region);
  println(record.address.pcode);
  println(record.address.country);
  println(record.isPartOf);
}

def upload(record, uploadApi) {

  try{
      def address_elements = []

      def identifiers = []
      record.other_identifier.each { oi ->
        identifiers.add(namespace:oi.'@scheme', value:oi.text());
      }

      marker.address?.split(',').each {
        address_elements.add(it);
      }
  
      api.request(POST) { request ->
        def json_record = [
          // "id": "",
          "keywords": [
          ],
          "categories": [
              "Institutions.${record.sector.text()}"
          ],
          "iconType":record.sector.text().type?.replaceAll("\\p{Punct}","").replaceAll(" ","").toLowerCase(),
          "title": [
              marker.request.title?.text().replaceAll("\\p{Punct}","").trim()
          ],
          "description": [
          ],
          "url": [
              record.relation.text()
          ],
          "address": address_elements,
          "contact": [
          ],
          "telephone": [
              record.voice.text()
          ],
          // "fax": [
          //     "nnnnn nnnnnnn"
          // ],
          // "mobile": [
          //     "nnnnn nnnnnnn"
          // ],
          "email": [
              record.email.text()
          ],
          "timesAndPlaces": [
              [
                  "address":address_elements,
                  "postcode": [
                    record.culturegrid_institution.pcode.text()
                  ],
                  "maplink": [
                  ],
                  "access": [
                  ],
                  "daysAndTimes": [
                  ],
                  "iconType": record.sector?.text().replaceAll("\\p{Punct}","").replaceAll(" ","").toLowerCase()
              ]
          ],
          "Languages": [
          ],
          "Identifiers" : identifiers
        ]
  
        def record = new JsonBuilder( json_record ).toPrettyString()
  
        println(record);
  
        requestContentType = 'multipart/form-data'
        uri.path="/olid/api/${coll_shortcode}/upload"
        def multipart_entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        // multipart_entity.addPart("owner", new StringBody( 'ofsted', 'text/plain', Charset.forName('UTF-8')))
        def uploaded_file_body_part = new org.apache.http.entity.mime.content.ByteArrayBody(record.getBytes('UTF8'), 'application/json', "sfn.json");
        multipart_entity.addPart("tf", uploaded_file_body_part);
  
        request.entity = multipart_entity
  
        response.success = { resp, data ->
          println("OK - Record uploaded");
        }
  
        response.failure = { resp ->
          println("Error - ${resp.status}");
          System.out << resp
          println("Done\n\n");
        }
      }
  
  }
  finally{
  }
}
