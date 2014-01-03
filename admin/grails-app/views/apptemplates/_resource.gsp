<r:require modules="tlistyle"/>
<r:require modules="editable"/>

<h3>${d.id ? d.title : 'Create New Directory Entry'}</h3>

<div id="content">

  <dl class="dl-horizontal">
      <dt>Title</dt>
      <dd><g:xEditable type="text" class="ipe" owner="${d}" field="title"/></dd>
  </dl>

  <ul id="tabs" class="nav nav-tabs">
    <li class="active"><a href="#details" data-toggle="tab">Details</a></li>
    <li><a href="#sessions" data-toggle="tab">Sessions</a></li>
  </ul>

  <div id="my-tab-content" class="tab-content">
    <div class="tab-pane active" id="details">

      <dl class="dl-horizontal">
          <dt>Description</dt>
          <dd><g:xEditable type="textarea" class="ipe" owner="${d}" field="description"/></dd>
      </dl>

      <dl class="dl-horizontal">
          <dt>URL</dt>
          <dd><g:xEditable type="text" class="ipe" owner="${d}" field="url"/></dd>
      </dl>

      <g:if test="${d.id != null}">
        <dl class="dl-horizontal">
          <dt>Collections</dt>
          <dd>
            <table class="table table-striped table-bordered table-condensed" id="subjects">
              <thead>
                <tr><th>Name</th></tr>
              </thead>
              <tbody>
                <g:each in="${d.collections}" var="c">
                  <tr><td>${c.name}</td></tr>
                </g:each>
              </tbody>
            </table>
          </dd>

          <dt>Subjects</dt>
          <dd>
            <table class="table table-striped table-bordered table-condensed" id="subjects">
                <thead>
                  <tr><th>Name</th><th>actions</th></tr>
                </thead>
                <tbody>
                  <g:each in="${d.subjects}" var="s">
                    <tr><td>${s.value} (${s.owner.desc})</td><td><a href="">delete</a></td></tr>
                  </g:each>
                </tbody>
            </table>

            <g:form controller="ajaxSupport" action="addToStdCollection" class="form-inline">
              <input type="hidden" name="__context" value="${d.class.name}:${d.id}" />
              <input type="hidden" name="__property" value="subjects" />
              <dt>Add Subject</dt>
              <dd> 
                <g:simpleReferenceTypedown class="input-xxlarge" 
                                           style="width:350px;" 
                                           name="__relatedObject" 
                                           baseClass="tli.RefdataValue" 
                                           data-filter2="Subject"/>
              </dd>
              <dt></dt> <dd> <button type="submit" class="btn btn-primary btn-small">Add</button> </dd>
            </g:form>
          </dd>
        </dl>
    </div>
    <div class="tab-pane" id="sessions">
        <dl class="dl-horizontal">
          <dt>Sessions</dt>
          <dd>
            <table class="table table-striped table-bordered table-condensed" id="subjects">
              <thead>
                <tr><th>Name</th></tr>
              </thead>
              <tbody>
                <g:each in="${d.sessions}" var="s">
                  <tr><td>${s.id}</td></tr>
                </g:each>
              </tbody>
            </table>
            <h3>Add session</h3>
            <g:render template="ical" contextPath="../apptemplates" model="${[d:displayobj]}"/>
          </dd>
        </dl>
      </g:if>


    </div>
  </div>

</div>

<script type="text/javascript">
  $(document).ready(function() {

    $.fn.editable.defaults.mode = 'inline';

    $('#frequency').change( function() {
      $( '.ical div' ).hide();
      $( '#freq'+$(this).val() ).show();
    });

  });
</script>
