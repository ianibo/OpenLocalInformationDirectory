package search

import grails.converters.*
import groovy.xml.MarkupBuilder
import tli.*;

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*
import me.ianibbo.common.*



class RequestAccessController {

  def springSecurityService
  def grailsApplication
  def mailService

  def index() { 
    log.debug("RequestAccess::index() ${params} currentUser:${springSecurityService.currentUser}");
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
    log.debug("RequestAccess::requestAccess(${params})");
    // log.debug("Session: ${session}");
    // log.debug("Session.SPRING_SECURITY_CONTEXT: ${session.SPRING_SECURITY_CONTEXT}");
    // log.debug("Session.SPRING_SECURITY_CONTEXT.Authentication: ${session.SPRING_SECURITY_CONTEXT?.authentication}");
    // log.debug("Session.SPRING_SECURITY_CONTEXT.Authentication.details: ${session.SPRING_SECURITY_CONTEXT?.authentication.details}");
    // log.debug("Request: ${request}");
    // request.attributeNames.each {
    //   log.debug(it);
    // }
    def result = [:]

    try {

      if ( params.id ) {
        log.debug("looking up ${params.id}");
        def entry = DirectoryEntry.executeQuery ('select e from DirectoryEntry as e join e.shortcodes as s where s.shortcode = ?',[params.id]);
        log.debug(entry);
        if ( entry.size() == 1 ) {
          result.entry = entry.get(0);
        }
        else {
          log.error("Unable to locate ${params.id}");
        }
      }
      else {
        log.debug("No id");
      }
  
  
      log.debug("Looking up any existing grants...");
      // Find all DirectoryEntryOwner records that might link this user and this record
      def existing_grants = DirectoryEntryOwner.executeQuery("select  e from DirectoryEntryOwner as e where e.party = ? and e.dirent = ?",
                                                             [springSecurityService.currentUser, result.entry]);
  
      // Logic - 1 : Does the user already have access - OR - an outstanding request on this record - OR - more than 5 outstanding requests
      if ( existing_grants.size() > 0 ) {
          // Yes - Cool - redirect
          log.debug("User already has access - redirect");
          redirect(controller:'entry', action:'edit', id:params.id);
      }
      else {
        log.debug("No existing grants found - work out who to ask");
  
        if ( ( result.entry.contactEmail != null ) && 
             ( springSecurityService.currentUser.email != null ) &&
             ( result.entry.contactEmail?.toLowerCase().contains(springSecurityService.currentUser.email?.toLowerCase()))) {
          // Yes thats easy then - grant permission
          log.debug("The contact email section of the email address contains the users email address. Grant access");
          def deo = new DirectoryEntryOwner(party:springSecurityService.currentUser, dirent:result.entry, role: RefdataCategory.lookupOrCreate("RecordOwnerType", 'Data Subject')).save()
          redirect(controller:'entry', action:'edit', id:params.id);
        }
        else {
          // We need to gather some information about the requester - show the request access form
        }
      }
    }
    finally {
      log.debug("Leaving requestPerm");
    }

    result
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def processRequestAccessForm() {
    def result = [:]
    try {
      if ( params.id ) {
        log.debug("looking up ${params.id}");
        def entry = DirectoryEntry.executeQuery ('select e from DirectoryEntry as e join e.shortcodes as s where s.shortcode = ?',[params.id]);
        log.debug(entry);
        if ( entry.size() == 1 ) {
          result.entry = entry.get(0);
        }
        else {
          log.error("Unable to locate ${params.id}");
        }
      }
      else {
        log.debug("No id");
      }

      def email_addresses = result.entry.contactEmail?.split(',')
      if ( email_addresses?.length > 0 ) {
        // Yes - Use the email request template to zip off a message requesting access
        log.debug("Email record owners (${email_addresses}) for permission...");
        def pending_perm_status_emailed_owner = RefdataCategory.lookupOrCreate("PendingPermStatus", "EmailedOwner" )
        def request_tracker = new PendingPermissionRequest(
                                                           dirent:result.entry,
                                                           whoRequested:springSecurityService.currentUser,
                                                           dateRequested:new Date(),
                                                           status:pending_perm_status_emailed_owner,
                                                           actionedBy:null,
                                                           dateActioned:null,
                                                           givenName:params.name,
                                                           givenEmail:params.email,
                                                           message:params.reason,
                                                           guid:java.util.UUID.randomUUID().toString() )
        request_tracker.save(failOnError:true, flush:true);
        emailRecordOwnersForPermission(result.entry, springSecurityService.currentUser, params.id, email_addresses, tracker)
        log.debug("Tracker saved");
      }
      else {
        // no way of automatically verifying that this user has permission to maintain this record
        // Flag up  request to admin interface.
        log.debug("Request permission - ask admin when no user available...");
        def pending_perm_status_with_admin = RefdataCategory.lookupOrCreate("PendingPermStatus", "WithAdmin" )
        def request_tracker = new PendingPermissionRequest(
                                                           dirent:result.entry,
                                                           whoRequested:springSecurityService.currentUser,
                                                           dateRequested:new Date(),
                                                           status:pending_perm_status_with_admin,
                                                           actionedBy:null,
                                                           dateActioned:null,
                                                           givenName:params.name,
                                                           givenEmail:params.email,
                                                           message:params.reason,
                                                           guid:java.util.UUID.randomUUID().toString() )
        request_tracker.save(failOnError:true, flush:true);
        log.debug("Tracker saved");
      }
    }
    finally {
      log.debug("Completed process request access form");
    }

    result
  }

  def private emailRecordOwnersForPermission(dirent, requester, shortcode, email_addresses, tracker) {
    log.debug("emailRecordOwnersForPermission()");
    try {
      def config = grailsApplication.config
      email_addresses.each { emailaddr ->
        if ( emailaddr?.trim().length() > 0 ) {
          println("email config: ${config}");
          // println("email config.baseURL: ${config.baseURL}");
          mailService.sendMail {     
            to "ianibbo@gmail.com"
            subject "Request permission to edit \"${dirent?.title}\" from \"${requester?.email}\""
            html view: "/emails/requestPermission", model: [requester: requester, entry: dirent, shortcode:shortcode, config:config, toaddr:emailaddr.trim(), tracker]
          }
        }
        else {
        }
      }
    }
    catch ( Exception e ) {
      log.error("Pronlem sending email",e);
    }
    finally {
      log.debug("done emailRecordOwnersForPermission()");
    }

   
  }

}
