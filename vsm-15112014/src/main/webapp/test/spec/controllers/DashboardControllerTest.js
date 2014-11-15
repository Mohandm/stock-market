
describe('Unit: DashboardController', function() {
    // Load the module with MainController
    beforeEach(module('vsmApp'));

    var ctrl, scope;
    // inject the $controller and $rootScope services
    // in the beforeEach block
    beforeEach(inject(function($controller, $rootScope) {
        // Create a new scope that's a child of the $rootScope
        scope = $rootScope.$new();
        // Create the controller
        ctrl = $controller('DashboardController', {
            $scope: scope
        });
    }));

    it('should define options for the animated pie chart',function() {
        expect(scope.options).toBeDefined();
        expect(scope.options.animate).toEqual(3000);
    });
})
