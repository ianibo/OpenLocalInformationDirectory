<html>
  <head>
    <meta name="layout" content="main"/>
    <title>olid - The Open Local Information Directory - Public information from trusted Sources curated freely using an open platform</title>
    <r:require modules="bootstrap"/>
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
          <div class="col-lg-12">
            <div class="well">
              <h1>Request editor permission on.... Step 1 of 3</h1>
              <p>You've requested permission to be able to maintain <strong>${entry.title}</strong> - Great!</p>
              <p>In order to maintain the quality of the directory we need users to identify themselves
                 using a username/password or some other service like facebook. If you have already
                 registered, or you would like to login using your facebook credentials please click 
                 <g:link controller="requestAccess" id="${params.id}" action="requestAccess">Here</g:link>.
              </p>
              <p>If you're new to this service, and would like to register an account, please click Here</p>
              <p>Bit of a technical note note if you work for an information provider like a local authority or library: <br/>
                 Our users generally fall into 2 categories - people who records are actually about [data subjects] like
                 club owners, service providers, etc and people who manage records like information service professionals [data processors].</p>
                 
                 <p>Individual users may use either facebook or email login. If you're a data manager
                 from a library or local authority however using an email login for your institution will help us work out where you are
                 from, what you can edit because of your organisational affiliation, and who to refer requests
                 for editor access to. Users wanting access in a professional capacity for public authorities are 
                 requested to register an account using thier work email address</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>

</html>
