package admin

import grails.converters.*
import tli.*;
// import org.springframework.security.acls.model.NotFoundException
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils



class HomeController {

  def springSecurityService

  // @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
    log.debug("home");

    def view="index.gsp"
    def model=[:]

    if ( request.user != null ) {
      log.debug("Logged in user is ${request.user}");
      view='userindex.gsp'
      model.records = []
      model.organisations = TliOrg.executeQuery("select o from TliOrg as o where exists ( select a from Affiliation as a where a.user = ? and a.org = o and a.status = 1 )",[request.user]);

      log.debug("Model: ${model}");
    }
    else {
      log.debug("No logged in user");
    }

    render view:view, model:model
  }
}
