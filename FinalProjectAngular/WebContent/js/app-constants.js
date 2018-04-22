/**
 * 
 */
angular.module('app')
.constant('AUTH_EVENTS', {
	loginSuccess: 'auth-login-success',
	logoutSuccess: 'auth-logout-success',
	loginFailed:'auth-login-falied',
	notAuthenticated : 'auth-not-authenticated',
	notAuthorized : 'auth-not-authorized',
	sessionTimeout: 'auth-session-timeout',

})
.constant('APP_CONSTANT',{
		DEMO:false,
		AUTH_HEADER:'Authorization',
		REMOTE_HOST:'http://localhost:8080/AnguarRESTApplication/rest'
});