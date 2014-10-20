var vsmApp = angular.module('vsmApp');

vsmApp.controller('ChartsController', ['$scope', '$log', 'ChartService', 'StockQuotesService',
    function ($scope, $log, ChartService, StockQuotesService) {

        var stockListsPromise = StockQuotesService.getStockLists();
        stockListsPromise.then(function(data){
            $scope.stockLists = data;
        });

        $scope.historicalChartData = [];
        $scope.historicalChartDataFirst;
        $scope.historicalChartDataSecond;
        $scope.historicalChartDataThird;
        $scope.historicalChartDataFourth;
        $scope.reloadComparisonChart = function(symbol, position){
            StockQuotesService.getHistoricalStockLists(symbol.tikerSymbol).then(function(historicalData){
                var reducedHistoricalData = StockQuotesService.reducedHistoricalData(historicalData, 30);
                var historicalChartDataOptions = ChartService.reloadHistoricalCharts(symbol.tikerSymbol,reducedHistoricalData);
                if(position === 1){
                    $scope.historicalChartDataFirst = historicalChartDataOptions;
                }
                else if(position === 2)
                {
                    $scope.historicalChartDataSecond = historicalChartDataOptions;
                }
                else if(position === 3)
                {
                    $scope.historicalChartDataThird = historicalChartDataOptions;
                }
                else
                {
                    $scope.historicalChartDataFourth = historicalChartDataOptions;
                }
                $scope.historicalChartData = [];
                if($scope.historicalChartDataFirst)
                {
                    $scope.historicalChartData.push($scope.historicalChartDataFirst);
                }
                if($scope.historicalChartDataSecond)
                {
                    $scope.historicalChartData.push($scope.historicalChartDataSecond);
                }
                if($scope.historicalChartDataThird)
                {
                    $scope.historicalChartData.push($scope.historicalChartDataThird);
                }
                if($scope.historicalChartDataFourth)
                {
                    $scope.historicalChartData.push($scope.historicalChartDataFourth);
                }
            });
        };

        $scope.xAxisDateTickFormatFunction = function(){
            return function(d){
                var formatNew = d3.time.format("%d-%m-%Y");
                return formatNew(new Date(d));
            }
        };

        $scope.lineCharttoolTipContentFunction = function(){
            return function(key, x, y, e, graph) {
                console.log('tooltip content');
                return  '<h1 class="chartToolTipHeader">' + key + '</h1>' +
                    '<p class="chartToolTipContent">' +  y + '% on ' + x + '</p>'
            };
        };
}]);
