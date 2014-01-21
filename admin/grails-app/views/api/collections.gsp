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

       </div>
     </div>
   </div>
  
  </body>
</html>
