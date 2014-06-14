/*
 *  OlidNG.js
 */

var olidNGControllers = angular.module('olidNGControllers', []);

olidNGControllers.controller('EntryDetailCtrl', ['$scope', '$http',
  function ($scope, $http) {
    //$http.get('phones/phones.json').success(function(data) {
    //  $scope.phones = data;
    //});
  }]);

var olidNGApp = angular.module('olidNGApp', ['ngRoute','olidNGControllers'])

olidNGApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider
      .when('/', {
        controller: 'EntryDetailCtrl',
        templateUrl: '/search/statichtml/entry.html'
      }).
      otherwise({
        redirectTo: '/'
      })
    ;
  }]);

