package tli

import me.ianibbo.common.*

class DomainClassInfo {

  String nameOfClass
  String description

  static mapping = {
  }

  static constraints = {
    nameOfClass(nullable:false, blank:false,unique:true)
    description(nullable:true, blank:true)
  }

}
