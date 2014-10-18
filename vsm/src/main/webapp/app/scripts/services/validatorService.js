(function () {
var vsmApp = angular.module('vsmApp');
 
vsmApp.service('ValidatorService', function($compile, $http, $rootScope, StockQuotesService) {
	
	var myStockHoldings;
	 StockQuotesService.getMyPortfolio(1).then(function(data){
            myStockHoldings = data.stockHoldings;
        });

	this.validateSellVolume = function(volume,ticker) {
		console.log (volume + ":" + ticker);
		for (stock in myStockHoldings) {
			if(myStockHoldings[stock].tikerSymbol==ticker && parseInt(myStockHoldings[stock].volume) < parseInt(volume)) {
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
 