class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:'home',action:'index')
        "/in/$portal"(controller:'home',action:'index')
        "/in"(controller:'home',action:'index')
        "/entry/$id"(controller:'entry', action:'index')
        "/entry/$id/edit"(controller:'entry', action:'edit')
        "/requestAccess/$id"(controller:'requestAccess', action:'index')

        "500"(view:'/error')
	}
}
