var vsmApp = angular.module('vsmApp');

vsmApp.controller('LeaderBoardController', ['$scope', '$rootScope', 'modals',
    function ($scope, $rootScope, modals) {

        $scope.$scope = $scope;

        //Add this to all page controllers
        $rootScope.onPageLoad();


    }]);

vsmApp.controller('LeaderBoardDialogController', ['$scope','$http','modals', function ($scope, $http, modals) {

    $scope.formmodel = {};
    $scope.formcontrol = {};

    $scope.perform = function(action){
        if (action === 'cancel') {
            modals.close();
        }
        else {
            if ($scope.mainForm.$invalid) {
                $scope.formcontrol.submitted = true;
            }
            else {
                $http.post(action, $scope.formmodel).success(function (response) {
                    modals.close();
                });
            }
        }
    };
}]);

