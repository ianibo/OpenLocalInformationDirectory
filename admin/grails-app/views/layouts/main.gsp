<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="TLI"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">

    <g:layoutHead/>
    <g:javascript library="application"/>
    <r:layoutResources />

  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Local Information Store</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <sec:ifLoggedIn>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin</a>
                <ul class="dropdown-menu">
                  <li><g:link controller="requestAuthority">Join existing Authority</g:link></li>
                  <li><g:link controller="requestAuthority">Request New Authority</g:link></li>
                </ul>
              </li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
            </sec:ifLoggedIn>
          </ul>
          <ul class="nav navbar-nav pull-right">
            <sec:ifLoggedIn>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">${request.user?.username} <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><g:link controller="profile">Profile</g:link></li>
                  <li><g:link controller="logout">Logout</g:link></li>
                </ul>
              </li>
            </sec:ifLoggedIn>
            <sec:ifNotLoggedIn>
              <!--<li><p class="navbar-text">Not logged in</p></li>-->
              <li><g:link controller="login">Login</g:link></li>
            </sec:ifNotLoggedIn>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

    <g:layoutBody/>

    <r:layoutResources/>

    </body>
</html>
