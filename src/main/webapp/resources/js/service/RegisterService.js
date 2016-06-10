(function () {
	'use strict';

	app.factory('RegisterService', Service);

	function Service($http, $localStorage) {
		var service = {};

		service.register = register;

		return service;

		function register(firstname,lastname,username,zipcode,householdid,smartmeterid,email,password,callback) {
			console.log("I am here");
			var data = {
					firstName: firstname,
					lastName: lastname,
					userName : username,
					zipCode: zipcode,
					householdID: householdid,
					email: email,  				
					password : password

			};
			console.log(firstname,
					lastname,
					username,
					zipcode,
					householdid,
					email,  				
					password
			);
			$http.post('register/', data)
			.success(function (response) {
				
				console.log(response.smartmeter);
				
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
