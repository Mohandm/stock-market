var vsmApp = angular.module('vsmApp');

vsmApp.controller('AchievementController', ['$scope', '$rootScope', 'modals','AchievementService','TourService',
    function ($scope, $rootScope, modals, AchievementService, TourService) {

        var promise = AchievementService.getUnpublishedAchievements();
        promise.then(function(data){
            $scope.unpublishedAchievements = data;
        });

        var promise2 = AchievementService.getCompletedAchievements();
        promise2.then(function(data){
            $scope.completedAchievements = data;
        });

        var promise3 = AchievementService.getPendingAchievements();
        promise3.then(function(data){
            $scope.pendingAchievements = data;
        });

        $scope.closeDialog = function() {
        	AchievementService.resetUnpublishedAchievements();
        	modals.close();
        }

 }]);



