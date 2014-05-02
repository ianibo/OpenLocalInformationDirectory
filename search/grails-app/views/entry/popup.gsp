<r:require modules="bootstrap"/>

    <div class="content">
      <div class="container">
        <div class="row">
          <div class="span9" itemscope itemtype="directoryEntry">

          <div>
            <h1 itemprop="name">${record.source.title}</h1>
            <p itemprop="description">${record.source.description}</p>

            <dl>
              <dt>Address</dt>
              <g:if test="${record.source.sessions && ( record.source.sessions.size() > 0 ) }">
              <dd itemprop="address">${record.source.sessions[0].location?.buildingName} ${record.source.sessions[0].location?.buildingNumber} ${record.source.sessions[0].location?.street} ${record.source.sessions[0].location?.city} ${record.source.sessions[0].location?.county} ${record.source.sessions[0].location?.postcode}</dd>
              </g:if>
              <g:if test="${record.source.contactEmail}"><dt>Email</td><dd><a href="mailto:${record.source.contactEmail}" itemprop="email">${record.source.contactEmail}</a></dd></g:if>
              <g:if test="${record.source.contactTelephone}"><dt>Telephone</td><dd><a href="mailto:${record.source.contactTelephone}" itemprop="telephone">${record.source.contactTelephone}</a></dd></g:if>
              <g:if test="${record.source.url}"><dt>Website</td><dd><a href="${record.source.url}" itemprop="url">${record.source.url}</a></dd></g:if>
              <dt>Subjects</dt>
              <dd><g:each in="${record.source.subjects}" var="s">
                <span class="badge">${s.subjname}</span>
              </g:each></dt>
              <dt>Categories</dt>
              <dd><g:each in="${record.source.categories}" var="s">
                <span class="badge">${s.catid}</span>
              </g:each></dt>
            </dl>
            <br/>

            <dl>
              <dt>Sessions</dt>
              <dd>
                <table class="table table-striped">
                  <tr>
                    <th colspan="5">Name &amp; Description</th>
                  </tr>
                  <tr>
                    <th>When</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Indicative Cost</th>
                    <th>Location</th>
                  </tr>
                  <g:each in="${record.source.sessions}" var="s">
                    <tr>
                      <td colspan="5"><h3>${s.name}</h3><br/>
                          ${s.description}</td>
                    </tr>
                    <tr>
                      <td>${s.trrule}</td>
                      <td>${s.startTime}</td>
                      <td>${s.endTime}</td>
                      <td>${s.indicativeCost}</td>
                      <td>${s.location?.buildingName} ${s.location?.buildingNumber} ${s.location?.street} ${s.location?.city} ${s.location?.county} ${s.location?.postcode}</td>
                    </tr>
                  </g:each>
                </table>
             
              </dd>

            </dl>
          </div>
        </div>
      </div>
    </div>
