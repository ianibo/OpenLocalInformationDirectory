package tli

import me.ianibbo.common.*

import tli.*;
import javax.persistence.Transient

class Component {

  String title

  // Timestamps
  Date dateCreated
  Date lastUpdated

  static mappedBy = [ids: 'component']
  static hasMany = [ids: IdentifierOccurrence]


  static mapping = {
  }

  static constraints = {
    title(nullable:false, blank:false)
  }

}

