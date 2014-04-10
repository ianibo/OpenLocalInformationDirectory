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
    fillInShortCodes()
    fillInMissingUID()
  }

  def fillInShortCodes() {
    try {
      log.debug("Filling out missing short codes...");
      def entries_without_shortcodes = DirectoryEntry.executeQuery('select e.id from tli.DirectoryEntry as e where not exists ( select s from tli.DirectoryEntryShortcode as s where s.dirent = e )');
      entries_without_shortcodes.each { e ->
        log.debug("Generate shortcode for ${e}");
        def de = DirectoryEntry.get(e);
        log.debug("Create shortcoce for ${de.id}, ${de.title}");
        DirectoryEntryShortcode.generateShortcode(de,de.title,true);
        de.refresh()

        de.shortcodes.each { sc ->
          log.debug("${sc.shortcode}");
        }
      }
    }
    catch ( Exception e ) {
      log.error("Problem: ${e}");
    }
  }

  def fillInMissingUID() {
    log.debug("Filling out missing UIDs...");
    def entries_without_uid = DirectoryEntry.executeQuery('select e.id from tli.DirectoryEntry as e where e.uid is null or e.uid=?',['']);
    entries_without_uid.each { e ->
      def de = DirectoryEntry.get(e);
      de.uid = java.util.UUID.randomUUID().toString();
      de.save();
    }
    
  }


  def doLocationEnrichment() {
    log.debug("Searching for postcodes to mark up");
    def locs_no_postcode = TliLocation.executeQuery("select l from TliLocation as l where l.lon is null and l.lat is null and l.postcode is not null")
    locs_no_postcode.each { l ->
      log.debug("Enrich: ${l}");
    }


    // result.outcode = result.postcode.substring(0,result.postcode.indexOf(' '));
    // if ( result.outcode )
    // result.postalArea = (result.outcode =~ /[A-Za-z]*/)[0]


    // if ( result.outcode && ( result.outcode.length() > 0 ) ) {
    // shortcodeService.getShortcodeFor('outcode',result.outcode,result.outcode)
    // }

    // if ( geocode.response.administrative ) {
    // if ( geocode.response.administrative?.district ) {
    // result.district = geocode.response.administrative.district.title
    // result.district_facet = "${geocode.response.administrative.district.snac}:${geocode.response.administrative.district.title}"
    // def district_shortcode = shortcodeService.getShortcodeFor('district',geocode.response.administrative.district.snac,geocode.response.administrative.district.title)
    // result.district_shortcode = district_shortcode.shortcode
    // }
    // if ( geocode.response.administrative?.ward ) {
    // result.ward = geocode.response.administrative.ward.title
    // if ( result.ward ) {
    // result.ward_facet = "${geocode.response.administrative.ward.snac}:${geocode.response.administrative.ward.title}"
    // def ward_shortcode = shortcodeService.getShortcodeFor('ward',geocode.response.administrative.ward.snac,geocode.response.administrative.ward.title)
    // result.ward_shortcode = ward_shortcode.shortcode
    // }
    // }
    // if ( geocode.response.administrative?.county ) {
    // result.county = geocode.response.administrative.county.title
    // result.county_facet = "${geocode.response.administrative.county.snac}:${geocode.response.administrative.county.title}"
    // def county_shortcode = shortcodeService.getShortcodeFor('county',geocode.response.administrative.county.snac,geocode.response.administrative.county.title)
    // result.county_shortcode = county_shortcode.shortcode
    // }

  }
}
