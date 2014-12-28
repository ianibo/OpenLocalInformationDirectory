package tli

import me.ianibbo.common.*

import tli.*;
import javax.persistence.Transient
import grails.rest.*

class PermRegInstance extends Component {

  String description
  RefdataValue typeOfPermitOrRegistration

  static hasMany = [ 
                   ]

  static mappedBy = [
                    ]

  // Timestamps
  Date dateCreated
  Date lastUpdated

  // Typical Price?
  // Age Range?
  // Experience?

  static mapping = {
    description type:'text'
  }

  static constraints = {
    description(nullable:true, blank:true)
  }

}

