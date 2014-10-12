var vsmApp = angular.module('vsmApp');

vsmApp.service('DashboardService', ['$http', function ($http) {

    this.getStockListDashboardGridOptions = function(){
        var columnDefs;
        columnDefs = [
                      { field: 'Symbol', sort: {
                          direction: 'asc',
                          priority: 1
                      	}, displayName:'Ticker Symbol',
                      	cellTemplate:'<div><a ng-click="getExternalScopes().openStockSummary(row.entity)" class="anchor">{{row.entity.Symbol}}</a></div>'
                      },
                      { field: 'Name', displayName:'Name', 
                      	cellTemplate:'<div><a ng-click="getExternalScopes().openStockSummary(row.entity)" class="anchor">{{row.entity.Name}}</a></div>'}
                  ];

        return {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: columnDefs
        };
    };

}]);