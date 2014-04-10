<html>
<%
  def addFacet = { params, facet, val ->
    def newparams = [:]
    newparams.putAll(params)
    def current = newparams[facet]
    if ( current == null ) {
      newparams[facet] = val
    }
    else if ( current instanceof String[] ) {
      newparams.remove(current)
      newparams[facet] = current as List
      newparams[facet].add(val);
    }
    else {
      newparams[facet] = [ current, val ]
    }
    newparams
  }
%>
   <head>
      <meta name="layout" content="searchmain"/>
      <r:require modules="bootstrap"/>

      <!-- Ask search engines not to index the search results pages, it looks horrible in google -->
      <meta name="robots" CONTENT="noindex, follow">
   </head>
<body>

  <g:if test="${flash.message}">
  <div class="content">
    <div class="container"><div class="row"><div class="well span12" style="text-align:center;">
      <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
    </div></div></div>
  </div>
  </g:if>

  <g:if test="${flash.error}">
  <div class="content">
    <div class="container"><div class="row"><div class="well span12" style="text-align:center;">
      <bootstrap:error class="alert-info">${flash.error}</bootstrap:error>
    </div></div></div>
  </div>
  </g:if>



  <div class="content">
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="col-lg-10">
          <div class="row-fluid">
            <div class="facetFilter col-lg-3">
              <g:each in="${facets}" var="facet">


                <div class="panel panel-default">
                  <div class="panel-heading">
                    <h3 class="panel-title">${facet.key}</h3>
                  </div>
                  <div class="panel-body">
                    <ul>
                      <g:each in="${facet.value}" var="v">
                        <li>
                          <g:set var="fname" value="facet:${facet.key+':'+v.term}"/>

                          <g:if test="${params.list(facet.key).contains(v.term.toString())}">
                            ${v.display} (${v.count})
                          </g:if>
                          <g:else>
                            <g:link controller="home" action="index" params="${addFacet(params,facet.key,v.term)}">${v.display}</g:link> (${v.count})
                          </g:else>

                        </li>
                      </g:each>
                    </ul>
                  </div>
                </div>
              </g:each>
            </div>
            <div class="col-lg-9">

              <div class="resultStats"><p>
              <g:if test="${hits?.totalHits}">Results ${params.offset+1} to ${params.lastrec} of ${hits?.totalHits}</g:if>
              </p></div>

              <ul class="media-list">
                <g:each in="${hits}" var="res">
                  <li class="media">
                    <strong><g:link controller="entry" id="${res.source.canonical_shortcode}">${res.source.title}</g:link></strong><br/>
                    <g:if test="${res.source.url != null}">
                      <a href="${res.source.url}">${res.source.url}</a><br/>
                    </g:if>

                    <p>${res.source.description}</p>
                    <g:if test="${( ( params.postcode ) && ( place != null ) ) }"><b>Distance from ${params.postcode} : ${res.sortValues[0].round(2)} ${dunit}</b><br/></g:if>
                    <g:if test="${res.source.contactName}">Contact ${res.source.contactName}</g:if>
                    <g:if test="${res.source.contactTelephone}">Tel: ${res.source.contactTelephone}</g:if>
                    <g:if test="${res.source.contactFax}">Fax: ${res.source.contactFax}</g:if>
                    <g:if test="${res.source.contactEmail}">Email: ${res.source.contactEmail}</g:if>
 
                  </li>
                </g:each>
              </ul>
              <g:paginate action="index" controller="home" params="${params}" next="Next" prev="Prev" maxsteps="10" total="${hits.totalHits}" class="pagination-right"/>
            </div>
          </div>
        </div>
        <div class="col-lg-2">
          <g:render template="addpanel" contextPath="../templates"/>
        </div>
      </div>
    </div>

  </div>


</body>
</html>
