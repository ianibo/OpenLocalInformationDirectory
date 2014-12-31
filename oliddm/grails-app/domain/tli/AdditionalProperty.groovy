package tli

import me.ianibbo.common.*

class AdditionalProperty {

  DomainClassInfo ownerClass
  Long ownerId
  String apValue

  static mapping = {
              id column:'ap_id'
      ownerClass column:'ap_owner_class'
         ownerId column:'ap_owner_id'
    propertyDefn column:'ap_apd_fk'
         apValue column:'ap_value'
  }

}
