
'use strict';

app.controller('RegisterController', [ '$rootScope','$scope','$http','$location','RegisterService',	function($rootScope,$scope,$http,$location,RegisterService) {
	
	var vm = this;
	vm.register = register;

	$rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	});

	function register() {
		
		RegisterService.register($scope.firstname,$scope.lastname,$scope.username,$scope.zipcode,$scope.email,$scope.householdid,$scope.password,$scope.confirmpassword, function (result) {
			if (result === true) {
				
				$location.path('/app/login');

			} else {
				console.log(result);  
				console.log('No householdID found');
		
				if(result==='Unauthorized'){
					$scope.error = 'Try to register again';
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
		});}
}])   




