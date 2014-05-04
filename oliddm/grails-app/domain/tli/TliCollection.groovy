package tli

import me.ianibbo.common.*
import tli.*;

class TliCollection {

  String name
  String description
  String shortcode

  static belongsTo = [owner:AuthCommonOrganisation]

  static constraints = {
    name(nullable:false, blank:false)
    shortcode(nullable:false, blank:false,unique:true)
    description(nullable:true, blank:true)
    owner(nullable:false, blank:false)
  }

  static def refdataFind(params) {
    def result = [];
    def ql = null;

    def query_params = []
    def query = "from TliCollection as c"
    query += " order by c.name"

    ql = TliCollection.findAll(query, query_params, params)

    if ( ql ) {
      ql.each { id ->
        result.add([id:"${id.class.name}:${id.id}",text:"${id.name}"])
      }
    }

    result
  }

  public String toString() {
    return name?.toString()
  }

}

