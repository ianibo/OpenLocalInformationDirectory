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
             Collections owned by ${org.displayName}
             <g:if test="${affiliation?.role?.value='Administrator'}">
               <g:link controller="org" id="${org.shortcode}" action="requestNewCollection" class="btn btn-success btn-xs pull-right">Create Collection</g:link>
             </g:if>
           </div>
           <div>
             <table class="table table-striped table-bordered">
               <g:each in="${collections}" var="c">
                 <tr>
                   <td>
                     <g:link controller="collection" action="index" id="${c.shortcode}">${c.name}</g:link>
                   </td>
                 </tr>
               </g:each>
             </table>
           </div>
         </div>
       </div>
     </div>
   </div>
  
  </body>
</html>
