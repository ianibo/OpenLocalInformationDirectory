package tli

import tli.*;

class DirectoryEntry {

  String title
  String description

  static constraints = {
    title(nullable:false, blank:false)
    description(nullable:true, blank:true)
  }
}

