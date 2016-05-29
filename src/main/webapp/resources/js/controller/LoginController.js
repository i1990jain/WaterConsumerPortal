/**
 * 
 */
'use strict';

app.controller('LoginController', [ '$scope','$http',	function($scope,$http) {
	
		              
	$scope.login = function() {
		var data = {
				username : $scope.username,
				password : $scope.password
		};
        
        $http.post('login/',data)
                .then(
                        function(response){
                        	 
                        }, 
                        function(errResponse){
                        	 console.error('Error while creating user');
                            
                        }
                );
	   };
}]);



