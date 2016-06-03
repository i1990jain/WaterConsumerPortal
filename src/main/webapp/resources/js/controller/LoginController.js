/**
 * 
 */
'use strict';

app.controller('LoginController', [ '$rootScope','$scope','$http','$location','AuthenticationService',	function($rootScope,$scope,$http,$location,AuthenticationService) {
	var user = this;
	
    user.login = login;
    
    $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	  });
    
    
    function login() {
		
    AuthenticationService.Login($scope.username, $scope.password, function (result) {
        if (result === true) {
            $location.path('/app/home');
            
        } else {
        	console.log(result);  
        	console.log('invalid user');
        	
        	if(result==='Unauthorized'){
        	$scope.error = 'Username or Password is incorrect';
        	}
        	if(result==='NoHouseHoldId'){
        		$scope.error = $scope.username+' has no HouseholdId associated';
        	}
        	
        	$scope.username="";
        	$scope.password="";
        	$scope.message = true;
        }
    });};
}]);