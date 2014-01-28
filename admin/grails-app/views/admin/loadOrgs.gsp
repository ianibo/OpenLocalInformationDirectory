<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <r:require modules="tlistyle"/>
    <title>TLI::Admin::Load Vocabulary</title>
  </head>
  <body>

   <br/>
   <div class="container">
     <div class="row">
       <div class="col-md-12">
         <div class="panel panel-default">
           <div class="panel-heading clearfix">
             Load Organisations
           </div>
           <div class="panel-body">
             &nbsp;<br/>
             <g:form controller="admin" action="loadOrgs" role="form" method="post">
               <div class="col-lg-12">
               <fieldset>
                 <div class="control-group">
                   <label class="control-label" for="vocabCode">Upload File</label>
                   <div class="controls">
                     <input type="file" id="orgscsv" name="orgscsv"/>
                     <p class="help-block"></p>
                   </div>
                 </div>
                 <input type="submit"/>
               </fieldset>
               </div>
             </g:form>

           </div>
         </div>
       </div>
     </div>
   </div>
  
  </body>
</html>
