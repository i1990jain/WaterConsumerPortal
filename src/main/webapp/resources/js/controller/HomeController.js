'use strict';

app.controller('HomeController', [ '$rootScope','$scope','$http','$location','AuthenticationService','$localStorage','$route','$document',	function($rootScope,$scope,$http,$location,AuthenticationService,$localStorage,$route,$document) {
	var user = this;

	user.logout = logout;
	user.loadMapView=loadMapView;
	user.addData=addData;
	$rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	});

	$scope.username=$localStorage.currentUser.username;


	$scope.getPartial = function () {

		return 'app/histogram';
	}

	function loadMapView() {
		$localStorage.currentUser.pagetoken="2";

		$location.path('/app/mapview');
	}
	
	function addData() {
		$localStorage.currentUser.pagetoken="3";

		$location.path('/app/adddata');
	}



	$document.ready(function () {



		$localStorage.currentUser.pagetoken="1";


		var token=$localStorage.currentUser.token;
		var data = {
				oid : token
		};

		$http.post('userdata/', data)
		.success(function (response) {
			$scope.houseHoldId=response.result.houseHoldId;
			$scope.smartMeterId=response.result.smartMeterId;
			$localStorage.currentUser.smartmeterid=$scope.smartMeterId;
			$scope.buildingId=response.result.buildingId;
			$scope.consumptionType=response.result.consumptionType;
			if($scope.consumptionType==="individual"){
				$localStorage.currentUser.usertype="individual";
				$scope.individual = true;
				$scope.common = false;
			}else{
				$localStorage.currentUser.usertype="common";
				$scope.individual = false;
				$scope.common = true;
			}
			
			if(response.result.zipcode===null){
				$scope.zipcode="-";
			}else{
				$scope.zipcode=response.result.zipcode;
				
			}
			
			if(response.result.country===null){
				$scope.country="-";
			}else{
				$scope.country=response.result.country;
				
			}
			$localStorage.currentUser.country=response.result.country;
			$localStorage.currentUser.zipcode=response.result.zipcode;
			
			if($localStorage.currentUser.type==="Non-MeteredUserFound"){
				
				$scope.nonmeterduser=true;
			}
		});
	});

	function logout() {
		console.log("here")
		$location.path('/app/login');
		AuthenticationService.Logout();

	};
}]);