var vsmApp = angular.module('vsmApp');

vsmApp.controller('MyDashboardController', ['$scope', '$rootScope', 'modals','LeaguesService','TourService','StockQuotesService',
    function ($scope, $rootScope, modals, LeaguesService, TourService, StockQuotesService) {

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
            $scope.leagueSelected = $scope.leagueList1;
            $scope.reloadMyPortfolio();

            /*setTimeout(function() {
             $('.counter').counterUp({
             delay: 2,
             time: 1000
             });
             },500);*/
        });

        $scope.selectLeague = function(league)
        {
            $scope.leagueSelected = league;
            if(league.stage === "1")
            {
                $('#portfolioTabContentContainer1').resize();
            }
            else if(league.stage === "2")
            {
                $('#portfolioTabContentContainer2').resize();
            }
            else
            {
                $('#portfolioTabContentContainer3').resize();
            }
        }

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

        $scope.reloadMyPortfolio = function(){
            StockQuotesService.getMyPortfolio($scope.leagueList1.leagueId).then(function(data){
                $scope.myPortfolio1  = data;
                $scope.getStockHoldingsDataGridOptions1.data = data.stockHoldings;
                setTimeout(function() {
                 $('.counter').counterUp({
                 delay: 10,
                 time: 1000
                 });
                 },1000);
            });
            StockQuotesService.getMyPortfolio($scope.leagueList2.leagueId).then(function(data){
                $scope.myPortfolio2  = data;
                $scope.getStockHoldingsDataGridOptions2.data = data.stockHoldings;
            });
            StockQuotesService.getMyPortfolio($scope.leagueList3.leagueId).then(function(data){
                $scope.myPortfolio3  = data;
                $scope.getStockHoldingsDataGridOptions3.data = data.stockHoldings;
            });
            StockQuotesService.getMyRecentTrades($scope.leagueList1.leagueId).then(function(data){
                $scope.myRecentTrades1  = data;
                $scope.getRecentTradesDataGridOptions1.data = data.recentTrades;
            });
            StockQuotesService.getMyRecentTrades($scope.leagueList2.leagueId).then(function(data){
                $scope.myRecentTrades2  = data;
                $scope.getRecentTradesDataGridOptions2.data = data.recentTrades;
            });
            StockQuotesService.getMyRecentTrades($scope.leagueList3.leagueId).then(function(data){
                $scope.myRecentTrades3  = data;
                $scope.getRecentTradesDataGridOptions3.data = data.recentTrades;
            });
        };

        var stockListsPromise = StockQuotesService.getStockLists();
        stockListsPromise.then(function(data){
            $scope.stockLists = data;
        }).then(function(){
            /* All Quotes */
            var allCurrentQuotesPromise = StockQuotesService.getAllCurrentQuotes();
            allCurrentQuotesPromise.then(function(data){
                var quotes = {}
                $(data).each(function(index,item){
                    quotes[item.stockId] = item;
                });

                var stockListData = [];
                $($scope.stockLists).each(function(index,item){
                    console.debug(item.stockId);
                    stockListData.push({tikerSymbol : item.tikerSymbol, name : item.name, lastTradePrice : quotes[item.stockId].lastTradePriceOnly});
                });
                $scope.stockListGridOptions1.data = stockListData;
                $scope.stockListGridOptions2.data = stockListData;
                $scope.stockListGridOptions3.data = stockListData;
            });
        });

        $scope.buy = function(item){
            item.leagueUserId = $scope.leagueSelected.leagueId;
            item.leagueStage = $scope.leagueSelected.stage;
            item.$parentScope = $scope;
            modals.showForm('Buy Stocks','buystock', item, "modal-lg");
        };

        $scope.sell = function(item){
            item.leagueUserId = $scope.leagueSelected.leagueId;
            item.leagueStage = $scope.leagueSelected.stage;
            item.$parentScope = $scope;
            modals.showForm('Sell Stocks','sellstock', item, "modal-lg");
        };

        $scope.getStockHoldingsDataGridOptions1 = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: [
                { field: 'tikerSymbol', displayName:'Symbol'},
                { field: 'volume', displayName:'Volume'},
                { field: 'marketCalculatedValue', displayName:'Market Value'},
                {name: 'sell', displayName: '', enableFiltering : false, enableSorting : false, cellTemplate: '<button id="buyBtn" type="button" class="btn-small" ng-click="getExternalScopes().sell(row.entity)" >Sell</button> '}
            ]
        };
        $scope.getStockHoldingsDataGridOptions2 = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: [
                { field: 'tikerSymbol', displayName:'Symbol'},
                { field: 'volume', displayName:'Volume'},
                { field: 'marketCalculatedValue', displayName:'Market Value'},
                {name: 'sell', displayName: '', enableFiltering : false, enableSorting : false, cellTemplate: '<button id="buyBtn" type="button" class="btn-small" ng-click="getExternalScopes().sell(row.entity)" >Sell</button> '}
            ]
        };
        $scope.getStockHoldingsDataGridOptions3 = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: [
                { field: 'tikerSymbol', displayName:'Symbol'},
                { field: 'volume', displayName:'Volume'},
                { field: 'marketCalculatedValue', displayName:'Market Value'},
                {name: 'sell', displayName: '', enableFiltering : false, enableSorting : false, cellTemplate: '<button id="buyBtn" type="button" class="btn-small" ng-click="getExternalScopes().sell(row.entity)" >Sell</button> '}
            ]
        };

        $scope.getRecentTradesDataGridOptions1 = {
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

        $scope.getRecentTradesDataGridOptions2 = {
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

        $scope.getRecentTradesDataGridOptions3 = {
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


        $scope.stockListGridOptions1 = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs:  [
                { field: 'tikerSymbol', displayName:'Ticker Symbol'},
                { field: 'name', displayName:'Name'},
                { field: 'lastTradePrice', displayName:'Price'},
                {name: 'buy', displayName: '', enableFiltering : false, enableSorting : false, cellTemplate: '<button id="buyBtn" type="button" class="btn-small" ng-click="getExternalScopes().buy(row.entity)" >Buy</button> '}
            ]
        };

        $scope.stockListGridOptions2 = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs:  [
                { field: 'tikerSymbol', displayName:'Ticker Symbol'},
                { field: 'name', displayName:'Name'},
                { field: 'lastTradePrice', displayName:'Price'},
                {name: 'buy', displayName: '', enableFiltering : false, enableSorting : false, cellTemplate: '<button id="buyBtn" type="button" class="btn-small" ng-click="getExternalScopes().buy(row.entity)" >Buy</button> '}
            ]
        };

        $scope.stockListGridOptions3 = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs:  [
                { field: 'tikerSymbol', displayName:'Ticker Symbol'},
                { field: 'name', displayName:'Name'},
                { field: 'lastTradePrice', displayName:'Price'},
                {name: 'buy', displayName: '', enableFiltering : false, enableSorting : false, cellTemplate: '<button id="buyBtn" type="button" class="btn-small" ng-click="getExternalScopes().buy(row.entity)" >Buy</button> '}
            ]
        };

}]);

vsmApp.controller('LeaguesDialogController', ['$scope','$http','modals','$location', function ($scope, $http, modals, $location) {

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
                    $http.post(action, {leagueId : $scope.formmodel.leagueId}).success(function (response) {
                        modals.close();
                        $location.path('/myPortfolio');
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
        $http.post('followUser',{"userId" : item.userId, "leagueId" : $scope.passValuesToDialog.leagueId}).success(function (response) {
            modals.close();
        });
    };
}]);

