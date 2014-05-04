package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*;
import grails.converters.*
import au.com.bytecode.opencsv.CSVReader
import me.ianibbo.common.*



class ApiController {

  def springSecurityService
  def genericOIDService
  def shortcodeService
  def ingestService
  def vocabSyncService

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def collections() {
    def model=[:]
    model.colls = AuthCommonOrganisation.executeQuery("select c from TliCollection as c where exists ( select a from AuthCommonAffiliation as a where a.org = c.owner and a.user = ? )", [request.user]);
    model
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def upload() {
    log.debug("upload... user:${request.user}");
    def model=[:]

    try {
      if ( request.method=='POST') {
        log.debug("Post....");
        def file = request.getFile("tf")
        def record = new String(file.getBytes())
        model.ingestResult = ingestService.ingest(record,params.id, request.user, file.contentType);
      }
    }
    catch ( Exception e ) {
      log.error("Problem",e);
    }
    
    render model as JSON
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def oai() {
    def model=[:]
    model.colls = AuthCommonOrganisation.executeQuery("select c from TliCollection as c where exists ( select a from AuthCommonAffiliation as a where a.org = c.owner and a.user = ? )", [request.user]);
    model
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def download() {
    def model=[:]
    model.colls = AuthCommonOrganisation.executeQuery("select c from TliCollection as c where exists ( select a from AuthCommonAffiliation as a where a.org = c.owner and a.user = ? )", [request.user]);
    model
  }

  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def loadVocabulary() {
    log.debug("loadVocabulary(${params})");

    def result=[:]
    if ( request.method=='POST' ) {
      vocabSyncService.update(params.vocabCode,params.vocabBaseUrl, params.vocabPath);
    }

    result
  }


  // unique_reference,org_name,email,url,publication_scheme
  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def loadOrgs() {
    log.debug("loadOrgs: ${request.method} ${params}");
    def result=[:]
    if ( request.method=='POST' ) {
      def orgs_csv = request.getFile("orgscsv")
      if ( orgs_csv != null ) {
        def upload_mime_type = orgs_csv?.contentType
        def upload_filename = orgs_csv?.getOriginalFilename()
        log.debug("Uploaded type: ${upload_mime_type} filename was ${upload_filename}");
        CSVReader r = new CSVReader( new InputStreamReader(orgs_csv?.inputStream, java.nio.charset.Charset.forName('UTF-8') ) )
        String[] nl;
        nl = r.readNext() // - Skip header

        while ((nl = r.readNext()) != null) {
          log.debug(nl);
          if ( ( nl.length > 5 ) && ( nl[1].length() > 0 ) ) {

            def shortcode_to_lookup = nl[0].length() > 0 ? nl[0] : nl[1].trim().toLowerCase().replaceAll("\\p{Punct}","").trim().replaceAll("\\W","_")

            def org = AuthCommonOrganisation.findByShortcode(shortcode_to_lookup)
            if ( org == null ) {
              log.debug("No existing org with shortcode ${shortcode_to_lookup} create...");
              def status_approved =  RefdataCategory.lookupOrCreate("status", "Approved" )
              org = new AuthCommonOrganisation(
                                               status:status_approved,
                                               shortcode:shortcode_to_lookup,
                                               url:nl[3],
                                               pubScheme:nl[4],
                                               email:nl[2],
                                               twitter:nl[5],
                                               facebook:nl[6],
                                               displayName:nl[1]).save()
            }
            else {
              log.debug("Located existing org with shortcode ${org.shortcode} : ${org}");
            }
          }
        }
      }
      else {
        log.debug("Unable to get part orgscsv in request");
      }
    }
    result
  }


}
