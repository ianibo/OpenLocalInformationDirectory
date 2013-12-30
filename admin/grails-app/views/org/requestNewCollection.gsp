<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <r:require modules="tlistyle"/>
    <title>TLI::${org.displayName} - Request New Collection</title>
  </head>
  <body>
    &nbsp;<br/>

    <div class="container">
      <ol class="breadcrumb">
        <li> <g:link controller="home" action="index">Home</g:link> <span class="divider"/> </li>
        <li> <g:link controller="org" id="${org.shortcode}">${org.displayName}</g:link></li>
        <li class="active">New Collection</li>
      </ol>
    </div>


   <div class="container">
     <div class="row">
       <div class="col-lg-12">
        <div class="panel panel-default">
           <div class="panel-heading clearfix">
             Create Collection for ${org.displayName}
           </div>
           <div>
         </div>
       </div>
     </div>
   </div>
  
  </body>
</html>
