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



println("unique_reference,org_name,email,url,publication_scheme,twitter,facebook,identifier.mla");
doSync('http://www.culturegrid.org.uk')



def doSync(host) {
  def http = new HTTPBuilder( host )

  def more = true
  def resumption=null

  // perform a GET request, expecting JSON response data
  while ( more ) {

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

          if ( r.header.'@status' == 'deleted' ) {
            // Skip deleted records
          }
          else {
            def record = r.metadata
            // notificationTarget.notifyChange(dto)
            // process(record.description, uploadApi)
            if ( record.description.title.text().length() > 0 ) {
              def shortcode = record.description.title.text().trim().toLowerCase().replaceAll("\\p{Punct}","").trim().replaceAll("\\W","_")
              println("\"${shortcode}\",\"${record.description.title}\",\"${record.description.email?.text()}\",\"${record.description.relation?.text()}\",,,,\"${record.description.identifier}\"");
            }
            else {
              // println("***Error record***:${r.header.identifier}\n${r}");
            }
          }
        }

        if ( xml.'ListRecords'.'resumptionToken'.size() == 1 ) {
          resumption=xml.'ListRecords'.'resumptionToken'.text()
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
  }
}
