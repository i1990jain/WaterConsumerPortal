/**
 * 
 */
'use strict';

app.controller('LoginController', [ '$scope','$window', '$resource', 	function($scope, $window, $resource, $rootScope) {
	
	
	$scope.login = function() {
		
		/*console.log('username:password @' + $scope.username + ','
				+ $scope.password);*/

		var User = $resource('login/');

		User.save({
			username : $scope.username,
			password : $scope.password
		}, function(response) {
			//redirect to home 	
			
		});
	};
}]);



