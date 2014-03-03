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
  String lat
  String lon

  static constraints = {
    buildingName(nullable:true, blank:false)
    buildingNumber(nullable:true, blank:false)
    street(nullable:true, blank:false)
    city(nullable:true, blank:false)
    postcode(nullable:true, blank:false)
    region(nullable:true, blank:false)
    country(nullable:true, blank:false)
    lat(nullable:true, blank:false)
    lon(nullable:true, blank:false)
  }

  public String toString() {
    StringWriter sw = new StringWriter()
    sw.write(buildingName?buildingName+' ':'')
    sw.write(buildingNumber?buildingNumber+' ':'')
    sw.write(street?street+' ':'')
    sw.write(city?city+' ':'')
    sw.write(postcode?postcode+' ':'')
    sw.write(region?region+' ':'')
    sw.write(country?country+' ':'')
    return sw.toString()
  }
}

