var vsmApp = angular.module('vsmApp');

vsmApp.service('LeaguesService', ['$http','$q','$log', function ($http, $q, $log) {

    this.getGameLeagues = function(){
        var deferred = $q.defer();
        //var actionUrl = 'gameLeaguesList/';
        var actionUrl = 'app/data/my_leagues.json';
        $http.post(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

}]);