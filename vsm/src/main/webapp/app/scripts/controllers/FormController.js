'use strict';
 
angular.module('vsmApp')
  .controller('FormController',['$scope', '$location','$http','$routeParams', 'FormLoader','modals','$upload',
        function ($scope, $location, $http, $routeParams, FormLoader, modals, $upload)
  { 
       
    $scope.formmodel = {};
    $scope.formcontrol = {};
    $scope.profilePic;

    $scope.genderMappings = [
        {"name":"Male", "type":"M"},
        {"name":"Female", "type":"F"}
    ];

    $scope.formmodel.gender = 'M';

    $('.fileinput').on('change.bs.fileinput', function(e){
        console.debug(this.files);
    });
    $('.fileinput').on('clear.bs.fileinput', function (e) {
        $scope.genderChange();
    });
    $scope.genderChange = function(){
        var img;
        if($scope.formmodel.gender === 'M')
        {
            img = $('<img />',{ src: 'app/images/male.jpg', alt:'male'})
            $('.fileinput .thumbnail').html(img);
            $('.fileinput').removeClass('fileinput-new').addClass('fileinput-exists');
        }
        else if($scope.formmodel.gender === 'F')
        {
            img = $('<img />',{ src: 'app/images/female.jpg', alt:'male'})
            $('.fileinput .thumbnail').html(img);
            $('.fileinput').removeClass('fileinput-new').addClass('fileinput-exists');
        }
    };
    $scope.genderChange();

    $scope.onFileSelect = function($files) {
        $scope.profilePic = $files[0];
    };
    
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
                        $scope.upload = $upload.upload({
                            url: 'registerUserProfilePic',
                            data: {userId: response.id},
                            file: $scope.profilePic
                        }).success(function(data, status, headers, config) {
                            console.log(data);
                        });
                        modals.close();
                        $location.path('/');
                    });
                }
            }
        };
}]);
