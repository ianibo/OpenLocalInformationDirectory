<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<%
  def removeFacet = { params, facet, val ->
    def newparams = [:]
    newparams.putAll(params)
    def current = newparams[facet]
    if ( current == null ) {
    }
    else if ( current instanceof String[] ) {
      newparams.remove(current)
      newparams[facet] = current as List
      newparams[facet].remove(val);
    }
    else if ( current?.equals(val.toString()) ) {
      newparams.remove(facet)
    }
    newparams
  }
%>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="olid - The Open Local Information Directory - Public information from trusted Sources curated freely using an open platform"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">

    <g:layoutHead/>
    <g:javascript library="application"/>
    <r:layoutResources />

  </head>

  <body>

    <div class="container-fluid">
      <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <g:link controller="home" action="index" class="navbar-brand">Open Local Information Directory</g:link>
        </div>

        <div class="collapse navbar-collapse">
        </div>

        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
          </ul>
          <ul class="nav navbar-nav pull-right">
          </ul>
        </div><!--/.nav-collapse -->
      </nav>
      <div class="row well text-center searchheader">
        <g:form role="form" action="index" controller="home" method="get" params="${params.portal != null ? [portal:params.portal] : [:]}" class="form-inline">
          <fieldset>
            <g:each in="${params.list('collections')}" var="subject"><input type="hidden" name="collections" value="${subject}"/></g:each>
            <g:each in="${params.list('categories')}" var="subject"><input type="hidden" name="categories" value="${subject}"/></g:each>
            <g:each in="${params.list('subjects')}" var="subject"><input type="hidden" name="subjects" value="${subject}"/></g:each>
            <label class="control-label">Postcode : </span>
            <div class="form-group">
              <input id="pcin" type="text" class="form-control" placeholder="Postcode" name="postcode" value="${params.postcode}">
            </div>
            <label class="control-label">Keywords : </span>
            <div class="form-group">
              <input id="kwin" type="text" class="form-control" placeholder="Keyword" name="q" value="${params.q}">
            </div>
            &nbsp;
            <button name="listSearchButton" type="submit" value="true" class="btn btn-default">List Closest</button>
            <button name="mapSearchButton" type="submit" value="true" class="btn btn-default">Show Closest on Map</button>
            <button name="mapAllButton" type="submit" value="true" class="btn btn-default">Show All on Map</button>
          </fieldset>
        </g:form>
        <p>
          <g:each in="${['collections','categories','subjects']}" var="facet">
            <g:each in="${params.list(facet)}" var="fv">
              <span class="badge alert-info">${fv} &nbsp; <g:link controller="home" action="index" params="${removeFacet(params,facet,fv)}"><span class="glyphicon glyphicon-remove"></span></g:link></span>
            </g:each>
          </g:each>
        </p>
        <g:if test="${hits!=null}">
          <p>
            Your search found ${hits?.totalHits} records
          </p>
        </g:if>
      </div>
    </div>

    <g:layoutBody/>


    <g:if test="${grailsApplication.config.analytics?.code!=null}">
      <g:javascript>
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga'); 
        ga('create', '${grailsApplication.config.analytics.code}', '${grailsApplication.config.analytics.host}');
        ga('send', 'pageview');
      </g:javascript>
    </g:if>

    <r:layoutResources/>
  </body>
</html>
