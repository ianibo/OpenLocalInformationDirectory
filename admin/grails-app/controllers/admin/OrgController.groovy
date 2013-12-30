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
    redirect(controller:'home', action:'index');
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def show(String id) {
    def result = [:]
    if ( id != null && id != '' ) {
      result.org = TliOrg.findByShortcode(id)
      result.affiliation = Affiliation.findByOrgAndUser(result.org, request.user)
    }
    else {
      redirect(controller:'home', action:'index');
    }
    result
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def requestNewCollection(String id) {
    def result = [:]
    if ( id != null && id != '' ) {
      result.org = TliOrg.findByShortcode(id)
    }
    else {
      redirect(controller:'home', action:'index');
    }
    result
  }

  // @Override
  // Book queryForResource(Serializable id) {
  // Book.where {
  // id == id && author.id = params.authorId
  // }.find()
  // }
}
