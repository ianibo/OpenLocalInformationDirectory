package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*

import grails.converters.*



class CollectionController extends SearchController {

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() {
    log.debug("CollectionController::index");

    def coll = TliCollection.findByShortcode(params.id)
    params.qbe='g:resources'
    params.qp_collection='tli.TliCollection:'+coll.id;
    params.collDisplayName=coll.name
    super.index();
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def batchExport() {
    log.debug("batchExport");
    def result = [:]
    result.coll = TliCollection.findByShortcode(params.id)
    result
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def batchImport() {
    log.debug("batchImport");
    def result = [:]
    result.coll = TliCollection.findByShortcode(params.id)
    result
  }
}
