package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*

class CollectionController {

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
      result.coll = TliCollection.findByShortcode(id)
    }
    else {
      redirect(controller:'home', action:'index');
    }
    result
  }

}
