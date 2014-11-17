var vsmApp = angular.module('vsmApp');

vsmApp.controller('DialogFormController', ['$scope','$http','modals', 'StockQuotesService','$timeout','ChartService',
    function ($scope, $http, modals, StockQuotesService, $timeout, ChartService) {
	
    $scope.formmodel = {};
    $scope.formcontrol = {};
    $scope.formmodel.symbol = '';
    $scope.formmodel.stockHoldingVolume = '';
    $scope.leagueStage = $scope.passValuesToDialog.leagueStage;
    $scope.$parentScope = $scope.passValuesToDialog.$parentScope;
    if($scope.leagueStage === "1")
    {
        $scope.priceTypeMappingsSell = [
            {"name":"Market", "type":"01"},
            {"name":"Limit", "type":"02"},
            {"name":"StopLoss", "type":"03"}
        ];
        $scope.priceTypeMappings = [
            {"name":"Market", "type":"01"},
            {"name":"Limit", "type":"02"}
        ];
        $scope.formmodel.priceType = '01';
    }
    else if($scope.leagueStage === "2")
    {
        $scope.priceTypeMappingsSell = [
            {"name":"Market", "type":"01"},
            {"name":"Limit", "type":"02"}
        ];
        $scope.priceTypeMappings = [
            {"name":"Market", "type":"01"},
            {"name":"Limit", "type":"02"}
        ];
        $scope.formmodel.priceType = '01';
    }
    else if($scope.leagueStage === "3")
    {
        $scope.priceTypeMappingsSell = [
            {"name":"Market", "type":"01"}
        ];
        $scope.priceTypeMappings = [
            {"name":"Market", "type":"01"}
        ];
        $scope.formmodel.priceType = '01';
    }
    else
    {
        $scope.priceTypeMappingsSell = [
            {"name":"Market", "type":"01"},
            {"name":"Limit", "type":"02"},
            {"name":"StopLoss", "type":"03"}
        ];
        $scope.priceTypeMappings = [
            {"name":"Market", "type":"01"},
            {"name":"Limit", "type":"02"}
        ];
        $scope.formmodel.priceType = '01';
    }

    setTimeout(function(){
        $scope.formmodel.symbol = $scope.passValuesToDialog.tikerSymbol;
        $scope.formmodel.leagueUserId = $scope.passValuesToDialog.leagueUserId;
        $scope.formmodel.stockHoldingVolume = $scope.passValuesToDialog.volume;
    },500);

   $scope.intraDayOptions = [
        {"title":"Yes", "value":"Y"},
        {"title":"No", "value":"N"}
    ];

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
                    $timeout(function(){
                        StockQuotesService.getMyRecentTrades($scope.formmodel.leagueUserId).then(function(data){
                            if($scope.leagueStage === "1")
                            {
                                $scope.$parentScope.myRecentTrades1  = data;
                                $scope.$parentScope.getRecentTradesDataGridOptions1.data = data.recentTrades;
                            }
                            else if($scope.leagueStage === "2")
                            {
                                $scope.$parentScope.myRecentTrades2  = data;
                                $scope.$parentScope.getRecentTradesDataGridOptions2.data = data.recentTrades;
                            }
                            else if($scope.leagueStage === "3")
                            {
                                $scope.$parentScope.myRecentTrades3  = data;
                                $scope.$parentScope.getRecentTradesDataGridOptions3.data = data.recentTrades;
                            }
                            else
                            {
                                $scope.$parentScope.myRecentTrades  = data;
                                $scope.$parentScope.getRecentTradesDataGridOptions.data = data.recentTrades;
                            }
                        });
                    }, 2000);
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

    StockQuotesService.getHistoricalStockLists($scope.passValuesToDialog.tikerSymbol).then(function(historicalData){
        ChartService.reloadSparklingCharts($scope.passValuesToDialog.tikerSymbol, historicalData, [33,40]);
    });

}]);
