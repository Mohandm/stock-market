var vsmApp = angular.module('vsmApp');

vsmApp.controller('FollowersController', ['$scope', '$rootScope','LeaguesService','modals',
    function ($scope, $rootScope, LeaguesService, modals) {

        $scope.$scope = $scope;

        //Add this to all page controllers
        $rootScope.onPageLoad();

        LeaguesService.getGameLeagues().then(function(data){
            var arrayMyLeagues = [];
            $(data).each(function(index,item){
                if(item.locked !== "true")
                {
                    arrayMyLeagues.push(item);
                }
            });

            $scope.myLeagues  = arrayMyLeagues;
            $scope.allLeagues = data;
            $scope.myLeagueSelected = arrayMyLeagues[0];
            $scope.allLeagueSelected = data[0];
            $scope.reloadMyFollowersGrids();
            $scope.reloadYourFollowingGrids();
        });

        $scope.getLeagueUsersFollowersDataGridOptions = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: [
                /*{ field: 'photo', displayName:'Profile Pic'},*/
                { field: 'name', displayName:'Name'},
                { field: 'status', displayName:'Status'},
                {name: 'follow', displayName: '', enableFiltering : false, enableSorting : false,
                    cellTemplate: '<button id="followBtn" type="button" class="btn-small" ng-show="getExternalScopes().checkFollowerStatus(row.entity,1)" ng-click="getExternalScopes().disallow(row.entity)" >Disallow</button> ' +
                        '<button id="followBtn" type="button" class="btn-small" ng-show="getExternalScopes().checkFollowerStatus(row.entity,2)" ng-click="getExternalScopes().accept(row.entity)" >Accept</button> ' +
                        '<button id="followBtn" type="button" class="btn-small" ng-show="getExternalScopes().checkFollowerStatus(row.entity,2)" ng-click="getExternalScopes().reject(row.entity)" >Reject</button> '}
            ]
        };

        $scope.getLeagueUsersFollowingDataGridOptions = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: [
                /*{ field: 'photo', displayName:'Profile Pic'},*/
                { field: 'name', displayName:'Name',
                    cellTemplate:'<a ng-click="getExternalScopes().showRecentTrades(row.entity)" class="anchor">{{row.entity.name}}</a>'},
                { field: 'ranking', displayName:'Ranking'},
                { field: 'totalValue', displayName:'League Value'},
                { field: 'followerCount', displayName:'Followers'},
                {name: 'follow', displayName: '', enableFiltering : false, enableSorting : false, cellTemplate: '<button id="followBtn" type="button" class="btn-small" ng-click="getExternalScopes().stopFollowing(row.entity)" >Stop Following</button> '}
            ]
        };

        $scope.reloadMyFollowersGrids = function(){
            LeaguesService.getMyFollowers($scope.myLeagueSelected.leagueId).then(function(data){
                $scope.getLeagueUsersFollowersDataGridOptions.data = data;
            });
        };
        $scope.reloadYourFollowingGrids = function(){
           LeaguesService.getYourFollowing($scope.allLeagueSelected.leagueId).then(function(data){
                $scope.getLeagueUsersFollowingDataGridOptions.data = data;
            });
        };

        $scope.checkFollowerStatus = function(item, btn){
            if(btn === 1)
            {
                if(item.status === "Accepted")
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            if(btn === 2)
            {
                if(item.status === "Request")
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        };

        $scope.accept = function(item){
          console.debug("Accept");
        };

        $scope.reject = function(item){
            console.debug("Reject");
        };

        $scope.disallow = function(item){
            console.debug("Disallow");
        };

        $scope.stopFollowing = function(item){
            console.debug("StopFollowing");
        };

        $scope.showRecentTrades = function(item){
            var passBack = {
                "leagueId" : $scope.allLeagueSelected.leagueId,
                "userId" : item.userId,
                "name" : item.name
            };
            modals.showForm('Recent Trades','leagueUsersRecentTradesDialog', passBack , "modal-lg");
        };
}]);

vsmApp.controller('LeagueUsersRecentTradesDialogController', ['$scope','$rootScope','$http','modals','LeaguesService', function ($scope, $rootScope, $http, modals, LeaguesService) {

    $scope.$scope = $scope;
    $scope.leagueId = $scope.passValuesToDialog.leagueId;
    $scope.userId = $scope.passValuesToDialog.userId;

    $scope.getRecentTradesDataGridOptions = {
        enableSorting: true,
        enableFiltering: true,
        columnDefs: [
            { field: 'dateTime', displayName:'Date'},
            { field: 'tikerSymbol', displayName:'Symbol'},
            { field: 'quantity', displayName:'Volume'},
            { field: 'orderType', displayName:'Order Type'},
            { field: 'status', displayName:'Status'}
        ]
    };

    LeaguesService.getUsersRecentTrades($scope.leagueId, $scope.userId).then(function(data){
        $scope.getRecentTradesDataGridOptions.data = data.recentTrades;
        setTimeout(function(){
            $('#leagueUsersDialogContainer').resize();
        },1000);
    });
}]);



