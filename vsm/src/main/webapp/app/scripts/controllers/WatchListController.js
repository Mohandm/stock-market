var vsmApp = angular.module('vsmApp');

vsmApp.controller('WatchListController', ['$scope', '$rootScope', 'StockQuotesService','modals','AlertsService','TourService',
    function ($scope, $rootScope, StockQuotesService, modals, AlertsService, TourService) {

    $scope.$scope = $scope;

    //Add this to all page controllers
    $rootScope.onPageLoad();
    TourService.setupTour('watchList');

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

    var stockListsPromise = StockQuotesService.getStockLists();
    stockListsPromise.then(function(data){
        $scope.stockLists = data;
    }).then(function(){
        /* All Quotes */
        var allCurrentQuotesPromise = StockQuotesService.getAllCurrentQuotes();
        allCurrentQuotesPromise.then(function(data){
            var quotes = {}
            $(data).each(function(index,item){
                quotes[item.stockId] = item;
            });

            var stockListData = [];
            $($scope.stockLists).each(function(index,item){
                stockListData.push({tikerSymbol : item.tikerSymbol, name : item.name, lastTradePrice : quotes[item.stockId].lastTradePriceOnly});
            });
            $scope.stockListGridOptions.data = stockListData;
        });
    });

    $scope.addWatch = function(item){
        modals.showForm('Watch Stocks','watchListDialog', item, "modal-lg");
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

