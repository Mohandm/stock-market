var vsmApp = angular.module('vsmApp');

vsmApp.controller('LeaderBoardController', ['$scope', '$rootScope', 'modals','LeaguesService','TourService',
    function ($scope, $rootScope, modals, LeaguesService, TourService) {

        $scope.$scope = $scope;

        //Add this to all page controllers
        $rootScope.onPageLoad();
        TourService.setupTour('leaderBoard');

        $scope.league1 = {};
        $scope.league2 = {};
        $scope.league3 = {};
        var leaderBoardPromise = LeaguesService.getLeaderBoard();
        leaderBoardPromise.then(function(data){
            $(data).each(function(index, item){
                if(item.stage === "1")
                {
                    $scope.league1 = item;
                }
                else if(item.stage === "2")
                {
                    $scope.league2 = item;
                }
                else
                {
                    $scope.league3 = item;
                }
            });
            /*setTimeout(function() {
                $('.counter').counterUp({
                    delay: 2,
                    time: 1000
                });
            },500);*/
        });

        $scope.listOfUsersLeague = function(league){
            var dialogTitle = league.name +'- Top 10 Champions';
            modals.showForm(dialogTitle,'leagueUsersDialog', league, "modal-lg");
        };

}]);


