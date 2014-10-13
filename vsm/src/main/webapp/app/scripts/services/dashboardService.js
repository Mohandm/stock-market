var vsmApp = angular.module('vsmApp');

vsmApp.service('DashboardService', ['$http', function ($http) {

    this.getStockListDashboardGridOptions = function(){
        var columnDefs;
        columnDefs = [
                      { field: 'Symbol', sort: {
                          direction: 'asc',
                          priority: 1
                      	}, displayName:'Ticker Symbol',
                      	cellTemplate:'<a ng-click="getExternalScopes().openStockSummary(row.entity)" class="anchor">{{row.entity.Symbol}}</a>'
                      },
                      { field: 'Name', displayName:'Name', 
                      	cellTemplate:'<a ng-click="getExternalScopes().openStockSummary(row.entity)" class="anchor">{{row.entity.Name}}</a>'}
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
                { field: 'Date', displayName:'Date'},
                { field: 'Open', displayName:'Open'},
                { field: 'Close', displayName:'Close'},
                { field: 'High', displayName:'High'},
                { field: 'Low', displayName:'Low'}
            ]
        };
    };


}]);