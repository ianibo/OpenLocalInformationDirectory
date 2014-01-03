
package tli

class TliOrg extends TliParty {

  RefdataValue status
  String shortcode
  String url

  static constraints = {
    status(nullable:false, blank:false)
    shortcode(nullable:false, blank:false, unique:true)
    url(nullable:true, blank:false)
  }

  static hasMany = [
    collections:TliCollection
  ]

  static mappedBy = [
    collections:'owner'
  ]

  static def refdataFind(params) {
    def result = [];
    def ql = null;

    def query = "from TliOrg as o where lower(o.displayName) like ?"
    def query_params = ["%${params.q.toLowerCase()}%"]

    if ( ( params.filter1 != null ) && ( params.filter1.length() > 0 ) ) {
      query += " and not exists ( select a from Affiliation as a where a.org = o and a.user.id = ? )"
      query_params.add(Long.parseLong(params.filter1));
    }

    ql = TliOrg.findAll(query, query_params, params)

    if ( ql ) {
      ql.each { id ->
        result.add([id:"${id.class.name}:${id.id}",text:"${id.displayName}"])
      }
    }

    result
  }

}
