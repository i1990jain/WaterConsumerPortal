app.controller('HistogramController', [ '$scope','$http','$location','$document','$localStorage',	function($scope,$http,$location,$document,$localStorage) {

	$scope.toggleLoading = function () {
		this.chartConfig.loading = !this.chartConfig.loading
	}
	
	$scope.types = [ {code: 1, name: 'Days'}, {code: 2, name: 'Months'}, {code: 3, name: 'Years'}];
	
	
	$scope.chartConfig = {
			options: {
				chart: {
					type: 'column',
					alignTicks: false
				},
				rangeSelector: {
					buttons: [{
	                    type: 'month',
	                    count: 1,
	                    text: '1m'
	                }, {
	                    type: 'month',
	                    count: 3,
	                    text: '3m'
	                },
	                {
	                    type: 'month',
	                    count: 6,
	                    text: '6m'
	                },{
	                    type: 'all',
	                    text: 'All'
	                }],
	                
					selected: 0,
					enabled: true
				},
				navigator: {
					enabled: true
				}
			},
			 yAxis: {
		            title: {
		                text: 'Water Consumption [m\u00B3]'
		            }
		        },
			series: [],
			title: {
				text: 'Household Consumption'
			},
			useHighStocks: true
	}

	$document.ready(function () {
		Highcharts.setOptions({
			global: {
				useUTC: false
			}
		});
		$scope.toggleLoading();

		var token=$localStorage.currentUser.token;
		var data = {
				oid : token
		};

		$http.post('histogram/', data)
		.success(function (response) {
			var array=response.result;
			var readingData =[];

			for (var i in array) {
				var reading=[array[i].readingDateTime,array[i].totalConsumptionAdjusted]
				readingData.push(reading);

			}
			readingData.sort();
			
			$scope.chartConfig.series.push({
				name: 'Consumption',
				data:readingData,
				tooltip: {
                    valueDecimals: 1,
                    valueSuffix: ' m\u00B3'
                },
				dataGrouping: {
		            approximation: "average",
		            enabled: true,
		            units: [[
		                'day',
		                [1]
		                ]]
		        
				}
			});

			


			$scope.toggleLoading();
		});
	});

	$scope.chartTypeChange = function () {
				
		if($scope.chartType.name==="Days"){
			$scope.chartConfig.series[0].dataGrouping.units=[['day',[1]]];
			this.chartConfig.options.rangeSelector.selected=0;
		}
		
		if($scope.chartType.name==="Months"){
			$scope.chartConfig.series[0].dataGrouping.units=[['month',[1]]];
			this.chartConfig.options.rangeSelector.selected=2;
		
		}
		
		if($scope.chartType.name==="Years"){
			$scope.chartConfig.series[0].dataGrouping.units=[['year',[1]]];
			this.chartConfig.options.rangeSelector.selected=3;
		}
	}


}]);