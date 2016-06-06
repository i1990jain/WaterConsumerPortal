app.controller('HistogramController', [ '$scope','$http','$location','$document','$localStorage','$mdDialog',	function($scope,$http,$location,$document,$localStorage,$mdDialog) {

	
	var readingData =[];
	
	$scope.toggleLoading = function () {
		this.chartConfig.loading = !this.chartConfig.loading
	}
	
	
	
	$scope.showAlert = function(date,timestamp, value) {
		
		if($scope.chartType.name==="Days"){
			
		var readingDate=new Date(date);
		var tmpl = '<b>Hourly Data [m\u00B3]</b> <br>';
		var readingDateString=readingDate.toDateString();
		tmpl=tmpl.concat('<ul>');
		for (var i in readingData) {
			var datesfromreading=new Date(readingData[i][0]);
			
			
			var checkdate=datesfromreading.toDateString();
			
			if(readingDateString===checkdate){
				console.log(checkdate);
				var hours = datesfromreading.getHours();
				var minutes = "0" + datesfromreading.getMinutes();
				var seconds = "0" + datesfromreading.getSeconds();
				tmpl=tmpl.concat('<li>'+hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2)+' - '+readingData[i][1]+'</li>');
			}
		}
		tmpl=tmpl.concat('</ul>');
		
		/*for (i = 0; i < timestamp.length; i += 1) {
			var time=new Date(timestamp[i]);
			console.log(time);
			
		}*/
		
		
		
	    $mdDialog.show(
	      $mdDialog.alert()
	        .parent(angular.element(document.querySelector('#popupContainer')))
	        .clickOutsideToClose(true)
	        .title(readingDate.toDateString())
	        .ariaLabel('Alert Dialog')
	        .ok('Ok')
	        .hasBackdrop(false)
	        .htmlContent(tmpl)
	    );
		}
	};
	
	
	$scope.types = [ {code: 1, name: 'Days'}, {code: 2, name: 'Weeks'}, {code: 3, name: 'Months'}];
	
	
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
			},
			lang:{
				rangeSelectorZoom: 'Select the time span to observe:'
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
			

			for (var i in array) {
				var reading=[array[i].readingDateTime,array[i].totalConsumptionAdjusted]
				readingData.push(reading);

			}
			readingData.sort();
			
			var chartdata=[];
			var j=0;
			var consumption=0;
			var currentTempReadingDate = new Date(array[j].readingDateTime);
			var currentTempReadingDateString = currentTempReadingDate.toDateString();
			for (var i in array) {
							
				var nextTempReadingDate = new Date(array[i].readingDateTime);
				var nextTempReadingDateString = nextTempReadingDate.toDateString();
					
								
				if(currentTempReadingDateString!==nextTempReadingDateString){
					console.log(array[i-1].totalConsumptionAdjusted+"and"+array[j].totalConsumptionAdjusted);
					consumption=array[i-1].totalConsumptionAdjusted-array[j].totalConsumptionAdjusted;
					if(consumption<0){
						consumption=consumption*-1;
					}
					var reading=[array[j].readingDateTime,consumption];
					chartdata.push(reading);
					currentTempReadingDate = new Date(array[i].readingDateTime);
					currentTempReadingDateString = currentTempReadingDate.toDateString();
					j=i;
				}												
				
			}
			
			chartdata.sort();
			
			
			$scope.chartConfig.series.push({
				name: 'Consumption',
				data:chartdata,
				cursor: 'pointer',
				tooltip: {
                    valueDecimals: 1,
                    valueSuffix: ' m\u00B3'
                },
				dataGrouping: {
		            approximation: "sum",
		            enabled: true,
		            units: [[
		                'day',
		                [1]
		                ]]
		        
				},
				point: {
                    events: {
                        click: function () {
                        	console.log(this);
                        	$scope.showAlert(this.x,this.series.processedXData,this.series.processedYData);
                            //alert('Category: ' + this.series.processedXData + ', value: ' + this.series.processedYData);
                            /*this.series.tooltip.formatter= function() {
                                return 'Extra data: <b>'+ this.series.processedXData +'</b>';
                            };*/
                        }
                    }
                }
			});

			


			$scope.toggleLoading();
		});
	});

	$scope.chartTypeChange = function () {
				
		if($scope.chartType.name==="Days"){
			$scope.chartConfig.series[0].dataGrouping.units=[['day',[1]]];
			this.chartConfig.options.rangeSelector.selected=0;
			$scope.chartConfig.series[0].cursor='pointer';
		}
		
		if($scope.chartType.name==="Weeks"){
			$scope.chartConfig.series[0].dataGrouping.units=[['week',[1]]];
			this.chartConfig.options.rangeSelector.selected=2;
			$scope.chartConfig.series[0].cursor='';
		}
		
		if($scope.chartType.name==="Months"){
			$scope.chartConfig.series[0].dataGrouping.units=[['month',[1]]];
			this.chartConfig.options.rangeSelector.selected=3;
			$scope.chartConfig.series[0].cursor='';
		}
	}
	
	


}]);