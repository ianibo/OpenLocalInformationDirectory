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

  AuthCommonParty actionedBy
  Date dateActioned

  static constraints = {
  }

  static mapping = {
    message type:'text'
  }

}
