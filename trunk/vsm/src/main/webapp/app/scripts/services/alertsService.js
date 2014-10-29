var vsmApp = angular.module('vsmApp');

vsmApp.service('AlertsService', ['$http','$q','$log', function ($http, $q, $log) {

    this.getAlertsList = function(){
        var deferred = $q.defer(),
            actionUrl = 'alertList/';
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