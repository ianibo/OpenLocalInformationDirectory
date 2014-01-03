package tli

import tli.*;

class DirectoryEntry {

  String title
  String description
  String url
  Set subjects
  Set sessions
  Set collections

  static hasMany = [ subjects:RefdataValue, sessions:TliSession, collections:TliCollection ]
  static mappedBy = [sessions:'owner' ]

  static mapping = {
    description type:'text'
    subjects joinTable:[name:'directory_entry_subjects',key:'dirent_id',column:'term_id']
    collections joinTable:[name:'directory_entry_collections',key:'dirent_id',column:'coll_id']
  }

  static constraints = {
    title(nullable:false, blank:false)
    description(nullable:true, blank:true)
    url(nullable:true, blank:true)
  }
}

