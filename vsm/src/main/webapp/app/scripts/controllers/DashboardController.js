var vsmApp = angular.module('vsmApp');

vsmApp.controller('DashboardController', ['$scope','$http', '$modal', '$log', 'NewsService','DashboardService','StockQuotesService','ChartService',
    function ($scope, $http, $modal, $log, NewsService, DashboardService, StockQuotesService, ChartService) {

    $scope.$scope = $scope;
    /* News */
    NewsService.reloadNews(null);
    var reloadNewsFeed = function(){
        NewsService.reloadNews($scope.tickerNewsSelected.Symbol);
    };

    /* Headlines */
    $('#news-feed-ticker').breakingNews({
        url: 'http://articlefeeds.nasdaq.com/nasdaq/symbols?symbol=AAPL',
        feedSize: {
            height: '60px',
            width: 'auto'
        },
        numberToShow: 20,
        refresh: 2000,
        effect: 'tricker',
        effectDuration: 50
    });

   /* Stocks Listing */
    $scope.stockListDashboardGridOptions = DashboardService.getStockListDashboardGridOptions();
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
            $scope.currentTickerQuote = $scope.quotes[$scope.tickerNewsSelected.Symbol];
        });
    });

    /* Reload Charts */
    var reloadSparklingCharts = function(symbol){
        StockQuotesService.getHistoricalStockLists(symbol).then(function(historicalData){
            ChartService.reloadSparklingCharts(symbol, historicalData, $scope.quotes[symbol]);
        });
    };


    $scope.reloadTicker = function(){
        $scope.currentTickerQuote = $scope.quotes[$scope.tickerNewsSelected.Symbol];
        reloadNewsFeed();
        reloadSparklingCharts($scope.tickerNewsSelected.Symbol);
    };

    $scope.openStockSummary = function(item){
    	console.debug(item);
        StockQuotesService.getHistoricalStockLists(item.Symbol).then(function(historicalData){
            $scope.openStockSummaryModal(item, historicalData, $scope.quotes[item.Symbol]);
        });
    };

    $scope.openStockSummaryModal = function (item, historicalData, quoteData) {

        $scope.items = ['item1', 'item2', 'item3'];
        var modalInstance = $modal.open({
            templateUrl: 'views/stockSummaryModal.html',
            controller: 'StockSummaryModalCtrl',
            size: 'lg',
            resolve: {
                item: function () {
                    return item;
                },
                historicalData : function(){
                    return historicalData;
                },
                quoteData : function(){
                    return quoteData;
                }
            }
        });

        /*modalInstance.result.then(function (selectedItem) {
            $scope.selected = selectedItem;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });*/
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

vsmApp.controller('StockSummaryModalCtrl', ['$scope', 'ChartService', '$modalInstance', '$timeout', 'item', 'historicalData', 'quoteData',
    function ($scope, ChartService, $modalInstance, $timeout, item, historicalData, quoteData) {

        $scope.item = item;
        $scope.historicalData = historicalData;
        $scope.quoteData = quoteData;

        $scope.getHistoryDataGridOptions = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: [
                { field: 'Date', displayName:'Date'},
                { field: 'Open', displayName:'Open'},
                { field: 'Close', displayName:'Close'},
                { field: 'High', displayName:'High'},
                { field: 'Low', displayName:'Low'}
            ]
        };

        $scope.getHistoryDataGridOptions.data = historicalData;
        $timeout(function (){
            $(window).resize();
        });


        $scope.ok = function () {
            $modalInstance.close();
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

}]);

