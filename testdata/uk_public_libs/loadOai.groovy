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



if ( args.length < 1 ) {
  println("Usage: groovy loadOai.groovy");
  println("Example hosts: [\"http://localhost:8888\"|\"http://data.opendatasheffield.org\"]");
  System.exit(1);
}


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

          if ( r.header.'@status' == 'deleted' ) {
            // Skip deleted records
          }
          else {
            def record = r.metadata
            // notificationTarget.notifyChange(dto)
            // process(record.description, uploadApi)
            if ( record.description.title.text().length() > 0 ) {
              println("***OK record***:${r.header.identifier}");
              upload(record.description, uploadApi)
            }
            else {
              println("***Error record***:${r.header.identifier}\n${r}");
            }
          }
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

def upload(record, uploadApi) {

  try{
      def address_elements = []

      def identifiers = []
      record.other_identifier.each { oi ->
        def new_id = [namespace:oi.'@scheme'.text(), value:oi.text()]
        println("Adding new id: ${new_id}");
        identifiers.add(new_id);

      }

      uploadApi.request(POST) { request ->
        def json_record = [
          // "id": "",
          // "keywords": [
          // ],
          "categories": [
              "Institutions.${record.sector.text()}"
          ],
          // "iconType":record.sector?.text().replaceAll("\\p{Punct}","").replaceAll(" ","").toLowerCase(),
          "title": [
              record.title?.text().replaceAll("\\p{Punct}","").trim()
          ],
          // "description": [
          // ],
          "url": [
              record.relation.text()
          ],
          // "address": address_elements,
          // "contact": [
          // ],
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
                     record.address.pcode.text()
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

        def record_json = new JsonBuilder( json_record ).toPrettyString()
  
        println(record_json);
  
        requestContentType = 'multipart/form-data'
        uri.path="/olid/api/mlaInst/upload"
        def multipart_entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        // multipart_entity.addPart("owner", new StringBody( 'ofsted', 'text/plain', Charset.forName('UTF-8')))
        def uploaded_file_body_part = new org.apache.http.entity.mime.content.ByteArrayBody(record_json.getBytes('UTF8'), 'application/json', "sfn.json");
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
