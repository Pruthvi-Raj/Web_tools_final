/**
 * 
 */
var registrationModule = angular.module("registrationModule", []);
registrationModule.controller('registrationController', function($location,
		$scope, registrationService) {

	var regCtrl = this;

	regCtrl.registration = {
		name : "",
		username : "",
		password : "",
		email : "",
		phone : "",
		role : ""
	};




	regCtrl.cancel = function() {
		$location.path('/');
	}

	regCtrl.register = function() {
		var reg = true;
		console.log(regCtrl.registration);
		if(regCtrl.registration.name=="") {
			regCtrl.error = true;
			regCtrl.message = "name cannot be empty";
			reg =false;
		}
		if(regCtrl.registration.username=="") {
			regCtrl.error = true;
			regCtrl.message = "username cannot be empty";
			reg = false;
		}
		if(regCtrl.registration.password=="") {
			regCtrl.error = true;
			regCtrl.message = "paswword cannot be empty";
			reg = false;
		}
		if(regCtrl.registration.email=="") {
			regCtrl.error = true;
			regCtrl.message = "email cannot be empty";
			reg = false;
		}
		if(regCtrl.registration.phone=="") {
			regCtrl.error = true;
			regCtrl.message = "phone cannot be empty";
			reg = false;
		}
		if(regCtrl.registration.role=="") {
			regCtrl.error = true;
			regCtrl.message = "Select a role to register";
			reg = false;
		}
		if(reg) {
			registrationService.register(regCtrl.registration, callbackSuccess,
					callbackError);
		}
		

	}

	regCtrl.error = false;
	regCtrl.message = "";

	var callbackSuccess = function(data, headers) { // Status
		// Code:200
		if (data.success) {
			alert("Registration Successful");
//			regCtrl.openComponentModal('Registration Successful');
			$location.path('/');

		} else {
			regCtrl.message = data.message;
			regCtrl.error = true;
		}

	};

	var callbackError = function(data, headers) {
//		alert("UserName already exists");
		regCtrl.message = data.message;
		regCtrl.error = true;

	};



});

registrationModule.factory('registrationService', function($rootScope, $http,
		$timeout, $cookieStore, $window, APP_CONSTANT, AUTH_EVENTS) {
	var regService = {};

	regService.register = function(data, callback, callbackError) {
		if (APP_CONSTANT.DEMO) {

			/*
			 * Dummy authentication for testing, uses $timeout to simulate api
			 * call ----------------------------------------------
			 */
			$timeout(function() {

				var response;
				if (data.username === 'test' && data.password === 'test') {
					response = {
						success : true,
					};
				} else {
					response = {
						success: false,
						message : 'Registration was not successful'
					};
				}

				callback(response);
			}, 1000);
		} else {

			/*
			 * Use this for real authentication
			 * ----------------------------------------------
			 */
			$http.post(APP_CONSTANT.REMOTE_HOST + '/user/registration', data

			)
			// On Success of $http call
			.success(function(data, status, headers, config) {
				callback(data, headers);
			}).error(function(data, status, headers, config) { // IF
				if(status== 422){
					callbackError(data);
				}
//				callbackError(data, headers);
			});
		}

	};
	
	return regService;

});