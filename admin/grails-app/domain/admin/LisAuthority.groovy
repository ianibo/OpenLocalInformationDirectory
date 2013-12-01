package admin

import tli.*

class LisAuthority {

  String code
  String name
  String description
  TliUser owner

  static constraints = {
    description(blank:false, nullable:true);
  }
}
