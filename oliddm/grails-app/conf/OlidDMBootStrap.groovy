import grails.converters.*

import tli.*
import me.ianibbo.common.*

class OlidDMBootStrap {

  def init = { servletContext ->
    def refdata_type = RefdataCategory.lookupOrCreate("CategoryType", "Refdata" )
    def pending_perm_status = RefdataCategory.findByDesc('pendingPermReq') ?: new RefdataCategory(desc:'PendingPermStatus',catType:refdata_type).save();
    def pending_perm_status_with_admin = RefdataCategory.lookupOrCreate("PendingPermStatus", "WithAdmin" )
    def pending_perm_status_emailed_owner = RefdataCategory.lookupOrCreate("PendingPermStatus", "EmailedOwner" )
    def pending_perm_status_denied = RefdataCategory.lookupOrCreate("PendingPermStatus", "Denied" )
    def pending_perm_status_approved = RefdataCategory.lookupOrCreate("PendingPermStatus", "Approved" )
  }

  def destroy = {
  }


}
