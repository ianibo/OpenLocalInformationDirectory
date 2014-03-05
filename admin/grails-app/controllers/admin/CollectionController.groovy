package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*

import grails.converters.*



class CollectionController extends SearchController {

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() {
    def coll = TliCollection.findByShortcode(params.id)
    log.debug("index");
    params.qbe='g:resources'
    params.qp_collection='tli.TliCollection:'+coll.id;
    super.index();
  }
}
