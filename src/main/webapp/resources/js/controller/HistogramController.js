app.controller('HistogramController', [ '$scope','$http','$location',	function($scope,$http,$location) {
	
	
		Highcharts.chart('container',{
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: 'Water Consumption'
	        },
	        xAxis: {
	            categories: ['Apples', 'Bananas', 'Oranges']
	        },
	        yAxis: {
	            title: {
	                text: 'Water Consumption [m3]'
	            }
	        },
	        series: [{
	            name: 'Jane',
	            data: [1, 6, 4]
	        }, {
	            name: 'John',
	            data: [5, 7, 3]
	        }]
	    });
	
	
	
	
	
}]);