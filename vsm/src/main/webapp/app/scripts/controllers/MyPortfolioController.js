var vsmApp = angular.module('vsmApp');

vsmApp.controller('MyPortfolioController', ['$scope', '$rootScope', '$log', 'StockQuotesService','modals',
    function ($scope, $rootScope, $log, StockQuotesService, modals) {

    $scope.$scope = $scope;

    //Add this to all page controllers
    $rootScope.onPageLoad();

    StockQuotesService.getMyLeagues().then(function(data){
        $scope.myLeagues  = data;
        $scope.leagueSelected = data[0];
        $scope.reloadMyPortfolio();
    });

    $scope.reloadMyPortfolio = function(){
        StockQuotesService.getMyPortfolio($scope.leagueSelected.leagueId).then(function(data){
            $scope.myPortfolio  = data;
            $scope.getStockHoldingsDataGridOptions.data = data.stockHoldings;
        });
        StockQuotesService.getMyRecentTrades($scope.leagueSelected.leagueId).then(function(data){
            $scope.myRecentTrades  = data;
            $scope.getRecentTradesDataGridOptions.data = data.recentTrades;
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
                quotes[item.stockMaster.stockId] = item;
            });

            var stockListData = [];
            $($scope.stockLists).each(function(index,item){
                stockListData.push({tikerSymbol : item.tikerSymbol, name : item.name, lastTradePrice : quotes[item.stockId].lastTradePriceOnly});
            });
            $scope.stockListGridOptions.data = stockListData;
        });
    });

    $scope.buy = function(item){
        item.leagueUserId = $scope.leagueSelected.leagueId;
        modals.showForm('Buy Stocks','buystock', item, "modal-lg");
    };

    $scope.sell = function(item){
        item.leagueUserId = $scope.leagueSelected.leagueId;
        modals.showForm('Sell Stocks','sellstock', item, "modal-lg");
    };

    $scope.getStockHoldingsDataGridOptions = {
        enableSorting: true,
        enableFiltering: true,
        columnDefs: [
            { field: 'tikerSymbol', displayName:'Symbol'},
            { field: 'volume', displayName:'Volume'},
            { field: 'marketCalculatedValue', displayName:'Market Value'},
            {name: 'sell', displayName: '', enableFiltering : false, enableSorting : false, cellTemplate: '<button id="buyBtn" type="button" class="btn-small" ng-click="getExternalScopes().sell(row.entity)" >Sell</button> '}
        ]
    };

    $scope.getRecentTradesDataGridOptions = {
        enableSorting: true,
        enableFiltering: true,
        columnDefs: [
            { field: 'tikerSymbol', displayName:'Symbol'},
            { field: 'name', displayName:'Company'},
            { field: 'date', displayName:'Date'},
            { field: 'time', displayName:'Time'},
            { field: 'price', displayName:'Price'},
            { field: 'orderType', displayName:'Order Type'}
        ]
    };

    $scope.stockListGridOptions = {
        enableSorting: true,
        enableFiltering: true,
        columnDefs:  [
            { field: 'tikerSymbol', displayName:'Ticker Symbol'},
            { field: 'name', displayName:'Name'},
            { field: 'lastTradePrice', displayName:'Price'},
            {name: 'buy', displayName: '', enableFiltering : false, enableSorting : false, cellTemplate: '<button id="buyBtn" type="button" class="btn-small" ng-click="getExternalScopes().buy(row.entity)" >Buy</button> '}
        ]
    };

    $("body").floatingShare({
        place: "top-right",
        buttons: ["facebook","twitter","linkedin"]
    });
}]);
