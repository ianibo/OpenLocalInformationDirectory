<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>TLI::${org.displayName}</title>
  </head>
  <body>

   <div class="container">
     <div class="row">
       <div class="col-lg-12">
        <div class="panel panel-default">
           <div class="panel-heading clearfix">
             Collections
             <g:link controller="org" action="requestNew" class="btn btn-success btn-xs pull-right">Create Collection</g:link>
           </div>
           <div>
             colls: ${collections}
             <ul class="media-list">
               <g:each in="${collections}" var="c">
                 <li class="media">
                   <div class="media-body">
                     <g:link controller="collection" action="index" id="${c.shortcode}">${c.name}</g:link>
                   </div>
                 </li>
               </g:each>
             </ul>
           </div>
         </div>
       </div>
     </div>
   </div>
  
  </body>
</html>
