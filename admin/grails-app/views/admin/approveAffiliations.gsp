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
       <div class="col-md-12">
         <div class="panel panel-default">
           <div class="panel-heading clearfix">
             Organisations Pending Approval
           </div>
           <div class="panel-body">
             <table class="table table-striped">
               <thead>
                 <tr>
                   <th>User Name</th>
                   <th>Organisation Name</th>
                   <th>Requested Permission</th>
                   <th>Status</th>
                 </tr>
               </thead>
               <tbody>
                 <g:each in="${pendinfg_affiliations}" var="a">
                   <tr>
                     <td>${a.user.displayName}</td>
                     <td>${a.org.displayName}</td>
                     <td>${a.role?.value}</td>
                     <td>${a.status?.value}</td>
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
