var vsmApp = angular.module('vsmApp');

vsmApp.service('ChartService', ['$http','StockQuotesService', function ($http,StockQuotesService) {

    this.reloadSparklingCharts = function(symbol, historicalData, quoteData){
        var reducedData1 = StockQuotesService.reducedHistoricalData(historicalData, 50);
        var reducedData2 = StockQuotesService.reducedHistoricalData(historicalData, 42);
        var chartData1 = [];
        var chartData2 = [];
        $(reducedData1).each(function(index, item){
            chartData1.push(item.close);
        });
        $(reducedData2).each(function(index, item){
            chartData2.push(item.close);
        });
        //chartData = chartData.reverse();
        $("#stats-line").sparkline(chartData1, {
            type: 'line',
            width: '250',
            height: '150',
            lineColor: 'rgba(255,255,255,0.4)',
            fillColor: 'rgba(0,0,0,0.2)',
            lineWidth: 1.25,
            barWidth: 5,
            barColor: '#C5CED6'
        });

        $("#stats-line-2").sparkline(chartData2, {
            type: 'bar',
            width: '250',
            height: '150',
            lineColor: 'rgba(255,255,255,0.4)',
            fillColor: 'rgba(0,0,0,0.2)',
            lineWidth: 1.25,
            barWidth: 5,
            barColor: '#C5CED6'
        });

        $("#stats-line-3").sparkline(chartData1, {
            type: 'discrete',
            width: '250',
            height: '150',
            lineColor: 'rgba(255,255,255,0.4)',
            lineWidth: 1.25,
            fillColor: 'rgba(0,0,0,0.2)'
        });
        return null;
    };

    this.reloadHistoricalCharts = function(symbol, data){
        var chartDataOptions = {"key": symbol};
        var values = [];

        var firstItem = true;
        $(data).each(function(index,item){
            var format = d3.time.format("%Y-%m-%d");
            var dateTemp = format.parse(item.stockDate);
            if(!firstItem)
            {
                var perviousItem = data[index-1];
                var difference = parseFloat(item.close) - parseFloat(perviousItem.close);
                var percentage = (difference * 100)/parseFloat(perviousItem.close);
                values.push([dateTemp.getTime(), percentage]);
            }
            else
            {
                firstItem = false;
            }
        });
        chartDataOptions.values = values;
        return chartDataOptions;
    };
}]);