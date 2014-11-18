'use strict';

var vsmApp = angular.module('vsmApp');

vsmApp.directive('topHeader', function ($http, $compile) {
    return {
        templateUrl: 'app/directive_templates/layout/topHeaderTitleNProfile.html',
        restrict: 'E',
        replace: true,
        scope: {
            authenticated : '=',
            username : '@',
            title : '@',
            titledescription : '@'
        }
    };
});
