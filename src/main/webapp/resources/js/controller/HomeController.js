'use strict';

app.controller('HomeController', [ '$scope','$http','$location','AuthenticationService','DataService',	function($scope,$http,$location,AuthenticationService,DataService) {
	var user = this;
	
	user.logout = logout;
	
	$scope.username=DataService.username;
	
	
	function logout() {
		console.log("here")
		 $location.path('/app/login');
		 AuthenticationService.Logout();
		
	};
}]);