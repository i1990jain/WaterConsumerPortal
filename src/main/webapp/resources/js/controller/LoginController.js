/**
 * 
 */
'use strict';

app.controller('LoginController', [ '$scope','$http','$location','AuthenticationService',	function($scope,$http,$location,AuthenticationService) {
	var user = this;
	
    user.login = login;
    
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



