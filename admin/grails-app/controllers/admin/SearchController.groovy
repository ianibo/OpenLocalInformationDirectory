package admin

import grails.converters.*
// import org.springframework.security.acls.model.NotFoundException
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils





class SearchController {

  def springSecurityService

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
    log.debug("Search::index");
  }
}
