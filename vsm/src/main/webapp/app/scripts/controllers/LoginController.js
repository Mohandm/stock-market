angular.module('vsmApp')
  .controller('LoginController', function ($scope, $rootScope, $location, AUTH_EVENTS, AuthService, modals, $http) {
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
          $location.path('/userHome');
        }
        else {
          $location.path('/userHome');
        }
      })
    }
  };

  $scope.logout = function (){
    AuthService.logout().then(function () {
      $rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);
      $location.path('/'); 
    })
  };

  var setError = function () {
    $scope.formcontrol.haserror = true;
  }
  $rootScope.$on(AUTH_EVENTS.notAuthenticated, setError);

  $scope.inviteUser = function() {
        modals.showForm('Invite Friends','inviteuser', {}, "modal-lg");
     };

  $scope.perform = function(action){
      if ($scope.mainForm.$invalid) {
        $scope.formcontrol.submitted = true;
      }
      else {
        action = $rootScope.getFinalURL(action);
        $http.post(action, $scope.formmodel).success(function (response) {
          modals.close();
        });
      }
    };
});