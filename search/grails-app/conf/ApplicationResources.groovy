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
    resource url:'https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0/angular.min.js', disposition: 'head'
  }
}
