/**
 * 
 */
'use strict';

app.controller('LoginController', [ '$rootScope','$scope','$http','$location','AuthenticationService','$localStorage','$document',	function($rootScope,$scope,$http,$location,AuthenticationService,$localStorage,$document) {
	var user = this;
	
    user.login = login;
    user.register = register;
    $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	  });
    
    
  
    
    function login() {
		
    AuthenticationService.Login($scope.username, $scope.password, function (result) {
    	console.log(result);
    	if(result===true){
    	/*	if (result.response === 'MeteredUserFound') {
            	console.log("i got in");
           		console.log("meteredUser found");
            		$location.path('/app/home');	
           
            }else if(result.response === 'Non-MeteredUserFound'){
            		console.log("Non-meteredUser found");
            		$location.path('/app/home');
            }else{*/
            	$location.path('/app/home');
           // }
                           
        } else if(result===false) {
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
    function register() {
    	 $location.path('/app/register');
    }
    
  $document.ready(function () {
	  if($localStorage.success){
    	if($localStorage.success.result==="yes"){
    		$scope.regSuccessMsg="Registration Success!";
    		$scope.regSuccess=true;
    		delete $localStorage.success;
    	}
	  }
	  
    });
}]);
