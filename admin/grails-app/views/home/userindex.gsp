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
             Your Authorities
             <g:link controller="authority" action="create" class="btn btn-success btn-xs pull-right">New Authority</g:link>
           </div>
           <div class="panel-body">
             <g:if test="${authorities?.size() > 0}">
               <table class="table table-striped">
                 <thead>
                   <tr>
                     <th>Head</th>
                   </tr>
                 </thead>
                 <tbody>
                   <tr>
                     <th>Head</th>
                   </tr>
                 </tbody>
               </table>
             </g:if>
             <g:else>
               No authorities currently
             </g:else>
           </div>
         </div>
       </div>
       <div class="col-md-6">
         <div class="panel panel-default">
           <div class="panel-heading clearfix">
             Your Records
             <g:link controller="resource" action="create" class="btn btn-success btn-xs pull-right">New Record</g:link>
           </div>
           <div class="panel-body">
             <g:if test="${records?.size() > 0}">
               <table class="table table-striped">
                 <thead>
                   <tr>
                     <th>Head</th>
                   </tr>
                 </thead>
                 <tbody>
                   <tr>
                     <th>Head</th>
                   </tr>
                 </tbody>
               </table>
             </g:if>
             <g:else>
               No records currently
             </g:else>
           </div>
         </div>
       </div>
     </div>
   </div>
  
  </body>
</html>
