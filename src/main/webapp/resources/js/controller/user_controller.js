/**
 * 
 */
'use strict';

app.controller('LoginController', [ '$scope', '$resource', 	function($scope, $resource) {
	
	
	$scope.login = function() {
		
		console.log('username:password @' + $scope.username + ','
				+ $scope.password);

		var User = $resource('login/');

		User.save({
			username : $scope.username,
			password : $scope.password
		}, function(response) {
			$scope.message = response.message;
		});
	};
}]);