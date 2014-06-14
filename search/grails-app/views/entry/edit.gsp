<html>
  <content tag="ngapp"><g:encodeAs codec="RAW">ng-app='olidNGApp'</g:encodeAs></content>
   <head>
      <meta name="layout" content="main"/>
      <title>OLID:Details</title>
      <r:require modules="bootstrap,angular"/>

      <g:javascript src="olidng.js"/>
  </head>
  <g:set var="grailsParams" value="${params.collect{ it.key + '=\'' + it.value + '\''}.join('; ')}" />
  <body ng-init="${grailsParams}" a="b">

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


    <div class="content" ng-view>
    </div>

    <p>TheEnd</p>

  </body>
</html>
