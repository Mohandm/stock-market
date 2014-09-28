'use strict';
 
angular.module('vsmApp')
  .controller('FormController',['$scope', '$location','$http','$routeParams', 'FormLoader','modals', function ($scope, $location, $http, $routeParams, FormLoader,modals)
  { 
       
    
    $scope.formControl = {submitted:false};
    
     $scope.confirm = function(action){
     		
 	    	var confirmAction, confirmMessage;
 	    	switch(action) {
             case 'register':
             	confirmAction = function(){
     				$scope.register();
     			};	
     			confirmMessage = "Are you sure you want to register?";
                 break;
            case 'reset':
                confirmAction = function(){
                    $scope.reset();
                };  
                confirmMessage = "Are you sure you want to reset your password?";
                 break;

             case 'cancel':
             	confirmAction = function(){
 					$scope.cancel();
 				};	
 				confirmMessage = "Are you sure you want to cancel?";
 				break;
 	    	}
     		modals.showConfirmation("Confirmation",confirmMessage,confirmAction);
     	};
     	
     	$scope.register = function(){
         	$http.post('registeruser', $scope.formmodel).success(function (response) {
         			modals.close();
         			$location.path('/'); 
         		});
     	};

        $scope.reset = function(){
            $http.post('resetpassword', $scope.formmodel).success(function (response) {
                    modals.close();
                    $location.path('/'); 
                });
        };

        $scope.login = function(){
            $http.post('login', $scope.formmodel).success(function (response) {
                    modals.close();
                    $location.path('/'); 
                });
        };     	
     	$scope.cancel = function(){
 			modals.close();
 			$location.path('/'); 
     	};
       
}]);
