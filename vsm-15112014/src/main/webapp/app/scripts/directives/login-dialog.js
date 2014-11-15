var vsmApp = angular.module('vsmApp');

vsmApp.directive('loginDialog', function ($location,AUTH_EVENTS,modals) {
  return {
    restrict: 'A',
    link: function (scope) {
      var showDialog = function () {
        if($location.path() != '/form/login') {
         modals.showForm('Login','dialoglogin', {}, "modal-lg", false);
        }
      };
  
       scope.$on(AUTH_EVENTS.notAuthenticated, showDialog);
       scope.$on(AUTH_EVENTS.sessionTimeout, showDialog);
    }
  };
})