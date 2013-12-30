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
             <g:link controller="home" action="requestAffiliation" class="btn btn-success btn-xs pull-right">Request Affiliation</g:link>
           </div>
           <div class="panel-body">
             <g:if test="${organisations?.size() > 0}">
               <table class="table table-striped">
                 <thead>
                   <tr>
                     <th>Org (Status)</th>
                     <th>Role (Status)</th>
                   </tr>
                 </thead>
                 <tbody>
                   <g:each in="${organisations}" var="a">
                     <tr>
                       <td>
                         <g:if test="${a.org.status.value=='Approved'}">
                           <g:link controller="org" id="${a.org.shortcode}" action="show">${a.org.displayName}</g:link><br/>
                           <ul class="inline">
                             <g:each in="${a.org.collections}" var="col">
                               <li><g:link controller="collection" id="${col.shortcode}" action="show">${col.name}</g:link></li>
                             </g:each>
                           </ul>
                     
                         </g:if>
                         <g:else>
                           ${a.org.displayName} (${a.org.status.value}) 
                         </g:else>
                       </td>
                       <td>${a.role} (${a.status.value})</td>
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
