var vsmApp = angular.module('vsmApp');

vsmApp.directive('topnavigationbar', function() {
   return{
        restrict: 'E',
        replace: true,
        templateUrl: 'app/directive_templates/navigation/horizontalHeaderNavigation.html',
        controller: function($scope, $http){
            var actionUrl = 'app/json_meta_data/navigation_menu.json';
            $http.get(actionUrl,{}).success(function (menuJSON) {
                $scope.menus = menuJSON.menus;
            });
        }
    };
});


