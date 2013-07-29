describe("List view controller", function () {
    var controller, scope, bridge = new FPRegistryBridge();

    beforeEach(module("smartRegistry.controllers"));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();
        scope.bridge = bridge;
        scope.defaultVillageOptions = {
            type: "filterVillage",
            options: [
                {
                    label: "All",
                    id: "",
                    handler: ""
                }
            ]
        };
        scope.defaultVillageFilterHandler = "defaultFilterByVillageName";
        controller = $controller("listViewController", {
            $scope: scope
        });
    }));

    it("should default the number of clients to show.", function () {
        expect(scope.numberOfClientsToShow).toBe(10);
    });

    describe("Reporting period", function () {
        it("should start on the 26th of the previous month if the specified day is on or before the 25th", function () {
            var date_str = '2012-02-25';
            expect(Date.parse(scope.reportingPeriodStart(date_str))).toEqual(Date.parse("2012-01-26"));
        });

        it("should end on the 25th of the current month if the day is on or before the 25th", function () {
            var date_str = '2012-02-25';
            expect(Date.parse(scope.reportingPeriodEnd(date_str))).toEqual(Date.parse("2012-02-25"));
        });

        it("should start on the 26th of the current month if the specified day is after the 25th", function () {
            var date_str = '2012-02-26';
            expect(Date.parse(scope.reportingPeriodStart(date_str))).toEqual(Date.parse("2012-02-26"));
        });

        it("should end on the 25th of the next month if the day is after the 25th", function () {
            var date_str = '2012-02-26';
            expect(Date.parse(scope.reportingPeriodEnd(date_str))).toEqual(Date.parse("2012-03-25"));
        });

        it("should end in Jan of the next year if the next month is january", function () {
            var date_str = '2012-12-26';
            expect(Date.parse(scope.reportingPeriodEnd(date_str))).toEqual(Date.parse("2013-01-25"));
        });
    });

    describe("sort", function () {
        it("should set currentSortOption to the selected one.", function () {
            var sortOption = {
                label: "Name (A to Z)",
                handler: "sortByName"
            };
            scope.sort(sortOption);

            expect(scope.currentSortOption).toBe(sortOption);
        });

        it("should set sort list handler based on the selected sort option.", function () {
            var sortOption = {
                label: "Name (A to Z)",
                handler: "sortByName"
            };
            scope.sort(sortOption);

            expect(scope.sortList).toBe(scope.sortByName);
        });

        it("should set sort order based on the selected sort option.", function () {
            var descendingSortOption = {
                label: "Name (A to Z)",
                handler: "sortByPriority",
                sortDescending: true
            };
            var ascendingSortOption = {
                label: "HP",
                handler: "sortByName",
                sortDescending: false
            };
            scope.sort(descendingSortOption);
            expect(scope.sortDescending).toBeTruthy();

            scope.sort(ascendingSortOption);
            expect(scope.sortDescending).toBeFalsy();
        });
    });

    describe("sortByName", function () {
        it("should sort by name field.", function () {
            expect(scope.sortByName({name: "name1"})).toBe("name1");
        });
    });

    describe("sortByPriority", function () {
        it("should sort clients by high priority.", function () {
            var client = {
                name: "client1",
                isHighPriority: true
            };

            var sort = scope.sortByPriority(client);

            expect(sort).toBeFalsy();

            sort = scope.sortByPriority({
                name: "client1",
                isHighPriority: false
            });

            expect(sort).toBeTruthy();
        });
    });

    describe("filterVillage", function () {
        it("should set villageFilterOption to selected one.", function () {
            var option = {};

            scope.filterVillage(option);

            expect(scope.villageFilterOption).toBe(option);
        });
    });

    describe("filterList", function () {
        it("should allow a client when it passes applied search criteria.", function () {
            scope.searchCriteria = function () {
                return true;
            };
            scope.searchFilterString = "foo";
            scope.villageFilterOption = {};
            scope.serviceModeOption = {};
            var client = {};

            expect(scope.filterList(client)).toBeTruthy();
        });

        it("should filter client when it does not pass applied search criteria.", function () {
            scope.searchCriteria = function () {
                return false;
            };
            scope.searchFilterString = "foo";
            scope.villageFilterOption = {};
            scope.serviceModeOption = {};
            var client = {};

            expect(scope.filterList(client)).toBeFalsy();
        });

        it("should allow a client when it passes applied village filter.", function () {
            scope.villageFilterHandler = function () {
                return true;
            };
            scope.villageFilterOption = {handler: "villageFilterHandler"};
            scope.searchFilterString = null;
            scope.serviceModeOption = {};
            var client = {};

            expect(scope.filterList(client)).toBeTruthy();
        });

        it("should filter client when it does not pass applied village filter.", function () {
            scope.villageFilterHandler = function () {
                return false;
            };
            scope.villageFilterOption = {handler: "villageFilterHandler"};
            scope.searchFilterString = null;
            scope.serviceModeOption = {};
            var client = {};

            expect(scope.filterList(client)).toBeFalsy();
        });

        it("should allow a client when it passes applied service mode filter.", function () {
            scope.serviceModeHandler = function () {
                return true;
            };
            scope.villageFilterOption = {};
            scope.searchFilterString = null;
            scope.serviceModeOption = {handler: "serviceModeHandler"};
            var client = {};

            expect(scope.filterList(client)).toBeTruthy();
        });

        it("should filter client when it does not pass applied village filter.", function () {
            scope.serviceModeHandler = function () {
                return false;
            };
            scope.villageFilterOption = {};
            scope.searchFilterString = null;
            scope.serviceModeOption = {handler: "serviceModeHandler"};
            var client = {};

            expect(scope.filterList(client)).toBeFalsy();
        });

        it("should allow only those clients that pass all the applied filters.", function () {
            scope.searchCriteria = function (client, searchCriteria) {
                return client.name === searchCriteria;
            };
            scope.villageFilterHandler = function () {
                return true;
            };
            scope.serviceModeHandler = function () {
                return true;
            };
            scope.searchFilterString = "foo";
            scope.villageFilterOption = {handler: "villageFilterHandler"};
            scope.serviceModeOption = {handler: "serviceModeHandler"};
            var client1 = {name: "foo"};
            var client2 = {name: "not foo"};

            expect(scope.filterList(client1)).toBeTruthy();
            expect(scope.filterList(client2)).toBeFalsy();
        });
    });

    describe("allClientsDisplayed", function () {
        it("should return true if all clients are displayed.", function () {
            scope.numberOfClientsToShow = 2;
            scope.filteredClients = [
                {name: "client1"},
                {name: "client2"}
            ];

            expect(scope.allClientsDisplayed(scope.filteredClients)).toBeTruthy();
        });
    });

    describe("addVillageNamesToFilterOptions", function () {
        it("should add filter options for every village.", function () {
            var expectedVillageOptions = {
                type: "filterVillage",
                options: [
                    {
                        label: "All",
                        id: "",
                        handler: ""
                    },
                    {
                        label: "Village 1",
                        id: "village_1",
                        handler: "defaultFilterByVillageName"
                    },
                    {
                        label: "Village2",
                        id: "village2",
                        handler: "defaultFilterByVillageName"
                    }
                ]
            };
            scope.villageBridge = new VillageBridge();
            spyOn(scope.villageBridge, "getVillages").andReturn([
                {name: "village_1"},
                {name: "village2"}
            ]);

            var villageFilterOptions = scope.getVillageFilterOptions();
            expect(villageFilterOptions).toEqual(expectedVillageOptions);
        });
    });

    describe("search", function () {
        it("should not be in search mode when user has not tapped on search box", function () {
            expect(scope.inSearchMode).toBeFalsy();
        });

        it("should be in search mode when user taps on search box", function () {
            scope.enterSearchMode();

            expect(scope.inSearchMode).toBeTruthy();
        });

        it("should clear search string when search is cancelled", function () {
            scope.enterSearchMode();
            scope.searchFilterString = "criteria";

            scope.cancelSearch();

            expect(scope.searchFilterString).toBe("");
            expect(scope.inSearchMode).toBeFalsy();
        });

        it("should enter search mode", function () {
            scope.enterSearchMode();

            expect(scope.inSearchMode).toBeTruthy();
        });
    });

    describe("sort by BPL", function () {
        it("should sort clients by BPL.", inject(function ($filter) {
            var clients = [
                {
                    name: "client1",
                    economicStatus: ""
                },
                {
                    name: "client2",
                    economicStatus: "bpl"
                }
            ];

            var filterFunc = scope.sortByBPL;
            var filteredClients = $filter('orderBy')(clients, filterFunc);
            expect(filteredClients[0]).toEqual(clients[1]);
            expect(filteredClients[1]).toEqual(clients[0]);
        }));
    });

    describe("sort by SC", function () {
        it("should sort clients by SC.", inject(function ($filter) {
            var clients = [
                {
                    name: "client1",
                    caste: "st"
                },
                {
                    name: "client2",
                    caste: "sc"
                }
            ];

            var filterFunc = scope.sortBySC;
            var filteredClients = $filter('orderBy')(clients, filterFunc);
            expect(filteredClients[0]).toEqual(clients[1]);
            expect(filteredClients[1]).toEqual(clients[0]);
        }));
    });

    describe("sort by BPL", function () {
        it("should sort clients by BPL.", inject(function ($filter) {
            var clients = [
                {
                    name: "client1",
                    caste: "sc"
                },
                {
                    name: "client2",
                    caste: "st"
                }
            ];

            var filterFunc = scope.sortByST;
            var filteredClients = $filter('orderBy')(clients, filterFunc);
            expect(filteredClients[0]).toEqual(clients[1]);
            expect(filteredClients[1]).toEqual(clients[0]);
        }));
    });
});
