<html>
<%
  def addFacet = { params, facet, val ->
    // ${params+[('fct_'+facet.key):(params.list('fct_'+facet.key).add(v.term))]}
    def newparams = [:]
    newparams.putAll(params)
    def current = newparams[facet]
    if ( current == null ) {
      newparams[facet] = val
    }
    else if ( current instanceof List ) {
      newparams[facet].add(val);
    }
    else {
      newparams[facet] = [ current, val ]
    }
    newparams
  }
%>

   <head>

      <meta name="layout" content="searchmain"/>
      <!-- Ask search engines not to index the search results pages, it looks horrible in google -->
      <meta name="robots" CONTENT="noindex, follow">

    <style>
      html, body, #mapcanvas {
        height: 100%;
        margin: 0px;
        padding: 0px
      }
      #mapcanvas {
      }
      .pt { 
      }
      .mc { 
        height: 100%;
      }
      .searchheader {
        margin-top:50px;
      }
    </style>

  <script>

    var map;

    var locations = [
      <g:each in="${hits}" var="h">
        <g:each in="${h.source.sessions}" var="s">
          [ '${h.source.title}', ${s.loc.lat}, ${s.loc.lon}, '${h.source._id}','${h.source.canonical_shortcode}'  ],
        </g:each>
      </g:each>
    ];

    function initialize() {
      var mapOptions = {
        zoom: 8,
      };

      map = new google.maps.Map(document.getElementById('mapcanvas'), mapOptions);

      var infowindow = new google.maps.InfoWindow();

      var bounds = new google.maps.LatLngBounds();

      var oms = new OverlappingMarkerSpiderfier(map, {keepSpiderfied: true, markersWontHide:true, markersWontMove:true, circleSpiralSwitchover :0});

      var iw = new google.maps.InfoWindow();
      oms.addListener('click', function(marker, event) {
        iw.setContent(marker.desc);
        iw.open(map, marker);
      });
      oms.addListener('spiderfy', function(markers) {
        iw.close();
      });

      var next_label = 'A'
      for (i = 0; i < locations.length; i++) {  
        if ( locations[i][1] != null ) {
          marker = new google.maps.Marker({ position: new google.maps.LatLng(locations[i][1], locations[i][2]), 
                                            icon: "http://maps.google.com/mapfiles/marker" + next_label + ".png",
                                            map: map });
  
          //extend the bounds to include each markers position
          bounds.extend(marker.position);
  
          google.maps.event.addListener(marker, 'click', (function(marker, i) {
            return function() {
              $.ajax({
                url: '<g:createLink controller="entry" action="popup"/>/' + locations[i][4],
                success: function(data){
                  infowindow.setContent(data);
                }
              });
              infowindow.open(map, marker);
            }
          })(marker, i));

          next_label = String.fromCharCode(next_label.charCodeAt(0) + 1);

          oms.addMarker(marker);
        }
      }

      map.fitBounds(bounds);
    }

    google.maps.event.addDomListener(window, "load", initialize);
  </script>

   </head>


  <body>

  <div class="content mc pt">
    <div class="container-fluid fill mc">
      <div class="row-fluid mc">
        <div class="col-lg-2">


              <g:each in="${facets}" var="facet">


                <div class="panel panel-default">
                  <div class="panel-heading">
                    <h3 class="panel-title">${facet.key}</h3>
                  </div>
                  <div class="panel-body">
                    <ul>
                      <g:each in="${facet.value}" var="v">
                        <li>
                          <g:set var="fname" value="facet:${facet.key+':'+v.term}"/>
                          <g:link controller="home" action="index" params="${addFacet(params,facet.key,v.term)}">${v.display}</g:link> (${v.count})
                        </li>
                      </g:each>
                    </ul>
                  </div>
                </div>
              </g:each>

        </div>
        <div class="col-lg-8 mc">
          <div id="mapcanvas"></div>
        </div>
        <div class="col-lg-2 mc">
          <div style="text-align:center;">
            <g:if test="${hits?.totalHits}">Results ${params.offset+1} to ${params.lastrec} of ${hits?.totalHits}</g:if>

            <small>
              <g:paginate action="index" 
                          controller="home" 
                          params="${params}" 
                          next="&gt;" 
                          prev="&lt;"
                          maxsteps="1" 
                          total="${hits.totalHits}" 
                          class="pagination-right"
                          omitFirst="true" omitLast="true" />
            </small>
          </div>

          <ul class="media-list">
            <g:set var="lab" value="A"/>
            <g:each in="${hits}" var="res">
              <li class="media">
                <div class="media-body">
                  <g:if test="${res.source.sessions.size() > 0}">
                    <img src="http://maps.google.com/mapfiles/marker${lab++}.png"/>
                    <strong><g:link controller="entry" id="${res.source.canonical_shortcode}">${res.source.title}</g:link></strong>
                  </g:if>
                  <g:else>
                    <strong><g:link controller="entry" id="${res.source.canonical_shortcode}">${res.source.title}</g:link></strong>
                  </g:else>
                </div>
              </li>
            </g:each>
          </ul>
        </div>
      </div>
    </div>
  </body>


</html>
