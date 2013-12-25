package tli

class TliUser extends TliParty {

  transient springSecurityService

  String username
  String password
  String email
  boolean enabled = true
  boolean accountExpired
  boolean accountLocked
  boolean passwordExpired

  static transients = ['springSecurityService']

  static constraints = {
    username blank: false, unique: true
    password blank: false
  }

  static mapping = {
    password column: '`password`'
  }

  Set<TliRole> getAuthorities() {
    TliUserTliRole.findAllByTliUser(this).collect { it.tliRole } as Set
  }

  def beforeInsert() {
    if ( ( displayName == null ) || ( displayName == '' ) ) {
      displayName = username
    }
    encodePassword()

  }

  def beforeUpdate() {
    if (isDirty('password')) {
      encodePassword()
    }
  }

  protected void encodePassword() {
    password = springSecurityService.encodePassword(password)
  }
}
