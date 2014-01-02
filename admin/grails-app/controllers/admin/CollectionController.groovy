package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*

import grails.converters.*



class CollectionController extends SearchController {

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() {
    log.debug("index");
    params.qbe='g:resources'
    super.index();
  }
}
