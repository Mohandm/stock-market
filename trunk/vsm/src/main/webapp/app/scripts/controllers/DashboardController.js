var vsmApp = angular.module('vsmApp');

vsmApp.controller('DashboardController', ['$scope','$http', '$modal', '$log', 'NewsService','DashboardService','StockQuotesService','ChartService',
    function ($scope, $http, $modal, $log, NewsService, DashboardService, StockQuotesService, ChartService) {

    $scope.$scope = $scope;
    /* News */
    NewsService.reloadNews(null);
    var reloadNewsFeed = function(){
        NewsService.reloadNews($scope.tickerNewsSelected.tikerSymbol);
    };

    /* Headlines */

    var breakingNews = $('#news-feed-ticker').breakingNews({
        url: 'http://articlefeeds.nasdaq.com/nasdaq/symbols?symbol=AAPL',
        feedSize: {
            height: '60px',
            width: 'auto'
        },
        numberToShow: 10,
        refresh: 2000,
        effect: 'tricker',
        effectDuration: 50
    });

    $scope.$on('$destroy', function(){
        $(breakingNews.timeoutArray).each(function(index,item){
            clearTimeout(item);
        });
    });

    /* Stocks Listing */
    $scope.stockListDashboardGridOptions = DashboardService.getStockListDashboardGridOptions();
    $scope.getHistoryDataGridOptions = DashboardService.getHistoryDataGridOptions();
    var stockListsPromise = StockQuotesService.getStockLists();
    stockListsPromise.then(function(data){
        $scope.stockLists = data;
        $scope.stockListDashboardGridOptions.data = data;
        $scope.tickerNewsSelected = data[8];
    }).then(function(){
        /* All Quotes */
        var allCurrentQuotesPromise = StockQuotesService.getAllCurrentQuotes($scope.stockLists);
        allCurrentQuotesPromise.then(function(data){
            $scope.quotes = data;
            reloadSparklingCharts('AAPL');
            $scope.currentTickerQuote = $scope.quotes[$scope.tickerNewsSelected.tikerSymbol];
        });
    });

    /* Reload Charts */
    var reloadSparklingCharts = function(symbol){
        StockQuotesService.getHistoricalStockLists(symbol).then(function(historicalData){
            ChartService.reloadSparklingCharts(symbol, historicalData, $scope.quotes[symbol]);
            $scope.getHistoryDataGridOptions.data = historicalData;
        });
    };


    $scope.reloadTicker = function(){
        $scope.currentTickerQuote = $scope.quotes[$scope.tickerNewsSelected.tikerSymbol];
        reloadNewsFeed();
        reloadSparklingCharts($scope.tickerNewsSelected.tikerSymbol);
    };

    $scope.options = {
        height : 160,
        width : 300,
        easing: 'easeOutBounce',
        barColor: 'rgba(255,255,255,0.75)',
        trackColor: 'rgba(0,0,0,0.3)',
        scaleColor: 'rgba(255,255,255,0.3)',
        lineCap: 'square',
        lineWidth: 4,
        size: 150,
        animate: 3000,
        onStep: function(from, to, percent) {
            $(this.el).find('.percent').text(Math.round(percent*Math.pow(10,2))/Math.pow(10,2) + '%');
        }
    };
}]);
