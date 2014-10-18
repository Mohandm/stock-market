var vsmApp = angular.module('vsmApp');

vsmApp.controller('DialogFormController', ['$scope','$http','modals', 'StockQuotesService', function ($scope, $http, modals, StockQuotesService) {
	
 $scope.formmodel = {};
 $scope.formcontrol = {};

 var stockListsPromise = StockQuotesService.getStockLists();
stockListsPromise.then(function(data){
        $scope.stockLists = data;
    });

$scope.priceTypeMappings = [
    {"name":"Market", "type":"01"},
    {"name":"Limit", "type":"02"},
    {"name":"StopLoss", "type":"03"}
];

$scope.intraDayOptions = [
    {"title":"Yes", "value":"Y"},
    {"title":"No", "value":"N"}
];

// TODO: Remove hardcoded league id
 StockQuotesService.getMyPortfolio(1).then(function(data){
            $scope.myPortfolio  = data;
            $scope.myStockHoldings = data.stockHoldings;
        });

$scope.formmodel.priceType = "01";

$scope.perform = function(action){
    if (action === 'cancel') {
        modals.close();
    }
    else {
        if ($scope.mainForm.$invalid) {
            $scope.formcontrol.submitted = true;
        }
        else {
            $http.post(action, $scope.formmodel).success(function (response) {
                modals.close();
            });
        }
    }
};

$scope.isNotMarket = function() {
    if ($scope.formmodel.priceType !== '01'){
        return true;
    }
    return false;
};

}]);
