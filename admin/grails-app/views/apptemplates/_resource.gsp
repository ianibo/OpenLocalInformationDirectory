<g:if test="${d.id == null}">
  <g:if test="${params.collection!=null}">
    <script language="javascript">
      var editable_defaults = {
        'collections':'${params.collection}'
      };  
    </script>
  </g:if>
</g:if>

<ul class="nav nav-pills">
<g:each in="${d.collections}" var="c">
  <li><g:link controller="collection" action="index" id="${c.shortcode}">${c.name}</g:link></li>
</g:each>
</ul>

<h3>${d.id ? d.title : 'Create New Directory Entry'}</h3>

<div id="content">

  <dl class="dl-horizontal">
      <dt>Title</dt>
      <dd><g:xEditable type="text" class="ipe" owner="${d}" field="title"/></dd>
  </dl>

  <dl class="dl-horizontal">
      <dt>Description</dt>
      <dd><g:xEditable type="textarea" class="ipe" owner="${d}" field="description"/></dd>
  </dl>

  <dl class="dl-horizontal">
      <dt>Primary URL</dt>
      <dd><g:xEditable type="text" class="ipe" owner="${d}" field="url"/></dd>
  </dl>

  <dl class="dl-horizontal">
      <dt>Status</dt>
      <dd><g:xEditableRefData owner="${d}" field="status" config='entrystatus' /></dd>
  </dl>


  <ul id="tabs" class="nav nav-tabs">
    <li class="active"><a href="#contact" data-toggle="tab">Contact Information</a></li>
    <li><a href="#details" data-toggle="tab">Details</a></li>
    <g:if test="${d.id != null}">
      <li><a href="#timesandplaces" data-toggle="tab">Times and Places</a></li>
      <li><a href="#regs" data-toggle="tab">Registrations</a></li>
      <li><a href="#dp" data-toggle="tab">Data Protection</a></li>
    </g:if>
  </ul>

  <div id="my-tab-content" class="tab-content">
    <div class="tab-pane" id="details">
      <p>
  
        <dl class="dl-horizontal">
            <dt>Source Reference</dt>
            <dd><g:xEditable type="text" class="ipe" owner="${d}" field="sourceReference"/></dd>
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
                    <tr><td><g:link controller="collection" action="index" id="${c.shortcode}">${c.name}</g:link></td></tr>
                  </g:each>
                </tbody>
              </table>
              <g:form controller="ajaxSupport" action="addToStdCollection" class="form-inline">
                <input type="hidden" name="__context" value="${d.class.name}:${d.id}" />
                <input type="hidden" name="__property" value="collections" />
                <dt>Add Collection</dt>
                <dd>
                  <g:simpleReferenceTypedown class="input-xxlarge" 
                                             style="width:350px;" 
                                             name="__relatedObject" 
                                             baseClass="tli.TliCollection" />
                </dd>
                <dt></dt> <dd> <button type="submit" class="btn btn-primary btn-small">Add</button> </dd>
              </g:form>
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
                                             baseClass="me.ianibbo.common.RefdataValue" 
                                             data-filter2="Subject"/>
                </dd>
                <dt></dt> <dd> <button type="submit" class="btn btn-primary btn-small">Add</button> </dd>
              </g:form>
              <br/>
            </dd>
            
            <dt>Categories</dt>
            <dd>
              <table class="table table-striped table-bordered table-condensed" id="subjects">
                  <thead>
                    <tr><th>Category</th><th>actions</th></tr>
                  </thead>
                  <tbody>
                    <g:each in="${d.categories}" var="s">
                      <tr><td>${s.value} (${s.owner.desc})</td><td><a href="">delete</a></td></tr>
                    </g:each>
                  </tbody>
              </table>
  
              <g:form controller="ajaxSupport" action="addToStdCollection" class="form-inline">
                <input type="hidden" name="__context" value="${d.class.name}:${d.id}" />
                <input type="hidden" name="__property" value="categories" />
                <dt>Add Subject</dt>
                <dd>
                  <g:simpleReferenceTypedown class="input-xxlarge" 
                                             style="width:350px;" 
                                             name="__relatedObject" 
                                             baseClass="me.ianibbo.common.RefdataValue" 
                                             data-filter2="Subject"/>
                </dd>
                <dt></dt> <dd> <button type="submit" class="btn btn-primary btn-small">Add</button> </dd>
              </g:form>
              <br/>
            </dd>
  
          </dl>
        </g:if>
        <g:else>
          <p>Other properties will become editable once you have saved the record</p>
        </g:else>
      </p>
    </div>
    <div class="tab-pane active" id="contact">
      <p>
        <dl class="dl-horizontal">
            <dt>Contact Name</dt>
            <dd><g:xEditable type="text" class="ipe" owner="${d}" field="contactName"/></dd>
        </dl>
        <dl class="dl-horizontal">
            <dt>Contact Email</dt>
            <dd><g:xEditable type="text" class="ipe" owner="${d}" field="contactEmail"/></dd>
        </dl>
        <dl class="dl-horizontal">
            <dt>Contact Telephone</dt>
            <dd><g:xEditable type="text" class="ipe" owner="${d}" field="contactTelephone"/></dd>
        </dl>
        <dl class="dl-horizontal">
            <dt>Contact Fax</dt>
            <dd><g:xEditable type="text" class="ipe" owner="${d}" field="contactFax"/></dd>
        </dl>
        <dl class="dl-horizontal">
            <dt>Facebook Link</dt>
            <dd><g:xEditable type="text" class="ipe" owner="${d}" field="facebook"/></dd>
        </dl>
        <dl class="dl-horizontal">
            <dt>Twitter</dt>
            <dd><g:xEditable type="text" class="ipe" owner="${d}" field="twitter"/></dd>
        </dl>
      </p>
    </div>

    <g:if test="${d.id != null}">
      <div class="tab-pane" id="timesandplaces">
        <p>
        <dl class="dl-horizontal">
          <dt>Times and Places</dt>
          <dd>
            <table class="table table-striped table-bordered table-condensed" id="subjects">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Start Time</th>
                  <th>End Time</th>
                  <th>Text Recurrence</th>
                  <th>Recurrence</th>
                  <th>Location</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <g:each in="${d.sessions}" var="s">
                  <tr>
                    <td>${s.name}</td>
                    <td>${s.description}</td>
                    <td>${s.startTime}</td>
                    <td>${s.endTime}</td>
                    <td>${s.trrule}</td>
                    <td>${s.rrule!=null?'OK':'Error'}</td>
                    <td>${s.location.toString()}</td>
                    <td></td>
                  </tr>
                </g:each>
              </tbody>
            </table>

            Add Session<hr/>
            <g:form controller="ajaxSupport" action="addToCollection" class="container-fluid">
              <input type="hidden" name="__context" value="${d.class.name}:${d.id}"/>
              <input type="hidden" name="__newObjectClass" value="tli.TliSession"/>
              <input type="hidden" name="__recip" value="owner"/>
              
                <div class="row">
                  <div class="col-md-6">
                    <dl>
                      <dt>Name</dt>
                      <dd><input type="text" name="name" class="form-control"/></dd>
                      <dt>Description</dt>
                      <dd><input type="textarea" name="description" class="form-control"/></dd>
                      <dt>Start Time</dt>
                      <dd><input type="text" name="startTime" class="form-control"/></dd>
                      <dt>End Time</dt>
                      <dd><input type="text" name="endTime" class="form-control"/></dd>
                    </dl>
                  </div>
                  <div class="col-md-6">
                    <dl>
                      <dt>Recurrence</dt>
                      <dd> <g:render template="ical" contextPath="../apptemplates" model="${[d:displayobj,fname:'rrule',tfname:'trrule']}"/> </dd>
                    </dl>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <dl>
                      <dt>Location</dt>
                      <dd>
                        <span id="SelectedLocation"><b>None Selected, use the fields below to select an existing address, or create a new one</b></span><br/>
                        <input id="location" type="hidden" name="location" value=""/>
                        <div id="ddwrap" class="dropdown">
                          <a href="#" id="fishy" class="dropdown-toggle rowlink" data-toggle="dropdown"></a>
                          <table>
                            <thead>
                              <tr>
                                <td>Postcode</td>
                                <td>Bulding Name</td>
                                <td>Building Number</td>
                                <td>Street</td>
                                <td>Town/City</td>
                                <td>Region</td>
                                <td>Country</td>
                              </tr>
                            </thead>
                            <tbody>
                              <tr class="rowlink">
                                <td><input id="__adPostcode" type="text" class="addrtd form-control"/></td>
                                <td><input id="__adBuildingName" type="text" class="addrtd form-control"/></td>
                                <td><input id="__adBuildingNumber" type="text" class="addrtd form-control"/></td>
                                <td><input id="__adStreet" type="text" class="addrtd form-control"/></td>
                                <td><input id="__adTown" type="text" class="addrtd form-control"/></td>
                                <td><input id="__adRegion" type="text" class="addrtd form-control"/></td>
                                <td><input id="__adCountry" type="text" class="addrtd form-control"/></td>
                                <td><button type="button" id="createAddrBtn" name="createAddress">Create</button></td>
                              </tr>
                            </tbody>
                          </table>
                          <ul id="addrdropdown" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                          </ul>
                        </div>
                      </dd>
                      <dt>
                      </dt>
                      <dd>
                          <button type="submit">Add Session</button>
                      </dd>
                    </dl>
                  </div>
                </div>
            </g:form>
          </dd>
        </dl>
        </p>
      </div>
      <div class="tab-pane" id="regs">
        <p>
          <dl class="dl-horizontal">
            <dt>Registered Charity#</dt>
            <dd><g:xEditable type="text" class="ipe" owner="${d}" field="registeredCharityNo"/></dd>
          </dl>
        </p>
      </div>
      <div class="tab-pane" id="dp">
        <table class="table table-striped table-bordered table-condensed" id="subjects">
          <thead>
            <tr>
              <th>Party</th>
              <th>Role</th>
            </tr>
          </thead>
          <tbody>
            <g:each in="${d.owners}" var="o">
              <tr>
                <td>${o.party.displayName}</td>
                <td>${o.role.value}</td>
              </tr>
            </g:each>
          </tbody>
        </table>
      </div>
    </g:if>
  </div>

</div>

<script type="text/javascript">

  function addNN(arr,val) {
    if ( val != null ) {
      arr.push(val);
    }
  }

  function selectLocation(oid,desc) {
    $('#SelectedLocation').html("<b>"+desc+"</b>");
    $('#location').val(oid);
    $('#__adPostcode').val('');
    $('#__adBuildingName').val('');
    $('#__adBuildingNumber').val('');
    $('#__adStreet').val('');
    $('#__adTown').val('');
    $('#__adRegion').val('');
    $('#__adCountry').val('');
  }

  $(document).ready(function() {

    $.fn.editable.defaults.mode = 'inline';

    $('#frequency').change( function() {
      $( '.ical div' ).hide();
      $( '#freq'+$(this).val() ).show();
    });

    $("input.hdp").datepicker({
      buttonImage: '../../images/calendar.gif',
      buttonImageOnly: true,
      changeMonth: true,
      changeYear: true,
      showOn: 'both',
      onSelect: function(dateText, inst) {
        inst.input.parent().find('span').html(dateText)
      }
    });

    $('form').attr('autocomplete', 'off');

    $('.addrtd').bind('input', function(e) { 
        // $(this).val() // get the current value of the input field.
        var orig = $(this);
        var num_addresses = 0;
        // Search for addresses
        var jqxhr = $.ajax( { url: "<g:createLink controller='search' action='index'/>",
                            type:"POST",
                            data:{
                              qbe:'g:locations',
                              format:'json',
                              qp_postcode: $('#__adPostcode').val(),
                              qp_buildname: $('#__adBuildingName').val(),
                              qp_buildnum: $('#__adBuildingNumber').val(),
                              qp_street: $('#__adStreet').val(),
                              qp_city: $('#__adTown').val(),
                              qp_region: $('#__adRegion').val(),
                              qp_country: $('#__adCountry').val(),
                            } })
        .done(function(resp) {
          num_addresses = parseInt(resp.count);
          if ( num_addresses > 0 ) {
            if ( $('#ddwrap').hasClass('open') ) {
              orig.focus();
            }
            else {
              $('#fishy').dropdown('toggle');
              orig.focus();
            }

            $('#addrdropdown').empty();
            for (var r in resp.records) {
              var arr = []
              addNN(arr,resp.records[r]['Building Name']);
              addNN(arr,resp.records[r]['Building Number']);
              addNN(arr,resp.records[r]['Street']);
              addNN(arr,resp.records[r]['Postcode']);
              addNN(arr,resp.records[r]['City']);
              addNN(arr,resp.records[r]['Region']);
              addNN(arr,resp.records[r]['Country']);
              
              var s = arr.join();
              var oid = resp.records[r]["oid"]
              var onclick="selectLocation('"+oid+"','"+s+"')";

              $('#addrdropdown').append('<li role="presentation"><a role="menuitem" tabindex="-1" href="#" onClick="'+onclick+'">'+s+'</a></li>');
            }
 
          }
          else {
          }
        })
        .fail(function() {
        })
        .always(function() {
        });

        if ( $('#ddwrap').hasClass('open') ) {
          $('#fishy').dropdown('toggle');
          $(this).focus();
        }
    });

    $('#createAddrBtn').click(function(){
      // Call the create controller process action with domain=tli.TliLocation, and then name:value pairs for the properties
      // Get result.id from json return - thats the OID to use.
      var jqxhr = $.ajax( { url: "<g:createLink controller='create' action='process'/>",
                            type:"POST",
                            data:{
                              cls:'tli.TliLocation',
                              postcode:$('#__adPostcode').val(),
                              buildingName:$('#__adBuildingName').val(),
                              buildingNumber:$('#__adBuildingNumber').val(),
                              street:$('#__adStreet').val(),
                              city:$('#__adTown').val(),
                              region:$('#__adRegion').val(),
                              country:$('#__adCountry').val()
                            } })
        .done(function(resp) {
          $('#SelectedLocation').html(resp.str);
          $('#location').val(resp.id);
          $('#__adPostcode').val('');
          $('#__adBuildingName').val('');
          $('#__adBuildingNumber').val('');
          $('#__adStreet').val('');
          $('#__adTown').val('');
          $('#__adRegion').val('');
          $('#__adCountry').val('');
        })
        .fail(function() {
        })
        .always(function() {
        });

      return false;
    });
  });


</script>
