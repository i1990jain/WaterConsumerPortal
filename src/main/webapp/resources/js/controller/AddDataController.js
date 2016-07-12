'use strict';

app.controller('AddDataController', [ '$rootScope','$scope','$http','$location','AuthenticationService','$localStorage','$route','$document',	function($rootScope,$scope,$http,$location,AuthenticationService,$localStorage,$route,$document) {
	var user = this;

	user.logout = logout;
	user.loadMapView=loadMapView;
	user.loadHome=loadHome;
	user.addReading=addReading;
	
	
	
	$rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	});

	$scope.username=$localStorage.currentUser.username;

	function addReading() {
		
		var smartmeterid=$localStorage.currentUser.smartmeterid;
		var data = {
				oid : smartmeterid,
				readingDateTime : $scope.datetime,
				totalConsumptionAdjusted : $scope.value
				
		};

		$http.post('addreading/', data)
		.success(function (response) {
			$localStorage.currentUser.pagetoken="1";
			$location.path('/app/home');
		});
		
		
		
		
	}
	
	function loadHome() {
		$localStorage.currentUser.pagetoken="1";
		
		$location.path('/app/home');
	}

	
	function loadMapView() {
		$localStorage.currentUser.pagetoken="2";

		$location.path('/app/mapview');
	}
	
	


	$document.ready(function () {

		 $(".form_datetime").datetimepicker({
		        
		        autoclose: true,
		    });

		$localStorage.currentUser.pagetoken="3";

	});

	function logout() {
		console.log("here")
		$location.path('/app/login');
		AuthenticationService.Logout();

	};
}]);