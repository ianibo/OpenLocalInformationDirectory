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
              <h1>Request Permission to Edit ${entry.title}.... </h1>
                <p><ul class="list-unstyled">
                     <li>
                       <h2>Step 1 : Sign in</h2>
                In order to edit records, users need to sign in to this service. Please
                <g:link controller="requestAccess" id="${params.id}" action="requestAccess" class="btn btn-info">Click Here</g:link> to Log in using Facebook or Google or a local account
                     </li>
                     <li>
                       <small>Step 2 : Fill out the request access form</small></li>
                     <li>
                       <small>Step 3 : Wait for a confirmation email</small></li>
                   </ul></p>

                <h3>Notes</h3>
                <p>In order to maintain the quality of the directory we need users to identify themselves
                   using a username/password or some other service like facebook. If you have already
                   registered, or you would like to login using your facebook credentials please click 
                   the link above. This is usually the best choice for individuals like people who run an 
                   activity, business, club or society looking to update a single record.</p>
                </p>
                <h3>For maintainers of public information (Like libraries and local authorities):</h3>
                <p>If you work for an information provider like a local authority or library: <br/>
                   Our users generally fall into 2 categories - people who records are actually about [data subjects] like
                   club owners, service providers, etc and people who manage records like information service professionals [data processors].</p>
                   
                   <p>If you're in this second category - a data manager
                   from a library or local authority then using an email login for your institution will help us work out where you are
                   from, what you can edit because of your organisational affiliation, and who to refer requests
                   for editor access to. Users wanting access in a professional capacity for public authorities are 
                   advised to register an account using thier work email address rather than using google or facebook authentication.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>

</html>
