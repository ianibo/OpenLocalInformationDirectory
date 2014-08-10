package tli

import me.ianibbo.common.*


public class PendingPermissionRequest {

  DirectoryEntry dirent
  AuthCommonParty whoRequested
  Date dateRequested
  RefdataValue status
  String givenName
  String givenEmail
  String message
  String guid

  AuthCommonParty actionedBy
  Date dateActioned

  static constraints = {
    dirent(nullable:false, blank:false)
    whoRequested(nullable:false, blank:false)
    dateRequested(nullable:false, blank:false)
    status(nullable:false, blank:false)
    givenName(nullable:true, blank:true)
    givenEmail(nullable:true, blank:true)
    message(nullable:true, blank:true)
    actionedBy(nullable:true, blank:true)
    dateActioned(nullable:true, blank:true)
    guid(nullable:false, blank:false)
  }
 

  static mapping = {
    message type:'text'
  }

}
