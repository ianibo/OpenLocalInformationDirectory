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

    def status_pending = RefdataCategory.lookupOrCreate("status", "Pending Approval" )
    def status_approved = RefdataCategory.lookupOrCreate("status", "Approved" )
    def status_rejected = RefdataCategory.lookupOrCreate("status", "Rejected" )
    def affiliation_ro = RefdataCategory.lookupOrCreate("affiliation", "Read Only User" )
    def affiliation_su = RefdataCategory.lookupOrCreate("affiliation", "Standard User" )
    def affiliation_ad = RefdataCategory.lookupOrCreate("affiliation", "Administrator" )

    def test_org_1 = TliOrg.findByDisplayName('AtestOrg1') ?: new TliOrg(displayName:'AtestOrg1', status:status_approved).save();
    def test_org_2 = TliOrg.findByDisplayName('BtestOrg2') ?: new TliOrg(displayName:'BtestOrg2', status:status_approved).save();
    def test_org_3 = TliOrg.findByDisplayName('CtestOrg3') ?: new TliOrg(displayName:'CtestOrg3', status:status_approved).save();
    def test_org_4 = TliOrg.findByDisplayName('DtestOrg4') ?: new TliOrg(displayName:'DtestOrg4', status:status_approved).save();
    def test_org_5 = TliOrg.findByDisplayName('EtestOrg5') ?: new TliOrg(displayName:'EtestOrg5', status:status_approved).save();
    def test_org_6 = TliOrg.findByDisplayName('FtestOrg6') ?: new TliOrg(displayName:'FtestOrg6', status:status_approved).save();
    def test_org_7 = TliOrg.findByDisplayName('GtestOrg7') ?: new TliOrg(displayName:'GtestOrg7', status:status_approved).save();
    def test_org_8 = TliOrg.findByDisplayName('HtestOrg8') ?: new TliOrg(displayName:'HtestOrg8', status:status_approved).save();

    log.debug("Bootstrap completed");
  }

  def destroy = {
  }
}
