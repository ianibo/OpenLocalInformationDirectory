grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://repo.spring.io/milestone/"
        mavenRepo "https://raw.github.com/fernandezpablo85/scribe-java/mvn-repo/"  // For scribe
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        runtime 'mysql:mysql-connector-java:5.1.30'
        runtime 'net.sf.opencsv:opencsv:2.0'
        runtime 'org.elasticsearch:elasticsearch:1.0.1'
        runtime 'org.elasticsearch:elasticsearch-client-groovy:1.0.1'
        runtime 'org.mongodb:mongo-java-driver:2.11.4'
        runtime 'com.gmongo:gmongo:1.3'
        compile "net.sf.ehcache:ehcache-core:2.4.8"
    }

    plugins {
        // plugins for the build system only
        build ':tomcat:7.0.52.1'

        // plugins for the compile step
        // compile ':scaffolding:2.0.3'
        compile ':cache:1.1.1'

        // plugins needed at runtime but not for compilation
        runtime ':hibernate:3.6.10.13'
        runtime ':database-migration:1.4.0'
        runtime ":jquery:1.11.0.2"
        runtime ":jquery-ui:1.10.3"
        runtime ":famfamfam:1.0.1"
        // Not easy to migrate:: compile ':asset-pipeline:1.8.3'
        compile ':asset-pipeline:1.8.3'
        // Replaced with asset pipeline above... 
        // runtime ':resources:1.2.8'
        // runtime ':gsp-resources:0.4.4'

        compile ":spring-security-core:2.0-RC3"
        compile ":spring-security-ui:1.0-RC2"
        runtime ":twitter-bootstrap:3.2.0"
        compile ':mail:1.0.6', {
           excludes 'spring-test'
        }
        compile ":rest:0.8"
        compile ":executor:0.3"
        compile ":authmodel:0.4.0"
        compile ":oliddm:0.4.11"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0.1"
        //runtime ":cached-resources:1.1"
        //runtime ":yui-minify-resources:0.1.5"
    }
}
