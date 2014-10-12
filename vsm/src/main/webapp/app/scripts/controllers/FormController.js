'use strict';
 
angular.module('vsmApp')
  .controller('FormController',['$scope', '$location','$http','$routeParams', 'FormLoader','modals', function ($scope, $location, $http, $routeParams, FormLoader,modals)
  { 
       
    $scope.formmodel = {};
    $scope.formcontrol = {};
    
     $scope.confirm = function(action){
     		
 	    	var confirmMessage;
            switch(action) {
             case 'registeruser':
             	confirmMessage = "Are you sure you want to register?";
                 break;
            case 'changepassword':
                confirmMessage = "Are you sure you want to change your password?";
                 break;
            case 'resetpassword':
                confirmMessage = "Are you sure you want to reset your password?";
                 break;

             case 'cancel':
             	confirmMessage = "Are you sure you want to cancel?";
 				break;
 	    	}
     		var confirmAction = function(){
                $scope.perform(action);
            };  
            modals.showConfirmation("Confirmation",confirmMessage,confirmAction);
     	};
     	
     	
        $scope.perform = function(action){
            if (action === 'cancel') {
                modals.close();
                $location.path('/'); 
            }
            else {
                if ($scope.mainForm.$invalid) {
                    $scope.formcontrol.submitted = true;
                    modals.close();
                }
                else {
                    $http.post(action, $scope.formmodel).success(function (response) {
                        modals.close();
                            $location.path('/'); 
                    });
                }
            }
        };
}]);
