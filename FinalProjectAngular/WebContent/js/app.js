
/**
 * 
 */
'use strict';
// Step 1: declare modules
 angular.module("authModule",[]);
 angular.module("projectModule",[]);
 angular.module("categoryModule",[]);
 angular.module("homeModule",[]);
 angular.module("registrationModule",[]);
 angular.module("adminDashboardModule",[]);
 angular.module("adminCategoryModule",[]);
 angular.module("creatorDashboardModule",[]);
 angular.module("paymentModule",[]);
 angular.module("reportsModule",[]);



 angular.module('appCoreModule', [
	 'ngRoute',
     'ngCookies'
 ]);
//Step 2: Register App
var app = angular.module("app", 
		[
		'appCoreModule',
		 'homeModule',
		 'authModule',
		 'projectModule',
		 'registrationModule',
		 'adminDashboardModule',
		 'adminCategoryModule',
		 'creatorDashboardModule',
		 'categoryModule',
		 'paymentModule',
		 'reportsModule'
		 ]);