package admin

import grails.converters.*
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils

class AuthorityController {


  def springSecurityService

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def create() { 
    log.debug("Authority::create ${params} ${request.method}");
    if ( request.method == 'POST' ) {
      log.debug("post..");
      redirect controller:'home', action:'index'
    }
    else {
      log.debug("Not post");
    }
  }
}
