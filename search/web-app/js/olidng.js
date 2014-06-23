/*
 *  OlidNG.js
 */

var olidNGControllers = angular.module('olidNGControllers', ['mgcrea.ngStrap',
                                                             'mgcrea.ngStrap.tooltip',
                                                             'mgcrea.ngStrap.helpers.parseOptions',
                                                             'mgcrea.ngStrap.typeahead']);

olidNGControllers.controller('EntryDetailCtrl', ['$scope', '$resource', 

  function ($scope, $resource, $http) {

    console.log("scope.parent.id:%o",$scope.$parent.id);
    console.log("scope.parent.base:%o",$scope.$parent.base);

    var DirEntryResource = $resource($scope.$parent.base+'dirent/:resourceId.json',{resourceId:'@id'}, {
      'update': { method:'PUT' }
    });
    $scope.dirEntry = DirEntryResource.get({resourceId:1});

    // Iterate through all properties, expanding

    console.log("$scope.dirEntry: %o",$scope.dirEntry);

    $scope.getAddress = function(e){
      var a={address:e,sensor:!1};
      return t.get("http://maps.googleapis.com/maps/api/geocode/json",{params:a}).then(function(e){return e.data.results})}
  }]);

// var olidNGApp = angular.module('olidNGApp', ['ngRoute','olidNGControllers','ngResource'])
var olidNGApp = angular.module('olidNGApp', ['olidNGControllers','ngResource'])


// olidNGApp.config(['$routeProvider','$locationProvider',
  // function($routeProvider, $locationProvider) {
  //   $routeProvider
  //     .when('/', {
  //       controller: 'EntryDetailCtrl',
  //       templateUrl: '/search/statichtml/entry.html'
  //     }).
  //     otherwise({
  //       redirectTo: '/'
  //     })
  //   ;
    // enable html5Mode for pushstate ('#'-less URLs)
    // $locationProvider.html5Mode(true);
    // $locationProvider.hashPrefix('!');
  // }]);
