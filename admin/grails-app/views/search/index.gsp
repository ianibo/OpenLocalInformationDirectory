<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<r:require modules="tlistyle,bootstrap-popover" />
<title>TLI Search</title>
<style>
#modal {
  width: 900px; /* SET THE WIDTH OF THE MODAL */
  margin-left: -450px;
}

#modal .modal-body {
  max-height: 800px;
}
</style>
</head>
<body>

  <div class="container">
    <div class="row">
      <div id="mainarea" class="${displayobj != null ? 'col-md-6' : 'col-md-12'}">

        <g:if test="${qbetemplate.customHeaderInclude!=null}">
          <g:render template="${qbetemplate.customHeaderInclude}" 
                    contextPath="../apptemplates" 
                    model="${[qbeConfig:qbetemplate.qbeConfig, rows:recset, offset:offset, det:det]}" />
        </g:if>

        <div class="well">

          <g:if test="${qbetemplate==null}">
              Please select a template from the navigation menu<br/>
              <pre>${params}</pre>
            </g:if>
          <g:else>

            <nav class="navbar navbar-default" role="navigation">

                <ul class="nav navbar-nav navbar-left">
                  <li>
                    <p class="navbar-text"><span class="brand">${qbetemplate.title?:'Search'}</brand>
                    <g:if test="${recset != null}"> : Records ${offset+1} to ${lasthit} of ${reccount}
                  </g:if></p>
                  </li>
                </ul>
                  
                <ul class="nav navbar-nav navbar-right">
                  <li><g:link title="Previous Page" controller="search"
                        action="index"
                        params="${params+[offset:(offset-max),det:null]}">
                        <i class="icon-chevron-left"></i>
                      </g:link></li>
                  <li class="divider-vertical"></li>
                  <li><g:link title="Next Page" controller="search"
                        action="index"
                        params="${params+[offset:(offset+max),det:null]}">
                        <i class="icon-chevron-right"></i>
                      </g:link></li>
                </ul>

            </nav>

            <g:if test="${(qbetemplate.message != null)}">
              <p style="text-align:center"><bootstrap:alert class="alert-info">${qbetemplate.message}</bootstrap:alert></p>
            </g:if>
            <g:if test="${!request.isAjax()}" >
	            <g:render template="qbeform" contextPath="../search"
	              model="${[formdefn:qbetemplate.qbeConfig?.qbeForm]}" />
	          </g:if>
            <g:if test="${recset != null}">
              <g:render template="qberesult" contextPath="../search"
                model="${[qbeConfig:qbetemplate.qbeConfig, rows:recset, offset:offset, det:det]}" />
            </g:if>
          </g:else>
        </div>
      </div>

      <g:if test="${displayobj != null}">
        <div id="resultsarea" class="col-md-6">
          <div class="well">

            <div class="navbar">
              <div class="navbar-inner">
                <div class="brand">
                  Record
                  ${det}
                  of
                  ${reccount}
                </div>
                <ul class="nav pull-right">

                  <li><a data-toggle="modal" data-cache="false"
                    title="Show History"
                    data-remote='<g:createLink controller="fwk" action="history" id="${displayobj.class.name}:${displayobj.id}"/>'
                    data-target="#modal"><i class="icon-time"></i></a></li>

                  <li><a data-toggle="modal" data-cache="false"
                    title="Show Notes"
                    data-remote='<g:createLink controller="fwk" action="notes" id="${displayobj.class.name}:${displayobj.id}"/>'
                    data-target="#modal"><i class="icon-comment"></i></a></li>

                  <li><g:link controller="search" title="Previous Record"
                      action="index"
                      params="${params+['det':det-1, offset:((int)((det-2) / max))*max]}">
                      <i class="icon-chevron-left"></i>
                    </g:link></li>
                  <li><g:link controller="search" title="Next Record"
                      action="index"
                      params="${params+['det':det+1, offset:((int)(det / max))*max]}">
                      <i class="icon-chevron-right"></i>
                    </g:link></li>
                </ul>
              </div>
            </div>
            <g:if test="${displaytemplate != null}">
              <g:if test="${displaytemplate.type=='staticgsp'}">
               
                <g:render template="${displaytemplate.rendername}"
                          contextPath="../apptemplates"
                          model="${[d:displayobj, rd:refdata_properties, dtype:displayobjclassname_short]}" />

              </g:if>
            </g:if>
            <g:else>
                No template currenly available for instances of ${displayobjclassname}
              ${displayobj as grails.converters.JSON}
            </g:else>
          </div>
        </div>
      </g:if>

    </div>
  </div>

  <div id="modal" class="qmodal modal hide fade" role="dialog">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal"
        aria-hidden="true">×</button>
      <h3 id="myModalLabel">Modal header</h3>
    </div>
    <div class="modal-body"></div>
    <div class="modal-footer">
      <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">Close</button>
    </div>
  </div>

  <script language="javascript">
      $('#modal').on('hidden', function() {
        $(this).data('modal').$element.removeData();
      })
    </script>
</body>
</html>
