import admin.ClassUtils

class InplaceTagLib {

  def genericOIDService

  /**
   * simpleReferenceTypedown - create a hidden input control that has the value fully.qualified.class:primary_key and which is editable with the
   * user typing into the box. Takes advantage of refdataFind and refdataCreate methods on the domain class.
   */
  def simpleReferenceTypedown = { attrs, body ->
    out << "<input type=\"hidden\" name=\"${attrs.name}\" data-domain=\"${attrs.baseClass}\" "
    if ( attrs.id ) {
      out << "id=\"${attrs.id}\" "
    }
    if ( attrs.style ) {
      out << "style=\"${attrs.style}\" "
    }

    attrs.each { att ->
      if ( att.key.startsWith("data-") ) {
        out << "${att.key}=\"${att.value}\" "
      }
    }

    out << "class=\"simpleReferenceTypedown ${attrs.class}\" />"
  }


  def xEditableRefData = { attrs, body ->

    def owner = ClassUtils.deproxy( attrs.owner )

    // out << "editable many to one: <div id=\"${attrs.id}\" class=\"xEditableManyToOne\" data-type=\"select2\" data-config=\"${attrs.config}\" />"
    def data_link = createLink(controller:'ajaxSupport', action: 'getRefdata', params:[id:attrs.config,format:'json'])
    def update_link = createLink(controller:'ajaxSupport', action: 'genericSetRel')
    def oid = owner.id != null ? "${owner.class.name}:${owner.id}" : ''
    def id = attrs.id ?: "${oid}:${attrs.field}"
    def type = attrs.type ?: "select"

    out << "<span>"

    // Output an editable link
    out << "<span id=\"${id}\" class=\"xEditableManyToOne\" "
    if ( ( owner != null ) && ( owner.id != null ) ) {
      out << "data-pk=\"${oid}\" "
    }
    else {
    }
    out << "data-url=\"${update_link}\" "

    out << "data-type=\"${type}\" data-name=\"${attrs.field}\" data-source=\"${data_link}\" >"

    // Here we can register different ways of presenting object references. The most pressing need to be
    // outputting a span containing an icon for refdata fields.
    out << renderObjectValue(owner[attrs.field])

    out << "</span></span>"
  }

  /**
   * ToDo: This function is a duplicate of the one found in AjaxController, both should be moved to a shared static utility
   */
  def renderObjectValue(value) {
    def result=''
    if ( value ) {
      switch ( value.class ) {
        case tli.RefdataValue.class:
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

  /**
   * Attributes:
   *   owner - Object
   *   field - property
   *   id [optional] - 
   *   class [optional] - additional classes
   */
  def xEditable = { attrs, body ->

    def owner = ClassUtils.deproxy(attrs.owner);

    def oid = owner.id != null ? "${owner.class.name}:${owner.id}" : ''
    def id = attrs.id ?: "${oid}:${attrs.field}"

    out << "<span id=\"${id}\" class=\"xEditableValue ${attrs.class?:''}\""
    out << " data-type=\"${attrs.type?:'textarea'}\""
    if ( oid && ( oid != '' ) )
      out << " data-pk=\"${oid}\""
    out << " data-name=\"${attrs.field}\""

    def data_link = null
    switch ( attrs.type ) {
      case 'date':
        data_link = createLink(controller:'ajaxSupport', action: 'editableSetValue', params:[type:'date',format:'yyyy/MM/dd'])
        break;
      case 'string':
      default:
        data_link = createLink(controller:'ajaxSupport', action: 'editableSetValue')
        break;
    }

    out << " data-url=\"${data_link}\""
    out << ">"

    if ( body ) {
      out << body()
    }
    else {
      if ( owner[attrs.field] && attrs.type=='date' ) {
        def sdf = new java.text.SimpleDateFormat(attrs.format?:'yyyy-MM-dd')
        out << sdf.format(owner[attrs.field])
      }
      else {
        out << owner[attrs.field]
      }
    }
    out << "</span>"
  }

  def manyToOne = { attrs, body ->
    def data_link = createLink(controller:'search', action: 'index', params:[qbe:'g:resources',mode:'lookup'])
    out << "<span class=\"someclass\" data-toggle=\"modal\" data-target=\"#manyToOneModal\" data-remote=\"${data_link}\">manyToOne</span>"
  }
}

