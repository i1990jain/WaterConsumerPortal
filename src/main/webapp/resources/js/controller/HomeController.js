'use strict';

app.controller('HomeController', [ '$scope','$http','$location','AuthenticationService','$localStorage','$route',	function($scope,$http,$location,AuthenticationService,$localStorage,$route) {
	var user = this;
	
	user.logout = logout;
	
	$scope.username=$localStorage.currentUser.username;
	
	
	$scope.getPartial = function () {
		
	      return 'app/histogram';
	  }
	
	function logout() {
		console.log("here")
		 $location.path('/app/login');
		 AuthenticationService.Logout();
		
	};
}]);