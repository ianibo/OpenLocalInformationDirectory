package admin

import tli.*
import org.hibernate.ScrollMode
import java.nio.charset.Charset
import java.util.GregorianCalendar

class PushService {

  static transactional = false

  def executorService
  def ESWrapperService
  def mongoService
  def sessionFactory
  def enrichmentService
  def propertyInstanceMap = org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP

  def updateFTIndexes() {
    log.debug("updateFTIndexes");
    def future = executorService.submit({
      enrichmentService.fillInShortCodes()
      doFTUpdate()
    } as java.util.concurrent.Callable)
    log.debug("updateFTIndexes returning");
  }

  def doFTUpdate() {
    log.debug("doFTUpdate");
    log.debug("Execute IndexUpdateJob starting at ${new Date()}");
    def start_time = System.currentTimeMillis();

    org.elasticsearch.groovy.node.GNode esnode = ESWrapperService.getNode()
    org.elasticsearch.groovy.client.GClient esclient = esnode.getClient()

    log.debug("Call updateES with de closure");

    updateES(esclient, tli.DirectoryEntry.class) { de ->
      def result = [:]
      println(de.title)
      result._id = "tli.DirectoryEntry:"+de.id
      result.dbid = de.id
      result.uid = de.uid
      result.title = de.title
      result.description = de.description
      result.url = de.url
      result.contactName = de.contactName
      result.contactEmail = de.contactEmail
      result.contactTelephone = de.contactTelephone
      result.contactFax = de.contactFax
      result.facebook = de.facebook
      result.twitter = de.twitter
      result.shortcodes=[]
      de.shortcodes.each { sc ->
        result.shortcodes.add([shortcode:sc.shortcode, canonical:sc.canonical]);
        if ( sc.canonical ) {
          result.canonical_shortcode = sc.shortcode
        }
      }
      result.sessions=[]
      de.sessions?.each { sess ->
        result.sessions.add(['sesid':"${sess.id}",
                             'name':sess.name,
                             'startTime':sess.startTime,
                             'endTime':sess.endTime,
                             'rrule':sess.rrule,
                             'trrule':sess.trrule,
                             'description':sess.description,
                             'location':[
                               'buildingName':sess.location?.buildingName,
                               'buildingNumber':sess.location?.buildingNumber,
                               'street':sess.location?.street,
                               'city':sess.location?.city,
                               'postcode':sess.location?.postcode,
                               'region':sess.location?.region,
                               'country':sess.location?.country
                             ],
                             'loc':[lat:sess.location.lat, lon:sess.location.lon]
                            ]);
      }
      result.subjects=[]
      de.subjects?.each { subj ->
        result.subjects.add(['subjid':subj.value,'subjname':subj.description]);
      }
      result.categories=[]
      de.categories?.each { cat ->
        result.categories.add(['catid':cat.value,'catname':cat.description]);
      }
      result.collections=[]
      de.collections?.each { coll ->
        result.collections.add(['collid':coll.shortcode,'collname':coll.name]);
      }
      result
    }

    def elapsed = System.currentTimeMillis() - start_time;
    log.debug("IndexUpdateJob completed in ${elapsed}ms at ${new Date()}");
  }

  def updateES(esclient, domain, recgen_closure) {

    def count = 0;
    try {
      log.debug("updateES - ${domain.name}");

      def latest_ft_record = FTControl.findByDomainClassNameAndActivity(domain.name,'ESIndex')

      log.debug("result of findByDomain: ${latest_ft_record}");
      if ( !latest_ft_record) {
        latest_ft_record=new FTControl(domainClassName:domain.name,activity:'ESIndex',lastTimestamp:0)
      }

      log.debug("updateES ${domain.name} since ${latest_ft_record.lastTimestamp}");
      def total = 0;
      Date from = new Date(latest_ft_record.lastTimestamp);
      // def qry = domain.findAllByLastUpdatedGreaterThan(from,[sort:'lastUpdated']);

      def c = domain.createCriteria()
      c.setReadOnly(true)
      c.setCacheable(false)
      c.setFetchSize(Integer.MIN_VALUE);

      c.buildCriteria{
          or {
            gt('lastUpdated', from)
            and {
              gt('dateCreated', from)
              isNull('lastUpdated')
            }
          }
          order("lastUpdated", "asc")
      }

      def results = c.scroll(ScrollMode.FORWARD_ONLY)
    
      log.debug("Query completed.. processing rows...");

      while (results.next()) {
        Object r = results.get(0);
        def idx_record = recgen_closure(r)

        def future = esclient.index {
          index "olid"
          type domain.name
          id idx_record['_id']
          source idx_record
        }

        latest_ft_record.lastTimestamp = r.lastUpdated?.getTime()

        count++
        total++
        if ( count > 100 ) {
          count = 0;
          log.debug("processed ${++total} records (${domain.name})");
          latest_ft_record.save(flush:true);
          cleanUpGorm();
        }
      }
      results.close();

      println("Processed ${total} records for ${domain.name}");

      // update timestamp
      latest_ft_record.save(flush:true);
    }
    catch ( Exception e ) {
      log.error("Problem with FT index",e);
    }
    finally {
      log.debug("Completed processing on ${domain.name} - saved ${count} records");
    }
  }

  def nvl(val,defval) {
    def result = defval
    if ( ( val ) && ( val.toString().trim().length() > 0 ) )
      result = val

    result
  }

  def cleanUpGorm() {
    log.debug("Clean up GORM");
    def session = sessionFactory.currentSession
    session.flush()
    session.clear()
    propertyInstanceMap.get().clear()
  }

}
