var vsmApp = angular.module('vsmApp');

vsmApp.controller('FollowersController', ['$scope', '$rootScope',
    function ($scope, $rootScope) {

        $scope.$scope = $scope;

        //Add this to all page controllers
        $rootScope.onPageLoad();

}]);



