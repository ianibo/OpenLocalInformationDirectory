package com.k_int

import groovy.util.logging.*
import org.gokb.cred.*;
import org.codehaus.groovy.grails.commons.GrailsClassUtils

@Log4j
public class HQLBuilder {

  public static def build(grailsApplication, qbetemplate, params, result, target_class, genericOIDService) {
    // select o from Clazz as o where 

    log.debug("build ${params}");

    // Step 1 : Walk through all the properties defined in the template and build a list of criteria
    def criteria = []
    qbetemplate.qbeConfig.qbeForm.each { query_prop_def ->
      if ( ( params[query_prop_def.qparam] != null ) && ( params[query_prop_def.qparam].length() > 0 ) ) {
        criteria.add([defn:query_prop_def, value:params[query_prop_def.qparam]]);
      }
    }

    qbetemplate.qbeConfig.qbeGlobals.each { global_prop_def ->
      log.debug("Adding query global: ${global_prop_def}");
      // creat a contextTree so we can process the filter just like something added to the query tree
      criteria.add([defn:[qparam:global_prop_def.prop.replaceAll('.','_'),contextTree:global_prop_def],value:global_prop_def.value])
    }

    def hql_builder_context = [:]
    hql_builder_context.declared_scopes = [:]
    hql_builder_context.query_clauses = []
    hql_builder_context.bindvars = [:]
    hql_builder_context.genericOIDService = genericOIDService;
    hql_builder_context.sort = params.sort
    hql_builder_context.order = params.order

    def baseclass = target_class.getClazz()
    criteria.each { crit ->
      log.debug("Processing crit: ${crit}");
      processProperty(hql_builder_context,crit,baseclass)
      // List props = crit.def..split("\\.")
    }

    log.debug("At end of build, ${hql_builder_context}");
    hql_builder_context.declared_scopes.each { ds ->
      log.debug("Scope: ${ds}");
    }

    hql_builder_context.query_clauses.each { qc ->
      log.debug("QueryClause: ${qc}");
    }

    def hql = outputHql(hql_builder_context, qbetemplate)
    log.debug("HQL: ${hql}");
    log.debug("BindVars: ${hql_builder_context.bindvars}");

    def count_hql = "select count (o) ${hql}"
    def fetch_hql = "select o ${hql}"

    log.debug("Attempt count qry");
    result.reccount = baseclass.executeQuery(count_hql, hql_builder_context.bindvars)[0]
    result.recset = baseclass.executeQuery(fetch_hql, hql_builder_context.bindvars,[max: result.max, offset: result.offset])
  }

  static def processProperty(hql_builder_context,crit,baseclass) {
    log.debug("processProperty ${hql_builder_context}, ${crit}");
    switch ( crit.defn.contextTree.ctxtp ) {
      case 'qry':
        processQryContextType(hql_builder_context,crit,baseclass)
        break;
      case 'filter':
        processQryContextType(hql_builder_context,crit,baseclass)
        break;
      default:
        log.error("Unhandled property context type ${crit}");
        break;
    }
  }

  static def processQryContextType(hql_builder_context,crit, baseclass) {
    List l =  crit.defn.contextTree.prop.split("\\.")
    processQryContextType(hql_builder_context, crit, l, 'o', baseclass)
  }

  static def processQryContextType(hql_builder_context,crit, proppath, parent_scope, the_class) {

    log.debug("processQryContextType.... ${proppath}");

    if ( proppath.size() > 1 ) {
      
      def head = proppath.remove(0)
      def newscope = parent_scope+'_'+head
      if ( hql_builder_context.declared_scopes.containsKey(newscope) ) {
        // Already established scope for this context
        log.debug("${newscope} already a declared contest");
      }
      else {
        log.debug("Intermediate establish scope - ${head} :: ${proppath}");
        // We're looking at an intermediate property which needs to add some bind scopes. The property can be a simple 

        // Target class can be looked up in standard way.
        def target_class = GrailsClassUtils.getPropertyType(the_class, head)
          
        // Standard association, just make a bind variable..
        establishScope(hql_builder_context, parent_scope, head, newscope)
        processQryContextType(hql_builder_context,crit, proppath, newscope, target_class)
      }
    }
    else {
      log.debug("head prop...");
      // If this is an ordinary property, add the operation. If it's a special, the make the extra joins
      log.debug("Standard property...");
      // The property is a standard property
      addQueryClauseFor(crit,hql_builder_context,parent_scope+'.'+proppath[0])
    }
  }

  static def establishScope(hql_builder_context, parent_scope, property_to_join, newscope_name) {
    log.debug("Establish scope ${newscope_name} as a child of ${parent_scope} property ${property_to_join}");
    hql_builder_context.declared_scopes[newscope_name] = "${parent_scope}.${property_to_join} as ${newscope_name}" 
  }

  static def addQueryClauseFor(crit, hql_builder_context, scoped_property) {

    switch ( crit.defn.contextTree.comparator ) {
      case 'eq':
        hql_builder_context.query_clauses.add("${crit.defn.contextTree.negate?'not ':''}${scoped_property} = :${crit.defn.qparam}");
        if ( crit.defn.type=='lookup' ) {
          hql_builder_context.bindvars[crit.defn.qparam] = hql_builder_context.genericOIDService.resolveOID2(crit.value)
        }
        else {
          switch ( crit.defn.contextTree.type ) {
            case 'java.lang.Long':
              hql_builder_context.bindvars[crit.defn.qparam] = Long.parseLong(crit.value)
              break;
            default:
              hql_builder_context.bindvars[crit.defn.qparam] = crit.value
              break;
          }
        }
        break;
      case 'ilike':
        hql_builder_context.query_clauses.add("${crit.defn.contextTree.negate?'not ':''}lower(${scoped_property}) like :${crit.defn.qparam}");
        hql_builder_context.bindvars[crit.defn.qparam] = crit.value.toLowerCase()
      default:
        log.error("Unhandled comparator. crit: ${crit}");
    }
  }

  static def outputHql(hql_builder_context, qbetemplate) {
    StringWriter sw = new StringWriter()
    sw.write(" from ${qbetemplate.baseclass} as o\n")

    hql_builder_context.declared_scopes.each { scope_name,ds ->
      sw.write(" join ${ds}\n");
    }
    
    if ( hql_builder_context.query_clauses.size() > 0 ) {
      sw.write(" where");
      boolean conjunction=false
      hql_builder_context.query_clauses.each { qc ->
        if ( conjunction ) {
          // output and on second and subsequent clauses
          sw.write(" AND");
        }
        else {  
          conjunction=true
        }
        sw.write(" ");
        sw.write(qc);
      }
    }

    if ( ( hql_builder_context.sort != null ) && ( hql_builder_context.sort.length() > 0 ) ) {
      sw.write(" order by ${hql_builder_context.sort} ${hql_builder_context.order}");
    }

    // Return the toString of the writer
    sw.toString();
  }

}
