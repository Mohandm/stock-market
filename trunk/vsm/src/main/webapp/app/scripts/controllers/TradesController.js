var vsmApp = angular.module('vsmApp');

vsmApp.controller('TradesController', ['$scope', '$rootScope', 'modals',
    function ($scope, $rootScope, modals, StockQuotesService) {

     //Add this to all page controllers
     $rootScope.onPageLoad();

     $scope.buyStock = function() {
        modals.showForm('Buy Stocks','buystock', {}, "modal-lg");
     };

     $scope.sellStock = function() {
        modals.showForm('Sell Stocks','sellstock', {}, "modal-lg");
     };

}]);
