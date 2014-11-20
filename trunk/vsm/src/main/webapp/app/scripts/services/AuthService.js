angular.module('vsmApp')
  .service('Session', function () {
  this.create = function (email) {
    this.email = email;
  };
  this.populatecsrftoken = function (csrfTokenHeader,csrfTokenValue) {
    this.csrfTokenHeader = csrfTokenHeader;
    this.csrfTokenValue = csrfTokenValue;
  };
  this.destroy = function () {
    this.email = null;
  };
  return this;
});

angular.module('vsmApp')
  .factory('AuthService', function ($http, Session, $rootScope) {
  var authService = {};
 
  authService.login = function (credentials) {
    var actionUrl = 'j_spring_security_check';
    actionUrl = $rootScope.getFinalURL(actionUrl);
    return $http.post(actionUrl, credentials)
      .then(function (res) {
        Session.create(res.data.email);
      });
  };

  authService.logout = function () {
    var actionUrl = 'j_spring_security_logout';
    actionUrl = $rootScope.getFinalURL(actionUrl);
    return $http.post(actionUrl, {})
      .then(function (res) {   
        Session.destroy();
      });
  };

  authService.syncSession = function () {
    var actionUrl = $rootScope.getFinalURL('getuserdetails');
    return $http.get(actionUrl, {})
      .then(function (res) {
        if (angular.isDefined(res.data.email)) {
          Session.create(res.data.email);
        }
      });
  };
 
  authService.isAuthenticated = function () {
    return !!Session.email;
  };
 
  return authService;
});