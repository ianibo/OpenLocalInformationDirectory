import tli.*;

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
        }
      }
    }
  }
}
