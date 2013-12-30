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
             &nbsp;<br/>
             <g:form controller="org" id="${org.shortcode}"action="requestNewCollection" role="form" method="post">
               <div class="col-lg-12">
               <fieldset>

                 <div class="control-group">
                   <label class="control-label" for="orgName">New Collection Name</label>
                   <div class="controls">
                     <input type="text" id="collName" Name="collName" class="form-control" placeholder="New Collection Name">
                     <p class="help-block">Name of the new collection. This name will also be used to generate a short code for the collection. The
                                           name should be sufficiently unique to identify the collection in the national context. For example, instead of
                                           "Help Yourself", a more appropriate name would be "Sheffield Help Yourself" - to identify the context of the collection.
                                           National Record Sets should be similarly named, for example "UK Public Libraries Information Offering"</p>
                   </div>
                 </div>

                 <div class="control-group">
                   <label class="control-label" for="orgName">Collection Description</label>
                   <div class="controls">
                     <textarea id="description" Name="description" class="form-control" placeholder="Collection Description"></textarea>
                     <p class="help-block">Brief Description</p>
                   </div>
                 </div>


                 <br/>
                 <div class="control-group">
                   <button class="btn btn-success" name="createCollection" class="form-control">Create Collection</button>
                 </div>
                 <br/>
                 &nbsp;
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
