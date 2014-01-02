<r:require modules="tlistyle"/>
<r:require modules="editable"/>

<h3>${d.id ? d.getNiceName() + ': ' + (d.name ?: d.id) : 'Create New ' + d.getNiceName()}</h3>

<div id="content">

  <dl class="dl-horizontal">
      <dt>Organisation Name</dt>
      <dd><g:xEditable class="ipe" owner="${d}" field="displayName"/></dd>
  </dl>

  <ul id="tabs" class="nav nav-tabs">
    <li class="active"><a href="#details" data-toggle="tab">Details</a></li>
  </ul>

  <div id="my-tab-content" class="tab-content">
    <div class="tab-pane active" id="details">
    </div>
  </div>

  <!--  template="componentStatus" contextPath="../apptemplates" model="${[d:displayobj, rd:refdata_properties, dtype:'KBComponent']}" -->
</div>

<script type="text/javascript">
  $(document).ready(function() {
    $.fn.editable.defaults.mode = 'inline';
  });
</script>
