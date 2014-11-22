var vsmApp = angular.module('vsmApp');

vsmApp.service('AchievementService', ['$http','$q','$log','$rootScope','$interval','modals', 
        function ($http, $q, $log,$rootScope,$interval, modals) {

    var unpublishedAchievements = {};

     var pollServer = function () {
            if (!$rootScope.isAuthenticated()) {
                return;
            }
            unpublishedAchievements = {};
            var actionUrl = 'getUnpublishedAchievements/';
            actionUrl = $rootScope.getFinalURL(actionUrl);
            $http.get(actionUrl).then(function(response){
                if (response.data.length>0) {
                    unpublishedAchievements = response.data;
                    modals.showAchievement();
                }
            });
        }
    
     var timer=$interval(function(){
        pollServer();
      },10000);
    
    this.cancelTimer = function () {
        if(angular.isDefined(timer)) {
            $interval.cancel(timer);
            timer=undefined;
          }
    }

    this.getUnpublishedAchievementsForUser = function () {
        return unpublishedAchievements;
    }

    this.getCompletedAchievements = function(){
        var deferred = $q.defer(),
            actionUrl = 'getCompletedAchievements/';
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

    this.getPendingAchievements = function(){
        var deferred = $q.defer(),
            actionUrl = 'getPendingAchievements/';
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