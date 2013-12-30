<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <r:require modules="tlistyle"/>
    <title>TLI::${org.displayName}</title>
  </head>
  <body>
    &nbsp;<br/>

    <div class="container">
      <ol class="breadcrumb">
        <li> <g:link controller="home" action="index">Home</g:link> <span class="divider"/> </li>
        <li class="active">${org.displayName}</li>
      </ol>
    </div>


   <div class="container">
     <div class="row">
       <div class="col-lg-12">
        <div class="panel panel-default">
           <div class="panel-heading clearfix">
             Collections
             <g:if test="${affiliation?.role?.value='Administrator'}">
               <g:link controller="org" id="${org.shortcode}" action="requestNewCollection" class="btn btn-success btn-xs pull-right">Create Collection</g:link>
             </g:if>
           </div>
           <div>
         </div>
       </div>
     </div>
   </div>
  
  </body>
</html>
