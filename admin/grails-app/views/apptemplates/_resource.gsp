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
    </div>
  </div>

  <!--  template="componentStatus" contextPath="../apptemplates" model="${[d:displayobj, rd:refdata_properties, dtype:'KBComponent']}" -->
</div>

<script type="text/javascript">
  $(document).ready(function() {
    $.fn.editable.defaults.mode = 'inline';
  });
</script>
