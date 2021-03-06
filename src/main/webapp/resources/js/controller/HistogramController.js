app.controller('HistogramController', [ '$scope','$http','$location','$document','$localStorage','$mdDialog',	function($scope,$http,$location,$document,$localStorage,$mdDialog) {


	var readingData =[];
	var chartdata=[];
	var neighbourAvg;

	

	$scope.toggleLoading = function () {
		this.chartConfig.loading = !this.chartConfig.loading
	}

	$scope.neighbourhoodAveragebox=false;
	$scope.neighbourhoodAverage = function (neighbourhoodAveragebox) {
		if($scope.neighbourhoodAveragebox){ 
			
			this.chartConfig.yAxis.plotLines=[{
				color: 'red',
				value: neighbourAvg,
				width: '2',
				zIndex: 2,
				label: { 
					text: 'Neighbourhood average: '+neighbourAvg, // Content of the label. 
					align: 'center', // Positioning of the label. 
					verticalAlign: "top"
				}
			}];

		}else{

			this.chartConfig.yAxis.plotLines='';
			//$scope.chartConfig.series.splice(1,1)

		}

	}

	$scope.userAveragebox=false;
	$scope.userAverage = function (userAveragebox) {
		if($scope.userAveragebox){ 

			var sum = 0;
			for( var i = 0; i < chartdata.length; i++ ){

				sum += chartdata[i][1]; 
			}

			var avg = sum/chartdata.length;
			avg=avg.toFixed(2);

			console.log(avg);
			this.chartConfig.yAxis.plotLines=[{
				color: 'red',
				value: avg,
				width: '2',
				zIndex: 2,
				label: { 
					text: 'Daily Average: '+avg, // Content of the label. 
					align: 'center', // Positioning of the label. 
					verticalAlign: "top"
				}
			}];



		}else{

			this.chartConfig.yAxis.plotLines='';
			//$scope.chartConfig.series.splice(1,1)

		}

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

					var hours = datesfromreading.getHours();
					var minutes = "0" + datesfromreading.getMinutes();
					var seconds = "0" + datesfromreading.getSeconds();
					tmpl=tmpl.concat('<li>'+hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2)+' - '+readingData[i][1]+'</li>');
				}
			}
			tmpl=tmpl.concat('</ul>');





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
		var lastUpdateDate;
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
			console.log(array);
			if(array.length>0){

			for (var i in array) {
				var reading=[array[i].readingDateTime,array[i].totalConsumptionAdjusted]
				readingData.push(reading);
				
				$localStorage.currentUser.total=array[i].totalConsumptionAdjusted;
				
			}
			readingData.sort();


			var j=0;
			var consumption=0;
			var currentTempReadingDate = new Date(array[j].readingDateTime);
			var currentTempReadingDateString = currentTempReadingDate.toDateString();
			var nextTempReadingDate;
			var nextTempReadingDateString;
			for (var i in array) {

				nextTempReadingDate = new Date(array[i].readingDateTime);

				nextTempReadingDateString = nextTempReadingDate.toDateString();


				if(currentTempReadingDateString!==nextTempReadingDateString){

					consumption=array[i-1].totalConsumptionAdjusted-array[j].totalConsumptionAdjusted;

					var reading=[array[i-1].readingDateTime,consumption];
					chartdata.push(reading);
					j=i;
					currentTempReadingDate = new Date(array[i].readingDateTime);
					currentTempReadingDateString = currentTempReadingDate.toDateString();

				}												

			}
			var lastConsumption=0;
			j=0;
			for (var i in array) {

				var currentTempReadingDate = new Date(array[i].readingDateTime);
				var currentTempReadingDateString = currentTempReadingDate.toDateString();

				if(j!=array.length-1){
					if(currentTempReadingDateString===nextTempReadingDateString){

						lastConsumption=lastConsumption+(array[j+1].totalConsumptionAdjusted-array[i].totalConsumptionAdjusted)

					}
				}
				j++;


			}

			var finalReading=[array[j-1].readingDateTime,lastConsumption];
			chartdata.push(finalReading);

			chartdata.sort();

			 lastUpdateDate = new Date(array[j-1].readingDateTime);

			$scope.lastUpdate= lastUpdateDate.toDateString();

			$scope.chartConfig.series.push({
				id: 'Consumption',
				name: 'Consumption',
				data:chartdata,
				cursor: 'pointer',
				tooltip: {
					valueDecimals: 3,
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

							$scope.showAlert(this.x,this.series.processedXData,this.series.processedYData);

						}
					}
				}
			});

			var sum = 0;
			var i;
			for(  i = 0; i < chartdata.length; i++ ){

				sum += chartdata[i][1]; 
			}

			var dailyavg = sum/chartdata.length;
			dailyavg=dailyavg.toFixed(2);

			$localStorage.currentUser.daily=dailyavg;
			var weeklyavg=dailyavg*7;
			weeklyavg=weeklyavg.toFixed(2);
			$localStorage.currentUser.weekly=weeklyavg;
			var monthlyavg=dailyavg*30;
			monthlyavg=monthlyavg.toFixed(2);
			$localStorage.currentUser.monthly=monthlyavg;

			
			
			
			var zipcode=$localStorage.currentUser.zipcode;
			var date=lastUpdateDate;
			console.log(lastUpdateDate);
			var data = {
					zipcode : zipcode,
					date : date
					
			};

			$http.post('nbhavg/', data)
			.success(function (response) {
				neighbourAvg=response.result;
				console.log(neighbourAvg);
				if(!isNaN(neighbourAvg)){
				neighbourAvg=neighbourAvg.toFixed(2);
				}
				$scope.optionsDiv=true;	
				$scope.toggleLoading();
			});
		}else{
			$scope.toggleLoading();
			var tmpl = 'Please add Data using the add button<br>';
			$mdDialog.show(
					$mdDialog.alert()
					.parent(angular.element(document.querySelector('#popupContainer')))
					.clickOutsideToClose(true)
					.title('No Consumption Data Available')
					.ariaLabel('Alert Dialog')
					.ok('Ok')
					.htmlContent(tmpl)
					.hasBackdrop(false));
			$scope.chartConfig.options.title='No Household Data Available';
				
		}
			
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
			this.chartConfig.yAxis.plotLines='';
			$scope.userAveragebox=false;
		}

		if($scope.chartType.name==="Months"){
			$scope.chartConfig.series[0].dataGrouping.units=[['month',[1]]];
			this.chartConfig.options.rangeSelector.selected=3;
			$scope.chartConfig.series[0].cursor='';
			this.chartConfig.yAxis.plotLines='';
			$scope.userAveragebox=false;
		}
	}



}]);