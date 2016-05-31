/**
 * 
 */
'use strict';

app.controller('LoginController', [ '$rootScope','$scope','$http','$location','AuthenticationService',	function($rootScope,$scope,$http,$location,AuthenticationService) {
	var user = this;
	
    user.login = login;
    
    $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	  });
    
    
    function login() {
		
    AuthenticationService.Login($scope.username, $scope.password, function (result) {
        if (result === true) {
            $location.path('/app/home');
        } else {
        	console.log('invalid user');
        	$scope.username="";
        	$scope.password="";
        	$scope.error = 'Username or Password is incorrect';
        	$scope.message = true;
        }
    });};
}]);



