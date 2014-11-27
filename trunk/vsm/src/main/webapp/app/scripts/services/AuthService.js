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
  .factory('AuthService', function ($http, Session, $rootScope, $q, $location, AUTH_EVENTS) {
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
      /*TODO - Bipin : Logout is not working fine after deploying CORS*/
    $http.post(actionUrl, {})
        .success(function (res) {
          Session.destroy();
          $rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);
          $location.path('/');
        })
        .error(function (res) {
         Session.destroy();
         $rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);
         $location.path('/');
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

 authService.getUserProfile = function(){
        var deferred = $q.defer(),
            actionUrl = $rootScope.getFinalURL('getUser');
        $http.get(actionUrl,{})
            .success(function (quotesJSON) {
                deferred.resolve(quotesJSON);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };
 
  return authService;
});