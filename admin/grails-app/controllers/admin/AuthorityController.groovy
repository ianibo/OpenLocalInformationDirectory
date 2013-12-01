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
      def new_authority = new LisAuthority(code:params.code,description:params.desc,owner:request.user).save(flush:true);
      def new_affiliation = new Affiliation(user:request.user, authority:new_authority, role:'admin').save(flush:true);
      redirect controller:'home', action:'index'
    }
    else {
      log.debug("Not post");
    }
  }
}
