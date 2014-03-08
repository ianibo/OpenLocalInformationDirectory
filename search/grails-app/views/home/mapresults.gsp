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

  <div class="content mc">
    <div class="container-fluid fill mc">
      <div class="row-fluid mc">
        <div class="col-lg-2">
        </div>
        <div class="col-lg-10 mc">
          <div id="mapcanvas"></div>
        </div>
      </div>
    </div>
  </body>


</html>
