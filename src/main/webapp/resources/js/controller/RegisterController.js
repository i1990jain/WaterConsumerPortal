
'use strict';

app.controller('RegisterController', [ '$rootScope','$scope','$http','$location','RegisterService',	function($rootScope,$scope,$http,$location,RegisterService) {

	$scope.name = 'smartmterid';
	$scope.shouldBeDisabled = true;

	var vm = this;
	vm.register = register;

	$rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	});

	function register(isValid) {
		console.log($scope.firstname,$scope.lastname,$scope.username,$scope.zipcode,$scope.householdid,$scope.email,$scope.password)

		
		if (isValid) {
			RegisterService.register($scope.firstname,$scope.lastname,$scope.username,$scope.zipcode,$scope.householdid,$scope.smartmeterid,$scope.email,$scope.password, function (result) {
				if (result === true) {


					$scope.smartmeter=result;

					$location.path('/app/registersucess');

				} else {
					console.log(result);  
					console.log('No householdID found');

					if(result==='Unauthorized'){
						$scope.error = 'Try to register again';
						/*	$scope.name = 'smartmterid';
	    					$scope.shouldBeDisabled = false;
						 */

					}

					$scope.firstname="";
					$scope.lastname="";
					$scope.username="";
					$scope.zipcode="";
					$scope.email="";
					$scope.householdid="";
					$scope.password="";
					$scope.confirmpassword="";
					$scope.message = true;
				}
			})
		}else {
			register.message = "There are still invalid fields below";
		}
	}	

}])   




