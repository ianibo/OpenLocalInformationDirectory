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

    if ( json.id  == null ) {
      log.debug("No source reference... reconcile/update will not be possible");
    }


    def db_record = DirectoryEntry.find('from DirectoryEntry as d where exists ( select c from d.collections as c where c = ? ) and d.sourceReference = ?',[collection, json.id])

    if ( db_record == null ) {
      def entry_status_current = RefdataCategory.lookupOrCreate("entrystatus", "Current" )
      db_record = new DirectoryEntry(status:entry_status_current)
      db_record.collections=[]
      db_record.subjects=[]
      db_record.categories=[]
      db_record.sessions=[]
    }

    db_record.title = gettxt(json.title)
    db_record.sourceReference = json.id
    db_record.description = gettxt(json.description)
    db_record.url = gettxt(json.url)
    db_record.collections.add(collection)
    db_record.registeredCharityNo = gettxt(json."Charity Number")
    db_record.contactName = gettxt(json."contact")
    db_record.contactEmail =  gettxt(json."email")
    db_record.contactTelephone =  gettxt(json."telephone")
    db_record.contactFax =  gettxt(json."fax")
    db_record.facebook =  gettxt(json."facebook")
    db_record.twitter =  gettxt(json."twitter")


    db_record.defaultLocation = processAddressElements(json)
  
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
      log.debug("Processing activity details: ${ad}");
      def location = processAddressElements(ad);
      if ( location != null ) {
        def new_session = new TliSession(owner:db_record, 
                                         name:db_record.title, 
                                         location:location, 
                                         trrule:ad.daysAndTimes)
        if ( new_session.validate() ) {
          db_record.sessions.add(new_session);
        }
        else {
          log.error(new_session.errors);
        }
      }
    }

    if ( ( db_record.sessions.size() == 0 ) && ( db_record.defaultLocation != null ) ) {
      def new_session = new TliSession(owner:db_record, name:db_record.title, location:db_record.defaultLocation)
      if ( new_session.validate() ) {
        db_record.sessions.add(new_session);
      }
      else {
        log.error(new_session.errors);
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
      log.error("Problem:${db_record.errors}");
    }
  }

  private def gettxt(i) {
    def result = null
    if ( ( i != null ) && ( i instanceof java.util.Collection ) ) {
      result = i.join(' ')
    }

    if ( result?.trim()?.length() == 0 ) {
      result = null
    }
    result
  }

  def processAddressElements(owner) {
    def location = null;

    def region = owner.address.size() > 0 ? owner.address[owner.address.size()-1].toString() : null
    def town = owner.address.size() > 1 ? owner.address[owner.address.size()-2].toString() : null
    def street = owner.address.size() > 2 ? owner.address[owner.address.size()-3].toString() : null

    def buildingname = owner.address[0].toString()
    def postcode = null;
    if ((owner.postcode != null )&&(owner.postcode.size() > 0)) {
      postcode = owner.postcode[0]
    }

    location = TliLocation.lookupOrCreate(buildingname,
                                          street,
                                          town,
                                          region,
                                          postcode,
                                          owner.location?.lat,
                                          owner.location?.lon,
                                          newGazetteerService);
    
    return location
  }
}
