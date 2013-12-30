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
    log.debug("requestNewCollection ${params},${id}");
    def result = [:]
    if ( id != null && id != '' ) {
      result.org = TliOrg.findByShortcode(id)
    }
    else {
      redirect(controller:'home', action:'index');
    }

    if ( request.method=='POST' ) {
      log.debug("Create new collection");
      def new_collection = new TliCollection(owner:result.org, 
                                             name:params.collName, 
                                             description:params.description,
                                             shortcode:shortcodeService.generate('tli.TliCollection','shortcode',params.collName)).save()
      redirect(controller:'org',id:result.org.shortcode)
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
