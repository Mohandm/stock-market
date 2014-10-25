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
                if(item.stage === "1")
                {
                    $scope.leagueList1 = item;
                }
                else if(item.stage === "2")
                {
                    $scope.leagueList2 = item;
                }
                else
                {
                    $scope.leagueList3 = item;
                }
            });
            setTimeout(function() {
                $('.counter').counterUp({
                    delay: 10,
                    time: 1000
                });
            },500);
        });

        $scope.listOfUsersLeague = function(leagueId){
            modals.showForm('League Players','leagueUsersDialog', {"leagueId" : leagueId}, "modal-lg");
        };


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

vsmApp.controller('LeagueUsersDialogController', ['$scope','$http','modals','LeaguesService', function ($scope, $http, modals, LeaguesService) {

    $scope.getLeagueUsersDataGridOptions = {
        enableSorting: true,
        enableFiltering: true,
        columnDefs: [
            { field: 'photo', displayName:'Profile Pic'},
            { field: 'name', displayName:'Name'},
            { field: 'ranking', displayName:'Ranking'},
            { field: 'totalValue', displayName:'Total Value'},
            { field: 'followerCount', displayName:'Followers'}
        ]
    };

    var leaguesUsersPromise = LeaguesService.getLeaguesUsers($scope.passValuesToDialog.leagueId);
    leaguesUsersPromise.then(function(data){
        $scope.getLeagueUsersDataGridOptions.data = data;
        setTimeout(function(){
            $('#leagueUsersDialogContainer').resize();
        },1000);
    });
}]);

