var vsmApp = angular.module('vsmApp');

vsmApp.controller('UserProfileController', ['$scope', '$location','$http','$routeParams', 'FormLoader','modals','$upload','$rootScope',
    function ($scope, $location, $http, $routeParams, FormLoader, modals, $upload, $rootScope)
    {
        $scope.formmodel = {};
        $scope.formcontrol = {};
        $scope.profilePic;

        /*$scope.genderMappings = [
            {"name":"Male", "type":"M"},
            {"name":"Female", "type":"F"}
        ];

        $scope.formmodel.gender = 'M';

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
        $scope.genderChange();*/

        $scope.onFileSelect = function($files) {
            $scope.profilePic = $files[0];
        };

        var actionUrl = $rootScope.getFinalURL('getUser');
        $http.get(actionUrl,{}).success(function (json) {
                $scope.formmodel = json;
            }).error(function(msg, code) {
              $log.error(msg, code);
        });

        $scope.confirm = function(action){
            var confirmMessage = "Are you sure you want to change your profile details ?";
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
                    action = $rootScope.getFinalURL(action);
                    $http.post(action, $scope.formmodel).success(function (response) {
                        if($scope.profilePic)
                        {
                            var url = $rootScope.getFinalURL('updateUserProfilePic');
                            $scope.upload = $upload.upload({
                                url: url,
                                file: $scope.profilePic
                            }).success(function(data, status, headers, config) {
                                console.log(data);
                            });
                        }
                        modals.close();
                        $location.path('/');
                    });
                }
            }
        };
}]);