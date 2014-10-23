var vsmApp = angular.module('vsmApp');

vsmApp.directive('loginDialog', function (AUTH_EVENTS,modals) {
  return {
    restrict: 'A',
    link: function (scope) {
      var showDialog = function () {
         modals.showForm('Login','dialoglogin', {}, "modal-lg");
      };
  
       scope.$on(AUTH_EVENTS.notAuthenticated, showDialog);
       scope.$on(AUTH_EVENTS.sessionTimeout, showDialog);
    }
  };
})