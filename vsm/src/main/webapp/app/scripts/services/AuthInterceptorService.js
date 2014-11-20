var vsmApp = angular.module('vsmApp');

vsmApp.factory('AuthInterceptorService', function ($rootScope, $q, AUTH_EVENTS, Session, $injector) {
  
    var populateTokens = function () {
      
      var defaultCsrfTokenHeader = 'X-CSRF-TOKEN';
      var csrfTokenHeaderName = 'X-CSRF-HEADER';
      var xhr = new XMLHttpRequest();
      /*xhr.open('head', 'http://localhost:9091/stockmarket/getuserdetails', false);*/
      xhr.open('head', '/stockmarket/getuserdetails', false);
      xhr.send();
      var csrfTokenHeader = xhr.getResponseHeader(csrfTokenHeaderName);
      this.csrfTokenHeader = csrfTokenHeader ? csrfTokenHeader : defaultCsrfTokenHeader;
      var csrfTokenValue = xhr.getResponseHeader(csrfTokenHeader); 
      Session.populatecsrftoken(csrfTokenHeader,csrfTokenValue);
    };
    
    var numRetries = 0;
    var MAX_RETRIES = 5;
    populateTokens();
            
  return {
    request: function (config) {
        config.headers[Session.csrfTokenHeader] = Session.csrfTokenValue;
        return config || $q.when(config);
    },

    responseError: function (response) { 
      if (response.status == 403 && numRetries < MAX_RETRIES) {
          populateTokens();
          ++numRetries;
          var $http = $injector.get('$http');
          return $http(response.config);
      }
      else {
        $rootScope.$broadcast({
          401: AUTH_EVENTS.notAuthenticated,
          403: AUTH_EVENTS.notAuthorized,
          419: AUTH_EVENTS.sessionTimeout,
          440: AUTH_EVENTS.sessionTimeout
        }[response.status], response);
        return $q.reject(response);
      }
    },
    response : function (response) {
      // Populate csrf token in session 
      numRetries = 0;
      return response;
    }
  };
});