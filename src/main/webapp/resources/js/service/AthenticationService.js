(function () {
'use strict';

    app.factory('AuthenticationService', Service);

    function Service($http, $localStorage) {
        var service = {};

        service.Login = Login;
        service.Logout = Logout;

        return service;

        function Login(username, password, callback) {
        	
        	var data = {
    				username : username,
    				password : password
    		};
        	
            $http.post('login/', data)
                .success(function (response) {
                    // login successful if there's a token in the response
                    if (response.token) {
                        // store username and token in local storage to keep user logged in between page refreshes
                        $localStorage.currentUser = { username: username,type: response.type, token: response.token, zipcode: "",smartmeterid: "", pagetoken: "",usertype: "",country: "",total: "",daily: "",weekly:"",monthly:"" };

                        // add jwt token to auth header for all requests made by the $http service
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.token;

                        // execute callback with true to indicate successful login
                        callback(true);
                        callback(response);
                    } else {
                        // execute callback with false to indicate failed login
                        callback(false);
                    }
                }).error(function(response){
                	 callback(false);
                	callback(response);
                                     
                });
        }

        function Logout() {
        	console.log("logging out")
            // remove user from local storage and clear http auth header
            delete $localStorage.currentUser;
            $http.defaults.headers.common.Authorization = '';
        }
    }
    })();
