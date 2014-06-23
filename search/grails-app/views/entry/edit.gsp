<html>
  <content tag="ngapp"><g:encodeAs codec="RAW">ng-app='olidNGApp'</g:encodeAs></content>
   <head>
      <meta name="layout" content="main"/>
      <title>OLID:Details</title>
      <r:require modules="bootstrap,angular,angularstrap"/>

      <g:javascript src="olidng.js"/>
  </head>
  <body>

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


    <div class="content container" ng-controller="EntryDetailCtrl">
      <form novalidate class="form-horizontal">

        <button ng-click="dirEntry.$update()" class="btn btn-primary pull-right">SAVE</button>
        <h1>{{dirEntry.title}}</h1>

        <div class="form-group">
          <label for="title" class="control-label col-xs-2">Title</label>
          <div class="col-xs-10">
            <input type="text" class="form-control" id="title" placeholder="Email" ng-model="dirEntry.title">
          </div>
        </div>
    
        <div class="form-group">
          <label for="description" class="control-label col-xs-2">Description</label>
          <div class="col-xs-10">
            <textarea class="form-control" id="description" rows="4" placeholder="Description" ng-model="dirEntry.description"></textarea>
          </div>
        </div>
    
        <div class="form-group">
          <label for="url" class="control-label col-xs-2">URL</label>
          <div class="col-xs-10">
            <input type="text" class="form-control" id="url" placeholder="URL" ng-model="dirEntry.url">
          </div>
        </div>

 <div class="form-group">
    <label><i class="fa fa-home"></i> Address <small>(async via maps.googleapis.com)</small></label>
    <input type="text" class="form-control" ng-model="selectedAddress" data-animation="am-flip-x" ng-options="address.formatted_address as address.formatted_address for address in getAddress($viewValue)" placeholder="Enter address" bs-typeahead>
  </div>
    
        <ul id="tabs" class="nav nav-tabs">
          <li class="active"><a href="#contactdetails" data-toggle="tab">Contact Details</a></li>
          <li><a href="#social" data-toggle="tab">Social Media</a></li>
          <li><a href="#sessions" data-toggle="tab">Sessions</a></li>
        </ul>
    
        <div id="my-tab-content" class="tab-content">
          <div class="tab-pane active" id="contactdetails">
            <div class="container" style="margin-top:20px;">
              <div class="form-group">
                <label for="contactName" class="control-label col-xs-2">contactName</label>
                <div class="col-xs-10">
                  <input type="text" class="form-control" id="contactName" placeholder="Contact Name" ng-model="dirEntry.contactName">
                </div>
              </div>
        
              <div class="form-group">
                <label for="contactEmail" class="control-label col-xs-2">contactEmail</label>
                <div class="col-xs-10">
                  <input type="email" class="form-control" id="contactEmail" placeholder="Contact Email" ng-model="dirEntry.contactEmail">
                </div>
              </div>
        
              <div class="form-group">
                <label for="contactEmail" class="control-label col-xs-2">contactTelephone</label>
                <div class="col-xs-10">
                  <input type="text" class="form-control" id="contactTelephone" placeholder="Contact Telephone" ng-model="dirEntry.contactTelephone">
                </div>
              </div>
          
              <div class="form-group">
                <label for="contactFax" class="control-label col-xs-2">contactFax</label>
                <div class="col-xs-10">
                  <input type="text" class="form-control" id="contactFax" placeholder="Contact Fax" ng-model="dirEntry.contactFax">
                </div>
              </div>
            </div>
          </div>
    
          <div class="tab-pane" id="social">
            <div class="container" style="margin-top:20px;">
    
              <div class="form-group">
                <label for="twitter" class="control-label col-xs-2">Twitter ID</label>
                <div class="col-xs-10">
                  <input type="text" class="form-control" id="twitter" placeholder="Twitter" ng-model="dirEntry.twitter">
                </div>
              </div>
    
              <div class="form-group">
                <label for="facebook" class="control-label col-xs-2">Facebook Page</label>
                <div class="col-xs-10">
                  <input type="text" class="form-control" id="facebook" placeholder="Facebook Page" ng-model="dirEntry.facebook">
                </div>
              </div>

            </div>
          </div>

          <div class="tab-pane" id="sessions">

            <div style="margin-top:20px;">

              <div ng-repeat="session in dirEntry.sessions" class="well">

                <h3>{{session.name}}, {{session.rrule}}, {{session.location.str}}, {{session.startTime}} - {{session.endTime}}</h3>

                <div class="form-group">
                  <label for="sessionName" class="control-label col-xs-2">Session Name</label>
                  <div class="col-xs-10">
                    <input type="text" class="form-control" id="sessionName" placeholder="Session Name" ng-model="session.name">
                  </div>
                </div>
              
                <div class="form-group">
                  <label for="sessionDescription" class="control-label col-xs-2">Session Description</label>
                  <div class="col-xs-10">
                    <input type="text" class="form-control" id="sessionDescription" placeholder="Session Description" ng-model="session.description">
                  </div>
                </div>

                <div class="form-group">
                  <label for="sessionLocation" class="control-label col-xs-2">Location</label>
                  <div class="col-xs-10">
                    <input type="text" class="form-control" id="sessionLocation" placeholder="Session Location" ng-model="session.location.str">
                  </div>
                </div>

                <div class="form-group">
                  <label for="sessionLocation" class="control-label col-xs-2">Recurrence</label>
                  <div class="col-xs-10">
                    <input type="text" class="form-control" id="sessionLocation" placeholder="Session Recurrence" ng-model="session.rrule">
                  </div>
                </div>

                <div class="form-group">
                  <label for="sessionStartTime" class="control-label col-xs-2">Start Time</label>
                  <div class="col-xs-10">
                    <input type="time" class="form-control" id="sessionStartTime" placeholder="Session Start Time" ng-model="session.startTime">
                  </div>
                </div>

                <div class="form-group">
                  <label for="sessionEndTime" class="control-label col-xs-2">End Time</label>
                  <div class="col-xs-10">
                    <input type="time" class="form-control" id="sessionEndTime" placeholder="Session End Time" ng-model="session.endTime">
                  </div>
                </div>

              </div>
            </div>
          </div>
        </div>
    
    
      </form>
      <g:if test="${params.debug=="true"}">
      <pre>form = {{dirEntry | json}}</pre>
      </g:if>
    </div>

    <p>TheEnd</p>

  </body>
</html>
