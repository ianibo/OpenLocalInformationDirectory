package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*;



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
    model.colls = TliOrg.executeQuery("select c from TliCollection as c where exists ( select a from Affiliation as a where a.org = c.owner and a.user = ? )", [request.user]);
    model
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def upload() {
    log.debug("upload...");
    def model=[:]
    if ( request.method=='POST') {
      log.debug("Post....");
      def file = request.getFile("tf")
      def record = new String(file.getBytes())
      model.ingestResult = ingestService.ingest(record,params.id, request.user, file.contentType);
    }
    
    model
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def oai() {
    def model=[:]
    model.colls = TliOrg.executeQuery("select c from TliCollection as c where exists ( select a from Affiliation as a where a.org = c.owner and a.user = ? )", [request.user]);
    model
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def download() {
    def model=[:]
    model.colls = TliOrg.executeQuery("select c from TliCollection as c where exists ( select a from Affiliation as a where a.org = c.owner and a.user = ? )", [request.user]);
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

}
