(function () {
'use strict';

    app.factory('RegisterService', Service);

    function Service($http, $localStorage) {
        var service = {};

        service.Register = register;
        
        return service;

        function register(firstname,lastname,username,zipcode,householdid,smartmeterid,email,password,confirmpassword, callback) {
        	
        	var data = {
        			firtname:firstname,
        			lastname:lastname,
        			username : username,
        			zipcode:zipcode,
        			householdid:householdid,
        			smartmeterid:smartmeterid,
        			email:email,  				
    				password : password,
    				confirmpassword:confirmpassword
    		};
        	
            $http.post('register/', data)
                .success(function (response) {
                    // register successful if there's a smartmeterID in the response
                    if (response.smartmeter) {
                    	
                    	
                         callback(true);
                    } else {
                        
                        callback(false);
                    }
                }).error(function(response){
                	callback(response.response);
                                     
                });
        }

        
    }
    })();
