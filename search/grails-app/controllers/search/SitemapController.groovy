package search

import grails.converters.*
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.commons.ApplicationHolder


class SitemapController {

  def elasticSearchService


  def index() { 


    def writer = new StringWriter()
    // Get max id from entry

    try {
      org.elasticsearch.groovy.node.GNode esnode = elasticSearchService.getESNode()
      org.elasticsearch.groovy.client.GClient esclient = esnode.getClient()

      def search = esclient.search{
        indices "olid"
        types "tli.DirectoryEntry"
        source {
          query {
            query_string (query: "*")
          }
          aggs {
            max_dbid = [
              "max" : [ 'field' : 'dbid' ]
            ]
          }
        }
      }

      log.debug(search.response);
      def max = search.response.aggregations.max_dbid.value
      log.debug("Max: ${max}");


      def xml = new MarkupBuilder(writer)

      xml.sitemapindex('xmlns:xsd':'http://www.w3.org/2001/XMLSchema',
                       'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance',
                       'xsi:schemaLocation':'http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd',
                       'xmlns':'http://www.sitemaps.org/schemas/sitemap/0.9',
                       'targetNamespace':'http://www.sitemaps.org/schemas/sitemap/0.9') {
        result.providers.each { prov ->
          sitemap() {
            loc("${ApplicationHolder.application.config.frontend}/sitemap/postalArea/${prov.name}")
            lastmod( prov.lastModified != null ? prov.lastModified : default_date );
            mkp.comment("Doc count for this authority: ${prov.count}")
          }
        }
      }
    }
    catch ( Exception e ) {
      log.error("Problem",e);
    }

    println "Render...siteindex"

    render(contentType:'application/xml', text: writer.toString())
  }
}
