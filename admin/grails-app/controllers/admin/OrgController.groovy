package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*
import me.ianibbo.common.*;

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
      result.org = AuthCommonOrganisation.findByShortcode(id)
      result.affiliation = AuthCommonAffiliation.findByOrgAndUser(result.org, request.user)
      result.collections = TliCollection.findAllByOwner(result.org);
    }
    else {
      redirect(controller:'home', action:'index');
    }

    log.debug("Result:${result}");
    result
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def requestNewCollection(String id) {
    log.debug("requestNewCollection ${params},${id}");
    def result = [:]
    if ( id != null && id != '' ) {
      result.org = AuthCommonOrganisation.findByShortcode(id)
    }
    else {
      redirect(controller:'home', action:'index');
    }

    if ( result.org ) {
      if ( request.method=='POST' ) {
        def new_collection_shortcode = shortcodeService.generate('tli.TliCollection','shortcode',params.collName)
        log.debug("Create new collection ${result.org} ${params.collName} ${params.description} ${new_collection_shortcode}");

        def new_collection = new TliCollection(owner:result.org, 
                                               name:params.collName, 
                                               description:params.description,
                                               shortcode:new_collection_shortcode).save()
        redirect(controller:'org',id:result.org.shortcode)
      }
    }
    else {
      log.error("Unable to locate org with shortcode ${id}");
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
