<r:require modules="tlistyle"/>
<r:require modules="editable"/>
<r:require modules="jquery-ui"/>
<r:require modules="rrule"/>

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
    <li class="active"><a href="#details" data-toggle="tab">Details</a></li>
    <g:if test="${d.id != null}">
      <li><a href="#sessions" data-toggle="tab">Sessions</a></li>
      <li><a href="#regs" data-toggle="tab">Registrations</a></li>
    </g:if>
  </ul>

  <div id="my-tab-content" class="tab-content">
    <div class="tab-pane active" id="details">

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
                                           baseClass="tli.RefdataValue" 
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
    </div>
    <g:if test="${d.id != null}">
      <div class="tab-pane" id="sessions">
        <dl class="dl-horizontal">
          <dt>Sessions</dt>
          <dd>
            <table class="table table-striped table-bordered table-condensed" id="subjects">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Start Time</th>
                  <th>End Time</th>
                  <th>Recurrence Rule</th>
                  <th>Text Recurrence</th>
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
                    <td>${s.rrule}</td>
                    <td>${s.trrule}</td>
                    <td></td>
                  </tr>
                </g:each>
              </tbody>
            </table>

            Add Session<hr/>
            <g:form controller="ajaxSupport" action="addToCollection" class="form-inline">
              <input type="hidden" name="__context" value="${d.class.name}:${d.id}"/>
              <input type="hidden" name="__newObjectClass" value="tli.TliSession"/>
              <input type="hidden" name="__recip" value="owner"/>
              <dl>
                <dt>Name</dt>
                <dd><input type="text" name="name"/></dd>
                <dt>Description</dt>
                <dd><input type="textarea" name="description"/></dd>
                <dt>Start Time</dt>
                <dd><input type="text" name="startTime"/></dd>
                <dt>End Time</dt>
                <dd><input type="text" name="endTime"/></dd>
                <dt>Recurrence</dt>
                <dd colspan="2"> <g:render template="ical" contextPath="../apptemplates" model="${[d:displayobj,fname:'rrule',tfname:'trrule']}"/>
                </dd>
                <dt>Location</dt>
                <dd>
                  <div class="dropdown">
                    btn: <a href="#" id="fishy" class="dropdown-toggle rowlink" data-toggle="dropdown">Dropdown<span class="caret"></span></a>aa
                    <table>
                      <thead>
                        <tr>
                          <td>Postcode</td>
                          <td>House Name</td>
                          <td>Number</td>
                          <td>Street</td>
                          <td>Town/City</td>
                          <td>Region</td>
                          <td>Country</td>
                        </tr>
                      </thead>
                      <tbody>
                        <tr class="rowlink">
                          <td><input type="text" class="addrtd"/></td>
                          <td><input type="text"/></td>
                          <td><input type="text"/></td>
                          <td><input type="text"/></td>
                          <td><input type="text"/></td>
                          <td><input type="text"/></td>
                          <td><input type="text"/></td>
                          <td>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Action aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</a></li>
                      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Another action</a></li>
                      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Something else here</a></li>
                      <li role="presentation" class="divider"></li>
                      <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Separated link</a></li>
                    </ul>
                  </div>
                </dd>
                <dt>
                </dt>
                <dd>
                    <button type="submit">Add Session</button>
                </dd>
              </dl>
            </g:form>
          </dd>
        </dl>
      </div>
      <div class="tab-pane" id="regs">
        <dl class="dl-horizontal">
            <dt>Registered Charity#</dt>
            <dd><g:xEditable type="text" class="ipe" owner="${d}" field="registeredCharityNo"/></dd>
        </dl>
      </div>
    </g:if>
  </div>

</div>

<script type="text/javascript">
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

    $('.addrtd').bind('input', function(e) { 
        // $(this).val() // get the current value of the input field.
        $('#fishy').dropdown('toggle');
        $(this).focus();
    });
  });

</script>
