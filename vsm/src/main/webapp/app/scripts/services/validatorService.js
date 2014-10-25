(function () {
var vsmApp = angular.module('vsmApp');
 
vsmApp.service('ValidatorService', function($compile, $http, $rootScope, StockQuotesService) {
	
	this.validateSellVolume = function(currentVolume,stockHoldingVolume) {
		console.log (currentVolume + ":" + stockHoldingVolume);
        if(currentVolume)
        {
            if(parseInt(stockHoldingVolume) < parseInt(currentVolume)) {
                return false;
            }
        }
        return true;
	};

	this.isNumber = function(value) {
		return !isNaN(value);
	};
});
}());
 