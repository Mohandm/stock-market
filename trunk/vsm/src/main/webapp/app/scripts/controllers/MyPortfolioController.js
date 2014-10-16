var vsmApp = angular.module('vsmApp');

vsmApp.controller('MyPortfolioController', ['$scope', '$log', 'StockQuotesService',
    function ($scope, $log, StockQuotesService) {

    $scope.userId = '007';
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

    $scope.getStockHoldingsDataGridOptions = {
        enableSorting: true,
        enableFiltering: true,
        columnDefs: [
            { field: 'tikerSymbol', displayName:'Symbol'},
            { field: 'name', displayName:'Company'},
            { field: 'volume', displayName:'Volume'},
            { field: 'pricePaid', displayName:'Price Paid'},
            { field: 'marketPrice', displayName:'Market Price'},
            { field: 'marketCalculatedValue', displayName:'Market Value'},
            { field: 'lifeTimeReturn', displayName:'Life Time Return'}
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

    $("body").floatingShare({
        place: "top-right",
        buttons: ["facebook","twitter","linkedin"]
    });



}]);
