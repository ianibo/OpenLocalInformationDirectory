package tli

import me.ianibbo.common.*


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

  public static def lookupOrCreate(buildingname,street,town,region,postcode,lat,lon,gaz) {

    def q = TliLocation.executeQuery("select l from TliLocation as l where lower(l.buildingName)=? and lower(l.street)=? and lower(l.city)=? and lower(l.region) = ? and lower(l.postcode)=?",
                                       [buildingname?buildingname.toLowerCase():'',
                                        street?street.toLowerCase():'',
                                        town?town.toLowerCase():'',
                                        region?region.toLowerCase():'',
                                        postcode?postcode.toLowerCase():'']);
    def result = null
    if ( q.size() > 0 ) {
      result = q[0];
    }
    else {
      result = new TliLocation(
                               buildingName:buildingname?:'',
                               street:street?:'',
                               town:town?:'',
                               region:region?:'',
                               postcode:postcode?:'',
                               lat:lat,
                               lon:lon)

      if ( ( lat == null ) 
           && ( lon==null ) 
           && ( gaz != null ) 
           && ( postcode != null ) ) {
        try {
          def gazres = gaz.geocode(postcode)
          
          println("Process result: ${gazres}");
          if ( ( gazres != null ) && ( gazres.response != null ) ) {
            result.lat = gazres.response.geo.lat;
            result.lon = gazres.response.geo.lng;
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
      result.save(flush:true)
    }

    return result
  }
}

