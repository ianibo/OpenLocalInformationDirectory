<html>
  <content tag="ngapp"><g:encodeAs codec="RAW">ng-app='olidNGApp'</g:encodeAs></content>
   <head>
      <meta name="layout" content="main"/>
      <title>OLID:Details</title>
      <r:require modules="bootstrap,angular"/>

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
                  <input type="text" class="form-control" id="contactEmail" placeholder="Contact Email" ng-model="dirEntry.contactEmail">
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
            <div class="container" style="margin-top:20px;">
              
              <table class="table table-striped table-bordered">
                <thead>
                <tr>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Where</th>
                  <th>When</th>
                  <th>Start Time</th>
                  <th>End Time</th>
                <tr>
                </thead>
                <tbody>
                  <tr ng-repeat="session in dirEntry.sessions">
                    <td><input type="text" class="form-control" id="sessionname" placeholder="Session Name" ng-model="session.name"></td>
                    <td>{{session.description}}</td>
                    <td>{{session.location.id}}</td>
                    <td>{{session.rrule}}</td>
                    <td>{{session.startTime}}</td>
                    <td>{{session.endTime}}</td>
                  </tr>
                </tbody>
               </table>
            </div>
          </div>
        </div>
    
        <div class="form-group">
          <div class="col-xs-offset-2 col-xs-10">
            <button ng-click="dirEntry.$update()" class="btn btn-primary">SAVE</button>
          </div>
        </div>
    
      </form>
      <pre>form = {{dirEntry | json}}</pre>
    </div>

    <p>TheEnd</p>

  </body>
</html>
