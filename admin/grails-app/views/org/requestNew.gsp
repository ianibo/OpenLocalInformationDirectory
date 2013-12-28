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
             Request New Organisation
           </div>
           <div>
             &nbsp;<br/>
             <g:form action="requestNew" role="form" method="post">
               <div class="col-lg-12">
               <fieldset>
                 <div class="control-group">  
                   <label class="control-label" for="orgName">Proposed Organisation Name</label>  
                   <div class="controls">  
                     <input type="text" id="orgName" Name="orgName" class="form-control" placeholder="Organisation Name">  
                     <p class="help-block">Please name the organisation you are proposing</p>
                   </div>  
                 </div>  
                 <br/>
                 <div class="control-group">  
                   <button class="btn btn-success" name="requestNewOrganisation" class="form-control">Request New Organisation</button>
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
