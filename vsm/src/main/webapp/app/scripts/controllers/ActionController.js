var vsmApp = angular.module('vsmApp');

vsmApp.controller('ActionController', ['$scope','$http','$routeParams','$location','modals','$rootScope', function ($scope, $http, $routeParams,$location,modals, $rootScope) {
	
   var actionCode, value;
	if (angular.isDefined($routeParams.actionCode)) {
		actionCode = $routeParams.actionCode;
     	
		if (angular.isDefined($routeParams.value)) {
		   	value = $routeParams.value;
            var actionUrl = $rootScope.getFinalURL(actionCode);
		    $http.post(actionUrl+"/"+value).success(function (metaResponseBody) {
				$location.path('/'); 
		   	}); 
		}
		else {
            var actionUrl = $rootScope.getFinalURL(actionCode);
			 $http.post(actionUrl).success(function (metaResponseBody) {
				$location.path('/'); 
		   	}); 
		}
	}
}]);
