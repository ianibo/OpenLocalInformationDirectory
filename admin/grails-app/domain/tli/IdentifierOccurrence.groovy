package tli

import javax.persistence.Transient
import org.codehaus.groovy.grails.commons.ApplicationHolder


class IdentifierOccurrence {

  static auditable = true

  Identifier identifier

  static belongsTo = [
    component:Component
  ]

  static mapping = {
            id column:'io_id'
    identifier column:'io_canonical_id'
     component column:'io_component_fk'
          tipp column:'io_tipp_fk'
           org column:'io_org_fk'
  }

  static constraints = {
     component(nullable:true)
  }
  
  String toString() {
    "IdentifierOccurrence(${id} - component:${component}";
  }

  @Transient
  def onSave = {
  }

  @Transient
  def onDelete = {
  }


}
