import tli.*

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import javax.servlet.http.HttpServletRequest

class BootStrap {

  def init = { servletContext ->

    try {
      // Add a custom check to see if this is an ajax request.
      HttpServletRequest.metaClass.isAjax = {
        'XMLHttpRequest' == delegate.getHeader('X-Requested-With')
      }
  
  
      // Global System Roles
      def userRole = TliRole.findByAuthority('ROLE_USER') ?: new TliRole(authority: 'ROLE_USER', roleType:'global').save(failOnError: true)
      def editorRole = TliRole.findByAuthority('ROLE_EDITOR') ?: new TliRole(authority: 'ROLE_EDITOR', roleType:'global').save(failOnError: true)
      def adminRole = TliRole.findByAuthority('ROLE_ADMIN') ?: new TliRole(authority: 'ROLE_ADMIN', roleType:'global').save(failOnError: true)
  
      log.debug("Create admin user...");
      def adminUser = TliUser.findByUsername('admin')
      if ( ! adminUser ) {
        log.error("No admin user found, create");
        adminUser = new TliUser(
                          username: 'admin',
                          password: 'admin',
                          display: 'Admin',
                          email: 'admin@localhost',
                          enabled: true).save(failOnError: true)
      }
  
      if (!adminUser.authorities.contains(adminRole)) {
        log.debug("Granting admin user admin role");
        TliUserTliRole.create adminUser, adminRole
      }
  
      if (!adminUser.authorities.contains(userRole)) {
        log.debug("Granting admin user user role");
        TliUserTliRole.create adminUser, userRole
      }
  
      log.debug("Checking cat types");
      def cattype_category = RefdataCategory.findByDesc('CategoryType') ?: new RefdataCategory(desc:'CategoryType').save();
      def refdata_type = RefdataCategory.lookupOrCreate("CategoryType", "Refdata" )
      def subject_type = RefdataCategory.lookupOrCreate("CategoryType", "Subject" )
  
      log.debug("Checking status types");
      def status_category = RefdataCategory.findByDesc('status') ?: new RefdataCategory(desc:'status',catType:refdata_type).save();
      def status_pending = RefdataCategory.lookupOrCreate("status", "Pending Approval" )
      def status_approved = RefdataCategory.lookupOrCreate("status", "Approved" )
      def status_rejected = RefdataCategory.lookupOrCreate("status", "Rejected" )

      def entry_status_category = RefdataCategory.findByDesc('entrystatus') ?: new RefdataCategory(desc:'entrystatus',catType:refdata_type).save();
      def entry_status_pending = RefdataCategory.lookupOrCreate("entrystatus", "Pending" )
      def entry_status_current = RefdataCategory.lookupOrCreate("entrystatus", "Current" )
      def entry_status_deleted = RefdataCategory.lookupOrCreate("entrystatus", "Deleted" )

  
      log.debug("Checking affiliation types");
      def affiliation_category = RefdataCategory.findByDesc('affiliation') ?: new RefdataCategory(desc:'affiliation',catType:refdata_type).save();
      def affiliation_ro = RefdataCategory.lookupOrCreate("affiliation", "Read Only User" )
      def affiliation_su = RefdataCategory.lookupOrCreate("affiliation", "Standard User" )
      def affiliation_ad = RefdataCategory.lookupOrCreate("affiliation", "Administrator" )

      RefdataCategory.lookupOrCreate('YN', 'Yes').save()
      RefdataCategory.lookupOrCreate('YN', 'No').save()
    }
    catch ( Exception e ) {
      log.error("Unhandled exception in bootstrap",e);
    }
    finally {
      log.debug("Bootstrap completed");
    }
  }

  def destroy = {
  }
}
