'use strict';

app.controller('HomeController', [ '$scope','$http','$location','AuthenticationService',	function($scope,$http,$location,AuthenticationService) {
	var user = this;
	
	user.logout = logout;
	
	function logout() {
		console.log("here")
		 $location.path('/app/login');
		 AuthenticationService.Logout();
		
	};
}]);