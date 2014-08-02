package tli

import me.ianibbo.common.*


public class PendingPermissionRequest {

  DirectoryEntry dirent
  AuthCommonParty whoRequested
  Date dateRequested
  RefdataValue status

  AuthCommonParty actionedBy
  Date dateActioned

  static constraints = {
  }
}
