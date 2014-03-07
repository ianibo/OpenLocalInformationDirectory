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

  public static def lookupOrCreate(buildingname,street,town,region,postcode,gaz) {
    def q = TliLocation.executeQuery("find l from TliLocation as l where lower(l.buildingName)=? and lower(l.street)=? and lower(l.city)=? and lower(l.postcode)=?",
                                       [buildingname.toLowerCase(),
                                        street.toLowerCase(),
                                        town.toLowerCase(),
                                        region.toLowerCase(),
                                        postcode.toLowerCase()]);
    def result = null
    if ( q.size() > 0 ) {
      result = q[0];
    }
    else {
      result = new TliLocation(
                               buildingName:buildingname,
                               street:street,
                               town:town,
                               region:region,
                               postcode:postcode)
      if ( ( gaz != null ) && ( postcode != null ) ) {
        try {
          def gazres = gaz.geocode(postcode)
          if ( ( gazres != null ) && ( gazres.result != null ) ) {
            result.lat = gazres.result.geo.lat;
            result.lon = gazres.result.geo.lng;
          }
          // result = [ address:postcode,
          //                 response:json,
          //                 lastSeen: System.currentTimeMillis(),
          //                 created: System.currentTimeMillis() ]
        }
        catch ( Exception e ) {
          e.printStackTrace()
        }
      }
      result.save()
    }

    return result
  }
}

