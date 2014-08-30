<html>
  <head>
    <asset:javascript src="fuelux.min.js"/>
    <asset:stylesheet src="fuelux.min.css"/>
    <meta name="layout" content="main"/>
    <title>olid - The Open Local Information Directory - Public information from trusted Sources curated freely using an open platform</title>
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
            <div class="well fuelux">
              <h1>Request Permission to Edit ${entry.title}.... </h1>
              <div class="wizard" data-initialize="wizard" data-restrict="previous" id="myWizard">
                <ul class="steps">
                  <li data-step="1" class='${(step==null||step==1)?'active':''}'><span class="badge">1</span>Sign In<span class="chevron"></span></li>
                  <li data-step="2" class='${(step==2)?'active':''}'><span class="badge">2</span>Set Email<span class="chevron"></span></li>
                  <li data-step="3" class='${(step==3)?'active':''}'><span class="badge">3</span>Confirm Email<span class="chevron"></span></li>
                  <li data-step="4" class='${(step==4)?'active':''}'><span class="badge">4</span>Request Access<span class="chevron"></span></li>
                  <li data-step="5" class='${(step==5)?'active':''}'><span class="badge">5</span>Confirmation<span class="chevron"></span></li>
                </ul>

                <div class="step-content">
                  <div class="step-pane active sample-pane alert  ${(step==null||step==1)?'active':''}'" data-step="1">
                    <h4>Sign In</h4>
                    <p>Veggies es bonus vobis, proinde vos postulo essum magis kohlrabi welsh onion daikon amaranth tatsoi tomatillo melon azuki bean garlic. Beetroot water spinach okra water chestnut ricebean pea catsear courgette.</p>
                  </div>

                  <div class="step-pane active sample-pane alert ${(step==2)?'active':''}" data-step="2">
                    <h4>Set Email</h4>
                    <p>
                      Your email address will ONLY be shared when you request permission to edit a resource. This is so the owner of a record
                      can confirm you are allowed to edit the record. We will remind you any time you might be sharing any personal data.
                    </p>
                    <p>
                      <g:form controller="requestAccess" id="${params.id}" action="setEmailAddress" method="get">
                        <div class="form-group">
                          <label for="email">Your Email Address</label>
                          <input name="email" type="text" class="form-control" id="email" placeholder="Email Address"/>
                        </div>
                        <div class="form-group">
                          <button type="submit" class="btn btn-default">Set email address</button>
                        </div>
                      </g:form>
                    </p>
                  </div>

                  <div class="step-pane active sample-pane alert ${(step==3)?'active':''}" data-step="3">
                    <h4>Confirm Email</h4>
                    <p>An confirmation message has been sent to your email address. Please click the link in that message to confirm your address. You will only need to complete this step once. To resend the confirmation email, please click <g:link controller="requestAccess" action="resendConfirmation" params="${params}">Here</g:link></p>
                  </div>
                  <div class="step-pane active sample-pane alert ${(step==4)?'active':''}" data-step="4">
                    <h4>Fill out request access form</h4>
                    <p>
                      <g:form>

                        <div class="form-group">
                          <label for="email">Your Email Address</label>
                          ${request.user?.email} - This will be shared with the record owner.
                        </div>

                        <div class="form-group">
                          <label for="reason">Reason for requesting edit access</label>
                          <textarea name="reason" class="form-control" id="reason" placeholder="Any information to support your request for access. Please add any organisational affiliation"></textarea>
                        </div>

                        <div class="form-group">
                          <button type="submit" class="btn btn-default">Request Editor Access</button>
                        </div>
                      </g:form>

                    </p>
                  </div>
                  <div class="step-pane active sample-pane alert ${(step==5)?'active':''}" data-step="5">
                    <h4>Confirmation</h4>
                    <p>Veggies es bonus vobis, proinde vos postulo essum magis kohlrabi welsh onion daikon amaranth tatsoi tomatillo melon azuki bean garlic. Beetroot water spinach okra water chestnut ricebean pea catsear courgette.</p>
                  </div>
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>

</html>
