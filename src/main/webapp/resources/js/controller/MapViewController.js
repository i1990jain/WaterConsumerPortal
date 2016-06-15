'use strict';

app.controller('MapViewController', [ '$rootScope','$scope','$http','$location','AuthenticationService','$localStorage','$route','$document',	function($rootScope,$scope,$http,$location,AuthenticationService,$localStorage,$route,$document) {
	
	var user = this;

	user.logout = logout;
	
	user.loadHome=loadHome;
	
	$rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	  });
	
	function loadHome() {
		$localStorage.currentUser.pagetoken="1";
		
		$location.path('/app/home');
	}
	
	
	$document.ready(function () {
		
		$localStorage.currentUser.pagetoken="2";
		var map;
		var latitude;
		var longitude;
		var location;
		var geocoder= new google.maps.Geocoder();
		var address = $localStorage.currentUser.zipcode+","+$localStorage.currentUser.country;
		geocoder.geocode({ 'address': address }, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
            	var myOptions = {
                        zoom: 12,
                        center: results[0].geometry.location,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    }
            	map = new google.maps.Map(document.getElementById("map"), myOptions);

                var marker = new google.maps.Marker({
                    map: map,
                    position: results[0].geometry.location
                });
            	
                 latitude = results[0].geometry.location.lat();
                location= results[0].geometry.location;
                 longitude = results[0].geometry.location.lng();
                console.log("Latitude: " + latitude + "\nLongitude: " + longitude);
            } else {
                alert("Request failed.")
            }
        });
		
			
		
		
	});
	
	function logout() {
		console.log("here")
		$location.path('/app/login');
		AuthenticationService.Logout();

	};
	
	
}]);
