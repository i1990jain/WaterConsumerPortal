
'use strict';

app.controller('RegisterController', [ '$rootScope','$scope','$http','$location','RegisterService',	function($rootScope,$scope,$http,$location,RegisterService) {

	$scope.name = 'smartmterid';
	$scope.show = true;

	
	var vm = this;
	vm.register = register;

	$rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	});

	function register(isValid) {
		console.log($scope.firstname,$scope.lastname,$scope.username,$scope.zipcode,$scope.householdid,$scope.smartmeterid,$scope.email,$scope.password)

		
		if (isValid) {
			RegisterService.register($scope.firstname,$scope.lastname,$scope.username,$scope.zipcode,$scope.householdid,$scope.smartmeterid,$scope.email,$scope.password, function (result) {
				if (result === true) {


					$scope.smartmeter=result;
					
					$location.path('/app/registersuccess');

				} else {
					console.log(result);  
					
					if(result==='Unauthorized'){
						console.log("i am here");
						$scope.error = 'Try to manually Give SmartMeterID again';
							$scope.name = 'smartmterid';
	    					$scope.show = false;
						 

					}

					$scope.firstname="";
					$scope.lastname="";
					$scope.username="";
					$scope.zipcode="";
					$scope.email="";
					$scope.householdid="";
					$scope.password="";
					$scope.message = true;
				}
			})
		}else {
			$scope.error = "There are still invalid fields below";
		}
	}	

}])   




