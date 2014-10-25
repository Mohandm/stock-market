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

    this.getAllCurrentQuotes = function(){
        var deferred = $q.defer(),
            actionUrl = 'stockListAllCurrentQuotes/';
        $http.get(actionUrl,{})
            .success(function (quotesJSON) {
                deferred.resolve(quotesJSON);
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

    this.getHistoricalStockLists = function(ticker){
        var deferred = $q.defer(),
            actionUrl = 'stockListHistory?stockSymbol=';
        $http.get(actionUrl+ticker,{})
            .success(function (json) {
               deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.reducedHistoricalData = function(historicalData, count){
        var length = historicalData.length;
        return historicalData.slice(length - count, length);
    };

    this.getMyLeagues = function(){
        var deferred = $q.defer();
        var actionUrl = 'userLeaguesList/';
        $http.post(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.getMyPortfolio = function(leagueId){
        var deferred = $q.defer();
        var actionUrl = 'myPortfolio?leagueId=';
        $http.post(actionUrl+leagueId,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.getMyRecentTrades = function(leagueId){
        var deferred = $q.defer();
        var actionUrl = 'myRecentTrades?leagueId=';
        $http.get(actionUrl+leagueId,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

}]);