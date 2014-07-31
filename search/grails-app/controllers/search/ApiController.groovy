package search

import grails.converters.*
import groovy.xml.MarkupBuilder
import tli.*;
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import me.ianibbo.common.*

class ApiController {

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def get() {
    // Return JSON for an object
    log.debug("get ${params}"); // class id
  }

}
