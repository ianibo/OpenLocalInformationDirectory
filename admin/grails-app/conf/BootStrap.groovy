import tli.*

class BootStrap {

  def init = { servletContext ->
    // Global System Roles
    def userRole = TliRole.findByAuthority('ROLE_USER') ?: new TliRole(authority: 'ROLE_USER', roleType:'global').save(failOnError: true)
    def editorRole = TliRole.findByAuthority('ROLE_EDITOR') ?: new TliRole(authority: 'ROLE_EDITOR', roleType:'global').save(failOnError: true)
    def adminRole = TliRole.findByAuthority('ROLE_ADMIN') ?: new TliRole(authority: 'ROLE_ADMIN', roleType:'global').save(failOnError: true)

    log.debug("Create admin user...");
    def adminUser = TliUser.findByUsername('admin')
    if ( ! adminUser ) {
      log.error("No admin user found, create");
      adminUser = new TliUser(
                        username: 'admin',
                        password: 'admin',
                        display: 'Admin',
                        email: 'admin@localhost',
                        enabled: true).save(failOnError: true)
    }

    if (!adminUser.authorities.contains(adminRole)) {
      log.debug("Granting admin user admin role");
      TliUserTliRole.create adminUser, adminRole
    }

    if (!adminUser.authorities.contains(userRole)) {
      log.debug("Granting admin user user role");
      TliUserTliRole.create adminUser, userRole
    }

    log.debug("Bootstrap completed");
  }

  def destroy = {
  }
}
