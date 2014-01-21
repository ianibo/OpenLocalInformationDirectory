<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <r:require modules="tlistyle"/>
    <title>TLI</title>
  </head>
  <body>

   <div class="container">
     <div class="row">
       <div class="col-lg-12">
         <h1>Collections available to user ${request.user?.username}</h1>
         <img src="${resource(dir: 'images', file: 'ods_transparent.png')}" class="pull-right"/>
         </h1>
       </div>
     </div>
     <div class="row">
       <div class="col-lg-12">
         <h2>TLI Collections API</h2>
         <p>
           This page explains the TLI Collections API calls.
         </p>


         <h3>Collections available to this user</h3>
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
               You do not currently have API access to any collections defined in this directory. Please register an affiliation
               and wait for your local administrator to approve the affiliation (Or a olid administrator if you are the first from your
               organisation).
             </g:else>


       </div>
     </div>
   </div>
  
  </body>
</html>
