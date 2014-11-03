var vsmApp = angular.module('vsmApp');

vsmApp.controller('MyPortfolioController', ['$scope', '$rootScope', '$log', 'StockQuotesService','modals','TourService',
    function ($scope, $rootScope, $log, StockQuotesService, modals, TourService) {

    $scope.$scope = $scope;

    var floatingShareOptions = {
        title : ' Misys - Stock Market League',
        description : '(A Game to Enthrall and Engage you on Stock Markets).',
        url : 'http://www.misys.com/'
    };
    //Add this to all page controllers
    $rootScope.onPageLoad(floatingShareOptions);
    TourService.setupTour('myPortfolio');

    StockQuotesService.getMyLeagues().then(function(data){
        var arrayLeagues = [];
        $(data).each(function(index,item){
            if(item.locked !== "true")
            {
                arrayLeagues.push(item);
            }
        });

        $scope.myLeagues  = arrayLeagues;
        $scope.leagueSelected = arrayLeagues[0];
        $scope.reloadMyPortfolio();
    });

    $scope.reloadMyPortfolio = function(){
        StockQuotesService.getMyPortfolio($scope.leagueSelected.leagueId).then(function(data){
            $scope.myPortfolio  = data;
            $scope.getStockHoldingsDataGridOptions.data = data.stockHoldings;
            setTimeout(function() {
                $('.counter').counterUp({
                    delay: 10,
                    time: 1000
                });
            },1000);
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
                quotes[item.stockId] = item;
            });

            var stockListData = [];
            $($scope.stockLists).each(function(index,item){
                console.debug(item.stockId);
                stockListData.push({tikerSymbol : item.tikerSymbol, name : item.name, lastTradePrice : quotes[item.stockId].lastTradePriceOnly});
            });
            $scope.stockListGridOptions.data = stockListData;
        });
    });

    $scope.buy = function(item){
        item.leagueUserId = $scope.leagueSelected.leagueId;
        item.leagueStage = $scope.leagueSelected.stage;
        modals.showForm('Buy Stocks','buystock', item, "modal-lg");
    };

    $scope.sell = function(item){
        item.leagueUserId = $scope.leagueSelected.leagueId;
        item.leagueStage = $scope.leagueSelected.stage;
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
            { field: 'dateTime', displayName:'Date'},
            { field: 'tikerSymbol', displayName:'Symbol'},
            { field: 'quantity', displayName:'Volume'},
            { field: 'orderType', displayName:'Order Type'},
            { field: 'status', displayName:'Status'}
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


}]);
