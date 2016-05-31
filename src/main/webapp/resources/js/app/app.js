//'use strict';

var app = angular.module('wcp',['ngRoute','ngAnimate','ngMessages', 'ngStorage']);



app.config(function ($routeProvider) {
    //configure the rounting of ng-view
    $routeProvider
            .when('/app/login',
            		{controller: 'LoginController',
    					templateUrl: 'app/login',
    					animation: 'first',
						publicAccess: true})
            .when('/app/home',
                    {controller: 'HomeController',
                        templateUrl: 'app/home',
                        animation: 'second'})
            .when('/app/histogram',
                    {controller: 'HistogramController',
                        templateUrl: 'app/histogram'})
            .otherwise('/app/login');

    
   
});

app.run(function ($rootScope, $http, $location, $localStorage) {
	
	
    // redirect to login page if not logged in and trying to access a restricted page
    $rootScope.$on('$locationChangeStart', function (event, next, current) {
    	
    	 if ($localStorage.currentUser) {
    		 console.log("user already logged in")
    	    	$location.path('/app/home');
    	        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
    	    }
    	 
        var publicPages = ['/app/login'];
        var restrictedPage = publicPages.indexOf($location.path()) === -1;
        if (restrictedPage && !$localStorage.currentUser) {
        	console.log("restricted page")
            $location.path('/app/login');
        }
    });
});