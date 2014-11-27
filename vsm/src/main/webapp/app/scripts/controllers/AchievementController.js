var vsmApp = angular.module('vsmApp');

vsmApp.controller('AchievementController', ['$scope', '$rootScope', 'modals','AchievementService','TourService','$timeout','$http',
    function ($scope, $rootScope, modals, AchievementService, TourService, $timeout, $http) {

        $scope.$scope = $scope;

        //Add this to all page controllers
        $rootScope.onPageLoad();
        TourService.setupTour('achievements');
        $rootScope.startTour();

        $scope.formmodel = {};
        $scope.formcontrol = {};
        $scope.unpublishedAchievements = AchievementService.getUnpublishedAchievementsForUser();
        
        if (angular.isDefined($scope.passValuesToDialog)) {
            $scope.formmodel.totalCoins = $scope.passValuesToDialog.totalCoins; 
        }
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
        };

        $scope.perform = function(action){
          if ($scope.mainForm.$invalid) {
            $scope.formcontrol.submitted = true;
          }
          else {
            action = $rootScope.getFinalURL(action);
            $http.post(action, $scope.formmodel).success(function (response) {
              modals.close();
            });
          }
        };

 }]);



