package tli

import tli.*;

class DirectoryEntry {

  String title
  String description
  String url

  static mapping = {
    description type:'text'
  }

  static constraints = {
    title(nullable:false, blank:false)
    description(nullable:true, blank:true)
    url(nullable:true, blank:true)
  }
}

