var vsmApp = angular.module('vsmApp');

vsmApp.controller('LeaguesController', ['$scope', '$rootScope', 'modals','LeaguesService','TourService',
    function ($scope, $rootScope, modals, LeaguesService, TourService) {

        $scope.$scope = $scope;

        //Add this to all page controllers
        $rootScope.onPageLoad();
        TourService.setupTour('leagues');

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
            /*setTimeout(function() {
                $('.counter').counterUp({
                    delay: 2,
                    time: 1000
                });
            },500);*/
        });

        $scope.listOfUsersLeague = function(leagueId){
            modals.showForm('League Players','leagueUsersDialog', {"leagueId" : leagueId}, "modal-lg");
        };

        $scope.unlockLeague1 = function(league){
            modals.showForm('How to Unlock League','league1Dialog', {"league" : league}, "modal-lg");
        };

        $scope.unlockLeague2 = function(league){
            modals.showForm('How to Unlock League','league2Dialog', {"league" : league}, "modal-lg");
        };

        $scope.unlockLeague3 = function(league){
            modals.showForm('How to Unlock League','league3Dialog', {"league" : league}, "modal-lg");
        };
}]);

vsmApp.controller('LeaguesDialogController', ['$scope','$http','modals','$location','$rootScope', function ($scope, $http, modals, $location, $rootScope) {

    $scope.formmodel = {};
    $scope.formcontrol = {};
    $scope.formmodel.leagueId = $scope.passValuesToDialog.league.leagueId;
    $scope.league = $scope.passValuesToDialog.league;

    $scope.question1Mappings = [
        {"name":"The Stock of a Corporation is a Share", "type":"01"},
        {"name":"Shares represents a fraction of ownership in a business", "type":"02"},
        {"name":"None of the above", "type":"03"}
    ];

    $scope.question2Mappings = [
        {"name":"Is a Individual", "type":"01"},
        {"name":"Is a Company", "type":"02"},
        {"name":"Both", "type":"03"}
    ];

    $scope.question3Mappings = [
        {"name":"Market Orders", "type":"01"},
        {"name":"Limit Orders", "type":"02"},
        {"name":"Both", "type":"03"}
    ];

    $scope.perform = function(action){
        if (action === 'cancel') {
            modals.close();
        }
        else {
            if ($scope.mainForm.$invalid) {
                $scope.formcontrol.submitted = true;
            }
            else {
                if($scope.formmodel.question1 === "02" && $scope.formmodel.question2 === "03" && $scope.formmodel.question3 === "02")
                {
                    $scope.wrongAnswer = "Right Answers!";
                    action = $rootScope.getFinalURL(action);
                    $http.post(action, {leagueId : $scope.formmodel.leagueId}).success(function (response) {
                        modals.close();
                        $location.path('#/myPortfolio');
                    });
                }
                else
                {
                    $scope.wrongAnswer = "Wrong Answers!";
                }
            }
        }
    };
}]);

vsmApp.controller('LeagueUsersDialogController', ['$scope','$rootScope','$http','modals','LeaguesService', function ($scope, $rootScope, $http, modals, LeaguesService) {

    $scope.isAuthenticated = $rootScope.isAuthenticated();
    $scope.$scope = $scope;

    $scope.getLeagueUsersDataGridOptions = {
        enableSorting: true,
        enableFiltering: true,
        columnDefs: [
            /*{ field: 'photo', displayName:'Profile Pic'},*/
            { field: 'name', displayName:'Name'},
            { field: 'rank', displayName:'Ranking'},
            { field: 'totalValue', displayName:'League Value'},
            { field: 'followerCount', displayName:'Followers'},
            {name: 'follow', displayName: '', enableFiltering : false, enableSorting : false, cellTemplate: '<button id="followBtn" type="button" class="btn-small" ng-show="getExternalScopes().isAuthenticated" ng-click="getExternalScopes().follow(row.entity)" >Follow</button> '}
        ]
    };

    if($scope.passValuesToDialog.players)
    {
        $('#leagueUsersDialogContainer').hide();
        $scope.getLeagueUsersDataGridOptions.data = $scope.passValuesToDialog.players;
        setTimeout(function(){
            $('#leagueUsersDialogContainer').show();
            $('#leagueUsersDialogContainer').resize();
        },1000);
    }
    else
    {
        var leaguesUsersPromise = LeaguesService.getLeaguesUsers($scope.passValuesToDialog.leagueId);
        leaguesUsersPromise.then(function(data){
            $('#leagueUsersDialogContainer').hide();
            $scope.getLeagueUsersDataGridOptions.data = data;
            setTimeout(function(){
                $('#leagueUsersDialogContainer').show();
                $('#leagueUsersDialogContainer').resize();
            },1000);
        });
    }


    $scope.follow = function(item){
        var actionUrl = 'followUser';
        actionUrl = $rootScope.getFinalURL(actionUrl);
        $http.post(actionUrl,{"userId" : item.userId, "leagueId" : $scope.passValuesToDialog.leagueId}).success(function (response) {
            modals.close();
        });
    };
}]);

