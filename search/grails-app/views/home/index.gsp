<html>
   <head>
      <meta name="layout" content="searchmain"/>
      <title>olid - The Open Local Information Directory - Public information from trusted Sources curated freely using an open platform</title>
   </head>
<body>

  <g:if test="${flash.message}">
    <div class="content">
      <div class="container-fluid"><div class="row-fluid"><div class="span12">
        <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
      </div></div></div>
    </div>
  </g:if>


  <div class="content" style="padding-top:50px;">
    <div class="container">
      <div class="row">
        <div class="col-lg-10">
          <div style="text-align:center;" class="well">
            <p>
            Search this directory of information by entering your postcode and/or keywords into the navigation bar above. Hit the See map button to see your
            search results on a map, or List for a more traditional search engine style result. The Search engine understands spelling variants and can cope with
            most alternatives.
            </p>
          </div>
        </div>
        <div class="col-lg-2 well">
          <g:render template="addpanel" contextPath="../templates"/>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
