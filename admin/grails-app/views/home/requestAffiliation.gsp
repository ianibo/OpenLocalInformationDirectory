<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>TLI::Request Organisational Affiliation</title>
  </head>
  <body>
   <br/>

   <div class="container">
     <div class="row">
       <div class="col-lg-12">
        <div class="panel panel-default">
           <div class="panel-heading clearfix">
             Request Affiliation
             <g:link controller="home" action="requestNewOrg" class="btn btn-success btn-xs pull-right">Request New Organisation</g:link>
           </div>
           <div class="clearfix">

             &nbsp;<br/>
             <g:form controller="home" action="requestAffiliation" role="form" method="post">
               <div class="col-lg-12">
               <fieldset>
                 <div class="control-group">
                   <label class="control-label" for="org">Organisation Name</label>
                   <div class="controls">
                     <g:simpleReferenceTypedown class="input-xxlarge" style="width:350px;" name="org" baseClass="me.ianibbo.common.AuthCommonOrganisation" data-filter1="${request.user?.id}"/>
                     <p class="help-block">Search for the organisation you wish to associate with</p>
                   </div>
                 </div>
                 <div class="control-group">
                   <label class="control-label" for="role">Requested Role</label>
                   <div class="controls">
                     <select name="role">
                       <option value="Standard User">Standard User - Able to manage records but not perform administrative functions</option>
                       <option value="Administrator">Administrator - Able to manage records AND peform administrative functions</option>
                       <option value="Read Only User">Read Only User - For reference use</option>
                     </select>
                     <p class="help-block">Role you wish to request</p>
                   </div>
                 </div>
                 <br/>
                 <div class="control-group">
                   <button class="btn btn-success" name="requestNewOrganisation" class="form-control">Ask to join this organisation </button>
                 </div>
                 <br/>
                 &nbsp;
               </fieldset>
               </div>
             </g:form>

           </div>
         </div>
       </div>
     </div>
   </div>
  
  </body>
</html>
