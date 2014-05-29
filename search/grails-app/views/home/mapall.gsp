<html>
   <head>
      <meta name="layout" content="searchmain"/>
      <r:require modules="bootstrap,spider"/>
      <meta name="description" content="Use localchatter to search for community improved information from trusted local sources. You will information collected from local authorities and other trusted sources, imrpved and refined by the community"/>

      <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
      <g:javascript library="markerclusterer"/>

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
      var markers = [];

      var oms = new OverlappingMarkerSpiderfier(map, {keepSpiderfied: true, markersWontHide:true, markersWontMove:true, circleSpiralSwitchover :0});

      var iw = new google.maps.InfoWindow();
      oms.addListener('click', function(marker, event) {
        iw.setContent(marker.desc);
        iw.open(map, marker);
      });
      oms.addListener('spiderfy', function(markers) {
        iw.close();
      });



      for (i = 0; i < locations.length; i++) {  
        if ( locations[i][1] != null ) {
          marker = new google.maps.Marker({ position: new google.maps.LatLng(locations[i][1], locations[i][2]), 
                                            map: map });

           oms.addMarker(marker);
         markers.push(marker);
  
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

        }
      }

      var mcOptions = {maxZoom:16}; // {gridSize: 50, maxZoom: 15};
      var mc = new MarkerClusterer(map, markers, mcOptions);

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
        <div class="col-lg-10 mc">
          <div id="mapcanvas"></div>
        </div>
      </div>
    </div>
  </body>


</html>
