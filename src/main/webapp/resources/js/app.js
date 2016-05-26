//'use strict';

var app = angular.module('wcp',['ngRoute','ngResource']);
var httpHeaders, message;

app.config(function ($routeProvider, $httpProvider) {
    //configure the rounting of ng-view
    $routeProvider
            .when('/',
            		{controller: 'HomeController',
    					templateUrl: 'login.jsp',
						publicAccess: true})
            .when('/login',
                    {controller: 'HomeController',
            			templateUrl: 'login.jsp',
                        publicAccess: true})
            .when('/home',
                    {controller: 'HomeController',
                        templateUrl: 'home.jsp'});


    //configure $http to catch message responses and show them
    $httpProvider.interceptors.push(function ($q) {
        var setMessage = function (response) {
            //if the response has a text and a type property, it is a message to be shown
            if (response.data.text && response.data.type) {
                message = {
                    text: response.data.text,
                    type: response.data.type,
                    show: true
                };
            }
        };

        return {
            //this is called after each successful server request
            'response': function (response) {
                // console.log('request:' + response);
                setMessage(response);
                return response || $q.when(response);
            },
            //this is called after each unsuccessful server request
            'responseError': function (response) {
                //console.log('requestError:' + response);
                setMessage(response);
                return $q.reject(response);
            }

        };
    });

    $httpProvider.interceptors.push(function ($rootScope, $q) {

        return {
            'request': function (config) {
                // console.log('request:' + config);
                return config || $q.when(config);
            },
            'requestError': function (rejection) {
                // console.log('requestError:' + rejection);
                return rejection;
            },
            //success -> don't intercept
            'response': function (response) {
                // console.log('response:' + response);
                return  response || $q.when(response);
            },
            //error -> if 401 save the request and broadcast an event
            'responseError': function (response) {
                console.log('responseError:' + response);
                if (response.status === 401) {
                    var deferred = $q.defer(),
                            req = {
                                config: response.config,
                                deferred: deferred
                            };
                    $rootScope.requests401.push(req);
                    $rootScope.$broadcast('event:loginRequired');
                    return deferred.promise;
                }
                return $q.reject(response);
            }

        };
    });


    httpHeaders = $httpProvider.defaults.headers;
});