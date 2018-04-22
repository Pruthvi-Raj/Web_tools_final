/**
 * 
 */
 
var projectModule = angular.module('projectModule');
projectModule.controller('projectController', function($scope,$rootScope, projectService, $location, $route) {
	var projectCtrl = this;
	if(!$rootScope.project) {
		$location.path("/");
	}
	projectCtrl.project = $rootScope.project;
	projectCtrl.user = $rootScope.userSession;
	projectCtrl.isLoggedIn = false;
	projectCtrl.isAdmin = false;
	projectCtrl.isDonor = false;
	if($rootScope.userSession) {
		projectCtrl.isLoggedIn = true;
		if($rootScope.userSession.role==="Admin") {
			projectCtrl.isAdmin = true;
		}
		if($rootScope.userSession.role==="Donor") {
			projectCtrl.isDonor = true;
		}
	}
	
	
	projectCtrl.commentInput = {
			comment : ""
	};
	
	
	projectCtrl.init = function(){
		
		console.log('init called')
		if(projectCtrl.isAdmin) {
			projectService.listPayments($rootScope.userSession.id,projectCtrl.project,
					
					function(responseData) {
						$scope.message = "Success";
			    		$scope.error = false;
						console.log(responseData);
						projectCtrl.payments = responseData;
					},
					function(responseData) {
						projectCtrl.error = {
								status : true,
								message : responseData.message
								
						};
						$scope.message = "Failed to load data";
			    		$scope.error = true;   
					}
					);
		}
		
		projectService.listReturns(null,projectCtrl.project,
				
				function(responseData) {
					$scope.message = "Success";
		    		$scope.error = false;
					console.log(responseData);
					projectCtrl.returns = responseData;
				},
				function(responseData) {
					projectCtrl.error = {
							status : true,
							message : responseData.message
							
					};
					$scope.message = "Failed to load data";
		    		$scope.error = true;   
				}
		);
		
		projectService.listComments(null,projectCtrl.project,
				
				function(responseData) {
					$scope.message = "Success";
		    		$scope.error = false;
					console.log(responseData);
					projectCtrl.comments = responseData;
				},
				function(responseData) {
					projectCtrl.error = {
							status : true,
							message : responseData.message
							
					};
					$scope.message = "Failed to load data";
		    		$scope.error = true;   
				}
		);
		
	}
	
	projectCtrl.pay = function(row) {
		console.log(row);
		row.userId = $rootScope.userSession.id;
		$rootScope.returns = row;
		$location.path("/payment");
	}
	
	projectCtrl.addComment = function() {
		if(projectCtrl.commentInput.comment === "") {
			alert("comment can't be empty");
		} else {
			projectCtrl.commentInput.userId = $rootScope.userSession.id,
			projectCtrl.commentInput.projectId = $rootScope.project.id
			projectService.addComment($rootScope.userSession.id,projectCtrl.commentInput,
				function(responseData) {
					$scope.message = "Success";
		    		$scope.error = false;
					console.log(responseData);
					alert(responseData.message);
					$route.reload();
				},
				function(responseData) {
					alert(responseData.message);
					$scope.message = "Failed to load data";
		    		$scope.error = true;   
				}
			);
		}
	
	}
	
	
	
});

projectModule.factory('projectService', function($http,$timeout,APP_CONSTANT) {
	var projectService = {};
	
	projectService.listPayments = function (id,data, callback,callbackError) {
		if(APP_CONSTANT.DEMO){
			$timeout(function(){
		     		var response;
		     			response = [
		     				  {
		     					    "id": 1,
		     					    "projectId": 3,
		     					    "userId": 11,
		     					    "userPaymentInfoId": 1,
		     					    "amount": 6,
		     					    "paymentDate": 1493326670000,
		     					    "userName": "donor"
		     					  }
		     					];
		         callback(response);
			}, 1000);
		} else {
			
			 $http.get(
         			APP_CONSTANT.REMOTE_HOST+'/payment/getPayments/projectId/'+data.id
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
	
	projectService.listReturns = function (id,data, callback,callbackError) {
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
         			APP_CONSTANT.REMOTE_HOST+'/returns/getReturn/projectId/'+data.id
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
	
	projectService.makePayment = function (id,data, callback,callbackError) {
		
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
	
	projectService.listComments = function (id,data, callback,callbackError) {
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
         			APP_CONSTANT.REMOTE_HOST+'/comments/getComments/projectId/'+data.id
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
	
	projectService.addComment = function (id,data, callback,callbackError) {
		
		if(APP_CONSTANT.DEMO) {
			$timeout(function(){
	     		var response;
     			response = {
     					  "success": true,
     					  "message": "Comment Added"
     					};
		         callback(response);
		     }, 1000);
		} else {
			 $http.post(
     			APP_CONSTANT.REMOTE_HOST+'/comments/addComment',data
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
	
	return projectService;
	
});