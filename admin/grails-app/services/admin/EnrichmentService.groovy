package admin

import tli.*
import org.hibernate.ScrollMode
import java.nio.charset.Charset
import java.util.GregorianCalendar

class EnrichmentService {

  def executorService
  def ESWrapperService
  def mongoService
  def sessionFactory
  def propertyInstanceMap = org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP

  def runEnrhchment() {
    doLocationEnrichment()
  }

  def doLocationEnrichment() {
    log.debug("Searching for postcodes to mark up");
    def locs_no_postcode = TliLocation.executeQuery("select l from TliLocation as l where l.lon is null and l.lat is null and l.postcode is not null")
    locs_no_postcode.each { l ->
      log.debug("Enrich: ${l}");
    }
  }
}
