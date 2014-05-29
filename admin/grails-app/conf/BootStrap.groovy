import tli.*

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import javax.servlet.http.HttpServletRequest
import me.ianibbo.common.*

class BootStrap {

  def grailsApplication

  def init = { servletContext ->

    try {
      // Add a custom check to see if this is an ajax request.
      HttpServletRequest.metaClass.isAjax = {
        'XMLHttpRequest' == delegate.getHeader('X-Requested-With')
      }
  
  
      // Global System Roles
      def userRole = AuthCommonRole.findByAuthority('ROLE_USER') ?: new AuthCommonRole(authority: 'ROLE_USER', roleType:'global').save(failOnError: true)
      def editorRole = AuthCommonRole.findByAuthority('ROLE_EDITOR') ?: new AuthCommonRole(authority: 'ROLE_EDITOR', roleType:'global').save(failOnError: true)
      def adminRole = AuthCommonRole.findByAuthority('ROLE_ADMIN') ?: new AuthCommonRole(authority: 'ROLE_ADMIN', roleType:'global').save(failOnError: true)
  
      grailsApplication.config.sysusers.each { su ->
        log.debug("test ${su.name} ${su.pass} ${su.display} ${su.roles}");
        def user = AuthCommonUser.findByUsername(su.name)
        if ( user ) {
          if ( user.password != su.pass ) {
            log.debug("Hard change of user password from config ${user.password} -> ${su.pass}");
            user.password = su.pass;
            user.save(failOnError: true)
          }
          else {
            log.debug("${su.name} present and correct");
          }
        }
        else {
          log.debug("Create user...");
          user = new AuthCommonUser(
                        username: su.name,
                        password: su.pass,
                        display: su.display,
                        email: su.email,
                        enabled: true).save(failOnError: true)
        }

        log.debug("Add roles for ${su.name}");
        su.roles.each { r ->
          def role = AuthCommonRole.findByAuthority(r)
          if ( ! ( user.authorities.contains(role) ) ) {
            log.debug("  -> adding role ${role}");
            AuthCommonUserAuthCommonRole.create user, role
          }
          else {
            log.debug("  -> ${role} already present");
          }
        }
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

      log.debug("Checking resource types");
      def entry_type = RefdataCategory.findByDesc('EntryType') ?: new RefdataCategory(desc:'EntryType').save();
      def service_entry_type = RefdataCategory.lookupOrCreate("EntryType", "Service" )
      def poi_entry_type = RefdataCategory.lookupOrCreate("EntryType", "POI" )
  
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
