/**
 * 
 */
var adminDashboardModule = angular.module('adminDashboardModule');
adminDashboardModule.controller('adminDashboardController', function($scope, $rootScope,$location,adminDashboardService, $route) {
	var adminDashboardCtrl = this;
	adminDashboardCtrl.messageDash = "Welcome " + $rootScope.userSession.name;
	
	if($rootScope.userSession.role !== "Admin") {
		alert("this page is for admin only");
		$location.path("/");
	}
	
	adminDashboardCtrl.category = {
			name : '',
			description : ''
	};
	
	adminDashboardCtrl.init = function(){
		
		console.log('init called')
		adminDashboardService.listCategories($rootScope.userSession.id,null,
				
		function(responseData) {
			$scope.message = "Success";
    		$scope.error = false;
			console.log(responseData);
			adminDashboardCtrl.categories = responseData;
		},
		function(responseData) {
			adminDashboardCtrl.error = {
					status : true,
					message : reponseData.message
					
			};
			$scope.message = "Failed to load data";
    		$scope.error = true;   
		}
		);
	}
	
	adminDashboardCtrl.viewCategory = function (row) {
		console.log(row);
		$rootScope.category = row.category;
		console.log($rootScope.category);
		$location.path('/admin/viewCategory');

	};
	
	adminDashboardCtrl.add = function () {
//		console.log(row);
		var unique = true;
		for(var i=0; i<adminDashboardCtrl.categories.length; i++) {
			var cat = adminDashboardCtrl.categories[i];
			if(cat.name ===  adminDashboardCtrl.category.name) {
				adminDashboardCtrl.alert = {
						status : true,
						message : "Category "+cat.name+" already exists."
						
				};
//				alert("Category with name already exists! Please try another!");
				unique = false;
			} 
		}
		if(unique) {
			adminDashboardService.addCategory($rootScope.userSession.id,adminDashboardCtrl.category,
					
					function(responseData) {
						$scope.message = "Success";
			    		$scope.error = false;
						console.log(responseData);
						alert(responseData.message);
						$route.reload();
//						adminDashboardCtrl.success = responseData;
					},
					function(responseData) {
						alert(responseData.message);
						adminDashboardCtrl.error = {
								status : true,
								message : reponseData.message
								
						};
						$scope.message = "Failed to load data";
			    		$scope.error = true;   
					}
					);
			
		}
		
	}
	
	adminDashboardCtrl.disableCategory = function (row) {
		console.log(row);
		adminDashboardService.disableCategory($rootScope.userSession.id,row,
				
				function(responseData) {
					$scope.message = "Success";
		    		$scope.error = false;
					console.log(responseData);
					alert(responseData.message);
					$route.reload();
//					adminDashboardCtrl.success = responseData;
				},
				function(responseData) {
//					alert(responseData.message);
					adminDashboardCtrl.error = {
							status : true,
							message : reponseData.message
							
					};
					$scope.message = "Failed to load data";
		    		$scope.error = true;   
				}
				);
		
	}
	
	adminDashboardCtrl.enableCategory = function (row) {
		console.log(row);
		adminDashboardService.enableCategory($rootScope.userSession.id,row,
				
				function(responseData) {
					$scope.message = "Success";
		    		$scope.error = false;
					console.log(responseData);
					alert(responseData.message);
					$route.reload();
				},
				function(responseData) {
//					alert(responseData.message);
					adminDashboardCtrl.error = {
							status : true,
							message : reponseData.message
							
					};
					$scope.message = "Failed to load data";
		    		$scope.error = true;   
				}
				);
		
	}
	
	adminDashboardCtrl.deleteCategory = function (row) {
		console.log(row);
		adminDashboardService.deleteCategory($rootScope.userSession.id,row,
				
				function(responseData) {
					$scope.message = "Success";
		    		$scope.error = false;
					console.log(responseData);
					alert(responseData.message);
					$route.reload();
				},
				function(responseData) {
//					alert(responseData.message);
					adminDashboardCtrl.error = {
							status : true,
							message : reponseData.message
							
					};
					$scope.message = "Failed to load data";
		    		$scope.error = true;   
				}
				);
		
	}
	
	adminDashboardCtrl.undeleteCategory = function (row) {
		console.log(row);
		adminDashboardService.undeleteCategory($rootScope.userSession.id,row,
				
				function(responseData) {
					$scope.message = "Success";
		    		$scope.error = false;
					console.log(responseData);
					alert(responseData.message);
					$route.reload();
				},
				function(responseData) {
//					alert(responseData.message);
					adminDashboardCtrl.error = {
							status : true,
							message : reponseData.message
							
					};
					$scope.message = "Failed to load data";
		    		$scope.error = true;   
				}
				);
		
	}
	


});

adminDashboardModule.factory('adminDashboardService', function($http,$timeout,APP_CONSTANT) {
	var adminDashboardService = {};
	
	adminDashboardService.listCategories = function (id,data, callback,callbackError) {
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
         			APP_CONSTANT.REMOTE_HOST+'/category/getAllCategories'
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
	
	adminDashboardService.addCategory = function (id,data, callback,callbackError) {
		
		if(APP_CONSTANT.DEMO) {
			$timeout(function(){
	     		var response;
     			response = {
     					  "success": true,
     					  "message": "Category Disabled"
     					};
		         callback(response);
		     }, 1000);
		} else {
			 $http.post(
     			APP_CONSTANT.REMOTE_HOST+'/category/addCategory',data
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
	
	adminDashboardService.disableCategory = function (id,data, callback,callbackError) {
		
		if(APP_CONSTANT.DEMO) {
			$timeout(function(){
	     		var response;
     			response = {
     					  "success": true,
     					  "message": "Category Disabled"
     					};
		         callback(response);
		     }, 1000);
		} else {
			 $http.put(
     			APP_CONSTANT.REMOTE_HOST+'/category/disableCategory/catId/'+data.category.id
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
		
	adminDashboardService.enableCategory = function (id,data, callback,callbackError) {
		if(APP_CONSTANT.DEMO){
			$timeout(function(){
		     		var response;
		     		response = {
		     					  "success": true,
		     					  "message": "Category Enabled"
		     					};
		         callback(response);
		     }, 1000);
		} else {
				
			 $http.put(
         			APP_CONSTANT.REMOTE_HOST+'/category/enableCategory/catId/'+data.category.id
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
		
	adminDashboardService.deleteCategory = function (id,data, callback,callbackError) {
		if(APP_CONSTANT.DEMO){
			$timeout(function(){
		     		var response;
		     		response = {
		     					  "success": true,
		     					  "message": "Category Deleted"
		     					};
		         callback(response);
		     }, 1000);
		} else {
				
			 $http.put(
         			APP_CONSTANT.REMOTE_HOST+'/category/deleteCategory/catId/'+data.category.id
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
		
	adminDashboardService.undeleteCategory = function (id,data, callback,callbackError) {
		if(APP_CONSTANT.DEMO){
			$timeout(function(){
	     		var response;
	     		response = {
	     					  "success": true,
	     					  "message": "Category Undeleted"
	     					};
		         callback(response);
		     }, 1000);
		}else{
			 $http.put(
         			APP_CONSTANT.REMOTE_HOST+'/category/undeleteCategory/catId/'+data.category.id
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
	
	
	return adminDashboardService;
});
