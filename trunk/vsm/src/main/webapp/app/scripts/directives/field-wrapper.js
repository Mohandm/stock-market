'use strict';

var vsmApp = angular.module('vsmApp');
  
vsmApp.directive('fieldWrapper', function ($http, $compile) {
        return {
            templateUrl: 'app/directive_templates/field/fieldWrapper.html',
            restrict: 'E',
            replace: true,
            scope: { 
                label: "@label", 
                type: "@type",
                formmodel:"=",
                name:"@name",
                required:"=",
                formcontrol:"="
            }
        };
  });
