<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
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

        <p class="navbar-text">Search:</p>

        <div class="collapse navbar-collapse">
          <g:form role="search" action="index" controller="home" method="get">
            <div class="navbar-form navbar-left">
              <div class="form-group">
                <input id="pcin" type="text" class="form-control" placeholder="Postcode" name="postcode" value="${params.postcode}">
              </div>
              <div class="form-group">
                <input id="kwin" type="text" class="form-control" placeholder="Keyword" name="q" value="${params.q}">
              </div>
            </div>
            <button name="listSearchButton" type="submit" value="true" class="btn btn-default navbar-btn">List Closest</button>
            <button name="mapSearchButton" type="submit" value="true" class="btn btn-default navbar-btn">Show Closest on Map</button>
            <button name="mapAllButton" type="submit" value="true" class="btn btn-default navbar-btn">Show All on Map</button>
          </g:form>
        </div>

        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
          </ul>
          <ul class="nav navbar-nav pull-right">
          </ul>
        </div><!--/.nav-collapse -->
      </nav>
    </div>

    <g:layoutBody/>

    <r:layoutResources/>

  </body>
</html>
