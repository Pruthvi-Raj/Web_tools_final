/**
 * 
 */

var homeModule = angular.module('homeModule'); // Please dont not use [],
// dependency

homeModule.controller("homeController", function($rootScope,$scope, homeService, $location,$scope,$window) {
	var homeCtrl = this;
	homeCtrl.init = function(){
		
		console.log('init called')
		homeService.listCategories(null,null,
				
		function(responseData) {
			$scope.message = "Success";
    		$scope.error = false;
			console.log(responseData);
			homeCtrl.categories = responseData;
		},
		function(responseData) {
			homeCtrl.error = {
					status : true,
					message : reponseData.message
					
			};
			$scope.message = "Failed to load data";
    		$scope.error = true;   
		}
		);
	}
	
	homeCtrl.viewProjects = function (row) {
		console.log(row);
		$rootScope.category = row.category;
		console.log($rootScope.category);
		$location.path('/category/view');

	}
	
});

homeModule.factory('homeService', function($http,$timeout,APP_CONSTANT) {
	var homeService = {};
	
	homeService.listCategories = function (id,data, callback,callbackError) {
		if(APP_CONSTANT.DEMO){
			$timeout(function(){
		     		var response;
		     			response = [
		     				  		{
		     					    "id": 1,
		     					    "name": "Cancer",
		     					    "description": "Cancer",
		     					    "isdeletable": false,
		     					    "deleted": false,
		     					    "enabled": true
		     					  },
		     					  {
		     					    "id": 4,
		     					    "name": "Kidney",
		     					    "description": "Kidney",
		     					    "isdeletable": false,
		     					    "deleted": false,
		     					    "enabled": true
		     					  }
		     					];
		         callback(response);
			}, 1000);
		} else {
			
			 $http.get(
         			APP_CONSTANT.REMOTE_HOST+'/category/getAllActiveCategories'
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
	
	
	
	
	return homeService;
});

