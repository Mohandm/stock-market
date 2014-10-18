var vsmApp = angular.module('vsmApp');

vsmApp.service('ChartService', ['$http', function ($http) {

    this.reloadSparklingCharts = function(symbol, historicalData, quoteData){

        var chartData = [];
        $(historicalData).each(function(index, item){
            chartData.push(item.Close);
        });
        chartData = chartData.reverse();
        $("#stats-line").sparkline(chartData, {
            type: 'line',
            width: '300',
            height: '150',
            lineColor: 'rgba(255,255,255,0.4)',
            fillColor: 'rgba(0,0,0,0.2)',
            lineWidth: 1.25,
            barWidth: 5,
            barColor: '#C5CED6'
        });

        $("#stats-line-2").sparkline(chartData, {
            type: 'bar',
            width: '300',
            height: '150',
            lineColor: 'rgba(255,255,255,0.4)',
            fillColor: 'rgba(0,0,0,0.2)',
            lineWidth: 1.25,
            barWidth: 5,
            barColor: '#C5CED6'
        });

        $("#stats-line-3").sparkline(chartData, {
            type: 'discrete',
            width: '300',
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
            var dateTemp = format.parse(item.Date);
            if(!firstItem)
            {
                var perviousItem = data[index-1];
                var difference = parseFloat(item.Close) - parseFloat(perviousItem.Close);
                var percentage = (difference * 100)/parseFloat(perviousItem.Close);
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