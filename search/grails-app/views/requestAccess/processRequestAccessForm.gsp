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
                       <small>Step 1 : Sign in Completed and Email Address Confirmed</small>
                     </li>
                     <li>
                       <small>Step 2 : Fill out the request access form</small></li>
                     <li>
                       <h3>Step 3 : Wait for a confirmation email</h3>
                       Thankyou for requesting access - Please wait for a confirmation email. A response depends upon the person identified
                       as owning, or an administrator, the information checking their email.
                     </li>
                   </ul></p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>

</html>
