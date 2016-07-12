/**
 * 
 */
'use strict';

app.controller('FirstPageController', [ '$rootScope','$scope','$http','$location','AuthenticationService','$localStorage','$document',	function($rootScope,$scope,$http,$location,AuthenticationService,$localStorage,$document) {
	var user = this;
	
    user.addMeter = addMeter;
    
    $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	  });
    
    
  
    
    function addMeter() {
    	console.log($scope.meterid);
    	
    	var token=$localStorage.currentUser.token;
		var data = {
				oid : token,
				meterid : $scope.meterid
		};

		$http.post('meterid/', data)
		.success(function (response) {
			$localStorage.currentUser.pagetoken="1";
			$location.path('/app/home');
		});
   };
    
    
  $document.ready(function () {
	  $(".form_datetime").datetimepicker({
	        format: "dd MM yyyy - hh:ii",
	        autoclose: true,
	    });
	  
	  if($localStorage.success){
    	if($localStorage.success.result==="yes"){
    		$scope.regSuccessMsg="Registration Success!";
    		$scope.regSuccess=true;
    		delete $localStorage.success;
    	}
	  }
	  
    });
}]);
