<r:require modules="tlistyle"/>
<r:require modules="editable"/>

<div class="container">
  <form class="form-horizontal" role="form" method="get">
    <input type="hidden" name="qbe" value="${params.qbe}"/>
    <fieldset>
      <g:each in="${formdefn}" var="fld">
        <div class="form-group">
          <label class="col-sm-3 control-label" for="${fld.qparam}">${fld.prompt}</label>
          <div class="col-sm-7">
            <g:if test="${fld.type=='lookup'}">
              <g:simpleReferenceTypedown id="refdata_combo_${fld.qparam}"
                                         class="input-xxlarge" 
                                         style="width:350px;" 
                                         name="${fld.qparam}" 
                                         baseClass="${fld.baseClass}"
                                         filter1="${fld.filter1?:''}"
                                         value="${params[fld.qparam]}"
                                         addBlankValue="yes" />
            </g:if>
            <g:else>
              <input type="text" name="${fld.qparam}" id="${fld.qparam}" placeholder="${fld.placeholder}" value="${params[fld.qparam]}">
            </g:else>
          </div>
        </div>
      </g:each>

      <div class="form-group">
        <div class="col-sm-3"></div>
        <div class="col-sm-7">
          <button type="submit" class="btn btn-primary">Search</button>
        </div>
      </div>

    <fieldset>
  </form>
</div>
