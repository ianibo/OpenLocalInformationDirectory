package tli

import tli.*;

class DirectoryEntry {

  String title
  String description
  String url
  Set subjects

  static hasMany = [ subjects:RefdataValue ]

  static mapping = {
    description type:'text'
    subject joinTable:[name:'directory_entry_subjects',key:'dirent_id',column:'term_id']
  }

  static constraints = {
    title(nullable:false, blank:false)
    description(nullable:true, blank:true)
    url(nullable:true, blank:true)
  }
}

