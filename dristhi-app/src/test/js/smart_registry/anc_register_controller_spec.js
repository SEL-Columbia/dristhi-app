describe('ANC Register controller', function () {

    var controller, scope;

    beforeEach(module("smartRegistry.controllers"));
    beforeEach(module("smartRegistry.services"));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();
        controller = $controller("ancRegisterController", {
            $scope:scope
        });
    }));

    it("should load clients from service", function () {
        expect(scope.clients.length).toBeGreaterThan(0);
    });

});