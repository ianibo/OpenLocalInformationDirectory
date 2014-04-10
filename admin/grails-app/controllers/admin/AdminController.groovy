package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*
import au.com.bytecode.opencsv.CSVReader

class AdminController {

  def springSecurityService
  def genericOIDService
  def vocabSyncService
  def pushService
  def enrichmentService

  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
  }

  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def approveOrgs() {
    def result=[:]
    if ( ( params.org != null ) &&
         ( params.act != null ) &&
         ( params.org != '' ) &&
         ( params.act == 'Y' || params.act == 'N' ) ) {
      def org_to_approve = TliOrg.get(params.org)
      if ( params.act == 'Y' ) {
        org_to_approve.status = RefdataCategory.lookupOrCreate("status", "Approved" )
      }
      else {
        org_to_approve.status = RefdataCategory.lookupOrCreate("status", "Rejected" )
      }
      org_to_approve.save(flush:true)
    }

    def status_pending = RefdataCategory.lookupOrCreate("status", "Pending Approval" )
    result.pendinfg_orgs = TliOrg.findAllByStatus(status_pending)
    result
  }

  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def approveAffiliations() {
    def result=[:]

    if ( ( params.aff != null ) &&
         ( params.act != null ) &&
         ( params.aff != '' ) &&
         ( params.act == 'Y' || params.act == 'N' ) ) {
      def aff_to_approve = Affiliation.get(params.aff)
      if ( params.act == 'Y' ) {
        aff_to_approve.status = RefdataCategory.lookupOrCreate("status", "Approved" )
      }
      else {
        aff_to_approve.status = RefdataCategory.lookupOrCreate("status", "Rejected" )
      }
      aff_to_approve.save(flush:true)
    }

    def status_pending = RefdataCategory.lookupOrCreate("status", "Pending Approval" )
    result.pendinfg_affiliations = Affiliation.findAllByStatus(status_pending)
    result
  }

  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def loadVocabulary() {
    def result=[:]
    if ( request.method=='POST' ) {
      vocabSyncService.update(params.vocabCode,params.vocabBaseUrl, params.vocabPath);
    }

    result
  }

  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def loadOrgs() {
    def result=[:]
    if ( request.method=='POST' ) {     
      def orgs_csv = request.getPart('orgscsv')
      def upload_mime_type = orgs_csv?.contentType
      def upload_filename = orgs_csv?.getOriginalFilename()
      log.debug("Uploaded type: ${upload_mime_type} filename was ${upload_filename}");
      CSVReader r = new CSVReader( new InputStreamReader(orgs_csv?.inputStream, 
                                   java.nio.charset.Charset.forName('UTF-8') ) )
      String[] nl;
      while ((nl = r.readNext()) != null) {
        println(nl)
      }
    }
    result
  }


  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def forceFTUpdate() {
    pushService.updateFTIndexes();
    redirect controller:'home',action:'index';
  }

  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def forceEnrichment() {
    enrichmentService.runEnrichment()
    redirect controller:'home',action:'index';
  }
}
