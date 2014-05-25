<r:require modules="bootstrap"/>

    <div class="content">
      <div class="container">
        <div class="row">
          <div class="span9" itemscope itemtype="directoryEntry">

          <div>
            <h1 itemprop="name"><g:link controller="entry" action="index" id="${record.source.canonical_shortcode}">${record.source.title}</g:link></h1>
            <p itemprop="description">${record.source.description}</p>

            <dl>
              <dt>Address</dt>
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
              <dt>Places, Days and Times</dt>
              <dd>
                <g:each in="${record.source.sessions}" var="s">
                  <div>
                    <div>
                    <h3>${s.name}</h3>
                      ${s.description?:'No Description'}
                      <div class="container">
                      <dl>
                        <dt>Location</dt><dd>${s.location?.buildingName} ${s.location?.buildingNumber} ${s.location?.street} ${s.location?.city} ${s.location?.county} ${s.location?.postcode}</dd>
                        <dt>Days</dt><dd>${s.trrule?:'Unknown'}</dd>
                        <dt>Times</dt><dd>Start: ${s.startTime?:'Unknown'}, End: ${s.endTime?:'Unknown'}</dd>
                        <dt>Indicative Cost</dt><dd>${s.indicativeCost?:'Unknown'}</dd>
                      </dl>
                      </div>
                    </div>
                  </div>
                </g:each>
              </dd>

            </dl>
          </div>
        </div>
      </div>
    </div>
