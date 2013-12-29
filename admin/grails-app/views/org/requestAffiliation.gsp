<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <r:require modules="tlistyle"/>
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
             <g:link controller="org" action="requestNew" class="btn btn-success btn-xs pull-right">Request New Organisation</g:link>
           </div>
           <div>

             &nbsp;<br/>
             <g:form action="requestNew" role="form" method="post">
               <div class="col-lg-12">
               <fieldset>
                 <div class="control-group">
                   <label class="control-label" for="orgName">Organisation Name</label>
                   <div class="controls">
                     <g:simpleReferenceTypedown class="input-xxlarge" style="width:350px;" name="orgName" baseClass="tli.TliOrg"/>
                     <p class="help-block">Search for the organisation you wish to associate with</p>
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
