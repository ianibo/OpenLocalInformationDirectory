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
         <h1>Record Upload</h1>
         <img src="${resource(dir: 'images', file: 'ods_transparent.png')}" class="pull-right"/>
       </div>
     </div>
     <div class="row">
       <div class="col-lg-12">
        <g:form controller="api" action="upload" method="post" enctype="multipart/form-data">
          Select file to upload: <input type="file" id="tf" name="tf"/><br/>
          <button type="submit" class="btn btn-primary">Upload SO</button>
        </g:form>

        <h3>Upload file format</h3>
        <p>
        </p>
       </div>
     </div>
   </div>
  
  </body>
</html>
