/**
 * 
 */
 
var paymentModule = angular.module('paymentModule');
paymentModule.controller('paymentController', function($scope,$rootScope, paymentService, $location) {
	var paymentCtrl = this;
	
	if(!$rootScope.userSession) {
		$location.path("/");
	}
	if(!$rootScope.returns) {
		$location.path("/");
	}
	if($rootScope.userSession.role !== "Donor") {
		alert("this page is for donor only");
		$location.path("/");
	}
	
	paymentCtrl.userPaymentInfo = {
			cardNum : "",
			cardExp : "",
			cardCvv : ""
	}
	
	paymentCtrl.returns = $rootScope.returns;
	paymentCtrl.user = $rootScope.userSession;
	if(!$rootScope.userSession) {
		$location.path("/");
	} 
	if($rootScope.userSession.role!=="Donor") {
		$location.path("/");
	} 
	

		
	paymentCtrl.pay = function() {
		var pay = true;
		console.log(paymentCtrl.userPaymentInfo);
		if(paymentCtrl.userPaymentInfo.cardNum=="") {
			paymentCtrl.error = true;
			paymentCtrl.message = "number cannot be empty";
			pay =false;
		}
		if(paymentCtrl.userPaymentInfo.cardExp=="") {
			paymentCtrl.error = true;
			paymentCtrl.message = "number cannot be empty";
			pay =false;
		}
		if(paymentCtrl.userPaymentInfo.cardCvv=="") {
			paymentCtrl.error = true;
			paymentCtrl.message = "number cannot be empty";
			pay =false;
		}
		if(pay) {
			paymentCtrl.returns.cardNum = paymentCtrl.userPaymentInfo.cardNum;
			paymentCtrl.returns.cardExp = paymentCtrl.userPaymentInfo.cardExp;
			paymentCtrl.returns.cardCvv = paymentCtrl.userPaymentInfo.cardCvv;
			console.log(paymentCtrl.returns);
			paymentService.makePayment($rootScope.userSession.id,paymentCtrl.returns,
					
				function(responseData) {
					$scope.message = "Success";
		    		$scope.error = false;
					console.log(responseData);
					alert(responseData.message);
					$location.path("/");
				},
				function(responseData) {
					alert(responseData.message);
					paymentCtrl.error = true;
					paymentCtrl.message = reponseData.message;
							
					$scope.message = "Failed to load data";
		    		$scope.error = true;   
				}
			);
		}

	};
	
	
	
	
	
});

paymentModule.factory('paymentService', function($http,$timeout,APP_CONSTANT) {
	var paymentService = {};
	
	
	paymentService.makePayment = function (id,data, callback,callbackError) {
		
		if(APP_CONSTANT.DEMO) {
			$timeout(function(){
	     		var response;
     			response = {
     					  "success": true,
     					  "message": "Payment Saved"
     					};
		         callback(response);
		     }, 1000);
		} else {
			 $http.post(
     			APP_CONSTANT.REMOTE_HOST+'/payment/makePayment',data
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
	
	
	return paymentService;
	
});