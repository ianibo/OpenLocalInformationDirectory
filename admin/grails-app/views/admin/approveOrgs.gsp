<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>TLI</title>
  </head>
  <body>

   <br/>
   <div class="container">
     <div class="row">
       <div class="col-md-12">
         <div class="panel panel-default">
           <div class="panel-heading clearfix">
             Organisations Pending Approval
           </div>
           <div class="panel-body">
             <table class="table table-striped">
               <thead>
                 <tr>
                   <th>Organisation Name</th>
                   <th>Actions</th>
                 </tr>
               </thead>
               <tbody>
                 <g:each in="${pendinfg_orgs}" var="o">
                   <tr>
                     <td>${o.displayName}</td>
                     <td>
                         <g:link controller="admin" action="approveOrgs" params="${[org:o.id,act:'Y']}" class="btn btn-success btn-xs">Approve</g:link>
                         <g:link controller="admin" action="approveOrgs" params="${[org:o.id,act:'N']}" class="btn btn-success btn-xs" >Reject</g:link>
                     </td>
                   </tr>
                 </g:each>
               </tbody>
             </table>
           </div>
         </div>
       </div>
     </div>
   </div>
  
  </body>
</html>
