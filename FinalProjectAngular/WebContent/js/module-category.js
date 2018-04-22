/**
 * 
 */

var categoryModule = angular.module('categoryModule'); // Please dont not use [],
// dependency

categoryModule.controller("categoryController", function($rootScope, categoryService, $location,$scope,$window) {
	var categoryCtrl = this;
	
	if(!$rootScope.category) {
		$location.path("/");
	}
	
	categoryCtrl.category = $rootScope.category;
	
	categoryCtrl.init = function(){
		
		console.log('init called')
		categoryService.listProjects(null,categoryCtrl.category,
				
		function(responseData) {
			$scope.message = "Success";
    		$scope.error = false;
			console.log(responseData);
			categoryCtrl.projects = responseData;
		},
		function(responseData) {
			categoryCtrl.error = {
					status : true,
					message : reponseData.message
					
			};
			$scope.message = "Failed to load data";
    		$scope.error = true;   
		}
		);
	}
	
	categoryCtrl.viewProjects = function (row) {
		console.log(row);
		$rootScope.project = row.project;
		console.log($rootScope.project);
		$location.path('/project/view');

	}
	
});

categoryModule.factory('categoryService', function($http,$timeout,APP_CONSTANT) {
	var categoryService = {};
	
	categoryService.listProjects = function (id,data, callback,callbackError) {
		if(APP_CONSTANT.DEMO){
			$timeout(function(){
		     		var response;
		     			response = [
		     				  {
		     					    "id": 1,
		     					    "name": "asd",
		     					    "description": "ads",
		     					    "requiredFunds": 26,
		     					    "acquiredFunds": 2,
		     					    "startDate": 1488949200000,
		     					    "endDate": 1488949200000,
		     					    "active": true,
		     					    "deleted": false
		     					  }
		     					];
		         callback(response);
			}, 1000);
		} else {
			
			 $http.get(
	         			APP_CONSTANT.REMOTE_HOST+'/project/getAllUndeletedProjects/catID/'+data.id
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
	
	
	
	
	return categoryService;
});

