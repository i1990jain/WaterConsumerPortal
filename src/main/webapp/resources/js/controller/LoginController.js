/**
 * 
 */
'use strict';

app.controller('LoginController', [ '$scope','$http','$location',	function($scope,$http,$location) {
	
		              
	$scope.login = function() {
		var data = {
				username : $scope.username,
				password : $scope.password
		};
        
        $http.post('login/',data)
                .then(
                        function(response){
                        	$location.path('/app/home');
                        	console.log('success');
                        }, 
                        function(errResponse){
                        	 console.error('Error while creating user');
                            
                        }
                );
	   };
}]);



