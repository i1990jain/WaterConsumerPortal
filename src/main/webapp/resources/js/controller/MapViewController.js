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

            	var image;
            	if($localStorage.currentUser.usertype==="individual"){
            	 image = 'resources/icons/individual.jpg';
            	}else{
            	 image = 'resources/icons/common.jpg'; 
            	}
                var marker = new google.maps.Marker({
                    map: map,
                    icon: image,
                    position: results[0].geometry.location
                });
            	
                var infowindow = new google.maps.InfoWindow({
                    content: '<b>' +$localStorage.currentUser.zipcode + ","+$localStorage.currentUser.country+'</b><br>'
                    +'<p><b>Total: </b> ' +$localStorage.currentUser.total + " [m\u00B3]<br> <b>Daily:</b> "+$localStorage.currentUser.daily+' [m\u00B3]<br>'
                    +'<b>Weekly: </b> ' +$localStorage.currentUser.weekly + " [m\u00B3]<br><b> Monthly:</b> "+$localStorage.currentUser.monthly+' [m\u00B3]<br></p>'
                    
                  });

                  google.maps.event.addListener(marker, 'click', function() {
                    infowindow.open(map, marker);
                  });
                  
                  
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
