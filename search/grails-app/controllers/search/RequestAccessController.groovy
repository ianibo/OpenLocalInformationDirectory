package search

import grails.converters.*
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.commons.ApplicationHolder
import tli.*;

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*
import me.ianibbo.common.*



class RequestAccessController {

  def springSecurityService

  def index() { 
    log.debug("RequestAccess::index() ${params} ${springSecurityService.currentUser}");
    // 1. Is the user logged in? If not, go to login request access
    if ( springSecurityService.currentUser == null ) {
      redirect(action:'loginToRequestAccess', id:params.id);
    }
    else {
      redirect(action:'requestAccess', id:params.id);
    }
  }

  def loginToRequestAccess() {
    log.debug("RequestAccess::loginToRequestAccess()");
    def result = [:]

    if ( params.id ) {
      log.debug("looking up ${params.id}");
      def entry = DirectoryEntry.executeQuery ('select e from DirectoryEntry as e join e.shortcodes as s where s.shortcode = ?',[params.id]);
      log.debug(entry);
      if ( entry.size() == 1 ) {
        result.entry = entry.get(0);
      }
      else {
      }
    }
    else {
      log.debug("No id");
    }
    result
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def requestAccess() {
    log.debug("RequestAccess::requestAccess()");
    def result = [:]

    if ( params.id ) {
      log.debug("looking up ${params.id}");
      def entry = DirectoryEntry.executeQuery ('select e from DirectoryEntry as e join e.shortcodes as s where s.shortcode = ?',[params.id]);
      log.debug(entry);
      if ( entry.size() == 1 ) {
        result.entry = entry.get(0);
      }
      else {
      }
    }
    else {
      log.debug("No id");
    }

    // Logic - 1 : Does the user already have access - OR - an outstanding request on this record - OR - more than 5 outstanding requests
    if ( 1 == 2 ) {
        // Yes - Cool - redirect
    }
    else {
      // 2 - Does the users email address match any of the email addresses for this record?
      if ( 2 == 3 ) {
        // Yes thats easy then - grant permission
      }
      else {
        // 3 - No default permission so - Does the record have an email address?
        if ( 3 == 4 ) {
          // Yes - Use the email request template to zip off a message requesting access
        }
        else {
          // no way of automatically verifying that this user has permission to maintain this record
          // Flag up  request to admin interface.
        }
      }
    }

    result
  }


}
