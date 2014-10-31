var vsmApp = angular.module('vsmApp');

vsmApp.controller('DialogFormController', ['$scope','$http','modals', 'StockQuotesService', function ($scope, $http, modals, StockQuotesService) {
	
    $scope.formmodel = {};
    $scope.formcontrol = {};
    $scope.formmodel.symbol = '';
    $scope.formmodel.stockHoldingVolume = '';

    setTimeout(function(){
        $scope.formmodel.symbol = $scope.passValuesToDialog.tikerSymbol;
        $scope.formmodel.leagueUserId = $scope.passValuesToDialog.leagueUserId;
        $scope.formmodel.stockHoldingVolume = $scope.passValuesToDialog.volume;
    },1000);

   $scope.priceTypeMappingsSell = [
        {"name":"Market", "type":"01"},
        {"name":"Limit", "type":"02"},
        {"name":"StopLoss", "type":"03"}
    ];

    $scope.priceTypeMappings = [
        {"name":"Market", "type":"01"},
        {"name":"Limit", "type":"02"}
    ];

    $scope.intraDayOptions = [
        {"title":"Yes", "value":"Y"},
        {"title":"No", "value":"N"}
    ];

    $scope.formmodel.priceType = '01';

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
