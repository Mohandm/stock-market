var vsmApp = angular.module('vsmApp');

vsmApp.factory('AuthInterceptorService', function ($rootScope, $q, AUTH_EVENTS, Session) {
  return {
    request: function (config) {
        config.headers[Session.csrfTokenHeader] = Session.csrfTokenValue;
        return config || $q.when(config);
    },
    responseError: function (response) { 
      $rootScope.$broadcast({
        401: AUTH_EVENTS.notAuthenticated,
        403: AUTH_EVENTS.notAuthorized,
        419: AUTH_EVENTS.sessionTimeout,
        440: AUTH_EVENTS.sessionTimeout
      }[response.status], response);
      return $q.reject(response);
    },
    response : function (response) {
      // Populate csrf token in session 
      var defaultCsrfTokenHeader = 'X-CSRF-TOKEN';
      var csrfTokenHeaderName = 'X-CSRF-HEADER';
      var csrfTokenHeader = response.headers(csrfTokenHeaderName);
      this.csrfTokenHeader = csrfTokenHeader ? csrfTokenHeader : defaultCsrfTokenHeader;
      var csrfTokenValue = response.headers(csrfTokenHeader); 
      Session.populatecsrftoken(csrfTokenHeader,csrfTokenValue);
      return response;
    }
  };
})