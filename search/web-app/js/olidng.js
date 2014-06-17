/*
 *  OlidNG.js
 */

var olidNGControllers = angular.module('olidNGControllers', []);

olidNGControllers.controller('EntryDetailCtrl', ['$scope', '$resource', 

  function ($scope, $resource, $http) {

    console.log("scope.parent.id:%o",$scope.$parent.id);
    console.log("scope.parent.base:%o",$scope.$parent.base);

    var DirEntryResource = $resource($scope.$parent.base+'dirent/:resourceId.json');
    var dirEntry = DirEntryResource.get({resourceId:1});

    console.log("entry: %o",dirEntry);
  }]);

var olidNGApp = angular.module('olidNGApp', ['ngRoute','olidNGControllers','ngResource'])

olidNGApp.config(['$routeProvider','$locationProvider',
  function($routeProvider, $locationProvider) {
    $routeProvider
      .when('/', {
        controller: 'EntryDetailCtrl',
        templateUrl: '/search/statichtml/entry.html'
      }).
      otherwise({
        redirectTo: '/'
      })
    ;
    // enable html5Mode for pushstate ('#'-less URLs)
    // $locationProvider.html5Mode(true);
    // $locationProvider.hashPrefix('!');
  }]);
