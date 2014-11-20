var vsmApp = angular.module('vsmApp');

vsmApp.service('AlertsService', ['$http','$q','$log','$rootScope', function ($http, $q, $log, $rootScope) {

    this.getAlertsList = function(){
        var deferred = $q.defer(),
            actionUrl = 'alertList/';
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

    this.getUserNotifications = function(){
        var deferred = $q.defer(),
            actionUrl = 'userNotifications/';
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

}]);