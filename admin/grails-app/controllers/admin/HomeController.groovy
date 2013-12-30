package admin

import grails.converters.*
import tli.*;
// import org.springframework.security.acls.model.NotFoundException
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils



class HomeController {

  def springSecurityService
  def genericOIDService
  def shortcodeService

  // @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
    log.debug("home");

    def view="index.gsp"
    def model=[:]

    if ( request.user != null ) {
      log.debug("Logged in user is ${request.user}");
      view='userindex.gsp'
      model.records = []
      model.organisations = TliOrg.executeQuery("select a from Affiliation as a where a.user = ?",[request.user]);

      // All collections this user has access to (Currently = collectons of the org)
      model.colls = TliOrg.executeQuery("select c from TliCollection as c where exists ( select a from Affiliation as a where a.org = c.owner and a.user = ? )", [request.user]);

    }
    else {
      log.debug("No logged in user");
    }

    render view:view, model:model
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def requestAffiliation() {
    log.debug("${params}");
    if ( request.method == 'POST' ) {
      def new_afflilation_request = new Affiliation(
        user:request.user,
        org:genericOIDService.resolveOID(params.org),
        status:RefdataCategory.lookupOrCreate("status", "Pending Approval" ),
        role:RefdataCategory.lookupOrCreate("affiliation", params.role )
      ).save()
      redirect(controller:'home', action:'index')
    }
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def requestNewOrg() {
    if ( request.method == 'POST' ) {
      def existing_org = TliOrg.findByDisplayNameIlike(params.orgName)
      if ( existing_org == null ) {
        def proposed_org = new TliOrg(displayName:params.orgName,
                                      status:RefdataCategory.lookupOrCreate("status", "Pending Approval" ),
                                      shortcode:shortcodeService.generate('tli.TliOrg','shortcode',params.orgName)).save()
        def affiliation = new Affiliation(user:request.user,
                                          org:proposed_org,
                                          status:RefdataCategory.lookupOrCreate("status", "Pending Approval" ),
                                          role:RefdataCategory.lookupOrCreate("affiliation", "Administrator" )).save(flush:true)
        redirect(controller:'home', action:'index')
      }
      else {
        redirect(controller:'org', action:'show', id:existing_org.id)
      }
    }
    else {
    }
  }

}
