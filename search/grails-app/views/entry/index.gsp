<html>
   <head>
      <meta name="layout" content="main"/>
      <title>OLID:Details</title>
      <r:require modules="bootstrap"/>

      <g:if test="${record.source.sessions && record.source.sessions[0] && record.source.sessions[0].loc && record.source.sessions[0].loc.lat && record.source.sessions[0].loc.lon}">
        <meta property="ICBM" name="ICBM" content="${record.source.sessions[0].loc.lat}, ${record.source.sessions[0].loc.lon}" />
        <meta property="geo.position" name="geo.position" content="${record.source.sessions[0].loc.lat}, ${record.source.sessions[0].loc.lon}" />
        <meta property="og:latitude" content="${record.source.sessions[0].loc.lat}" />
        <meta property="og:longitude" content="${record.source.sessions[0].loc.lon}" />
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
        <meta property="geo.region" name="geo.region" content="${record.source.sessions[0].location.toString()}" />
        <meta property="geo.placename" name="geo.placename" content="${record.source?.sessions[0].location.toString()}" />
      </g:if>


      <!-- OGP Properties -->
      <meta property="og:title" content="${record.source.title?:''}" />
      <meta property="og:description" content="${record.source.description?:''}" />
      <meta property="og:type" content="activity" />

   </head>
  <body>

    <div class="container" style="padding-top:10px;">
      <ul class="breadcrumb">
        <li> <g:link controller="home" action="index">Home</g:link> <span class="divider">/</span> </li>
        <g:if test="${request.getHeader('referer')?.contains('search')}">
          <a class="pull-right" href="${request.getHeader('referer')}">Return to search</a>
        </g:if>
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
          <div class="span9" itemscope itemtype="directoryEntry">

          <div>
            <h1 itemprop="name">${record.source.title}</h1>
            <p itemprop="description">${record.source.description}</p>

            <dl>
              <dt>Address</dt>
              <g:if test="${record.source.sessions && ( record.source.sessions.size() > 0 ) }">
              <dd itemprop="address">${record.source.sessions[0].location?.buildingName} ${record.source.sessions[0].location?.buildingNumber} ${record.source.sessions[0].location?.street} ${record.source.sessions[0].location?.city} ${record.source.sessions[0].location?.county} ${record.source.sessions[0].location?.postcode}</dd>
              </g:if>
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
              <dt>Sessions</dt>
              <dd>
                <g:each in="${record.source.sessions}" var="s">
                  <div>
                    <g:if test="s.loc?.lat && s.loc?.lon }">
                      <div id="map_${s.sesid}" class="pull-right" style="width: 250px; height: 250px;"></div>
                    </g:if>
                    <div>
                    <h3>${s.name}</h3>
                      <div class="container">
                      <dl class="dl-horizontal" style="border:1px solid black;">
                        <dt>Description</dt><dd>${s.description?:'No Description'}</dd>
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
          </div>
        </div>

        <div class="span3">
          <g:render template="addpanel" contextPath="../templates"/>
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
