<html>
   <head>
      <meta name="layout" content="main"/>
      <title>OLID:Details</title>
      <r:require modules="bootstrap"/>

      <g:each in="${record.source.sessions}" var="s">
        <meta property="ICBM" name="ICBM" content="${s.loc.lat}, ${s.loc.lon}" />
        <meta property="geo.position" name="geo.position" content="${s.loc.lat}, ${s.loc.lon}" />
        <meta property="og:latitude" content="${s.loc.lat}" />
        <meta property="og:longitude" content="${s.loc.lon}" />
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
        <meta property="geo.region" name="geo.region" content="${s.location.toString()}" />
        <meta property="geo.placename" name="geo.placename" content="${s.location.toString()}" />
      </g:each>


      <!-- OGP Properties -->
      <meta property="og:title" content="${record.source.title?:''}" />
      <meta property="og:description" content="${record.source.description?:''}" />
      <meta property="og:type" content="activity" />

  </head>
  <body>
    <div class="container" style="padding-top:10px;">
      <ul class="breadcrumb pull-right">
        <g:if test="${request.getHeader('referer')?.contains('search')}">
          <li> <a href="${request.getHeader('referer')}">Return to search</a> </li>
        </g:if>
        <li><g:link controller="requestAccess" action="index" id="${record.source.canonical_shortcode}">Update this record</g:link></li>
      </ul>
      <ul class="breadcrumb">
        <li> <g:link controller="home" action="index">Home</g:link> <span class="divider"></span> </li>
        <li> ${record.source.title} </li>
      </ul>
    </div>


    <g:if test="${flash.message}">
      <div class="content">
        <div class="container-fluid"><div class="row-fluid"><div class="span12">
          <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
        </div></div></div>
      </div>
    </g:if>


    <div class="content">
      <div class="container">
        <div class="row">
          <div class="col-lg-10" itemscope itemtype="directoryEntry">

            <div>

              <g:if test="${1==2}">
                <div id="headercontainer">
                  <div id="olid-entry-header-carousel" class="carousel slide" data-ride="carousel">
                    <!-- Indicators -->
                    <ol class="carousel-indicators">
                      <li data-target="#olid-header-1" data-slide-to="0" class="active"></li>
                    </ol>
    
                    <!-- Wrapper for slides -->
                    <div class="carousel-inner">
                      <div class="item active">
                        <img alt="No Pics" src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSI5MDAiIGhlaWdodD0iNTAwIj48cmVjdCB3aWR0aD0iOTAwIiBoZWlnaHQ9IjUwMCIgZmlsbD0iIzc3NyI+PC9yZWN0Pjwvc3ZnPgo=" class="active"/>
                        <div class="carousel-caption">
                        </div>
                      </div>
                    </div>
    
                    <!-- Controls -->
                    <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                      <span class="glyphicon glyphicon-chevron-left"></span>
                    </a>
                      <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                      <span class="glyphicon glyphicon-chevron-right"></span>
                    </a>
                  </div>
                </div>
              </g:if>

              <h1 itemprop="name">${record.source.title}</h1>
              <p itemprop="description">${record.source.description}</p>

              <dl>
                <g:if test="${record.source.contactEmail}"><dt>Email</td><dd><a href="mailto:${record.source.contactEmail}" itemprop="email">${record.source.contactEmail}</a></dd></g:if>
                <g:if test="${record.source.contactTelephone}"><dt>Telephone</td><dd><a href="mailto:${record.source.contactTelephone}" itemprop="telephone">${record.source.contactTelephone}</a></dd></g:if>
                <g:if test="${record.source.url}"><dt>Website</td><dd><a href="${record.source.url}" itemprop="url">${record.source.url}</a></dd></g:if>
                <dt>Subjects</dt>
  
  
  
                <dd><g:each in="${record?.source?.subjects}" var="s">
                  <span class="badge">${s?.subjname?.toString()}</span>
                </g:each></dt>
                <dt>Categories</dt>
                <dd><g:each in="${record.source.categories}" var="s">
                  <span class="badge">${s?.catid}</span>
                </g:each></dt>
  
  
              </dl>
              <br/>
              <dl>
                <dt>Days, Times and Places</dt>
                <dd>
                  <g:each in="${record.source.sessions}" var="s">
                    <div>
                      <g:if test="s.loc?.lat && s.loc?.lon }">
                        <div id="map_${s.sesid}" class="pull-right" style="width: 250px; height: 250px;"></div>
                      </g:if>
                      <div>
                      <h3>${s.name}</h3>
                        ${s.description?:'No Description'}
                        <div class="container">
                        <dl>
                          <dt>Location</dt><dd>${s.location?.buildingName} ${s.location?.buildingNumber} ${s.location?.street} ${s.location?.city} ${s.location?.county} ${s.location?.postcode}</dd>
                          <dt>Reccurrence</dt><dd>${s.trrule?:'Unknown'}</dd>
                          <dt>Start Time</dt><dd>${s.startTime?:'Unknown'}</dd>
                          <dt>End Time</dt><dd>${s.endTime?:'Unknown'}</dd>
                          <dt>Indicative Cost</dt><dd>${s.indicativeCost?:'Unknown'}</dd>
                        </dl>
                        </div>
                      </div>
                    </div>
                  </g:each>
                </dd>
  
              </dl>
              <!-- If user not logged in, or user logged in but not an owner -->
              <div class="well text-center">
                Is this data about you something
                you are responsible for? You can 
                update and improve your listing <g:link controller="requestAccess" action="index" id="${record.source.canonical_shortcode}">here</g:link>
              </div>
  
            </div>
          </div>

          <div class="col-lg-2">
            <g:render template="addpanel" contextPath="../templates"/>
          </div>
        </div>

      </div>
    </div>

    <script type="text/javascript">
      //<![CDATA[

        function map2(lat,lon,title,map_elem) {
          var myLatlng = new google.maps.LatLng(lat,lon);

          var myOptions = {
             zoom: 15,
             center: myLatlng,
             mapTypeId: google.maps.MapTypeId.ROADMAP
          }

          var map = new google.maps.Map(document.getElementById(map_elem), myOptions);
  
          var marker = new google.maps.Marker({
               position: myLatlng,
               map: map,
               title:title
          });
          marker.setMap(map);
        }

      <g:each in="${record.source.sessions}" var="s">
        map2("${s.loc.lat}", "${s.loc.lon}","${s.name}","map_${s.sesid}");
      </g:each>
      //]]>
    </script>


  </body>
</html>
