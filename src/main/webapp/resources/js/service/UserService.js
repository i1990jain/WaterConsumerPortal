'use strict';
 
app.factory('UserService', ['$http', '$q', function($http, $q){
	
	return {
	
		
		createUser: function( username, password){
			//httpHeaders.common['Authorization'] = 'Basic ' + base64.encode(username + ':' + password);
			var data = {
					name : username,
					password : password
			};
            
            $http.post('/login',data)
                    .then(
                            function(response){
                                return response.data;
                            }, 
                            function(errResponse){
                                console.error('Error while creating user');
                                return $q.reject(errResponse);
                            }
                    );
    }
	};
	
}]);