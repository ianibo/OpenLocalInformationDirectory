<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <r:require modules="tlistyle"/>
    <title>TLI</title>
  </head>
  <body>

   <br/>
   <div class="container">
     <div class="row">
       <div class="col-md-6">
         <div class="panel panel-default">
           <div class="panel-heading clearfix">
             Your Records
             <g:link controller="resource" action="create" class="btn btn-success btn-xs pull-right">New Record</g:link>
           </div>
           <div class="panel-body">
             Panel content
           </div>
         </div>
       </div>
       <div class="col-md-6">
         <div class="panel panel-default">
           <div class="panel-heading clearfix">
             Your Authorities
             <g:link controller="authority" action="create" class="btn btn-success btn-xs pull-right">New Authority</g:link>
           </div>
           <div class="panel-body">
             Panel content
           </div>
         </div>
       </div>
     </div>
   </div>
  
  </body>
</html>
