
'use strict';

app.controller('RegisterController', [ '$rootScope','$scope','$http','$location','RegisterService','$localStorage',	function($rootScope,$scope,$http,$location,RegisterService,$localStorage) {

	var vm = this;
	vm.register = register;

	$rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	});

	function register() {
		console.log($scope.firstname,$scope.lastname,$scope.username,$scope.zipcode,$scope.householdid,$scope.registerform.value1,$scope.email,$scope.password,$scope.password)


		
			RegisterService.register($scope.firstname,$scope.lastname,$scope.username,$scope.zipcode,$scope.householdid,$scope.registerform.value1,$scope.email,$scope.password, function (result) {
				console.log(result);
				if (result === true) {
					
					$localStorage.success={result:"yes"};
					$location.path('/app/login');

				} else {
					if(result.response==='Unauthorized'){

					
						$scope.error = result.errormsg;
					}

					
					$scope.householdid="";
					$scope.password="";
					$scope.message = true;
				}
			})
		
	}	

}])   




