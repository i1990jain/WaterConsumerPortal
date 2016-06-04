
    'use strict';
 
  //  app.controller('RegisterController', RegisterController);
    app.controller('RegisterController', [ '$rootScope','$scope','$http','$location','UserService',	function($rootScope,$scope,$http,$location,UserService) {
  //  RegisterController.$inject = ['UserService', '$location', '$rootScope', 'FlashService'];
 //   function RegisterController(UserService, $location, $rootScope, FlashService) {
        var vm = this;
        vm.register = register;
 
        $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
    		$rootScope.animation = currRoute.animation;
    	  });
        
        function register() {
            vm.dataLoading = true;
            UserService.Create(vm.user)
                .then(function (response) {
                    if (response.success) {
                        FlashService.Success('Registration successful', true);
                        $location.path('/login');
                    } else {
                        FlashService.Error(response.message);
                        vm.dataLoading = false;
                    }
                });
        }
       
    }]);
 
