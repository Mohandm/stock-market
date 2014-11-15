var vsmApp = angular.module('vsmApp');

vsmApp.service('DashboardService', ['$http', function ($http) {

    this.getStockListDashboardGridOptions = function(){
        var columnDefs;
        columnDefs = [
                      { field: 'tikerSymbol', sort: {
                          direction: 'asc',
                          priority: 1
                      	}, displayName:'Ticker Symbol',
                      	cellTemplate:'<a ng-click="getExternalScopes().openStockSummary(row.entity)" class="anchor">{{row.entity.tikerSymbol}}</a>'
                      },
                      { field: 'name', displayName:'Name',
                      	cellTemplate:'<a ng-click="getExternalScopes().openStockSummary(row.entity)" class="anchor">{{row.entity.name}}</a>'}
                  ];

        return {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: columnDefs
        };
    };

    this.getHistoryDataGridOptions = function(){
        return {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: [
                { field: 'stockDate', displayName:'Date'},
                { field: 'open', displayName:'Open'},
                { field: 'high', displayName:'High'},
                { field: 'low', displayName:'Low'},
                { field: 'close', displayName:'Close'}
            ]
        };
    };


}]);