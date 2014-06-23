modules = {
  application {
    resource url:'js/application.js'
  }
  spider {
    resource url: 'js/oms.min.js'
  }
  markerclusterer {
    resource url:'js/markerclusterer.js'
  }
  angular {
    resource url:'https://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.min.js', disposition: 'head'
    resource url:'https://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular-route.min.js', disposition: 'head'
    resource url:'https://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular-resource.min.js', disposition: 'head'
    // resource url:'js/ui-bootstrap-tpls-0.11.0.min.js', disposition: 'head'
  }
  angularstrap {
    resource url:'js/angular-strap.min.js', disposition: 'head'
    resource url:'js/angular-strap.tpl.min.js', disposition: 'head'
    resource url:'js/modules/typeahead.min.js', disposition: 'head'
    resource url:'js/modules/typeahead.tpl.min.js', disposition: 'head'
  }
}
