/**
 * 
 */
var reportsModule = angular.module('reportsModule');
reportsModule.controller('reportsController', function($scope, $rootScope,$location,reportsService, $route) {
	var reportsCtrl = this;
	
	if($rootScope.userSession.role !== "Admin") {
		alert("this page is for admin only");
		$location.path("/");
	}
	
	reportsCtrl.error = false;
	reportsCtrl.message = "";
	
	reportsCtrl.options = {};
	
	reportsCtrl.payments = {
			labels : [],
			data : []
	}
	
	reportsCtrl.projects = {
			labels : [],
			data : []
	}
	
	reportsCtrl.comments = {
			labels : [],
			data : []
	}
	
	reportsCtrl.init = function(){
		
		console.log('init called')
		reportsService.listPayments($rootScope.userSession.id,null,
			function(responseData) {
				$scope.message = "Success";
	    		$scope.error = false;
				console.log(responseData);
				for(var i =0 ; i<responseData.length;i++ ){
					reportsCtrl.payments.labels.push(responseData[i].name);
					reportsCtrl.payments.data.push(responseData[i].number);
				}
				reportsCtrl.paymentsArr = responseData;
			},
			function(responseData) {
				reportsCtrl.error =  true;
				reportsCtrl.message = responseData.message;
				$scope.message = "Failed to load data";
	    		$scope.error = true;   
			}
		);
		
		reportsService.listProjects($rootScope.userSession.id,null,
			function(responseData) {
				$scope.message = "Success";
	    		$scope.error = false;
				console.log(responseData);
				for(var i =0 ; i<responseData.length;i++ ){
					reportsCtrl.projects.labels.push(responseData[i].name);
					reportsCtrl.projects.data.push(responseData[i].number);
				}
				reportsCtrl.projectsArr = responseData;
			},
			function(responseData) {
				reportsCtrl.error =  true;
				reportsCtrl.message = responseData.message;
				$scope.message = "Failed to load data";
	    		$scope.error = true;   
			}
		);
		
		reportsService.listComments($rootScope.userSession.id,null,
			function(responseData) {
				$scope.message = "Success";
	    		$scope.error = false;
				console.log(responseData);
				for(var i =0 ; i<responseData.length;i++ ){
					reportsCtrl.comments.labels.push(responseData[i].name);
					reportsCtrl.comments.data.push(responseData[i].number);
				}
				reportsCtrl.commentsArr = responseData;
			},
			function(responseData) {
				reportsCtrl.error =  true;
				reportsCtrl.message = responseData.message;
				$scope.message = "Failed to load data";
	    		$scope.error = true;   
			}
		);
	}

});

reportsModule.factory('reportsService', function($http,$timeout,APP_CONSTANT) {
	var reportsService = {};
	
	reportsService.listPayments = function (id,data, callback,callbackError) {
		if(APP_CONSTANT.DEMO){
			$timeout(function(){
		     		var response;
		     			response = data;
		         callback(response);
			}, 1000);
		} else {
			
			$http.get(
         			APP_CONSTANT.REMOTE_HOST+'/reports/paymentsInProjects'
         			).success(function (data, status, headers, config) {
    					callback(data);
        			})
        			.error(function (data, status, headers, config) { // IF STATUS CODE NOT 200
        					if(status== 422){
        						callbackError(data);
        					}
        			}
        	);
		}
	};
	
	reportsService.listProjects = function (id,data, callback,callbackError) {
		if(APP_CONSTANT.DEMO){
			$timeout(function(){
		     		var response;
		     			response = data;
		         callback(response);
			}, 1000);
		} else {
			$http.get(APP_CONSTANT.REMOTE_HOST+'/reports/projectsInCategory'
         			).success(function (data, status, headers, config) {
    					callback(data);
        			}).error(function (data, status, headers, config) { // IF STATUS CODE NOT 200
        					if(status== 422){
        						callbackError(data);
        					}
        			}
        	);
		}
	};
	
	reportsService.listComments = function (id,data, callback,callbackError) {
		if(APP_CONSTANT.DEMO){
			$timeout(function(){
		     		var response;
		     			response = data;
		         callback(response);
			}, 1000);
		} else {
			
			$http.get(
         			APP_CONSTANT.REMOTE_HOST+'/reports/commentsInProjects'
         			).success(function (data, status, headers, config) {
    					callback(data);
        			})
        			.error(function (data, status, headers, config) { // IF STATUS CODE NOT 200
        					if(status== 422){
        						callbackError(data);
        					}
        			}
        	);
		}
	};
		
	
	
	
	return reportsService;
});
