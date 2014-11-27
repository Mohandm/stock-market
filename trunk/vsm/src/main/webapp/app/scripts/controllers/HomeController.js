var vsmApp = angular.module('vsmApp');

vsmApp.controller('HomeController', ['$scope', '$rootScope', 'modals','AlertsService','TourService','$http',
    function ($scope, $rootScope, modals, AlertsService, TourService,$http) {

        $scope.$scope = $scope;

        //Add this to all page controllers
        $rootScope.onPageLoad();
        TourService.setupTour('userhome');
        $rootScope.startTour();

        $scope.user = {};
        var actionUrl = 'getusersnapshot/';
            actionUrl = $rootScope.getFinalURL(actionUrl);
            $http.get(actionUrl).then(function(response){
               $scope.user = response.data;
               var leaguesNumber = response.data.leagues.length;
               $scope.leagueclass = 'col-md-'+12/leaguesNumber;
            });
}]);



