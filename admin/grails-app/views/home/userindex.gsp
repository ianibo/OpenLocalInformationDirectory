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
             Your Organisations
             <g:link controller="org" action="requestAffiliation" class="btn btn-success btn-xs pull-right">Request Affiliation</g:link>
           </div>
           <div class="panel-body">
             <g:if test="${organisations?.size() > 0}">
               <table class="table table-striped">
                 <thead>
                   <tr>
                     <th></th>
                     <th>Name</th>
                   </tr>
                 </thead>
                 <tbody>
                   <g:each in="${organisations}" var="a">
                     <tr>
                       <td>${a.code}</td>
                       <td><g:link controller="org" action="index" id="${o.id}">${a.name}</g:link></td>
                     </tr>
                   </g:each>
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
