describe('FP Register controller', function () {

    var controller, scope;

    beforeEach(module("smartRegistry.controllers"));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();
        controller = $controller("fpRegisterController", {
            $scope: scope
        });
    }));

    it("should close the FP modal once the click is handled", function () {
        scope.isFPModalOpen = true;

        scope.onFPModalOptionClick({
            label: "Condom",
            id: "condom",
            handler: "fpMethodFilter"
        }, "filterFPMethod");

        expect(scope.isFPModalOpen).toBeFalsy();
    });
});