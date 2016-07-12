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
    	console.log(result.username);
    	if(result===true){
    		if ($localStorage.currentUser.type === 'MeteredUserFound') {
            	
           		console.log("meteredUser found");
            		$location.path('/app/home');	
           
            }else if($localStorage.currentUser.type === 'Non-MeteredUserFound'){
            		console.log("Non-meteredUser found");
            		
            		var token=$localStorage.currentUser.token;
            		var data = {
            				oid : token
            		};

            		$http.post('userdata/', data)
            		.success(function (response) {
            			
            			if(response.result.smartMeterId!=0){
            				$location.path('/app/home');
            			}else{
            				$location.path('/app/first');
            			}
            			
            			
            		});
            		
                        
            		
            }else{
            	$location.path('/app/home');
            }
                           
        } else {
        	console.log(result.username);  
        	console.log('invalid user');
        	
        	if(result.response==='Unauthorized'){
        	$scope.error = 'Username or Password is incorrect';
        	$scope.message = true;
        	}
        	if(result.response==='NoHouseHoldId'){
        		$scope.error = $scope.username+' has no HouseholdId associated';
        		$scope.message = true;
        	}
        	
        	$scope.username="";
        	$scope.password="";
        	
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
