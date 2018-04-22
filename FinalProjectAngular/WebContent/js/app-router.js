/**
 * 
 */
var configModule = angular.module('app'); // Please dont not use [], dependency 


configModule.config(function($routeProvider) {	
//    $urlRouterProvider.otherwise('/login');
	$routeProvider
    // route for the home page
	.when('/', {
		 templateUrl : 'partial/home.html',
	     controller  : 'homeController',
	     controllerAs: 'homeCtrl'
	})
	.when('/category/view', {
		 templateUrl : 'partial/category.html',
	     controller  : 'categoryController',
	     controllerAs: 'categoryCtrl'
	})
	.when('/login', {
		 templateUrl : 'partial/login.html',
	     controller  : 'authController',
	     controllerAs: 'authCtrl'
	})
	.when('/registration', {
		 templateUrl : 'partial/registration.html',
	     controller  : 'registrationController',
	     controllerAs: 'regCtrl'
	})
	.when('/logout', {
		template :"",
		controller  : 'logoutController',
		redirectTo: '/' 
	})
    .when('/adminDashboard', {
        templateUrl : 'partial/adminDashboard.html',
        controller  : 'adminDashboardController',
        controllerAs: 'adminDashboardCtrl'
    })
    .when('/admin/viewCategory', {
    	templateUrl : 'partial/adminCategory.html',
        controller  : 'adminCategoryController',
        controllerAs: 'adminCategoryCtrl'
    })
    .when('/reports', {
    	templateUrl : 'partial/reports.html',
        controller  : 'reportsController',
        controllerAs: 'reportsCtrl'
    })
    .when('/project/view', {
        templateUrl : 'partial/project.html',
        controller  : 'projectController',
        controllerAs: 'projectCtrl'
    })
    .when('/payment', {
        templateUrl : 'partial/payment.html',
        controller  : 'paymentController',
        controllerAs: 'paymentCtrl'
    })
    .when('/creatorDashboard', {
        templateUrl : 'partial/creatorDashboard.html',
        controller  : 'creatorDashboardController',
        controllerAs: 'creatorDashboardCtrl'
    })
    .otherwise({ redirectTo: '/' });
});


configModule.run(
	    function ($rootScope, $location, $cookieStore,$window, $http,AUTH_EVENTS,APP_CONSTANT) {
	    	//Management 
	    	$rootScope.$on('$locationChangeStart', function (event, next, current) {
	            // redirect to login page if not logged in
	    		console.log(' Requested path '+$location.path());
	            if ( 
	            		!(
	            				$location.path() == '/' || 
	            				$location.path() == '/registration'|| 
	            				$location.path() == '/login' ||
	            				$location.path() == '/category/view' ||
	            				$location.path() == '/project/view'
	            		 ) && 
	            		 !$rootScope.globals.userSession) {
	            	console.log('Invalid failed');
	                $location.path('/');
	            }
	        });
	    	
			$rootScope.$on(AUTH_EVENTS.loginFailed, function(event, next){
		    		console.log('Login failed');
		        	 //$scope.message = "Login failed";
		    });
		
			$rootScope.$on(AUTH_EVENTS.logoutSuccess, function(event, next){
				console.log('Logout Success');
				$window.localStorage.removeItem("globals");
				$rootScope.userSession=null;
				$rootScope.$emit("CallParentMethod", {});
				
			});
			
			$rootScope.$on(AUTH_EVENTS.notAuthorized, function(event, next){
				console.log('notAuthorized');
				$window.localStorage.removeItem("globals");
				$rootScope.userSession=null;
				$rootScope.$emit("CallParentMethod", {});
				
			});
	    
		    $rootScope.$on(AUTH_EVENTS.loginSuccess, function(event, next){
				//$scope.message = "Login Success";
				console.log('Login success');
			    $window.localStorage.setItem("globals", angular.toJson($rootScope.globals));
			    //$rootScope.userSession=angular.toJson($rootScope.globals.userSession)
			    $rootScope.userSession = JSON.parse($window.localStorage.getItem("globals")).userSession;
			    $rootScope.$emit("CallParentMethod", {});
			    console.log("at login ");
			    console.log($rootScope.userSession.role);
			    if($rootScope.userSession.role === "Admin") {
			    	$location.path('/adminDashboard');
			    } else {
			    	if($rootScope.userSession.role === "Creator") {
				    	$location.path('/creatorDashboard');
				    } else {
				    	$location.path('/');
				    }
			    }
		    });
	    
		    // keep user logged in after page refresh
		    $rootScope.globals = $cookieStore.get('globals') || {};
		    if ($rootScope.globals.userSession) {
		    	$http.defaults.headers.common[APP_CONSTANT.AUTH_HEADER] =$rootScope.globals.userSession.token;
			    $window.localStorage.setItem("globals", angular.toJson($rootScope.globals));
			    $rootScope.userSession = JSON.parse($window.localStorage.getItem("globals")).userSession;
		    }

	})