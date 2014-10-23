var vsmApp = angular.module('vsmApp');

vsmApp.service('TourService', ['$http','$rootScope', function ($http, $rootScope) {

    var dashboard = [
        {
            element: "#stock-quote-summary-panel-id",
            title: "NASDAQ 100 Index",
            content: "This section gives you the NASDAQ 100 index. Along with which the Global News from NASDAQ is also shown",
            placement: "bottom"
        },
        {
            element: "#select-symbol-dashboard-id",
            title: "Select Ticker Stock Symbol",
            content: "Select a Ticker Symbol to know more about its Current Standings in the Stock Market.",
            placement: "bottom"
        },
        {
            element: "#current-quote-data-dashboard-id",
            title: "Current Quote Data",
            content: "Below is the current quote data for the selected Symbol.",
            placement: "top"
        },
        {
            element: "#charts-dashboard-id",
            title: "Sparkline Charts",
            content: "These charts give you a quick understanding of how the stock value is changing over time. Read left to right. 2 months of market price is considered",
            placement: "top"
        },
        {
            element: "#historical-data-dashboard-id",
            title: "Historical Data",
            content: "This table gives you the OHLC (Open, High, Low, Close) for a stock in the past. 6 months of Historical Data is available.",
            placement: "top"
        },
        {
            element: "#news-dashboard-id",
            title: "Specific News",
            content: "NASDAQ News specific to Companies is shown here.",
            placement: "top"
        }
    ];

    var chart = [
        {
            element: "#charts-symbols-id",
            title: "Select Ticker Stock Symbol",
            content: "Select a Stock to compare it with other Stocks",
            placement: "bottom"
        },
        {
            element: "#charts-multi-bar-id",
            title: "Multi Bar Chart",
            content: "This chart compares the stocks based on their historical data.",
            placement: "top"
        },
        {
            element: "#charts-stacked-area-id",
            title: "Stacked Area Chart",
            content: "This chart compares the stocks based on their historical data.",
            placement: "top"
        }
    ];


    var tourOptions = {
        'dashboard' : dashboard,
        'chart' : chart
    };

    var currentPage = 'dashboard';

    this.setupTour = function(page){
        currentPage = page;
    };

    this.getCurrentPageSetup = function(){
        return tourOptions[currentPage];
    };
}]);