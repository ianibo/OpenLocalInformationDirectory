package search

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.*
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.commons.ApplicationHolder
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import tli.*
import me.ianibbo.common.*


class AjaxController {

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def lookup() {
    def result = [:]
    params.max = params.max ?: 20;
    def domain_class = grailsApplication.getArtefact('Domain',params.baseClass)
    if ( domain_class ) {
      result.values = domain_class.getClazz().refdataFind(params);
    }
    else {
      log.error("Unable to locate domain class ${params.baseClass}");
      result.values=[]
    }
    render result as JSON
  }
}
