<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title><g:layoutTitle default="olid - The Open Local Information Directory - Public information from trusted Sources curated freely using an open platform"/></title>

<link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon"/>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">


   <r:require modules="register,tlistyle,bootstrap-popover"/>
   <r:layoutResources/>

<g:layoutHead/>

</head>

<body>

  <s2ui:layoutResources module='register' />

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Open Local Information Directory</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>


<g:layoutBody/>

<s2ui:showFlash/>

</body>
</html>

