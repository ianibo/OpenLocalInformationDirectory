package tli

import tli.*;

class TliLocation {

  String description
  String locType

  static constraints = {
    description(nullable:false, blank:false)
    locType(nullable:false, blank:false)
  }
}

