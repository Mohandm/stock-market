var vsmApp = angular.module('vsmApp');

vsmApp.service('AchievementService', ['$http','$q','$log','modals', function ($http, $q, $log,modals) {

   var unpublishedAchievements = 'false';

    this.setUnpublishedAchievements = function(){
        if (angular.equals(unpublishedAchievements, 'false')) {
            unpublishedAchievements = 'true';
            modals.showAchievement();
         }
    };

    this.resetUnpublishedAchievements = function(){
        unpublishedAchievements = 'false';
    };

    this.hasUnpublishedAchievements = function(){
        return angular.equals(unpublishedAchievements, 'true');
    };

    this.getUnpublishedAchievements = function(){
        var deferred = $q.defer(),
            actionUrl = 'getUnpublishedAchievements/';
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