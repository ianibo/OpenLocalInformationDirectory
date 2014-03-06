<html>
   <head>
      <meta name="layout" content="main"/>
      <title>LocalChatter.info - Search for local services and information from trusted sources</title>
      <r:require modules="bootstrap"/>
      <meta name="description" content="Use localchatter to search for community improved information from trusted local sources. You will information collected from local authorities and other trusted sources, imrpved and refined by the community. Content includes child care, registered Childminders and Family Information, Activities,  OfSTED registered Childcare and Family Information Service’s across England."/>
      <meta name="dc.description" content="Use localchatter to search for community improved information from trusted local sources. You will information collected from local authorities and other trusted sources, imrpved and refined by the community. Content includes child care, registered Childminders and Family Information, Activities,  OfSTED registered Childcare and Family Information Service’s across England."/>
      <meta name="og.description" content="Use localchatter to search for community improved information from trusted local sources. You will information collected from local authorities and other trusted sources, imrpved and refined by the community. Content includes child care, registered Childminders and Family Information, Activities,  OfSTED registered Childcare and Family Information Service’s across England."/>

      <meta property="dc.title" name="dc.title" content="Search the UK Directory of approved services including child care, registered Childminders and Family Information"/>
      <meta property="title" name="title" content="Search the UK Directory of approved services including child care, registered Childminders and Family Information"/>
      <meta property="og:title" name="og:title" content="Search the UK Directory of approved services including child care, registered Childminders and Family Information"/>

      <meta property="keywords" name="keywords" content="Family, Childcare, Search, Information, Positive Activities, Events, Organisations, child care, babysitters, childminders, registered childminders, localchatter, Family Information Services"/>

   </head>
<body>

  <g:if test="${flash.message}">
    <div class="content">
      <div class="container"><div class="row"><div class="span12">
        <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
      </div></div></div>
    </div>
  </g:if>


  <div class="content">
    <div class="container">
      <div class="row">
        <div class="col-lg-9">
          <div style="text-align:center;">
            <g:form action="index" method="get" class="form-inline">
              <h1>Search</h1>
              <p>Search the our database for information about organisations and services near you. We collect information from trusted local sources and then allow the information owners to correct and improve their own information whilst preserving the statutory parts. Just enter your postcode to find local services near you:
                 or add keywords to look for specific kinds of information, EG childcare</p>
              Postcode: <input name="postcode" placeholder="Enter your postcode..." type="text" />
              Keywords: <input name="q" placeholder="Keywords, eg Childcare..." type="text" />
              <button class='btn'>Go!</button>
            </g:form>
          </div>
        </div>
        <div class="col-lg-3">
          <g:render template="addpanel" contextPath="../templates"/>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
