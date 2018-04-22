/**
 * 
 */
var creatorDashboardModule = angular.module('creatorDashboardModule');
creatorDashboardModule.controller('creatorDashboardController', function($scope, $rootScope,$location,creatorDashboardService, $route) {
	var creatorDashboardCtrl = this;
	creatorDashboardCtrl.messageDash = "Welcome " + $rootScope.userSession.name;
	
	if($rootScope.userSession.role !== "Creator") {
		alert("this page is for creator only");
		$location.path("/");
	}
	
	creatorDashboardCtrl.project = {
		name : "",
		description : "",
		requiredFunds : "",
		startDate : "",
		endDate : "",
		category : "",
		donationsAllowed : "",
		returns : [],
		userId : $rootScope.userSession.id
	};
	
	creatorDashboardCtrl.returns1 = {
		amount : "",
		returnNumber : "",
		returnDescription : ""	
	};
	
	creatorDashboardCtrl.returns2 = {
			amount : "",
			returnNumber : "",
			returnDescription : "hgvn"	
		};
	
	creatorDashboardCtrl.error = false;
	creatorDashboardCtrl.message = "";
	
	creatorDashboardCtrl.init = function(){
		
		console.log('init called')
		creatorDashboardService.listCategories($rootScope.userSession.id,null,
				
		function(responseData) {
			$scope.message = "Success";
    		$scope.error = false;
			console.log(responseData);
			creatorDashboardCtrl.categories = responseData;
		},
		function(responseData) {
			creatorDashboardCtrl.error = {
					status : true,
					message : reponseData.message
					
			};
			$scope.message = "Failed to load data";
    		$scope.error = true;   
		}
		);
	}
	
	creatorDashboardCtrl.viewProject = function (row) {
		console.log(row);
		$rootScope.project = row.project;
		console.log($rootScope.project);
		$location.path('/project/view');

	};
	
	creatorDashboardCtrl.addProject = function () {
		var add = true;
		
		if(creatorDashboardCtrl.project.name=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Name cannot be empty";
			add =false;
		}
		if(creatorDashboardCtrl.project.description=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Description cannot be empty";
			add =false;
		}
		if(creatorDashboardCtrl.project.requiredFunds=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Required funds cannot be empty";
			add =false;
		}
		if(creatorDashboardCtrl.project.startDate=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Start Date cannot be empty";
			add =false;
		}
		if(creatorDashboardCtrl.project.endDate=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "End Date cannot be empty";
			add =false;
		}
		if(creatorDashboardCtrl.project.category=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Select a category";
			add =false;
		}
		if(creatorDashboardCtrl.project.donationsAllowed=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Donations allowed cannot be empty";
			add =false;
		}
		if(creatorDashboardCtrl.returns1.amount=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Returns amount cannot be empty";
			add =false;
		}
		if(creatorDashboardCtrl.returns1.returnNumber=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Returns Nummber cannot be empty";
			add =false;
		}
		if(creatorDashboardCtrl.returns1.returnDescription=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Returns Description cannot be empty";
			add =false;
		}
		if(creatorDashboardCtrl.returns2.amount=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Returns amount cannot be empty";
			add =false;
		}
		if(creatorDashboardCtrl.returns2.returnNumber=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Returns Nummber cannot be empty";
			add =false;
		}
		if(creatorDashboardCtrl.returns2.returnDescription=="") {
			creatorDashboardCtrl.error = true;
			creatorDashboardCtrl.message = "Returns Description cannot be empty";
			add =false;
		}
		if(add) {
			creatorDashboardCtrl.project.returns.push(creatorDashboardCtrl.returns1);
			creatorDashboardCtrl.project.returns.push(creatorDashboardCtrl.returns2);
			creatorDashboardService.addProject($rootScope.userSession.id,creatorDashboardCtrl.project,
					
					function(responseData) {
						$scope.message = "Success";
			    		$scope.error = false;
						console.log(responseData);
						alert(responseData.message);
						$location.path("/");
//							adminDashboardCtrl.success = responseData;
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
	
	
	


});

creatorDashboardModule.factory('creatorDashboardService', function($http,$timeout,APP_CONSTANT) {
	var creatorDashboardService = {};
	
	creatorDashboardService.listCategories = function (id,data, callback,callbackError) {
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
		
	creatorDashboardService.addProject = function (id,data, callback,callbackError) {
		
		if(APP_CONSTANT.DEMO) {
			$timeout(function(){
	     		var response;
     			response = {
     					  "success": true,
     					  "message": "Project Added"
     					};
		         callback(response);
		     }, 1000);
		} else {
			 $http.post(
     			APP_CONSTANT.REMOTE_HOST+'/project/addProject',data
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
	
	
	return creatorDashboardService;
});
