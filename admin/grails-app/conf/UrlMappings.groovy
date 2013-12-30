class UrlMappings {

  static mappings = {
    // "/$controller/$action?/$id?(.${format})?"{
    "/$controller/$action?/$id?"{
      constraints {
        // apply constraints here
      }
    }

    "/"(controller:'home',action:'index')
    "500"(view:'/error')

    "/orgs"(resources:"org") {
    }

    "/org/$id/requestNewCollection"(controller='org',action:'requestNewCollection')

    "/collections" (resources:"collection") {
    }

  }
}
