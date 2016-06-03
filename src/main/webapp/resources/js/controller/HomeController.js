'use strict';

app.controller('HomeController', [ '$scope','$http','$location','AuthenticationService','$localStorage',	function($scope,$http,$location,AuthenticationService,$localStorage) {
	var user = this;
	
	user.logout = logout;
	
	$scope.username=$localStorage.currentUser.username;
	
	
	function logout() {
		console.log("here")
		 $location.path('/app/login');
		 AuthenticationService.Logout();
		
	};
}]);