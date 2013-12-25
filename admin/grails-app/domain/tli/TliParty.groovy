package tli

class TliParty {

  String displayName

  static constraints = {
    displayName blank: false, unique: true
  }

}
