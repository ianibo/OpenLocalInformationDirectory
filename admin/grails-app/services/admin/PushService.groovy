package admin

import tli.*
import org.hibernate.ScrollMode
import java.nio.charset.Charset
import java.util.GregorianCalendar

class PushService {

  def executorService
  def ESWrapperService
  def mongoService
  def sessionFactory

  def updateFTIndexes() {
    log.debug("updateFTIndexes");
    def future = executorService.submit({
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

    updateES(esclient, tli.DirectoryEntry.class) { de ->
      def result = [:]
      result._id = "tli.DirectoryEntry:"+de.id
      result.title = de.title
      result.description = de.description
      result.sessions=[]
      de.sessions?.each { sess ->
        sessions.add(['sesid':"${sess.id}"]);
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
          index "kbplus"
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
