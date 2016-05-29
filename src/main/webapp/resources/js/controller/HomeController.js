'use strict';

app.controller('HomeController', [ '$scope', '$resource', 	function($scope, $resource, $rootScope) {
	
	
	$scope.login = function() {
		
		console.log('username:password @' + $scope.username + ','
				+ $scope.password);

		var User = $resource('login/');

		User.save({
			username : $scope.username,
			password : $scope.password
		}, function(response) {
			
			$scope.message = response.message;
			console.log($scope.message);
			if(scope.message.result="success"){
				
				 $rootScope.$emit("HomeController", {});
			}
		});
	};
}]);