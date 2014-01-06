<%@ page import="grails.converters.JSON" %>

<g:set var="counter" value="${offset}" />

<table class="table table-striped table-condensed table-bordered">
  <thead>
    <tr class="nav">
      <g:each in="${qbeConfig.qbeResults}" var="c">
        <th>
          <g:if test="${c.sort}">
            <g:if test="${params.sort==c.sort && params.order=='asc'}">
              <g:link params="${params+['sort':c.sort,order:'desc']}"> ${c.heading} <i class="icon-sort-up"></i></g:link>
            </g:if>
            <g:else>
              <g:if test="${params.sort==c.sort && params.order=='desc'}">
                <g:link params="${params+['sort':c.sort,order:'asc']}"> ${c.heading} <i class="icon-sort-down"></i></g:link>
              </g:if>
              <g:else>
                <g:link params="${params+['sort':c.sort,order:'desc']}"> ${c.heading} <i class="icon-sort"></i></g:link>
              </g:else>
            </g:else>
          </g:if>
          <g:else>
            ${c.heading}
          </g:else>
        </th>
      </g:each>
    </tr>
  </thead>
  <tbody>
    <g:each in="${rows}" var="r">
      <g:set var="r" value="${r}"/>
      <tr class="${++counter==det ? 'success':''}">
        <!-- Row ${counter} -->
        <g:each in="${qbeConfig.qbeResults}" var="c">
          <td>
            <g:if test="${c.link != null}">
              <g:link controller="${c.link.controller}" 
                      action="${c.link.action}" 
                      id="${c.link.id!=null?groovy.util.Eval.x(pageScope,c.link.id):''}"
                      params="${c.link.params!=null?groovy.util.Eval.x(pageScope,c.link.params):[]}">${groovy.util.Eval.x(r, 'x.' + c.property) ?: 'Empty'}</g:link>
            </g:if>
            <g:else>
              ${groovy.util.Eval.x(r, 'x.' + c.property)}
            </g:else>
          </td>
        </g:each>
      </tr>
    </g:each>
  </tbody>
</table>
