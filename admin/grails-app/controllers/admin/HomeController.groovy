package admin

import grails.converters.*
import tli.*;
// import org.springframework.security.acls.model.NotFoundException
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import me.ianibbo.common.*
import grails.plugin.springsecurity.SpringSecurityUtils


class HomeController {

  def springSecurityService
  def genericOIDService
  def shortcodeService

  private static final String HEADER_PRAGMA = "Pragma";
  private static final String HEADER_EXPIRES = "Expires";
  private static final String HEADER_CACHE_CONTROL = "Cache-Control";

  protected preventCache (response) {
    response.setHeader(HEADER_PRAGMA, "no-cache");
    response.setDateHeader(HEADER_EXPIRES, 1L);
    response.setHeader(HEADER_CACHE_CONTROL, "no-cache");
    response.addHeader(HEADER_CACHE_CONTROL, "no-store");
  }

  // @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
    preventCache(response);
    log.debug("home");

    def view="index.gsp"
    def model=[:]

    if ( request.user != null ) {
      log.debug("Logged in user is ${request.user}");
      view='userindex.gsp'
      model.records = []
      model.organisations = AuthCommonOrganisation.executeQuery("select a from AuthCommonAffiliation as a where a.user = ?",[request.user]);

      // All collections this user has access to (Currently = collectons of the org)
      model.colls = AuthCommonOrganisation.executeQuery("select c from TliCollection as c where exists ( select a from AuthCommonAffiliation as a where a.org = c.owner and a.user = ? )", [request.user]);

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
      def new_afflilation_request = new AuthCommonAffiliation(
        user:request.user,
        org:genericOIDService.resolveOID(params.org),
        status:RefdataCategory.lookupOrCreate("status",  SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN') ? "Approved" : "Pending Approval" ),
        role:RefdataCategory.lookupOrCreate("affiliation", params.role )
      ).save()
      redirect(controller:'home', action:'index')
    }
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def requestNewOrg() {
    if ( request.method == 'POST' ) {
      def existing_org = AuthCommonOrganisation.findByDisplayNameIlike(params.orgName)
      if ( existing_org == null ) {
        def proposed_org = new AuthCommonOrganisation(displayName:params.orgName,
                                      status:RefdataCategory.lookupOrCreate("status", "Pending Approval" ),
                                      shortcode:shortcodeService.generate('me.ianibbo.common.AuthCommonOrganisation','shortcode',params.orgName)).save()
        def affiliation = new AuthCommonAffiliation(user:request.user,
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
