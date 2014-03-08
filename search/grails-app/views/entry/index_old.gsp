<html>
   <head>
      <meta name="layout" content="main"/>
      <title>OLID:Details</title>
      <r:require modules="bootstrap"/>

      <!-- OGP Properties -->
      <meta property="og:title" content="${record.source.name}" />
      <meta property="og:description" content="${record.source.description}" />
      <meta property="og:type" content="activity" />
   </head>
  <body>

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
  
            <h1 itemprop="name">${record.source.name}</h1>
          <p itemprop="description">${record.source.description}</p>
  
            <dl>
              <dt>Address</dt>
              <dd itemprop="address">
                ${record.source.sessions[0].address}<br/>
              </dd>
  
              <g:if test="${record.source.contactEmail}"><dt>Email</td><dd><a href="mailto:${record.source.contactEmail}" itemprop="email">${record.source.contactEmail}</a></dd></g:if>
  
              <g:if test="${record.source.contactTelephone}"><dt>Telephone</td><dd><a href="mailto:${record.source.contactTelephone}" itemprop="telephone">${record.source.contactTelephone}</a></dd></g:if>
  
              <g:if test="${record.source.orig.url}"><dt>Website</td><dd><a href="${record.source.orig.url}" itemprop="url">${record.source.url}</a></dd></g:if>
            </dl>
            <dl>
          </div>
  
          <div class="span3">
            <g:render template="addpanel" contextPath="../templates"/>
          </div>
  
        </div>
      </div>
    </div>

  </body>
</html>
