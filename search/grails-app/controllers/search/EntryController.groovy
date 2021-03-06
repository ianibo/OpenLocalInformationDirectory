package search

class EntryController {

 def newGazetteerService
  def elasticSearchService

  def index() {
    def result = [:]
    if ( params.id ) {

      log.debug("lookup ${params.id}");
      org.elasticsearch.groovy.node.GNode esnode = elasticSearchService.getESNode()
      org.elasticsearch.groovy.client.GClient esclient = esnode.getClient()

      def search = esclient.search {
        indices "olid"
        types "tli.DirectoryEntry"
        source {
          query {
            term('canonical_shortcode': params.id.toString())
          }
        }
      }

      log.debug("Search returned ${search.response.hits.totalHits}")


      if ( search.response.hits.totalHits == 1 ) {
        log.debug("Setting result.record...");
        result.record = search.response.hits.getAt(0)
        log.debug("Render: result.record.source:${result.record.source}");
      }
      else {
        redirect(controller:'home')
      }
    }
    result
  }

  def popup() {
    log.debug(params);
    def result = [:]
    if ( params.id ) {
      org.elasticsearch.groovy.node.GNode esnode = elasticSearchService.getESNode()
      org.elasticsearch.groovy.client.GClient esclient = esnode.getClient()
      def search = esclient.search {
        indices "olid"
        types "tli.DirectoryEntry"
        source {
          query {
            term('canonical_shortcode': params.id.toString())
          }
        }
      }
      if ( search.response.hits.totalHits == 1 ) {
        log.debug("Setting result.record...");
        result.record = search.response.hits.getAt(0)
      }

    }
    else {
    }
    result
  }

  def edit() {
    def result = [:]
    result
  }

}
