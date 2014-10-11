var vsmApp = angular.module('vsmApp');

vsmApp.service('StockQuotesService', ['$http','$q','$log', function ($http, $q, $log) {

    this.getStockLists = function(){
        var deferred = $q.defer();
        var actionUrl = 'app/data/nasdaq100_stock_company_list.json';
        $http.get(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.getAllCurrentQuotes = function(allStockLists){
        var deferred = $q.defer();
        var tickers = '';
        $(allStockLists).each(function() {
            tickers = tickers.concat(encodeURI('\''));
            tickers = tickers.concat(this.Symbol);
            tickers = tickers.concat(encodeURI('\''));
            tickers = tickers.concat(encodeURI(','));
        });

        tickers = tickers.concat(encodeURI('\''));
        tickers = tickers.concat('^NDX');
        tickers = tickers.concat(encodeURI('\''));

        var quotesUrl = 'http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20('+ tickers +')%0A%09%09&env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json';
        var quotes = {};
        $http.get(quotesUrl,{})
            .success(function (quotesJSON) {
                $( quotesJSON.query.results.quote).each(function() {
                    quotes[this.symbol] = this;
                });
                deferred.resolve(quotes);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.getHistoricalStockLists = function(ticker){
        var deferred = $q.defer();
        var tickers = '';
        tickers = tickers.concat(encodeURI('\''));
        tickers = tickers.concat(ticker);
        tickers = tickers.concat(encodeURI('\''));

        Date.prototype.yyyymmdd = function() {

            var yyyy = this.getFullYear().toString();
            var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
            var dd  = this.getDate().toString();

            return yyyy + '-' + (mm[1]?mm:"0"+mm[0]) + '-' + (dd[1]?dd:"0"+dd[0]);
        };

        var d = new Date(); // today!
        d.setDate(d.getDate() - 70);

        var startDate = d.yyyymmdd();
        var endDate = new Date().yyyymmdd();

        var actionUrl = 'http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20in%20('+ tickers +')%20and%20startDate%20=%20%22'+startDate+'%22%20and%20endDate%20=%20%22'+endDate+'%22&diagnostics=true&env=store://datatables.org/alltableswithkeys&format=json';
        $http.get(actionUrl,{})
            .success(function (json) {
                var result = json.query.results.quote;
                /*var dataBasedOnDate = {};
                $(result).each(function(index, item){
                    dataBasedOnDate[item.Date] = item;
                });*/
                deferred.resolve(result);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };
}]);