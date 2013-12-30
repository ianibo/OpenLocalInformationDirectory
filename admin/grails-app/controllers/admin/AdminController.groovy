package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*

class AdminController {

  def springSecurityService
  def genericOIDService

  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
  }

  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def approveOrgs() {
    def result=[:]
    def status_pending = RefdataCategory.lookupOrCreate("status", "Pending Approval" )
    result.pendinfg_orgs = TliOrg.findAllByStatus(status_pending)
    result
  }

  @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
  def approveAffiliations() {
    def result=[:]
    def status_pending = RefdataCategory.lookupOrCreate("status", "Pending Approval" )
    result.pendinfg_affiliations = Affiliation.findAllByStatus(status_pending)
    result
  }


}
