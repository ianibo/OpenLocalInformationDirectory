<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>Open Local Information Directory</title>
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
                             <g:each in="${tli.TliCollection.findAllByOwner(a.org)}" var="col">
                               <li><g:link controller="collection" action="index" id="${col.shortcode}">${col.name}</g:link></li>
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
             Your Collections
           </div>
           <div class="panel-body">
             <g:if test="${colls?.size() > 0}">
               <table class="table table-striped">
                 <thead>
                   <tr>
                     <th>Name</th>
                     <th>Owned By</th>
                   </tr>
                 </thead>
                 <tbody>
                   <g:each in="${colls}" var="c">
                     <tr>
                       <td><g:link controller="collection" id="${c.shortcode}" action="index">${c.name}</g:link></td>
                       <td>${c.owner.displayName}</td>
                     </tr>
                   </g:each>
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
