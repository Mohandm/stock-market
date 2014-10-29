var vsmApp = angular.module('vsmApp');

vsmApp.service('LeaguesService', ['$http','$q','$log', function ($http, $q, $log) {

    this.getGameLeagues = function(){
        var deferred = $q.defer();
        var actionUrl = 'gameLeaguesList/';
        //var actionUrl = 'app/data/my_leagues.json';
        $http.post(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.getLeaguesUsers = function(leagueId){
        var deferred = $q.defer();
        var actionUrl = 'leaguesUsersList?leagueId=';
        //var actionUrl = 'app/data/league_users.json';
        $http.post(actionUrl+leagueId,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.getLeaderBoard = function(){
        var deferred = $q.defer();
        var actionUrl = 'leaderBoard/';
        //var actionUrl = 'app/data/leader_board.json';
        $http.post(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.getMyFollowers = function(leagueId){
        var deferred = $q.defer();
        //var actionUrl = 'myFollowers?leagueId=';
        var actionUrl = 'app/data/my_followers.json';
        $http.post(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.getYourFollowing = function(leagueId){
        var deferred = $q.defer();
        //var actionUrl = 'myFollowing?leagueId=';
        var actionUrl = 'app/data/your_following.json';
        $http.post(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

    this.getUsersRecentTrades = function(leagueId, userId){
        var deferred = $q.defer();
        //var actionUrl = 'playersRecentTrades?leagueId=' + leagueId + '&userId='+userId;
        var actionUrl = 'app/data/my_recent_trades.json';
        $http.get(actionUrl,{})
            .success(function (json) {
                deferred.resolve(json);
            }).error(function(msg, code) {
                deferred.reject(msg);
                $log.error(msg, code);
            });
        return deferred.promise;
    };

}]);