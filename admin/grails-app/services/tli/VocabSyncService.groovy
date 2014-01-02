package tli

import tli.*

/**
 * VocabSyncService
 * A service class encapsulates the core business logic of a Grails application
 */
class VocabSyncService {

  def shortcodeService
  def sessionFactory
  def propertyInstanceMap = org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP


  static transactional = true

  def update(code, baseUrl, path) {

    log.debug("Get vocab ${code} ${baseUrl} ${path}");

    def vocab = null


    try {
      log.debug("Loading...${baseUrl}");

      withHttp(uri: baseUrl) {
        vocab = get(path:path)
      }

      def bt_rel = RefdataCategory.lookupOrCreate('thes_relations','Broader Term')
      def rel_rel = RefdataCategory.lookupOrCreate('thes_relations','Related Term')

      if ( vocab != null ) {
        log.debug("Got vocab Title: ${vocab.Metadata.Title.text()}");

        def voc_id = shortcodeService.generate('tli.RefdataCategory','code',vocab.Metadata.Title.text());

        log.debug("Using ${voc_id} as vocab ID");

        // Step 1 - Do we have a vocab already for code?
        def voc = RefdataCategory.findByCode(voc_id) ?: new RefdataCategory(code:voc_id, desc:vocab.Metadata.Title.text()).save(flush:true)
        def voc_pk = voc.id;
  
        log.debug("Processing items.... voc is ${voc}");
        vocab.Item.each { item ->
          def term_id = item.'@Id'.text()
          log.debug("Test item id:\"${term_id}\" Name:\"${item.Name.text()}\"");
  
          def existing_term = RefdataValue.findByOwnerAndTermId(voc, term_id)
          if ( existing_term == null ) {
            log.debug("Unable to locate term for voc ${voc.id}, term ${term_id}");
            existing_term = new RefdataValue(owner:voc, value:item.Name.text(), termId:term_id)
            if ( existing_term.save(flush:true) ) {
            }
            else {
              log.error(existing_term.errors)
            }
          }
          else {
            log.debug("Existing term located");
          }
        }

        // Process relations
        vocab.Item.each { item ->
          voc = RefdataCategory.get(voc_pk)
          def rel_term_id = item.'@Id'.text()
          def existing_term = RefdataValue.findByOwnerAndTermId(voc, rel_term_id)

          item.BroaderItem.each { bi ->
            def related_term = RefdataValue.findByOwnerAndTermId(voc, bi.'@Id'.text())
            if ( related_term != null ) {
              def rel = RefdataRelation.findByFromValueAndToValueAndRelationType(existing_term, related_term, bt_rel)
              if ( rel == null ) {
                log.debug("Create new BT rel from ${rel_term_id} to ${bi.'@Id'.text()}");
                rel = new RefdataRelation(fromValue:existing_term, toValue:related_term, relationType:bt_rel).save(flush:true);
              }
            }
          }

          item.RelatedItem.each { bi ->
            item.RelatedItem.each { ri ->
              def related_term = RefdataValue.findByOwnerAndTermId(voc, ri.'@Id'.text())
              if ( related_term != null ) {
                def rel = RefdataRelation.findByFromValueAndToValueAndRelationType(existing_term, related_term, rel_rel)
                if ( rel == null ) {
                  log.debug("Create new RT rel from ${rel_term_id} to ${ri.'@Id'.text()}");
                  rel = new RefdataRelation(fromValue:existing_term, toValue:related_term, relationType:rel_rel).save(flush:true);
                }
              }
            }
          }
          cleanUpGorm();
        }

      }
    }
    catch ( Throwable e ) {
      log.error("Problem",e);
    }
    finally {
      log.debug("All done");
    }
  }

  def cleanUpGorm() {
    log.debug("Clean up GORM");
    def session = sessionFactory.currentSession
    session.flush()
    session.clear()
    propertyInstanceMap.get().clear()
  }

}
