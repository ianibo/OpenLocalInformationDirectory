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
             Load Vocabulary
           </div>
           <div class="panel-body">
             &nbsp;<br/>
             <g:form controller="admin" action="loadVocabulary" role="form" method="post">
               <div class="col-lg-12">
               <fieldset>

                 <div class="control-group">
                   <label class="control-label" for="vocabCode">Vocab Code</label>
                   <div class="controls">
                     <input type="text" id="vocabCode" Name="vocabCode" class="form-control" placeholder="Vocab URL">
                     <p class="help-block"></p>
                   </div>
                 </div>

                 <div class="control-group">
                   <label class="control-label" for="vocabBaseUrl">Vocab Base URL</label>
                   <div class="controls">
                     <input type="text" id="vocabBaseUrl" Name="vocabBaseUrl" class="form-control" placeholder="Vocab Base URL">
                     <p class="help-block">eg http://some.server/</p>
                   </div>
                 </div>

                 <div class="control-group">
                   <label class="control-label" for="vocabPath">Vocab Path</label>
                   <div class="controls">
                     <input type="text" id="vocabPath" Name="vocabPath" class="form-control" placeholder="Vocab Path">
                     <p class="help-block">eg path/to/vocab.xml (Note no leading /)</p>
                   </div>
                 </div>

                 <div class="control-group">
                   <button class="btn btn-success" name="loadVocab" class="form-control">Load Vocab</button>
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
