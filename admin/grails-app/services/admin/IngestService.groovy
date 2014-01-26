package admin

import grails.transaction.Transactional

@Transactional
class IngestService {

  def ingest(record, collection, user, contentType) {
    log.debug("ingest.... [coll=${collection}]");
  }
}
