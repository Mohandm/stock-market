var vsmApp = angular.module('vsmApp');

vsmApp.controller('LeaguesController', ['$scope', '$rootScope', 'modals','LeaguesService',
    function ($scope, $rootScope, modals, LeaguesService) {

        $scope.$scope = $scope;

        //Add this to all page controllers
        $rootScope.onPageLoad();

        $scope.leagueList1 = {};
        $scope.leagueList2 = {};
        $scope.leagueList3 = {};
        var leagueListPromise = LeaguesService.getGameLeagues();
        leagueListPromise.then(function(data){
            $(data).each(function(index, item){
                if(item.level === "1")
                {
                    $scope.leagueList1 = item;
                }
                else if(item.level === "2")
                {
                    $scope.leagueList2 = item;
                }
                else
                {
                    $scope.leagueList3 = item;
                }
            });
        });
}]);

vsmApp.controller('LeaguesDialogController', ['$scope','$http','modals', function ($scope, $http, modals) {

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

