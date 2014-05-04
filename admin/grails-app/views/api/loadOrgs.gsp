<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <r:require modules="tlistyle"/>
    <title>TLI::API::Bulk Load Organisations</title>
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
             <g:form controller="api" action="loadOrgs" role="form" method="post" enctype="multipart/form-data">
               <div class="col-lg-12">
               <fieldset>
                 <div class="control-group">
                   <label class="control-label" for="orgscsv">Upload File</label>
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
     <div class="row">
       <div class="col-md-12">
         This function allows you to upload a .csv file containing organisations. The bulk load function expects a csv file containing the following columns: 
         <ul>
           <li>Unique Reference - a mandatory column (Which may be blank) headed up as unique_reference. This column is used on repeated bulk uploads to detect duplicates. If no value
               is supplied the system will generate a unique reference based on the name</li>
           <li>Org Name - a mandatory column which may not be blank. Used as the org name</li>
           <li>Email - A mandatory column which may be blank but which is strongly reccommended. This email address will be used to bootstrap the organisational membership
               process</li>
           <li>URL - a mandatory column which may be blank - URL of the organisation</li>
           <li>Publication Scheme</li>
         </ul>
         An example file is posted below. These contents must be saved in a stand-alone file and uploaed using the form above.
         <pre>
unique_reference,org_name,email,url,publication_scheme
Org0001,Open Local Directory Test Organisation, some.user@some.domain,
UKCT,Collections Trust,office@collectionstrust.org.uk,http://www.collectionstrust.org.uk/,
         </pre>
       </div>
     </div>
   </div>
  
  </body>
</html>
