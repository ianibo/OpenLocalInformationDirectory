package tli

class RefdataRelation {

  RefdataValue relationType

  static belongsTo = [
    fromValue:RefdataValue,
    toValue:RefdataValue
  ]

  static mapping = {
              id column:'rdr_id'
         version column:'rdr_version'
       fromValue column:'rdr_from_value_fk', index:'rdr_rel_idx'
         toValue column:'rdr_to_value_fk', index:'rdr_rel_idx'
    relationType column:'rdr_type_fk', index:'rdr_rel_idx'
  }

  static constraints = {
    relationType(nullable:true)
  }
}
