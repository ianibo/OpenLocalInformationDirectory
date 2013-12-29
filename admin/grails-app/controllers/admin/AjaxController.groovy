package com.k_int.kbplus

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured
import grails.converters.*
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil

class AjaxController {



  def lookup() {
    log.debug("AjaxController::lookup ${params}");
    def result = [:]
    params.max = params.max ?: 10;
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
