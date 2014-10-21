var vsmApp = angular.module('vsmApp');

vsmApp.controller('WatchListController', ['$scope', '$rootScope', 'StockQuotesService','modals','AlertsService',
    function ($scope, $rootScope, StockQuotesService, modals, AlertsService) {

    $scope.$scope = $scope;

    //Add this to all page controllers
    $rootScope.onPageLoad();

    $scope.stockListGridOptions = {
        enableSorting: true,
        enableFiltering: true,
        columnDefs:  [
            { field: 'tikerSymbol', displayName:'Ticker Symbol'},
            { field: 'name', displayName:'Name'},
            { field: 'lastTradePrice', displayName:'Price'},
            {name: 'watch', displayName: '', enableFiltering : false, enableSorting : false, cellTemplate: '<button id="watchBtn" type="button" class="btn-small" ng-click="getExternalScopes().addWatch(row.entity)" >Watch</button> '}
        ]
    };

    $scope.getAlertsDataGridOptions = {
        enableSorting: true,
        enableFiltering: true,
        columnDefs:  [
            { field: 'message', displayName:'Alert',
            cellTemplate:'<a ng-click="getExternalScopes().showAlert(row.entity)" class="anchor">{{row.entity.message}}</a>'},
            { field: 'notifiedDate', displayName:'Date'}
        ]
    };

    var stockListsPromise = StockQuotesService.getStockLists();
    stockListsPromise.then(function(data){
        $scope.stockLists = data;
    }).then(function(){
        /* All Quotes */
        var allCurrentQuotesPromise = StockQuotesService.getAllCurrentQuotes();
        allCurrentQuotesPromise.then(function(data){
            var quotes = {}
            $(data).each(function(index,item){
                quotes[item.stockMaster.stockId] = item;
            });

            var stockListData = [];
            $($scope.stockLists).each(function(index,item){
                stockListData.push({tikerSymbol : item.tikerSymbol, name : item.name, lastTradePrice : quotes[item.stockId].lastTradePriceOnly});
            });
            $scope.stockListGridOptions.data = stockListData;
        });
    });

    var alertsListPromise = AlertsService.getAlertsList();
    alertsListPromise.then(function(data){
        $scope.getAlertsDataGridOptions.data = data;
    });

    $scope.addWatch = function(item){
        modals.showForm('Watch Stocks','watchListDialog', item, "modal-lg");
    };

    $scope.showAlert = function(item)
    {
        modals.showInfo('Alert Description',item.message);
    };
}]);

vsmApp.controller('WatchListDialogController', ['$scope','$http','modals', function ($scope, $http, modals) {

    $scope.formmodel = {};
    $scope.formcontrol = {};
    $scope.formmodel.tikerSymbol = $scope.passValuesToDialog.tikerSymbol;
    $scope.formmodel.name = $scope.passValuesToDialog.name;
    $scope.formmodel.lastTradePrice = $scope.passValuesToDialog.lastTradePrice;

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
}]);

