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
						
			/*console.log(response);
			var data = response.data;
			  var status = response.status;
			if(data.result==="success"){
			*/	
				 $window.location.href = 'home/';
			/*}*/
		});
	};
}]);



/*app.controller('HomeController', [ '$scope', '$resource', 	function($scope, $resource, $rootScope) {
	
	
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
}]);*/