package admin

import grails.converters.*
import grails.plugin.springsecurity.annotation.Secured

import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*
import me.ianibbo.common.*;


class ProfileController {

  def springSecurityService

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
    def result = [:]
    AuthCommonUser user = springSecurityService.currentUser
    result.user = user
    result
  }
}
