var app = angular.module('app', ['ngRoute','ngResource']);
app.config(['$routeProvider', function($routeProvider){
    $routeProvider
        .when('/', {
            redirectTo: 'login'
        })
        .when('/login', {
            templateUrl: 'view/loginPage.html',
            controller: 'LoginController',
            controllerAs: 'loginCtrl'
        })
        .when('/operator', {
            templateUrl: 'view/operatorPage.html',
            controller: 'OperatorCtrl',
        })
        .when('/passenger', {
            templateUrl: 'view/passengerPage.html',
            controller: 'PassengerCtrl',
        })
        .when('/driver', {
                    templateUrl: 'view/driverPage.html',
                    controller: 'DriverCtrl',
                })
        .otherwise(
            { redirectTo: '/'}
        );
}]);
