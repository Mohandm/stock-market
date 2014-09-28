var vsmApp = angular.module("vsmApp");

vsmApp.controller('ActionController', ['$scope','$http','$routeParams','$location','modals', function ($scope, $http, $routeParams,$location,modals) {
	
   var actionCode, value;
	if (angular.isDefined($routeParams.actionCode)) {
		actionCode = $routeParams.actionCode;
     	
		if (angular.isDefined($routeParams.value)) {
		   	value = $routeParams.value;
		    $http.post(actionCode+"/"+value).success(function (metaResponseBody) {
				$location.path('/'); 
		   	}); 
		}
		else {
			 $http.post(actionCode).success(function (metaResponseBody) {
				$location.path('/'); 
		   	}); 
		}
	}
}]);
