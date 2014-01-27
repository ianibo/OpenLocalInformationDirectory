package tli

class RefdataValue {

  String value
  String icon
  String description
  String sortKey
  String termId
  RefdataValue useInstead

  static belongsTo = [
    owner:RefdataCategory
  ]

  static mapping = {
    id column:'rdv_id'
    version column:'rdv_version'
    owner column:'rdv_owner', index:'rdv_entry_idx,rdv_term_id_idx'
    termId column:'rdv_term_id', index:'rdv_term_id_idx'
    value column:'rdv_value', index:'rdv_entry_idx'
    description column:'rdv_desc'
    sortKey column:'rdv_sortkey'
    useInstead column:'rdv_use_instead'
    icon column:'rdv_icon'
  }

  static constraints = {
    icon(nullable:true, blank:true)
    description(nullable:true, blank:true, maxSize:64)
    useInstead(nullable:true, blank:false)
    sortKey(nullable:true, blank:false)
    termId(nullable:true, blank:false)
  }

  @Override
  public String toString() {
    return "${value}"
  }

  @Override
  public boolean equals (Object obj) {

    if (obj != null) {
      Object dep_obj = KBComponent.deproxy (obj)
      if (dep_obj instanceof RefdataValue) {
        return dep_obj.id == id
      }
    }

    return false
  }

  static def refdataFind(params) {
    def result = [];
    def ql = null;
    // ql = RefdataValue.findAllByValueIlikeOrDescriptionIlike("%${params.q}%","%${params.q}%",params)
    // ql = RefdataValue.findWhere("%${params.q}%","%${params.q}%",params)

    def query = "from RefdataValue as rv where rv.useInstead is null and lower(rv.value) like ?"
    def query_params = ["%${params.q.toLowerCase()}%"]

    if ( ( params.filter1 != null ) && ( params.filter1.length() > 0 ) ) {
      query += " and rv.owner.desc = ? "
      query_params.add(params.filter1);
    }

    if ( ( params.filter2 != null ) && ( params.filter2.length() > 0 ) ) {
      query += " and rv.owner.catType.value = ? "
      query_params.add(params.filter2);
    }
    
    query += " order by rv.sortKey, rv.description"

    ql = RefdataValue.findAll(query, query_params, params)

    if ( ql ) {
      ql.each { id ->
        result.add([id:"${id.class.name}:${id.id}",text:"${id.value} (${id.owner.desc}) - ${id.description?:'No description'}"])
      }
    }

    result
  }

  //  def availableActions() {
  //    [ [ code:'object::delete' , label: 'Delete' ] ]
  //  }
}
