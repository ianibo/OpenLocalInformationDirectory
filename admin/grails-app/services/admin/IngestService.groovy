package admin

import grails.transaction.Transactional
import java.security.MessageDigest
import javax.annotation.PostConstruct;
import net.sf.json.JSON
import groovy.json.JsonSlurper
import tli.*


@Transactional
class IngestService {

  def newGazetteerService

  def ingest(record, collection, user, contentType) {
    log.debug("ingest.... [coll=${collection}, contentType:${contentType}]");
    def result = [:]

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
    else {
      log.error("Unknown Collection: ${collection}");
      result.status=false
      result.message("Unknown Collection: ${collection}");
    }

    result
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
        def entry_status_current = RefdataCategory.lookupOrCreate("entrystatus", "Current" )
        db_record = new DirectoryEntry(status:entry_status_current)
        db_record.collections=[]
        db_record.subjects=[]
        db_record.categories=[]
      }

      db_record.title = gettxt(json.title)
      db_record.sourceReference = json.id
      db_record.description = gettxt(json.description)
      db_record.url = gettxt(json.url)
      db_record.collections.add(collection)
      db_record.registeredCharityNo = gettxt(json."Charity Number")
    
      // Set subjects
      // Set sessions
      // Set collections
      // String registeredCharityNo

      json.keywords.each { kw ->
        // 1. See if we can match the keyword against a IPSV term, if so, use that in preference
        def kw_object = RefdataCategory.lookup("Integrated Public Sector Vocabulary", kw )

        // If not matched, see if we can match on a private local term
        if ( kw_object == null ) {
          kw_object = RefdataCategory.lookupOrCreate("${collection.shortcode}-kw", kw )
        }

        db_record.subjects.add(kw_object)
      }

      json.activityDetails.each { ad ->
        def location = null;
        if ( ad.address.size() > 3 ) {
          def region = ad.address[ad.address.size()-1]
          def town = ad.address[ad.address.size()-2]
          def street = ad.address[ad.address.size()-3]
          def buildingname = ad.address[0]
          location = TliLocation.lookupOrCreate(buildingname,street,town,region,ad.postcode,newGazetteerService);
        }
      }

      json.categories.each { cat ->
        // 1. See if we can match the keyword against a IPSV term, if so, use that in preference
        def cat_object = RefdataCategory.lookup("Integrated Public Sector Vocabulary", cat )

        // If not matched, see if we can match on a private local term
        if ( cat_object == null ) {
          cat_object = RefdataCategory.lookupOrCreate("${collection.shortcode}-cat", cat )
        }

        db_record.categories.add(cat_object)
      }


      if ( db_record.save(flush:true) ) {
      }
      else {
        log.error("Problem:${record.errors}");
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
