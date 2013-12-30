package admin

import grails.converters.JSON
import admin.ClassUtils
import tli.*
import grails.plugin.springsecurity.annotation.Secured

class AjaxSupportController {

  def genericOIDService
  def aclUtilService


  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def edit() { 
    // edit [name:name, value:project:12, pk:org.gokb.cred.Package:2950, action:edit, controller:ajaxSupport]
    log.debug("edit ${params}");
    def result = [:]

    try {
      if ( params.pk ) {
        def target = genericOIDService.resolveOID(params.pk)
        if ( target ) {
          target[params.name] = params.value
          target.save(flush:true)
        }

        pk_components = pk.split(':')
        if ( pk_components.length == 2 ) {
        }
      }
    }
    catch ( Exception e ) {
      log.error(e)
    }

    render result as JSON
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def setRef() {
    def result = [:]
    render result as JSON
  }


  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def getRefdata() {

    def result = [:]

    def config = refdata_config[params.id]
	
	if (!config) {
	  // Use generic config.
	  config = [
		domain:'RefdataValue',
		countQry:"select count(rdv) from RefdataValue as rdv where rdv.useInstead is null and rdv.owner.desc='${params.id}'",
		rowQry:"select rdv from RefdataValue as rdv where rdv.useInstead is null and rdv.owner.desc='${params.id}'",
		qryParams:[],
		cols:['value'],
		format:'simple'
	  ]
	}
	
    if ( config ) {
      def query_params = []
      config.qryParams.each { qp ->
        if ( qp.clos ) {
          query_params.add(qp.clos(params[qp.param]?:''));
        }
        else {
          query_params.add(params[qp.param]);
        }
      }

      log.debug("Params: ${query_params}");
      log.debug("Count qry: ${config.countQry}");
      log.debug("Row qry: ${config.rowQry}");

      def cq = Org.executeQuery(config.countQry,query_params);
      def rq = Org.executeQuery(config.rowQry,
                                query_params,
                                [max:params.iDisplayLength?:400,offset:params.iDisplayStart?:0]);

      rq.each { it ->
        def o = ClassUtils.deproxy(it)
        result["${o.class.name}:${o.id}"] = o[config.cols[0]];
      }
    }

    render result as JSON
  }


  def refdata_config = [
    'ContentProvider' : [
      domain:'Org',
      countQry:'select count(o) from Org as o where lower(o.name) like ?',
      rowQry:'select o from Org as o where lower(o.name) like ? order by o.name asc',
      qryParams:[
		[
		  param:'sSearch',
		  clos:{ value ->
			def result = '%'
			if ( value && ( value.length() > 0 ) )
			  result = "%${value.trim().toLowerCase()}%"
			result
		  }
		]
	  ],
      cols:['name'],
      format:'map'
    ],
    'PackageType' : [
      domain:'RefdataValue',
      countQry:"select count(rdv) from RefdataValue as rdv where rdv.useInstead is null and rdv.owner.desc='Package Type'",
      rowQry:"select rdv from RefdataValue as rdv where rdv.useInstead is null and rdv.owner.desc='Package Type'",
      qryParams:[],
      cols:['value'],
      format:'simple'
    ],
    'KBComponent.Status' : [
      domain:'RefdataValue',
      // countQry:"select count(rdv) from RefdataValue as rdv where rdv.owner.desc='KBComponent.Status' and rdv.value !='${KBComponent.STATUS_DELETED}'",
      // rowQry:"select rdv from RefdataValue as rdv where rdv.owner.desc='KBComponent.Status' and rdv.value !='${KBComponent.STATUS_DELETED}'",
      countQry:"select count(rdv) from RefdataValue as rdv where rdv.useInstead is null and rdv.owner.desc='KBComponent.Status'",
      rowQry:"select rdv from RefdataValue as rdv where rdv.useInstead is null and rdv.owner.desc='KBComponent.Status'",
      qryParams:[],
      cols:['value'],
      format:'simple'
    ],
    'VariantNameType' : [
      domain:'RefdataValue',
      countQry:"select count(rdv) from RefdataValue as rdv where rdv.useInstead is null and rdv.owner.desc='KBComponentVariantName.VariantType'",
      rowQry:"select rdv from RefdataValue as rdv where rdv.useInstead is null and rdv.owner.desc='KBComponentVariantName.VariantType'",
      qryParams:[],
      cols:['value'],
      format:'simple'
    ],
    'Locale' : [
      domain:'RefdataValue',
      countQry:"select count(rdv) from RefdataValue as rdv where rdv.useInstead is null and rdv.owner.desc='KBComponentVariantName.Locale'",
      rowQry:"select rdv from RefdataValue as rdv where rdv.useInstead is null and rdv.owner.desc='KBComponentVariantName.Locale'",
      qryParams:[],
      cols:['value'],
      format:'simple'
    ]
  ]



  /**
   *  addToCollection : Used to create a form which will add a new object to a named collection within the target object.
   * @param __context : the OID ("<FullyQualifiedClassName>:<PrimaryKey>") Of the context object
   * @param __newObjectClass : The fully qualified class name of the instance to create
   * @param __recip : Optional - If set, then new_object.recip will point to __context
   * @param __addToColl : The name of the local set to which the new object should be added
   * @param All other parameters are taken to be property names on newObjectClass and used to init the new instance.
   */ 
  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def addToCollection() {
    log.debug("AjaxController::addToCollection ${params}");

    def contextObj = resolveOID2(params.__context)
    def domain_class = grailsApplication.getArtefact('Domain',params.__newObjectClass)

    if ( domain_class ) {

      if ( contextObj ) {
        log.debug("Create a new instance of ${params.__newObjectClass}");

        def new_obj = domain_class.getClazz().newInstance();

        domain_class.getPersistentProperties().each { p -> // list of GrailsDomainClassProperty
          log.debug("${p.name} (assoc=${p.isAssociation()}) (oneToMany=${p.isOneToMany()}) (ManyToOne=${p.isManyToOne()}) (OneToOne=${p.isOneToOne()})");
          if ( params[p.name] ) {
            if ( p.isAssociation() ) {
              if ( p.isManyToOne() || p.isOneToOne() ) {
                // Set ref property
                log.debug("set assoc ${p.name} to lookup of OID ${params[p.name]}");
                // if ( key == __new__ then we need to create a new instance )
                new_obj[p.name] = resolveOID2(params[p.name])
              }
              else {
                // Add to collection
                log.debug("add to collection ${p.name} for OID ${params[p.name]}");
                new_obj[p.name].add(resolveOID2(params[p.name]))
              }
            }
            else {
              switch ( p.type ) {
                case Long.class:
                  log.debug("Set simple prop ${p.name} = ${params[p.name]} (as long=${Long.parseLong(params[p.name])})");
                  new_obj[p.name] = Long.parseLong(params[p.name]);
                  break;
                default:
                  log.debug("Set simple prop ${p.name} = ${params[p.name]}");
                  new_obj[p.name] = params[p.name]
                  break;
              }
            }
          }
        }
 
        // Need to do the right thing depending on who owns the relationship. If new obj
        // BelongsTo other, should be added to recip collection.
        if ( params.__recip ) {
          log.debug("Set reciprocal property ${params.__recip} to ${contextObj}");
          new_obj[params.__recip] = contextObj
          log.debug("Saving ${new_obj}");
          if ( new_obj.save() ) {
            log.debug("Saved OK");
          }
          else {
            new_obj.errors.each { e ->
              log.debug("Problem ${e}");
            }
          }
        }
        else if ( params.__addToColl ) {
          contextObj[params.__addToColl].add(new_obj)
          log.debug("Saving ${new_obj}");

          if ( new_obj.save() ) {
            log.debug("Saved OK");
          }
          else {
            new_obj.errors.each { e ->
              log.debug("Problem ${e}");
            }
          }

          if ( contextObj.save() ) {
            log.debug("Saved OK");
          }
          else {
            contextObj.errors.each { e ->
              log.debug("Problem ${e}");
            }
          }
        } 

        // Special combo processing
        if ( ( new_obj != null ) && ( new_obj.hasProperty('hasByCombo') ) && ( new_obj.hasByCombo != null ) ) {
          log.debug("Processing hasByCombo properties...${new_obj.hasByCombo}");
          new_obj.hasByCombo.keySet().each { hbc ->
            log.debug("Testing ${hbc} -> ${params[hbc]}");
            if ( params[hbc] ) {
              log.debug("Setting ${hbc} to ${params[hbc]}");
              new_obj[hbc] = resolveOID2(params[hbc])
            }
          }
          new_obj.save()
        }


      }
      else {
        log.debug("Unable to locate instance of context class with oid ${params.__context}");
      }
    }
    else {
      log.error("Unable to ookup domain class ${params.__newObjectClass}");
    }

    redirect(url: request.getHeader('referer'))
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def addToStdCollection() {
    log.debug("addToStdCollection(${params})");
    // Adds a link to a collection that is not mapped through a join object
    def contextObj = resolveOID2(params.__context)
    if ( contextObj ) {
      contextObj[params.__property].add(resolveOID2(params.__relatedObject))
    }
    redirect(url: request.getHeader('referer'))
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def unlinkManyToMany() {
    log.debug("unlinkManyToMany(${params})");
    // Adds a link to a collection that is not mapped through a join object
    def contextObj = resolveOID2(params.__context)
    if ( contextObj ) {
      def item_to_remove = resolveOID2(params.__itemToRemove)
      if ( item_to_remove ) {
        contextObj[params.__property].remove(item_to_remove)
        contextObj.save()
      }
      else {
        log.error("Unable to resolve item to remove : ${params.__itemToRemove}");
      }
    }
    else {
      log.error("Unable to resolve context obj : ${params.__context}");
    }
    redirect(url: request.getHeader('referer'))
  }

  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def delete() {
    log.debug("delete(${params})");
    // Adds a link to a collection that is not mapped through a join object
    def contextObj = resolveOID2(params.__context)
    if ( contextObj ) {
      contextObj.delete()
    }
    else {
      log.error("Unable to resolve context obj : ${params.__context}");
    }
    redirect(url: request.getHeader('referer'))
  }

  def resolveOID2(oid) {
    def oid_components = oid.split(':');
    def result = null;
    def domain_class=null;
    domain_class = grailsApplication.getArtefact('Domain',oid_components[0])
    if ( domain_class ) {
      if ( oid_components[1]=='__new__' ) {
        result = domain_class.getClazz().refdataCreate(oid_components)
        log.debug("Result of create ${oid} is ${result}");
      }
      else {
        result = domain_class.getClazz().get(oid_components[1])
      }
    }
    else {
      log.error("resolve OID failed to identify a domain class. Input was ${oid_components}");
    }
    result
  }


  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def lookup() {
    log.debug("AjaxController::lookup ${params} ${request.user}");
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


  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def editableSetValue() {
    log.debug("editableSetValue ${params}");
    def target_object = resolveOID2(params.pk)
    if ( target_object ) {
      if ( params.type=='date' ) {
        target_object."${params.name}" = params.date('value','yyyy-MM-dd')
      }
      else {
        def binding_properties = [:]
        binding_properties[ params.name ] = params.value
        bindData(target_object, binding_properties)
      }
      target_object.save(flush:true);
    }

    response.setContentType('text/plain')
    def outs = response.outputStream
    outs << params.value
    outs.flush()
    outs.close()
  }

  def genericSetRel() {
    // [id:1, value:JISC_Collections_NESLi2_Lic_IOP_Institute_of_Physics_NESLi2_2011-2012_01012011-31122012.., type:License, action:inPlaceSave, controller:ajax
    // def clazz=grailsApplication.domainClasses.findByFullName(params.type)
    log.debug("genericSetRel ${params}");

    def target=genericOIDService.resolveOID(params.pk)
    def value=genericOIDService.resolveOID(params.value)

    def result = null

    if ( ( target != null ) && ( value != null ) ) {
      // def binding_properties = [ "${params.name}":value ]
      log.debug("Binding: ${params.name} into ${target} - a ${target.class.name}");
      // bindData(target, binding_properties)
      target[params.name] = value
      log.debug("Saving... after assignment ${params.name} = ${target[params.name]}");
      if ( target.save(flush:true) ) {
        if ( params.resultProp ) {
          result = value[params.resultProp]
        }
        
        // We should clear the session values for a user if this is a user to force reload of the,
        // parameters.
        if (target instanceof TliUser) {
          session.userPereferences = null
        } 
        else {
          if ( value ) {
            result = renderObjectValue(value);
            // result = value.toString()
          }
        }
      }
      else {
        log.error("Problem saving.. ${target.errors}");
        result="ERROR"
      }
    }
    else {
      log.error("no type (target=${params.pk}, value=${params.value}");
    }

    def resp = [ newValue: result ]
    log.debug("return ${resp as JSON}");
    render resp as JSON
  }

  def renderObjectValue(value) {
    def result=''
    if ( value ) {
      switch ( value.class ) {
        case org.gokb.cred.RefdataValue.class:
          if ( value.icon != null ) {
            result="<span class=\"select-icon ${value.icon}\"></span>${value.value}"
          }
          else {
            result=value.value
          }
          break;
        default:
          result=value.toString();
      }
    }
    result;
  }

//  def grant() {
//    log.debug("Grant: ${params}");
//    def grantee_obj = genericOIDService.resolveOID(params.grantee)
//    def dc = genericOIDService.resolveOID(params.__context)
//    def perm_to_grant = null;
//    switch ( params.perm ) {
//      case 'READ':
//        perm_to_grant = org.springframework.security.acls.domain.BasePermission.READ;
//        break;
//      case 'WRITE':
//        perm_to_grant = org.springframework.security.acls.domain.BasePermission.WRITE;
//        break;
//      case 'ADMINISTRATION':
//        perm_to_grant = org.springframework.security.acls.domain.BasePermission.ADMINISTRATION;
//        break;
//      case 'DELETE':
//        perm_to_grant = org.springframework.security.acls.domain.BasePermission.DELETE;
//        break;
//      case 'CREATE':
//        perm_to_grant = org.springframework.security.acls.domain.BasePermission.CREATE;
//        break;
//    }
//
//    if ( ( grantee_obj != null ) && ( dc != null ) && ( perm_to_grant != null ) ) {
//      // http://docs.spring.io/autorepo/docs/spring-security/3.0.x/apidocs/org/springframework/security/acls/model/Acl.html
//      def grantee = grantee_obj instanceof User ? grantee_obj.username : grantee_obj.authority
//      aclUtilService.addPermission(dc, grantee, perm_to_grant);
//    }
//    redirect(url: request.getHeader('referer'))
//  }
//
//  def revoke() {
//    log.debug("Revoke: ${params}");
//
//    def dc = genericOIDService.resolveOID(params.__context)
//    def perm_to_revoke = null
//    switch ( params.perm?.toUpperCase() ) {
//      case 'READ':
//        perm_to_revoke = org.springframework.security.acls.domain.BasePermission.READ;
//        break;
//      case 'WRITE':
//        perm_to_revoke = org.springframework.security.acls.domain.BasePermission.WRITE;
//        break;
//      case 'ADMINISTRATION':
//        perm_to_revoke = org.springframework.security.acls.domain.BasePermission.ADMINISTRATION;
//        break;
//      case 'DELETE':
//        perm_to_revoke = org.springframework.security.acls.domain.BasePermission.DELETE;
//        break;
//      case 'CREATE':
//        perm_to_revoke = org.springframework.security.acls.domain.BasePermission.CREATE;
//        break;
//    }
//
//    if ( ( dc != null ) && ( perm_to_revoke != null ) ) {
//      // http://docs.spring.io/autorepo/docs/spring-security/3.0.x/apidocs/org/springframework/security/acls/model/Acl.html
//      log.debug("Revoking ${perm_to_revoke} from ${dc} for ${params.grantee}");
//      aclUtilService.deletePermission(dc, params.grantee, perm_to_revoke);
//    }
//
//    redirect(url: request.getHeader('referer'))
//  }
}
