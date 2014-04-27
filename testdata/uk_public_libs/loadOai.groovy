@Grapes([
    @Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.14'),
    @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.1'),
    @Grab(group='org.apache.httpcomponents', module='httpmime', version='4.1.2'),
    @Grab(group='org.apache.httpcomponents', module='httpclient', version='4.0'),
    @Grab(group='xerces', module='xercesImpl', version='2.9.1') ])


import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*


// http://www.culturegrid.org.uk/dpp/oai?verb=listRecords&set=PN:MLAInstitutions:*&metadataPrefix=CultureGrid_Institution


doSync('http://www.culturegrid.org.uk',null);


def doSync(host, notificationTarget) {
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
          process(record.description)
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

def process(record) {
  println("\n\n");
  println(record.identifier);
  println(record.title);
  println(record.type);
  println(record.jurisdiction);
  println("Other Identifiers....");
  record.other_identifier.each { oi ->
    println("Other identifier ${oi.'@scheme'} ${oi.text()}");
  }
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
