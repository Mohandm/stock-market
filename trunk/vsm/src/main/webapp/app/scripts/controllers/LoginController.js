angular.module('vsmApp')
  .controller('LoginController', function ($scope, $rootScope, $location, AUTH_EVENTS, AuthService, modals) {
    $scope.formmodel = {};
    $scope.formcontrol = {};
  
  
  $scope.login = function (){
    if ($scope.mainForm.$invalid) {
        $scope.formcontrol.submitted = true;
    }
    else {
      AuthService.login($scope.formmodel).then(function () {
        $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
        if(angular.isDefined($scope.closeDialog)){
          modals.close();
        }
        else {
          $location.path('/'); 
        }
      }, 
      function () {
        $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
      })
    }
  };
});