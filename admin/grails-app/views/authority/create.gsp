<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <r:require modules="tlistyle"/>
    <title>Create Authority</title>
  </head>
  <body>

   <div class="container">
     <div class="row">
       <div class="col-lg-12">
         <div class="panel-heading clearfix">
           <h3>Create Authority</h3>
           <div class="panel-body">
             <g:form role="form" method="post" controller="authority" action="create">
               <div class="form-group">
                 <label for="code">Authority Code</label>
                 <input type="text" class="form-control" id="code" name="code" placeholder="Authority Code">
                 <span class="help-block">A block of help text that breaks onto a new line and may extend beyond one line.</span>
               </div>
               <div class="form-group">
                 <label for="exampleInputPassword1">Description</label>
                 <input type="text" class="form-control" name="desc" id="desc" placeholder="Description">
                 <span class="help-block">A block of help text that breaks onto a new line and may extend beyond one line.</span>
               </div>
               <button type="submit" class="btn btn-default">Submit</button>
             </g:form>
           </div>
         </div>
       </div>
     </div>
   </div>
  
  </body>
</html>
