var vsmApp = angular.module('vsmApp');

vsmApp.controller('DashboardController', ['$scope', '$rootScope', '$http', '$modal', '$log', 'NewsService','DashboardService','StockQuotesService','ChartService',
    function ($scope, $rootScope, $http, $modal, $log, NewsService, DashboardService, StockQuotesService, ChartService) {

    $scope.$scope = $scope;

    //Add this to all page controllers
    $rootScope.onPageLoad();

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
        var allCurrentQuotesPromise = StockQuotesService.getCurrentQuote($scope.tickerNewsSelected.tikerSymbol);
        allCurrentQuotesPromise.then(function(data){
            reloadSparklingCharts($scope.tickerNewsSelected.tikerSymbol);
            $scope.currentTickerQuote = data;
        });

        var ndxQuotesPromise = StockQuotesService.getCurrentQuote('%5ENDX');
        ndxQuotesPromise.then(function(data){
            $scope.ndxTickerQuote = data;
        });
    });

    /* Reload Charts */
    var reloadSparklingCharts = function(symbol){
        StockQuotesService.getHistoricalStockLists(symbol).then(function(historicalData){
            var reducedHistoricalData = StockQuotesService.reducedHistoricalData(historicalData, 50);
            ChartService.reloadSparklingCharts(symbol, reducedHistoricalData, $scope.currentTickerQuote);
            $scope.getHistoryDataGridOptions.data = historicalData;
        });
    };


    $scope.reloadTicker = function(){
        var allCurrentQuotesPromise = StockQuotesService.getCurrentQuote($scope.tickerNewsSelected.tikerSymbol);
        allCurrentQuotesPromise.then(function(data){
            $scope.currentTickerQuote = data;
            reloadNewsFeed();
            reloadSparklingCharts($scope.tickerNewsSelected.tikerSymbol);
        });
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
