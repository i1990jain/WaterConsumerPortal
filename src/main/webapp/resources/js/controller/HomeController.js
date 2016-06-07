'use strict';

app.controller('HomeController', [ '$scope','$http','$location','AuthenticationService','$localStorage','$route','$document',	function($scope,$http,$location,AuthenticationService,$localStorage,$route,$document) {
	var user = this;
	
	user.logout = logout;
	
	$scope.username=$localStorage.currentUser.username;
	
	
	$scope.getPartial = function () {
		
	      return 'app/histogram';
	  }
	
	$document.ready(function () {
		
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