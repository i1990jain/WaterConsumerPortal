//'use strict';

var app = angular.module('wcp',['ngRoute']);
var httpHeaders, message;


app.config(function ($routeProvider) {
    //configure the rounting of ng-view
    $routeProvider
            .when('/app/login',
            		{controller: 'LoginController',
    					templateUrl: 'app/login',
						publicAccess: true})
            .when('/app/home',
                    {controller: 'HomeController',
                        templateUrl: 'app/home'})
            .otherwise('/app/login');

    
   
});