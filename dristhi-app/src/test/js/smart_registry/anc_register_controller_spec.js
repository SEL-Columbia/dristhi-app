describe('ANC Register controller', function () {

    var controller, scope;

    beforeEach(module("smartRegistry.controllers"));
    beforeEach(module("smartRegistry.services"));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();
        controller = $controller("ancRegisterController", {
            $scope: scope
        });
    }));

    it("should load clients from service", function () {
        expect(scope.clients.length).toBeGreaterThan(0);
    });

    it("should sum the number of tablets from ifa data", function () {
        var ifaData = {
            ifa1:{
                status: 'done',
                visit_date: '04/04',
                data:{
                    dose: 120
                }
            },
            next:{
                name: 'ifa2',
                status: 'upcoming',
                visit_date: null
            },
            'ifa2':{
                status: 'done',
                visit_date: '04/04',
                data:{
                    dose: 100
                }
            },
            previous: 'ifa1'
        };
        var tabletsGiven = scope.sumIFATablets(ifaData);
        expect(tabletsGiven).toEqual(220);
    });

    describe("defaultVillageFilterOptions", function () {
        it("should default village filter options", function () {
            var expectedDefaultVillageOptions = {
                type: "filterVillage",
                options: [
                    {
                        label: "All",
                        id: "",
                        handler: "filterByInAreaLocationStatus"
                    },
                    {
                        label: "O/A",
                        id: "out_of_area",
                        handler: "filterByLocationStatus"
                    },
                    {
                        label: "L/P",
                        id: "left_the_place",
                        handler: "filterByLocationStatus"
                    }
                ]
            };

            expect(JSON.stringify(scope.defaultVillageOptions)).toBe(JSON.stringify(expectedDefaultVillageOptions));
        });
    });

});