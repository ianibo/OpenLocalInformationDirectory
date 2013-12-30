package tli

import tli.*;

class TliCollection {

  String name
  String description
  String shortcode

  static belongsTo = [owner:TliOrg]

  static constraints = {
    name(nullable:false, blank:false)
    shortcode(nullable:false, blank:false,unique:true)
    description(nullable:true, blank:true)
    owner(nullable:false, blank:false)
  }
}

