import tli.*;
import me.ianibbo.common.*;

class UserDetailsFilters {

  def springSecurityService

  // grailsApplication.config.appDefaultPrefs

  def filters = {
    setUsetFilter(controller:'*', action:'*') {
      before = {
        // if ( session.sessionPreferences == null ) {
        //   session.sessionPreferences = grailsApplication.config.appDefaultPrefs
        // }
        // else {
        // }
        if ( springSecurityService.principal instanceof String ) {
        }
        else {
          request.user = springSecurityService.currentUser
          request.tliorganisations = AuthCommonOrganisation.executeQuery("select a from AuthCommonAffiliation as a where a.user = ?",[request.user]);
          request.tlicolls = AuthCommonOrganisation.executeQuery("select c from TliCollection as c where exists ( select a from AuthCommonAffiliation as a where a.org = c.owner and a.user = ? )", [request.user]);

        }
      }
    }
  }
}
