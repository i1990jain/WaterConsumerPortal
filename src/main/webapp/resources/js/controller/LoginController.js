/**
 * 
 */
'use strict';

app.controller('LoginController', [ '$scope','$http','$location','AuthenticationService',	function($scope,$http,$location,AuthenticationService) {
	var user = this;

    user.login = login;
    
    function login() {
		$scope.loading = true;
    AuthenticationService.Login($scope.username, $scope.password, function (result) {
        if (result === true) {
            $location.path('/app/home');
        } else {
        	$scope.error = 'Username or password is incorrect';
        	$scope.loading = false;
        }
    });};
}]);



