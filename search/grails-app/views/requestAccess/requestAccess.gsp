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
                       <small>Step 1 : Sign in and confirmed your email address</small><br/>
                       You've signed in.
                     </li>
                     <li>
                       <h2>Step 2 : Fill out the request access form</h2>

              <g:form controller="requestAccess" id="${params.id}" action="processRequestAccessForm" method="get">
                  <div class="form-group">
                      <label for="name">Your Name</label>
                      <input name="name" type="text" class="form-control" id="name" placeholder="Enter name"/>
                  </div>
                  <div class="form-group">
                      <label for="email">Your Email Address</label>
                      ${request.user?.email}
                  </div>
                  <div class="form-group">
                      <label for="reason">Reason for requesting edit access</label>
                      <textarea name="reason" class="form-control" id="reason" placeholder="Any information to support your request for access. Please add any organisational affiliations (For example, if you are a member of staff, an owner, or volunteer) or other information that the reviewer will be able to use to process your request" rows="4"></textarea>
                  </div>
                  <button type="submit" class="btn btn-default">Request Editor Access</button>
              </g:form>

                     </li>
                     <li>&nbsp;<br/>
                       <small>Step 3 : Confirmation</small>
                     </li>
                   </ul></p>


            </div>
          </div>
        </div>
      </div>
    </div>
  </body>

</html>
