package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*

class OrgController {

  def springSecurityService

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def requestAffiliation() {
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def requestNew() {
    if ( request.method == 'POST' ) {
      def existing_org = TliOrg.findByDisplayNameIlike(params.orgName)
      if ( existing_org == null ) {
        def proposed_org = new TliOrg(displayName:params.orgName, status:RefdataCategory.lookupOrCreate("status", "Pending Approval" )).save()
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