package admin

import tli.*;

class Affiliation {

  TliUser user
  LisAuthority authority
  String role
  int status  // 0=Pending, 1=Approved, 2=Rejected

  static constraints = {
  }
}
