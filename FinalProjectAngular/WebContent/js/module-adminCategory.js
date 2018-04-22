/**
 * 
 */
var adminCategoryModule = angular.module('adminCategoryModule');
adminCategoryModule.controller('adminCategoryController', function($scope, $rootScope,$location,adminCategoryService, $route) {
	var adminCategoryCtrl = this;
	adminCategoryCtrl.messageDash = "Welcome " + $rootScope.userSession.name;
	
	if($rootScope.userSession.role !== "Admin") {
		alert("this page is for admin only");
		$location.path("/");
	}
	if(!$rootScope.category) {
		$location.path("/");
	}
	
	adminCategoryCtrl.category = $rootScope.category;
	
	adminCategoryCtrl.init = function(){
		
		console.log('init called')
		adminCategoryService.listProjects($rootScope.userSession.id,adminCategoryCtrl.category,
				
		function(responseData) {
			$scope.message = "Success";
    		$scope.error = false;
			console.log(responseData);
			adminCategoryCtrl.projects = responseData;
		},
		function(responseData) {
			adminCategoryCtrl.error = {
					status : true,
					message : reponseData.message
					
			};
			$scope.message = "Failed to load data";
    		$scope.error = true;   
		}
		);
	}
	
	adminCategoryCtrl.viewProject = function (row) {
		console.log(row);
		$rootScope.project = row.project;
		console.log($rootScope.project);
		$location.path('/project/view');

	};
	
	adminCategoryCtrl.deleteProject = function (row) {
		console.log(row);
		adminCategoryService.deleteProject($rootScope.userSession.id,row.project,
				
				function(responseData) {
					$scope.message = "Success";
		    		$scope.error = false;
					console.log(responseData);
					alert(responseData.message);
				},
				function(responseData) {
					alert(responseData.message);
					adminCategoryCtrl.error = {
							status : true,
							message : reponseData.message
							
					};
					$scope.message = "Failed to load data";
		    		$scope.error = true;   
				}
				);
		$route.reload();
	}
	
	adminCategoryCtrl.undeleteProject = function (row) {
		console.log(row);
		adminCategoryService.undeleteProject($rootScope.userSession.id,row.project,
				
				function(responseData) {
					$scope.message = "Success";
		    		$scope.error = false;
					console.log(responseData);
					alert(responseData.message);
				},
				function(responseData) {
					alert(responseData.message);
					adminCategoryCtrl.error = {
							status : true,
							message : reponseData.message
							
					};
					$scope.message = "Failed to load data";
		    		$scope.error = true;   
				}
				);
		$route.reload();
	}
	


});

adminCategoryModule.factory('adminCategoryService', function($http,$timeout,APP_CONSTANT) {
	var adminCategoryService = {};
	
	adminCategoryService.listProjects = function (id,data, callback,callbackError) {
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
         			APP_CONSTANT.REMOTE_HOST+'/project/getAllProjects/catID/'+data.id
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
		
	adminCategoryService.deleteProject = function (id,data, callback,callbackError) {
		if(APP_CONSTANT.DEMO){
			$timeout(function(){
		     		var response;
		     		response = {
		     					  "success": true,
		     					  "message": "Project Deleted"
		     					};
		         callback(response);
		     }, 1000);
		} else {
				
			 $http.put(
         			APP_CONSTANT.REMOTE_HOST+'/project/deleteProject/projectId/'+data.id
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
		
	adminCategoryService.undeleteProject = function (id,data, callback,callbackError) {
		if(APP_CONSTANT.DEMO){
			$timeout(function(){
	     		var response;
	     		response = {
	     					  "success": true,
	     					  "message": "Project Undeleted"
	     					};
		         callback(response);
		     }, 1000);
		}else{
			 $http.put(
         			APP_CONSTANT.REMOTE_HOST+'/project/undeleteProject/projectId/'+data.id
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
	
	
	return adminCategoryService;
});
