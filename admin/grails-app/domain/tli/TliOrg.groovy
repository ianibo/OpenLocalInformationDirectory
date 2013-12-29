
package tli

class TliOrg extends TliParty {

  RefdataValue status

  static constraints = {
    status(nullable:true, blank:false)
  }

}
