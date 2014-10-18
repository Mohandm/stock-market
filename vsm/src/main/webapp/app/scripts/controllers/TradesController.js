var vsmApp = angular.module('vsmApp');

vsmApp.controller('TradesController', ['$scope','modals', function ($scope, modals,StockQuotesService) {


 $scope.buyStock = function() {
 	modals.showForm('Buy Stocks','buystock');
 };

 $scope.sellStock = function() {
	modals.showForm('Sell Stocks','sellstock');
 };

}]);
