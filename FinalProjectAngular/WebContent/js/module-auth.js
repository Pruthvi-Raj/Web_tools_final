/**
 * 
 */
var authModule = angular.module("authModule");

authModule.controller('authController', function($scope,$rootScope,authService,AUTH_EVENTS) {
	var authCtrl = this; // variable should be Same as controllerAs: 'authCtrl'
	authCtrl.message = "";
	authCtrl.error = false;
	
	authCtrl.credentials = {
		username : '',
		password : ''
	};
	
	authService.clearCredentials();
	
	authCtrl.login = function() {
		authService.login(authCtrl.credentials,callbackSuccess,callbackError);
	};
	
	var callbackSuccess = function(data,headers) {
        
			authCtrl.message = "Login Success";
			//Storing information on $rootScope and Cookies
			authService.setCredentials(data,headers);
			$rootScope.responseData = data;
    		$rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
    		
    };
    
    var callbackError = function(data) {
    	
    	
    	if("user-not-found" === data.message){
    		authCtrl.message = "Username password is not valid";
    	}
		
		authCtrl.error = true;
		$rootScope.$broadcast(AUTH_EVENTS.loginFailed);
       
    };
    
});

authModule.controller('logoutController', function($scope,$rootScope,authService,AUTH_EVENTS) {
	 authService.clearCredentials();
	 console.log('....');

});


authModule.factory('authService', function($rootScope,$http,$timeout,$cookieStore,$window,APP_CONSTANT,AUTH_EVENTS) {
		var authService = {};
		
		authService.login = function (data, callback,callbackError) {
			if(APP_CONSTANT.DEMO){
			
	            /* Dummy authentication for testing, uses $timeout to simulate api call
	             ----------------------------------------------*/
	            $timeout(function(){
	            	
	            		var response;
	            		if(data.username ==='admin' && data.password==='pass'){
	            			response = {
	            					  "username": "abc",
	            					  "name": "abc",
	            					  "email": "abc@abc.com",
	            					  "role": "Admin",
	            					  "phone": "1234567890",
	            					  "id": 1
	            					};
	            		}else{
	                    response = {message:'Username or password is incorrect'};
	            		}
	     
	                callback(response);
	            }, 1000);
			}else{

            /* Use this for real authentication
             ----------------------------------------------*/
				console.log(data);
				
				$http.post(APP_CONSTANT.REMOTE_HOST+'/user/auth', {
					"username":data.username,
					"password":data.password
				})
				.success(function(data, status, headers, config) {
					callback(data,headers);
				})
				.error(function(data, status, headers, config) {
					callbackError(data);
				})
//            $http.post(
//            			APP_CONSTANT.REMOTE_HOST+'/user/auth'
//            			,
//            			{ 
//            				username: data.username,
//            				password: data.password 
//            			}
//            			
//            			)
//            			//On Success of $http call
//            			.success(function (data, status, headers, config) {
//            					callback(data,headers);
//            			})
//            			.error(function (data, status, headers, config) { // IF STATUS CODE NOT 200
//            					if(status== 422){
//            						callbackError(data,headers);
//            					}
//            			});
			}

        };


        authService.setCredentials = function (data,headers) {
        	
        	 //Setting of Auth ID
        	var headerToken=APP_CONSTANT.DEMO?'dummyToken':headers(APP_CONSTANT.AUTH_HEADER);
           	data.token = headerToken;
	        $rootScope.globals = {
    		 					userSession: data
	         					};
	        $http.defaults.headers.common[APP_CONSTANT.AUTH_HEADER] = headerToken;
	 	    $cookieStore.put('globals', $rootScope.globals);
	 	     //$window.sessionStorage.setItem("globals", angular.toJson($rootScope.globals));
	 	     //$window.sessionStorage.globals = $rootScope.globals;
    };
 
    authService.clearCredentials = function () {
        $rootScope.globals = {};
        $cookieStore.remove('globals');
        $rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);

    };		

	return authService;
});