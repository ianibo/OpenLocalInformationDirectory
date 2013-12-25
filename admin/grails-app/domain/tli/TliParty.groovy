package tli

class TliParty {

  String displayName

  static constraints = {
    displayname blank: false, unique: true
  }

}
