var vsmApp = angular.module('vsmApp');

vsmApp.controller('AlertsController', ['$scope', '$rootScope', 'modals','AlertsService',
    function ($scope, $rootScope, modals, AlertsService) {

        $scope.$scope = $scope;

        //Add this to all page controllers
        $rootScope.onPageLoad();

        $scope.getAlertsDataGridOptions = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs:  [
                { field: 'message', displayName:'Alert',
                    cellTemplate:'<a ng-click="getExternalScopes().showAlert(row.entity)" class="anchor">{{row.entity.message}}</a>'},
                { field: 'notifiedDate', displayName:'Date'}
            ]
        };

        var alertsListPromise = AlertsService.getAlertsList();
        alertsListPromise.then(function(data){
            $scope.getAlertsDataGridOptions.data = data;
        });

        $scope.showAlert = function(item)
        {
            modals.showInfo('Alert Description',item.message);
        };
 }]);



