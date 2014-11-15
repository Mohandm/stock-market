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
  .factory('AuthService', function ($http, Session) {
  var authService = {};
 
  authService.login = function (credentials) {
    return $http.post('j_spring_security_check', credentials)
      .then(function (res) {
        Session.create(res.data.email);
      });
  };

  authService.logout = function () {
    return $http.post('j_spring_security_logout', {})
      .then(function (res) {   
        Session.destroy();
      });
  };

  authService.syncSession = function () {
    return $http.get('getuserdetails', {})
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