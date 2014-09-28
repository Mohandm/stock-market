(function () {
var vsmApp = angular.module('vsmApp');
 
vsmApp.service('ValidatorService', function($compile, $http, $rootScope, $templateCache, $cacheFactory,$filter) {
	
	var currencies;
	
	this.fieldSize = function(currency) {
		if (currency && currency.length == 3)
		{
			return true;
		}
		return false;
			
	};
	
	this.noSpecialCharactersAllowed = function(value) {
		return !/[!@#$%^&*()_+-+={}[]|\'";:?<>,.]$/.test(value);
	};
});
}());
 