<html>
   <head>
      <meta name="layout" content="searchmain"/>
      <r:require modules="bootstrap"/>
      <meta name="description" content="Use localchatter to search for community improved information from trusted local sources. You will information collected from local authorities and other trusted sources, imrpved and refined by the community"/>

      <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>

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
        margin-top:50px;
      }
      .mc { 
        height: 100%;
      }
    </style>

  <script>

    var map;


    var locations = [
      <g:each in="${hits}" var="h">
        <g:each in="${h.source.sessions}" var="s">
          [ '${h.source.title}', ${s.loc.lat}, ${s.loc.lon}  ],
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

      for (i = 0; i < locations.length; i++) {  
        console.log("marker");
        marker = new google.maps.Marker({ position: new google.maps.LatLng(locations[i][1], locations[i][2]), map: map });
  
        //extend the bounds to include each markers position
        bounds.extend(marker.position);
  
        google.maps.event.addListener(marker, 'click', (function(marker, i) {
          return function() {
            infowindow.setContent(locations[i][0]);
            infowindow.open(map, marker);
          }
        })(marker, i));
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
                <div>
                  ${facet.key}
                  <ul>
                    <g:each in="${facet.value}" var="v">
                      <li>
                        <g:set var="fname" value="facet:${facet.key+':'+v.term}"/>
                        <g:link controller="home" action="index" params="${params+[fname:'Y']}">${v.display}</g:link> (${v.count})
                        <g:if test="${params[fname]=='Y'}">Tick</g:if>
                      </li>
                    </g:each>
                  </ul>
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
            <g:each in="${hits}" var="res">
              <li class="media">
                <div class="media-body"> 
                  <strong><g:link controller="entry" id="${res.source._id}">${res.source.title}</g:link></strong>
                </div>
              </li>
            </g:each>
          </ul>
        </div>
      </div>
    </div>
  </body>


</html>
