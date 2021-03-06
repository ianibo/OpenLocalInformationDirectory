// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

grails.config.locations = [ "classpath:${appName}-config.properties",
                            "classpath:${appName}-config.groovy",
                            "file:${userHome}/.grails/${appName}-config.properties",
                            "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
 
grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}


// log4j configuration
log4j = {
    appenders {
      console name: "stdout", threshold: org.apache.log4j.Level.ALL
    }

    // Example of changing the log pattern for the default console appender:
    //
    // appenders {
    //     console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    // }

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    debug  'grails.app.controllers',
           'grails.app.service',
           'grails.app.services',
           'grails.app.domain',
           'grails.app.tagLib',
           'grails.app.filters',
           'grails.app.conf',
           'grails.app.jobs',
           'grails.app.bootstrap',
           'grails.app.startup',
           'com.k_int'
           'tli'
           // 'grails.app.services.grails.plugin.springsecurity.ui.SpringSecurityUiService',
           // 'org.codehaus.groovy.grails.web.mapping'

}


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'me.ianibbo.common.AuthCommonUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'me.ianibbo.common.AuthCommonUserAuthCommonRole'
grails.plugin.springsecurity.authority.className = 'me.ianibbo.common.AuthCommonRole'
grails.plugin.springsecurity.securityConfigType = "Annotation"
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':                              ['permitAll'],
	'/home':                          ['permitAll'],
	'/home/**':                       ['permitAll'],
	'/index':                         ['permitAll'],
	'/index.gsp':                     ['permitAll'],
	'/oai':                           ['permitAll'],
	'/oai/**':                        ['permitAll'],
	'/register':                      ['permitAll'],
	'/register/**':                   ['permitAll'],
	'/login/**':                      ['permitAll'],
	'/**/js/**':                      ['permitAll'],
	'/**/css/**':                     ['permitAll'],
	'/**/images/**':                  ['permitAll'],
	'/**/favicon.ico':                ['permitAll']
]

grails.plugin.springsecurity.ui.password.minLength = 6
grails.plugin.springsecurity.ui.password.maxLength = 64
grails.plugin.springsecurity.ui.password.validationRegex = '^.*$'

//configure register
grails.plugin.springsecurity.ui.register.emailFrom = "Open Local Information Directory <no-reply@data.opendatasheffield.org>"
grails.plugin.springsecurity.ui.register.emailSubject = 'Welcome to the Open Local Information Directory'
grails.plugin.springsecurity.ui.register.defaultRoleNames = [
        "ROLE_USER"
]

// The following 2 entries make the app use basic auth by default
grails.plugin.springsecurity.useBasicAuth = true
grails.plugin.springsecurity.basic.realmName = "olid"

// This stanza then says everything should use form apart from /api
// More info: http://stackoverflow.com/questions/7065089/how-to-configure-grails-spring-authentication-scheme-per-url
grails.plugin.springsecurity.filterChain.chainMap = [
        '/api/**': 'JOINED_FILTERS,-exceptionTranslationFilter',
        '/**': 'JOINED_FILTERS,-basicAuthenticationFilter,-basicExceptionTranslationFilter'
        // '/soap/deposit': 'JOINED_FILTERS,-exceptionTranslationFilter',
        // '/rest/**': 'JOINED_FILTERS,-exceptionTranslationFilter'
        // '/rest/**': 'JOINED_FILTERS,-basicAuthenticationFilter,-basicExceptionTranslationFilter'

]


globalSearchTemplates = [
  'refdataCategories':[
    baseclass:'me.ianibbo.common.RefdataCategory',
    title:'Refdata Categories ',
    group:'Secondary',
    qbeConfig:[
      qbeForm:[
        [
          prompt:'Description',
          qparam:'qp_desc',
          placeholder:'Category Description',
          contextTree:['ctxtp':'qry', 'comparator' : 'ilike', 'prop':'desc']
        ],
      ],
      qbeGlobals:[
        // ['ctxtp':'filter', 'prop':'desc', 'comparator' : 'ilike', 'value':'Combo.%', 'negate' : true]
      ],
      qbeResults:[
        [heading:'Description', property:'desc',  link:[controller:'resource',action:'show',id:'x.r.class.name+\':\'+x.r.id']]
      ]
    ]
  ],
  'resources':[
    baseclass:'tli.DirectoryEntry',
    title:'Directory Entry ',
    group:'Primary',
    customHeaderInclude:'collheader',
    qbeConfig:[
      qbeForm:[
        [
          type:'lookup',
          baseClass:'tli.TliCollection',
          filter1:'__USER__',
          prompt:'Collection',
          qparam:'qp_collection',
          placeholder:'Collection',
          contextTree:['ctxtp':'qry', 'comparator' : 'contains', 'prop':'collections']
        ],
        [
          prompt:'Title',
          qparam:'qp_title',
          placeholder:'Title',
          contextTree:['ctxtp':'qry', 'comparator' : 'ilike', 'prop':'title', 'wildcard':'B']
        ],
        [
          prompt:'Description',
          qparam:'qp_description',
          placeholder:'Description',
          contextTree:['ctxtp':'qry', 'comparator' : 'ilike', 'prop':'description', 'wildcard':'B']
        ],
      ],
      qbeGlobals:[
        // ['ctxtp':'filter', 'prop':'desc', 'comparator' : 'ilike', 'value':'Combo.%', 'negate' : true]
      ],
      qbeResults:[
        [heading:'Title', property:'title',  link:[controller:'resource',action:'show',id:'x.r.class.name+\':\'+x.r.id', cls:'col-md-6']],
        [heading:'Description', property:'description', cls:'col-md-6']
      ]
    ]
  ],
  'orgs':[
    baseclass:'me.ianibbo.common.AuthCommonOrganisation',
    title:'Organisation ',
    group:'Primary',
    qbeConfig:[
      qbeForm:[
        [
          prompt:'Title',
          qparam:'qp_title',
          placeholder:'Org Name',
          contextTree:['ctxtp':'qry', 'comparator' : 'ilike', 'prop':'displayName', 'wildcard':'B']
        ],
      ],
      qbeGlobals:[
        // ['ctxtp':'filter', 'prop':'desc', 'comparator' : 'ilike', 'value':'Combo.%', 'negate' : true]
      ],
      qbeResults:[
        [heading:'Organisation Name', property:'displayName',  link:[controller:'resource',action:'show',id:'x.r.class.name+\':\'+x.r.id']],
        [heading:'Email', property:'email'],
        [heading:'Status', property:'status.value']
      ]
    ]
  ],
  'locations':[
    baseclass:'tli.TliLocation',
    title:'Addresses ',
    group:'Primary',
    qbeConfig:[
      qbeForm:[
        [ prompt:'Postcode', qparam:'qp_postcode', placeholder:'Postcode', contextTree:['ctxtp':'qry', 'comparator' : 'ilike', 'prop':'postcode','wildcard':'B'] ],
        [ prompt:'Building Name', qparam:'qp_buildname', placeholder:'Building Name', 
                          contextTree:['ctxtp':'qry', 'comparator' : 'ilike', 'prop':'buildingName', 'wildcard':'B'] ],
        [ prompt:'Building Number', qparam:'qp_buildnum', placeholder:'Building Number', 
                          contextTree:['ctxtp':'qry','comparator':'ilike', 'prop':'buildingNumber', 'wildcard':'B'] ],
        [ prompt:'Street', qparam:'qp_street', placeholder:'Street', 
                          contextTree:['ctxtp':'qry','comparator':'ilike', 'prop':'street', 'wildcard':'B'] ],
        [ prompt:'City', qparam:'qp_city', placeholder:'City', 
                          contextTree:['ctxtp':'qry','comparator':'ilike', 'prop':'city', 'wildcard':'B'] ],
        [ prompt:'Region', qparam:'qp_region', placeholder:'Region', 
                          contextTree:['ctxtp':'qry','comparator':'ilike', 'prop':'region', 'wildcard':'B'] ],
        [ prompt:'Country', qparam:'qp_country', placeholder:'Country', 
                          contextTree:['ctxtp':'qry','comparator':'ilike', 'prop':'country', 'wildcard':'B'] ],
      ],
      qbeGlobals:[
        // ['ctxtp':'filter', 'prop':'desc', 'comparator' : 'ilike', 'value':'Combo.%', 'negate' : true]
      ],
      qbeResults:[
        [heading:'Building Name', property:'buildingName',  link:[controller:'resource',action:'show',id:'x.r.class.name+\':\'+x.r.id']],
        [heading:'Building Number', property:'buildingNumber'],
        [heading:'Street', property:'street'],
        [heading:'Postcode', property:'postcode'],
        [heading:'City', property:'city'],
        [heading:'Region', property:'region'],
        [heading:'Country', property:'country']
      ]
    ]
  ],


]

// Types: staticgsp: under views/templates, dyngsp: in database, dynamic:full dynamic generation, other...
globalDisplayTemplates = [
  'tli.DirectoryEntry': [ type:'staticgsp', rendername:'resource' ],
  'me.ianibbo.common.AuthCommonOrganisation': [ type:'staticgsp', rendername:'org' ],
  'tli.TliLocation': [ type:'staticgsp', rendername:'location' ],
]

defaultOaiConfig = [
  lastModified:'lastUpdated',
  schemas:[
    'oai_dc':[
      type:'method',
      methodName:'toOaiDcXml',
      schema:'http://www.openarchives.org/OAI/2.0/oai_dc.xsd',
      metadataNamespaces: [
        '_default_' : 'http://www.openarchives.org/OAI/2.0/oai_dc/',
        'dc'        : "http://purl.org/dc/elements/1.1/"
      ]],
    'tli':[
      type:'method',
      methodName:'toTliXml',
      schema:'http://www.tli.org/schemas/oai_metadata.xsd',
      metadataNamespaces: [
        '_default_': 'http://www.tli.org/oai_metadata/'
      ]],
  ]
]

