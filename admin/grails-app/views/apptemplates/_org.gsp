
<h3>${d.id ? d.displayName : 'Create New Organisation'}</h3>

<div id="content">

  <dl class="dl-horizontal">
      <dt>Organisation Name</dt>
      <dd><g:xEditable class="ipe" owner="${d}" field="displayName"/></dd>

      <dt>URL</dt>
      <dd><g:xEditable class="ipe" owner="${d}" field="url"/></dd>

      <dt>Email</dt>
      <dd><g:xEditable class="ipe" owner="${d}" field="email"/></dd>

      <dt>Notes</dt>
      <dd><g:xEditable class="ipe" owner="${d}" field="notes"/></dd>

      <dt>Status</dt>
      <dd><g:xEditableRefData owner="${d}" field="status" config='status' /></dd>
  </dl>

  <ul id="tabs" class="nav nav-tabs">
    <li class="active"><a href="#details" data-toggle="tab">Details</a></li>
  </ul>

  <div id="my-tab-content" class="tab-content">
    <div class="tab-pane active" id="details">
      <dl class="dl-horizontal">
          <dt>Twitter</dt>
          <dd><g:xEditable class="ipe" owner="${d}" field="twitter"/></dd>
          <dt>Facebook</dt>
          <dd><g:xEditable class="ipe" owner="${d}" field="facebook"/></dd>
          <dt>Publication Scheme</dt>
          <dd><g:xEditable class="ipe" owner="${d}" field="pubScheme"/></dd>
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
