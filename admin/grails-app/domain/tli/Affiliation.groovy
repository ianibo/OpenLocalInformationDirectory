package tli

import tli.*;

class Affiliation {

  TliUser user
  TliOrg org
  RefdataValue status;  // 0=Pending, 1=Approved, 2=Rejected
  RefdataValue role  // 0=unspecified, 5=admin

  static constraints = {
  }
}
