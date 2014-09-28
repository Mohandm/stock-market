'use strict';
 
angular.module('vsmApp')
  .controller('FormSelectorController',['$scope', '$location','$http','$routeParams', 'FormLoader','modals', function ($scope, $location, $http, $routeParams, FormLoader,modals)
  { 

    // Using our FormLoader factory, 
        // we create getForm() to be called in the home.html file 
    $scope.getForm = function() {
        return FormLoader.getTheForm();
    }  
}]);
