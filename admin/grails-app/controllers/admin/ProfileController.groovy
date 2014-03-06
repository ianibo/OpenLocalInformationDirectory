package admin

import grails.converters.*
import grails.plugin.springsecurity.annotation.Secured

import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*


class ProfileController {

  def springSecurityService

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
    def result = [:]
    TliUser user = springSecurityService.currentUser
    result.user = user
    result
  }
}
