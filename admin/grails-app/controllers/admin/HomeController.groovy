package admin

import grails.converters.*
// import org.springframework.security.acls.model.NotFoundException
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils



class HomeController {

  def springSecurityService

  // @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
    log.debug("home");

    def view="index.gsp"

    if ( request.user != null ) {
      log.debug("Logged in user is ${request.user}");
      view='userindex.gsp'
    }
    else {
      log.debug("No logged in user");
    }

    render view:view
  }
}
