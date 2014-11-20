var vsmApp = angular.module('vsmApp');

vsmApp.service('StockQuotesService', ['$http','$q','$log','$rootScope', function ($http, $q, $log, $rootScope) {

    this.getStockLists = function(){
        var deferred = $q.defer(),
            actionUrl = 'stockList/';
        actionUrl = $rootScope.getFinalURL(actionUrl);
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
        actionUrl = $rootScope.getFinalURL(actionUrl);
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
        actionUrl = $rootScope.getFinalURL(actionUrl);
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
        actionUrl = $rootScope.getFinalURL(actionUrl);
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
        actionUrl = $rootScope.getFinalURL(actionUrl);
        $http.get(actionUrl,{})
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
        actionUrl = $rootScope.getFinalURL(actionUrl);
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
        actionUrl = $rootScope.getFinalURL(actionUrl);
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