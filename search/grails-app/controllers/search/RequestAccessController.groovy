package search

import grails.converters.*
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.commons.ApplicationHolder


class RequestAccessController {

  def index() { 
    log.debug("RequestAccess::index()");
    // 1. Is the user logged in? If not, go to login request access
    redirect(action:'loginToRequestAccess', id:params.id)
  }

  def loginToRequestAccess() {
    log.debug("RequestAccess::loginToRequestAccess()");
  }
}
