/*
 *  OlidNG.js
 */

var olidNGControllers = angular.module('olidNGControllers', []);

olidNGControllers.controller('EntryDetailCtrl', ['$scope', '$http',

  function ($scope, $html) {

    console.log("scope:%o html:%o",$scope, $html);
    console.log("scope.parent.id:%o",$scope.$parent.id);
    
    //$http.get('/entrydata/scope.parent.id/get.json').success(function(data) {
    //  $scope.phones = data;
    //});
  }]);

var olidNGApp = angular.module('olidNGApp', ['ngRoute','olidNGControllers'])

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
    $locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
  }]);
