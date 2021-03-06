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

    "/org/$id/requestNewCollection"(controller:'org',action:'requestNewCollection')

    "/collections/$id"(controller:'collection', action:"index")
    "/collections/$id/$action"(controller:'collection')
    "/api/$id/$action"(controller:'api')
    "/oai/$id"(controller:'oai',action:'index')
  }
}
