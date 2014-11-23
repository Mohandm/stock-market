'use strict';

var vsmApp = angular.module('vsmApp');

vsmApp.directive('topHeader', function ($http, $compile, AuthService, modals,LeaguesService) {
    return {
        templateUrl: 'app/directive_templates/layout/topHeaderTitleNProfile.html',
        restrict: 'E',
        replace: true,
        scope: {
            title : '@',
            titledescription : '@'
        },
         controller: function($scope){
            var promise  =  AuthService.getUserProfile();
            promise.then(function(data){
                $scope.user = data;
                $scope.userName = $scope.user.firstName + " " +  $scope.user.lastName;
            });
            $scope.authenticated = AuthService.isAuthenticated();

            var promise  =  LeaguesService.getGameLeagues();
            promise.then(function(data){
                $scope.userLeagues = data;
            });
            

            $scope.redeemCoins = function() {
                var item = {};
                item.leagues = $scope.userLeagues;
                item.totalCoins = $scope.user.coins;
                modals.showForm('Redeem Coins','redeemcoins', item, "modal-lg");
            };
        }
    };
});
