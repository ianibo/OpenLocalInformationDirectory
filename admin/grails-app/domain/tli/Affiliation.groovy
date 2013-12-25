package tli

import tli.*;

class Affiliation {

  TliUser user
  TliOrg org
  int status=0  // 0=Pending, 1=Approved, 2=Rejected
  int role=0

  static constraints = {
  }
}
