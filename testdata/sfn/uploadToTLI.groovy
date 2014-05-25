@Grapes([
    @Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.14'),
    @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.1'),
    @Grab(group='org.apache.httpcomponents', module='httpmime', version='4.1.2'),
    @Grab(group='org.apache.httpcomponents', module='httpclient', version='4.0'),
    @Grab(group='xerces', module='xercesImpl', version='2.9.1') ])

import groovyx.net.http.*
import static groovyx.net.http.ContentType.URLENC
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

def sfn_data = null

File f = new File('sfn_formatted.json');
if (f){
  FileReader f2 = new FileReader(f);
  if (f2 == null) {
    //do something
  } else {
    JsonSlurper jsonParser = new JsonSlurper();
    sfn_data = jsonParser.parse(new FileReader(f));
  }
} 

if ( args.length < 1 ) {
  println("Usage: groovy uploadToTli.groovy");
  println("Example hosts: [\"http://localhost:8888\"|\"http://data.opendatasheffield.org\"]");
  system.exit(1);
}

try{
  // def url = "http://localhost:8888"
  // def url = "http://data.opendatasheffield.org"
  url = args[0]
  def coll_shortcode = 'sfn'

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


  sfn_data.markers.each { marker ->
    println(marker);

    def address_elements = []
    marker.address?.split(',').each {
      address_elements.add(it);
    }

    api.request(POST) { request ->
      def json_record = [
        // "id": "",
        "keywords": [
            "FOOD"
        ],
        "categories": [
            "SFN","FOOD",marker.type
        ],
        "entryType":"POI",
        "iconType":marker.type?.replaceAll("\\p{Punct}","").replaceAll(" ","").toLowerCase(),
        "title": [
            marker.title?.replaceAll("\\p{Punct}","").trim()
        ],
        "description": [
            marker.description
        ],
        "url": [
            marker.website
        ],
        "address": address_elements,
        "contact": [
            ""
        ],
        "telephone": [
            marker.tel
        ],
        // "fax": [
        //     "nnnnn nnnnnnn"
        // ],
        // "mobile": [
        //     "nnnnn nnnnnnn"
        // ],
        "email": [
            marker['e-mail']
        ],
        "timesAndPlaces": [
            [
                "address":address_elements,
                "location" : [
                  lat:marker.lat,
                  lon:marker.lng
                ],
                "postcode": [
                ],
                "maplink": [
                ],
                "access": [
                ],
                "daysAndTimes": [
                ],
                "iconType": marker.type?.replaceAll("\\p{Punct}","").replaceAll(" ","").toLowerCase()
            ]
        ],
        "Languages": [
        ]
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

}
finally{
}


//  "address" : "N/A (private home)",
//         "category" : "BUY",
//         "description" : "Home baked bread and buns, made using stoneground organic wheat, spelt, & rye flours. Limited delivery service to the south west area of Sheffield. Bread is baked to order, so to ensure your loaf please contact Mick Saxton by 4:00pm the day before baking. Also runs baking classes.",
//         "e-mail" : "mjsaxton55@gmail.com",
//         "heading" : "157.429435235281",
//         "keywords" : "",
//         "lat" : "53.371573",
//         "lng" : "-1.50070700000003",
//         "opening" : "Order by phone or email.",
//         "pitch" : "-11.0145931025694",
//         "rank" : 1,
//         "source" : "DA Apr 13",
//         "title" : "Saxton's Home Bakery",
//         "type" : "Bakers",
//         "website" : "http://www.saxtonshomebakery.co.uk/",
//         "zoom" : "1"

