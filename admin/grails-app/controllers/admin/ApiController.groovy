package admin

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*;



class ApiController {

  def springSecurityService
  def genericOIDService
  def shortcodeService

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def collections() {
    def model=[:]
    model.colls = TliOrg.executeQuery("select c from TliCollection as c where exists ( select a from Affiliation as a where a.org = c.owner and a.user = ? )", [request.user]);
    model
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def upload() {
    def model=[:]
    model.colls = TliOrg.executeQuery("select c from TliCollection as c where exists ( select a from Affiliation as a where a.org = c.owner and a.user = ? )", [request.user]);
    model
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def oai() {
    def model=[:]
    model.colls = TliOrg.executeQuery("select c from TliCollection as c where exists ( select a from Affiliation as a where a.org = c.owner and a.user = ? )", [request.user]);
    model
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def download() {
    def model=[:]
    model.colls = TliOrg.executeQuery("select c from TliCollection as c where exists ( select a from Affiliation as a where a.org = c.owner and a.user = ? )", [request.user]);
    model
  }
}
