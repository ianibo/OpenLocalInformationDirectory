package tli

import tli.*;
import javax.persistence.Transient

class DirectoryEntry {

  String title
  String sourceReference
  String description
  String url
  Set subjects
  Set sessions
  Set collections
  Set categories
  String registeredCharityNo
  RefdataValue status
  TliLocation defaultLocation
  String contactName
  String contactEmail
  String contactTelephone
  String contactFax
  String facebook
  String twitter

  static hasMany = [ subjects:RefdataValue, sessions:TliSession, collections:TliCollection, categories:RefdataValue, shortcodes:DirectoryEntryShortcode ]
  static mappedBy = [sessions:'owner', shortcodes:'dirent' ]

  // Timestamps
  Date dateCreated
  Date lastUpdated

  // Typical Price?
  // Age Range?
  // Experience?

  static mapping = {
    description type:'text'
    subjects joinTable:[name:'directory_entry_subjects',key:'dirent_id',column:'term_id']
    collections joinTable:[name:'directory_entry_collections',key:'dirent_id',column:'coll_id']
    categories joinTable:[name:'directory_entry_cats',key:'dirent_id',column:'term_id']
  }

  static constraints = {
    title(nullable:false, blank:false)
    sourceReference(nullable:true, blank:true)
    description(nullable:true, blank:true)
    url(nullable:true, blank:true)
    registeredCharityNo(nullable:true, blank:true)
    status(nullable:true, blank:false)
    defaultLocation(nullable:true, blank:false)
    contactName(nullable:true, blank:false)
    contactEmail(nullable:true, blank:false)
    contactTelephone(nullable:true, blank:false)
    contactFax(nullable:true, blank:false)
    facebook(nullable:true, blank:false)
    twitter(nullable:true, blank:false)
  }

  @Transient
  static def oaiConfig = [
    id:'resources',
    lastModified:'lastUpdated',
    textDescription:'TLI Resources',
    schemas:[
      'oai_dc':[type:'method',methodName:'toOaiDcXml'],
      'tli':[type:'method',methodName:'toTliXml'],
    ],
    // query:" from DirectoryEntry as o where o.status.value != 'Deleted'"
    query:" from DirectoryEntry as o where o.status.value != 'Deleted'"
  ]

  /**
   *  Render this package as OAI_dc
   */
  @Transient
  def toOaiDcXml(builder) {
    builder.'oai_dc:dc'('xmlns:oai_dc':'http://www.openarchives.org/OAI/2.0/oai_dc/',
                    'xmlns:dc':'http://purl.org/dc/elements/1.1/',
                    'xsi:schemaLocation':'http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd')
    {
      'dc:title'(title)
    }
  }


  @Transient
  def afterInsert() {
    DirectoryEntryShortcode.generateShortcode(this, this.title, true);
  }

}

