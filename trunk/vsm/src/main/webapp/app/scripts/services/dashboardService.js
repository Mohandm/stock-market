var vsmApp = angular.module('vsmApp');

vsmApp.service('DashboardService', ['$http', function ($http) {

    this.getStockListDashboardGridOptions = function(){
        var columnDefs;
        if($(window).width() < 500)
        {
            columnDefs = [
                { field: 'Symbol', sort: {
                    direction: 'asc',
                    priority: 1
                }, maxWidth: 100, displayName:'Ticker Symbol'},
                { field: 'last_sale', displayName:'Last Sale'}
            ];
        }
        else
        {
            columnDefs = [
                { field: 'Symbol', sort: {
                    direction: 'asc',
                    priority: 1
                }, displayName:'Ticker Symbol'},
                { field: 'Name', displayName:'Name'},
                { field: 'last_sale', displayName:'Last Sale'}
            ];
        }

        return {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: columnDefs
        };
    };

}]);