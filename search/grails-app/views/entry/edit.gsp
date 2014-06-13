<html>

   <head>
      <meta name="layout" content="main"/>
      <title>OLID:Details</title>
      <r:require modules="bootstrap,angular"/>
  </head>
  <body>
    <div class="container" style="padding-top:10px;">
      <ul class="breadcrumb pull-right">
        <g:if test="${request.getHeader('referer')?.contains('search')}">
          <li> <a href="${request.getHeader('referer')}">Return to search</a> </li>
        </g:if>
      </ul>
      <ul class="breadcrumb">
        <li> <g:link controller="home" action="index">Home</g:link> <span class="divider"></span> </li>
        <li> ${record.source?.title} </li>
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
          <div class="col-lg-10">
                   EDIT...
          </div>
        </div>

      </div>
    </div>

  </body>
</html>
