<h3>${d.id ? d.buildingName : 'Create New Address or Location'}</h3>

<div id="content">

  <dl class="dl-horizontal">
      <dt>Building Name</dt>
      <dd><g:xEditable class="ipe" owner="${d}" field="buildingName"/></dd>
  </dl>

</div>

<script type="text/javascript">
  $(document).ready(function() {
    $.fn.editable.defaults.mode = 'inline';
  });
</script>
