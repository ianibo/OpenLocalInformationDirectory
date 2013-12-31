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
  Long defaultPageSize = 10;
  RefdataValue showQuickView

  static transients = ['springSecurityService']

  static constraints = {
    username blank: false, unique: true
    password blank: false
    password blank: false
    defaultPageSize(nullable:true, blank:false)
    showQuickView(nullable:true, blank:false)
  }

  static mapping = {
    password column: '`password`'
  }

  Set<TliRole> getAuthorities() {
    TliUserTliRole.findAllByTliUser(this).collect { it.tliRole } as Set
  }

  def beforeInsert() {
    encodePassword()

  }

  def setUsername(String username) {
    this.username=username;
    if ( displayName == null ) {
      displayName = username;
    }
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
