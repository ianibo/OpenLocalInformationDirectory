package search

import grails.converters.*
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.commons.ApplicationHolder
import tli.*;


class RequestAccessController {

  def index() { 
    log.debug("RequestAccess::index() ${params}");
    // 1. Is the user logged in? If not, go to login request access

    def result = [:]

    if ( params.id ) {
      log.debug("looking up ${params.id}");
      def entry = DirectoryEntry.executeQuery ('select e from DirectoryEntry as e join e.shortcodes as s where s.shortcode = ?',[params.id]);
      log.debug(entry);
      if ( result.entry.size() == 1 ) {
        result.entry = entry.get(0);
        log.debug(entry);
      }
      else {
      }
    }
    else {
      log.debug("No id");
    }

    redirect(action:'loginToRequestAccess', id:params.id, model:result);
  }

  def loginToRequestAccess() {
    log.debug("RequestAccess::loginToRequestAccess()");
  }
}
