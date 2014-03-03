package tli

import tli.*;

class TliLocation {

  String buildingName
  String buildingNumber
  String street
  String city
  String postcode
  String region
  String country


  static constraints = {
    buildingName(nullable:true, blank:false)
    buildingNumber(nullable:true, blank:false)
    street(nullable:true, blank:false)
    city(nullable:true, blank:false)
    postcode(nullable:true, blank:false)
    region(nullable:true, blank:false)
    country(nullable:true, blank:false)
  }
}

