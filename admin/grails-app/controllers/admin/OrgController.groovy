package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*

class OrgController {

  def springSecurityService
  def genericOIDService
  def shortcodeService

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
    def result = [:]
    if ( params.id != null && params.id != '' ) {
      result.org = TliOrg.findByShortcode(params.id)
    }
    else {
      redirect(controller:'home', action:'index');
    }
    result
  }
}
