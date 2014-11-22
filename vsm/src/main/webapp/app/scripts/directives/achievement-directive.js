'use strict';

var vsmApp = angular.module('vsmApp');
  
vsmApp.directive('achievementDirective', function ($http, $compile) {
        return {
            templateUrl: 'app/directive_templates/field/singleAchievement.html',
            restrict: 'E',
            replace: true,
            scope: { 
                achievement : "="
            },
            controller: function($scope, $http){
                $scope.getNumber = function() {
                    return new Array($scope.achievement.level);   
                }
            }
        };
  });
