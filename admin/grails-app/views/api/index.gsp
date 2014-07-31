<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>TLI</title>
  </head>
  <body>

   <div class="container">
     <div class="row">
       <div class="col-lg-12">
         <h1>Local Information Store - Admin
         <img src="${resource(dir: 'images', file: 'ods_transparent.png')}" class="pull-right"/>
         </h1>
       </div>
     </div>
     <div class="row">
       <div class="col-lg-12">
         <h2>TLI API</h2>
         <p>
           This page explains the TLI API calls.
           <ul>
             <li>The <g:link controller="api" action="collections">Collections</g:link> API will allow you to see all the collections available via API calls to the logged in user</li>
             <li>The <g:link controller="api" action="loadOrgs">Load Organistions</g:link> API will allow you to bulk load organisations</li>
           </ul>

         </p>

       </div>
     </div>
   </div>
  
  </body>
</html>
