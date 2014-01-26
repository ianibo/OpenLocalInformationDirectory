package admin

import grails.transaction.Transactional
import java.security.MessageDigest
import javax.annotation.PostConstruct;
import net.sf.json.JSON
import groovy.json.JsonSlurper
import tli.*


@Transactional
class IngestService {

  def ingest(record, collection, user, contentType) {
    log.debug("ingest.... [coll=${collection}, contentType:${contentType}]");

    def coll_obj = TliCollection.findByShortcode(collection)

    if ( coll_obj != null ) {

      switch(contentType) {
        case 'text/xml':
          log.debug("Process XML");
          // uploadXML(file,authoritative,owner,user, contentType)
          break;
        case 'application/json':
          log.debug("Process JSON");
          uploadJSON(record,coll_obj,user)
          break;
        default:
          log.error("Unhandled submission type: ${contentType}");
          break;
      }
    }
  }
 

  def uploadJSON(record,collection,user) {

    def json = new JsonSlurper().parseText(record)
    log.debug("Got record with source reference ${json.id}");
    log.debug("Got record with title ${json.title}");
    log.debug("Got record with description ${json.description}");
    log.debug("Got record with url ${json.url}");
    log.debug("Got record with postcode ${json.postcode}");

    if ( json.id  != null ) {

      def db_record = DirectoryEntry.find('from DirectoryEntry as d where exists ( select c from d.collections as c where c = ? ) and d.sourceReference = ?',[collection, json.id])

      if ( db_record == null ) {
        db_record = new DirectoryEntry()
        db_record.collections=[]
      }

      db_record.title = gettxt(json.title)
      db_record.sourceReference = json.id
      db_record.description = gettxt(json.description)
      db_record.url = gettxt(json.url)
      db_record.collections.add(collection)
      db_record.registeredCharityNo = gettxt(json."Charity Number")
    
      if ( db_record.save(flush:true) ) {
      }
      else {
        log.error("Problem:${record.errors}");
      }
      // Set subjects
      // Set sessions
      // Set collections
      // String registeredCharityNo


      json.keywords.each { kw ->
        log.debug("Kw: ${kw}");
      }
    }
  }

  private def gettxt(i) {
    def result = null
    if ( ( i != null ) && ( i instanceof java.util.Collection ) ) {
      result = i.join(' ')
    }
    result
  }
}
