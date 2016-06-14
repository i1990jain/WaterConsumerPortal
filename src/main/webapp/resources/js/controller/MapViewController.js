'use strict';

app.controller('MapViewController', [ '$rootScope','$scope','$http','$location','AuthenticationService','$localStorage','$route','$document',	function($rootScope,$scope,$http,$location,AuthenticationService,$localStorage,$route,$document) {
	
	var user = this;

	user.logout = logout;
	
	user.loadHome=loadHome;
	
	$rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	  });
	
	function loadHome() {
		$localStorage.currentUser.pagetoken="1";
		
		$location.path('/app/home');
	}
	
	$document.ready(function () {
		
			$localStorage.currentUser.pagetoken="2";
		
		
		var mapDiv = document.getElementById('map');
        var map = new google.maps.Map(mapDiv, {
          center: {lat: 44.540, lng: -78.546},
          zoom: 8
        });
		
	});
	
	function logout() {
		console.log("here")
		$location.path('/app/login');
		AuthenticationService.Logout();

	};
	
	
}]);
