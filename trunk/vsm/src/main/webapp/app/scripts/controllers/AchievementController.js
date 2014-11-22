var vsmApp = angular.module('vsmApp');

vsmApp.controller('AchievementController', ['$scope', '$rootScope', 'modals','AchievementService','TourService','$timeout',
    function ($scope, $rootScope, modals, AchievementService, TourService,$timeout) {


        $scope.unpublishedAchievements = AchievementService.getUnpublishedAchievementsForUser();
        
        var promise2 = AchievementService.getCompletedAchievements();
        promise2.then(function(data){
            $scope.completedAchievements = data;
        });

        var promise3 = AchievementService.getPendingAchievements();
        promise3.then(function(data){
            $scope.pendingAchievements = data;
        });

        $scope.closeDialog = function() {
        	modals.close();
        }

 }]);



