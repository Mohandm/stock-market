var vsmApp = angular.module('vsmApp');

vsmApp.service('StockQuotesService', ['$http','$q','$log', function ($http, $q, $log) {

    this.getStockLists = function(){
        var deferred = $q.defer(),
            actionUrl = 'stockList/';
        $http.get(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.getCurrentQuote = function(symbol){
        var deferred = $q.defer(),
            actionUrl = 'stockListCurrentQuotes?stockSymbol=';
        $http.get(actionUrl+symbol,{})
            .success(function (quotesJSON) {
                deferred.resolve(quotesJSON);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.getHistoricalStockLists = function(ticker, periodDays){
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
        d.setDate(d.getDate() - periodDays);

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

    this.getMyLeagues = function(){
        var deferred = $q.defer();
        var actionUrl = 'app/data/my_leagues.json';
        $http.get(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    }

    this.getMyPortfolio = function(groupId){
        var deferred = $q.defer();
        var actionUrl = 'app/data/my_portfolio.json';
        $http.get(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    }

    this.getMyRecentTrades = function(groupId){
        var deferred = $q.defer();
        var actionUrl = 'app/data/my_recent_trades.json';
        $http.get(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    }

}]);