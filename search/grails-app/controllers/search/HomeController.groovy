package search

class HomeController {

  def newGazetteerService
  def elasticSearchService

  // Map the parameter names we use in the webapp with the ES fields
  def reversemap = ['subject':'subjectKw',
                    'title':'title',
                    'description':'description',
                    'provider':'provider' ]

  def non_analyzed_fields = ['docid','outcode','district','ward','district_facet','ward_facet','ofstedUrn','childcareType','postcode']

  def portal_configs = [
    'sfn':[
      coll_id:'sfn',
      default_view:'mapall'
    ],
    'hys':[
      coll_id:'sheffield_help_yourself',
      default_view:'mapall'
    ]
  ]


  def index() { 
    log.debug("index ${params}");
    def result = [:]

    def portal_config = null;
    if ( params.portal != null ) {
      result.portal_config = portal_configs[params.portal]
    }

    def dunit = params.dunit ?: 'miles'
    result.dunit=dunit

    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    params.offset = params.offset ? params.int('offset') : 0
    params.distance = params.distance ? params.int('distance') : 5

    if ( params.mapAllButton=='true' ) {
      params.offset=0
      params.max=1000
    }

    if ( params.postcode || params.q ) {

      def geo = false;
      def g_lat = null;
      def g_lon = null;

      if ( params.postcode ) {
        log.debug("Geocode ${params.postcode}"); 
        def place = newGazetteerService.geocode(params.postcode.toUpperCase().trim())
        if ( place == null ) {
          flash.error = "Unable to look up postcode ${params.postcode}, please try again with a different one"
        }
        else {
          if ( ( place.response.geo.lat != null ) && ( place.response.geo.lng != null ) ) {
            g_lat = Double.parseDouble(place.response.geo.lat)
            g_lon = Double.parseDouble(place.response.geo.lng)
            geo = true;
          }
        }
        log.debug("Place:${place}");
      }

      def filters = geo

      // Get hold of some services we might use ;)
      // def db = mongoService.getMongo().getDB("localchatter")
      org.elasticsearch.groovy.node.GNode esnode = elasticSearchService.getESNode()
      org.elasticsearch.groovy.client.GClient esclient = esnode.getClient()

      def query_str = buildQuery(params, result.portal_config)
      log.debug("query: ${query_str}");

      try {
       def search = esclient.search{
          indices "olid"
          types "tli.DirectoryEntry"
          source {
            from = params.offset
            size = params.max
            if ( geo == true ) {
                sort = [
                  '_geo_distance' : [
                    'sessions.loc' : [
                      'lat':g_lat,
                      'lon':g_lon
                    ],
                    'order' : 'asc',
                    'unit' : dunit
                  ]
                ]
            }
            if ( filters == true ) {
              query {
                filtered {
                  query {
                    query_string (query: query_str)
                  }
                  filter {
                    geo_distance {
                      distance = "${params.distance}${dunit}"
                      'sessions.loc' {
                        lat=g_lat
                        lon=g_lon
                      }
                    }
                  }
                }
              }
            }
            else {
              query {
                query_string (query: query_str)
              }
            }
            facets {
              subjects {
                terms {
                  field = 'subjects.subjname'
                }
              }
              categories {
                terms {
                  field = 'categories.catid'
                }
              }
              collections {
                terms {
                  field = 'collections.collname'
                }
              }
              // ward {
              //     terms {
              //          field = 'ward_facet'
              //     }
              // }
            }
          }
        }

        // log.debug("Assign result.hits... ${search.response.hits}");

        result.hits = search.response.hits
        result.resultsTotal = search.response.hits.totalHits

        result.lastrec = params.offset + params.max
        if ( result.lastrec > result.resultsTotal )
          result.lastrec = result.resultsTotal

        if(search.response.hits.maxScore == Float.NaN) {
            search.response.hits.maxScore = 0;
        }

        if ( search.response.facets != null ) {
          result.facets = [:]
          search.response.facets.facets.each { facet ->
            log.debug("Facet: ${facet.key}");
            def facet_values = []
            facet.value.entries.each { fe ->
              if ( fe.term != null ) {
                log.debug('adding to '+ facet.key + ': ' + fe.term + ' (' + fe.count + ' )')
                facet_values.add([term: fe.term,display:fe.term,count:"${fe?.count}"])
              }
            }

            result.facets[facet.key] = facet_values
          }
        }

        if ( params.mapSearchButton=='true' ) {
          render(view:'mapresults',model:result);
        }
        else if ( params.mapAllButton=='true' ) {
          render(view:'mapall',model:result);
        }
        else if ( params.listSearchButton=='true' ) {
          render(view:'results',model:result);
        }
        else if ( result.portal_config != null ) {
          render(view:result.portal_config.default_view,model:result)
        }
        else {
          render(view:'results',model:result);
        }
      }
      catch ( Exception e ) {
        log.error("Problem",e);
      }
    }
    else {
      render(view:'index');
    }
  }


  def buildQuery(params, portal_config) {
    log.debug("BuildQuery...");

    StringWriter sw = new StringWriter()

    if ( portal_config != null ) {
      sw.write("collections.collid:'${portal_config.coll_id}' AND ");
    }

    if ( ( params != null ) && ( params.q != null ) && ( params.q.trim().length() > 0 ) ) {
      if(params.q.equals("*")){
        sw.write(params.q)
      }
      else{
        sw.write("(${params.q})") // add ~ for fuzzy
      }
    }
    else
      sw.write("*:*")

    //ensure search is always on public
    // sw.write(" AND recstatus:\"public\"")

    // For each reverse mapping
    reversemap.each { mapping ->

      // log.debug("testing ${mapping.key}");

      // If the query string supplies a value for that mapped parameter
      if ( params[mapping.key] != null ) {

        // If we have a list of values, rather than a scalar
        if ( params[mapping.key].class == java.util.ArrayList) {
          params[mapping.key].each { p ->
                sw.write(" AND ")
                sw.write(mapping.value)
                sw.write(":")

                if(non_analyzed_fields.contains(mapping.value))
                {
                    sw.write("${p}")
                }
                else
                {
                    sw.write("\"${p}\"")
                }
          }
        }
        else {
          // We are dealing with a single value, this is "a good thing" (TM)
          // Only add the param if it's length is > 0 or we end up with really ugly URLs
          // II : Changed to only do this if the value is NOT an *
          if ( params[mapping.key].length() > 0 && ! ( params[mapping.key].equalsIgnoreCase('*') ) ) {
            sw.write(" AND ")
            // Write out the mapped field name, not the name from the source
            sw.write(mapping.value)
            sw.write(":")

            // Couldn't be more wrong as it was: non_analyzed_fields.contains(params[mapping.key]) Should be checking mapped property, not source
            if(non_analyzed_fields.contains(mapping.value))
            {
                sw.write("${params[mapping.key]}") // Add ~ for fuzzy
            }
            else
            {
               sw.write("\"${params[mapping.key]}\"")
            }
          }
        }
      }
    }

    def result = sw.toString();

    result;
  }



}
