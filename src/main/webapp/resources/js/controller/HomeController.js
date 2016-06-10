'use strict';

app.controller('HomeController', [ '$scope','$http','$location','AuthenticationService','$localStorage','$route','$document',	function($scope,$http,$location,AuthenticationService,$localStorage,$route,$document) {
	var user = this;

	user.logout = logout;
	user.loadMapView=loadMapView;
	user.loadHome=loadHome;

	$scope.username=$localStorage.currentUser.username;


	$scope.getPartial = function () {

		return 'app/histogram';
	}

	function loadMapView() {
		$localStorage.currentUser.pagetoken="2";
		
		$location.path('/app/mapview');
	}

	function loadHome() {
		$localStorage.currentUser.pagetoken="1";
		
		$location.path('/app/home');
	}

	$document.ready(function () {

		
		if($localStorage.currentUser.pagetoken=== "2"){
			$localStorage.currentUser.pagetoken="1";
		}else if($localStorage.currentUser.pagetoken=== "1"){
			$localStorage.currentUser.pagetoken="2";
		}else if($localStorage.currentUser.pagetoken=== ""){
			$localStorage.currentUser.pagetoken="1";
		}

		var token=$localStorage.currentUser.token;
		var data = {
				oid : token
		};

		$http.post('userdata/', data)
		.success(function (response) {
			$scope.houseHoldId=response.result.houseHoldId;
			$scope.smartMeterId=response.result.smartMeterId;
			$scope.buildingId=response.result.buildingId;
			$scope.consumptionType=response.result.consumptionType;
			$scope.zipcode=response.result.zipcode;
			$localStorage.currentUser.zipcode=response.result.zipcode;
			$scope.country=response.result.country;
		});
	});

	function logout() {
		console.log("here")
		$location.path('/app/login');
		AuthenticationService.Logout();

	};
}]);