
'use strict';

app.controller('RegisterController', [ '$rootScope','$scope','$http','$location','RegisterService',	function($rootScope,$scope,$http,$location,RegisterService) {

	var vm = this;
	vm.register = register;

	$rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	});

	function register(isValid) {
		console.log($scope.firstname,$scope.lastname,$scope.username,$scope.zipcode,$scope.householdid,$scope.email,$scope.password)


		if (isValid) {
			RegisterService.register($scope.firstname,$scope.lastname,$scope.username,$scope.zipcode,$scope.householdid,$scope.email,$scope.password, function (result) {
				if (result === true) {
					$location.path('/app/registersuccess');

				} else {
					console.log(result);  

					if(result==='Unauthorized'){

						$scope.error = 'InValid houseHoldID';
					}

					
					$scope.householdid="";
					$scope.password="";
					$scope.message = true;
				}
			})
		}else {
			$scope.message = true;
			$scope.error = "There are still invalid fields below";
		}
	}	

}])   




