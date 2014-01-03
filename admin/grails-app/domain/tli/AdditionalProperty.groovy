package tli

class AdditionalProperty {

  AdditionalPropertyDefinition propertyDefn
  String apValue

  static belongsTo = [ or:TliOrg ]

  static mapping = {
              id column:'ap_id'
             org column:'ap_owner_org_fk'
    propertyDefn column:'ap_apd_fk'
         apValue column:'ap_value'
  }

}
