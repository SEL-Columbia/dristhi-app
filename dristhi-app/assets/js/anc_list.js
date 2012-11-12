function ANCList(ancListBridge, cssIdOf) {
    var ALL_VILLAGES_FILTER_OPTION = "All";
    var VILLAGE_FILTER_OPTION = "village";
    var allANCs;
    var appliedVillageFilter;

    var showANCsAndUpdateCount = function (appliedVillageFilter) {
        var filteredHighRiskANCs;
        var filteredNormalRiskANCs;

        if (appliedVillageFilter === ALL_VILLAGES_FILTER_OPTION) {
            filteredHighRiskANCs = allANCs.highRisk;
            filteredNormalRiskANCs = allANCs.normalRisk;
        }
        else {
            filteredHighRiskANCs = getANCsBelongingToVillage(allANCs.highRisk, appliedVillageFilter);
            filteredNormalRiskANCs = getANCsBelongingToVillage(allANCs.normalRisk, appliedVillageFilter);
        }

        populateANCs(filteredHighRiskANCs, cssIdOf.highRiskContainer, cssIdOf.highRiskListContainer, cssIdOf.highRiskANCsCount);
        populateANCs(filteredNormalRiskANCs, cssIdOf.normalRiskContainer, cssIdOf.normalRiskListContainer, cssIdOf.normalRiskANCsCount);
    };

    var populateANCs = function (ancs, cssIdOfListContainer, cssIdOfListItemsContainer, cssIdOfANCsCount) {
        if (ancs.length === 0)
            $(cssIdOfListContainer).hide();
        else {
            $(cssIdOfListItemsContainer).html(Handlebars.templates.anc_list(ancs));
            $(cssIdOfANCsCount).text(ancs.length);
            $(cssIdOfListContainer).show();
        }
    };

    var getANCsBelongingToVillage = function (ancs, village) {
        return jQuery.grep(ancs, function (anc, index) {
            return anc.villageName === village;
        });
    };

    var updateFilterIndicator = function (appliedFilter) {
        var text = "Show: " + appliedFilter;
        $(cssIdOf.appliedFilterIndicator).text(text);
    };

    var filterByVillage = function () {
        var filterToApply = $(this).data(VILLAGE_FILTER_OPTION);
        if (filterToApply === appliedVillageFilter) {
            return;
        }

        showANCsAndUpdateCount(filterToApply);
        updateFilterIndicator($(this).text());
        appliedVillageFilter = filterToApply;
        ancListBridge.delegateToSaveAppliedVillageFilter(filterToApply);
    };


    return {
        populateInto: function () {
            allANCs = ancListBridge.getANCs();
            appliedVillageFilter = ancListBridge.getAppliedVillageFilter(ALL_VILLAGES_FILTER_OPTION);
            showANCsAndUpdateCount(appliedVillageFilter);
            updateFilterIndicator(formatText(appliedVillageFilter));
        },
        bindEveryItemToANCView: function () {
            $(cssIdOf.rootElement).on("click", cssIdOf.everyListItem, function (event) {
                ancListBridge.delegateToANCDetail($(this).data("caseid"));
            });
        },
        bindItemToCommCare: function () {
            $(cssIdOf.commCareItems).click(function () {
                ancListBridge.delegateToCommCare($(this).data("form"));
            })
        },
        populateVillageFilter: function () {
            $(cssIdOf.villageFilter).html(Handlebars.templates.filter_by_village(ancListBridge.getVillages()));
        },
        bindToVillageFilter: function () {
            $(cssIdOf.villageFilterOptions).click(filterByVillage);
        }
    };
}

function ANCListBridge() {
    var ancContext = window.context;
    if (typeof ancContext === "undefined" && typeof FakeANCListContext !== "undefined") {
        ancContext = new FakeANCListContext();
    }

    return {
        getANCs: function () {
            return JSON.parse(ancContext.get());
        },

        delegateToANCDetail: function (caseId) {
            return ancContext.startANC(caseId);
        },
        delegateToCommCare: function (formId) {
            ancContext.startCommCare(formId);
        },
        getVillages: function () {
            return JSON.parse(ancContext.villages());
        },
        delegateToSaveAppliedVillageFilter: function (village) {
            return ancContext.saveAppliedVillageFilter(village);
        },
        getAppliedVillageFilter: function (defaultFilterValue) {
            return ancContext.appliedVillageFilter(defaultFilterValue);
        }
    };
}

function FakeANCListContext() {
    return {
        get: function () {
            return JSON.stringify({
                highRisk: [
                    {
                        caseId: "12345",
                        womanName: "Wife 1",
                        husbandName: "Husband 1",
                        thaayiCardNumber: "TC Number 1",
                        ecNumber: "EC 1",
                        villageName: "chikkabheriya",
                        hasTodos: true,
                        isHighRisk: true
                    },
                    {
                        caseId: "11111",
                        womanName: "Wife 2",
                        husbandName: "Husband 2",
                        thaayiCardNumber: "TC Number 2",
                        ecNumber: "EC 2",
                        villageName: "munjanahalli",
                        hasTodos: false,
                        isHighRisk: true
                    }
                ],
                normalRisk: [
                    {
                        caseId: "12355",
                        womanName: "Wife 4",
                        husbandName: "Husband 4",
                        thaayiCardNumber: "TC Number 4",
                        ecNumber: "EC 3",
                        villageName: "chikkabheriya",
                        hasTodos: true,
                        isHighRisk: false
                    },
                    {
                        caseId: "12355",
                        womanName: "Wife 5",
                        husbandName: "Husband 5",
                        thaayiCardNumber: "TC Number 5",
                        ecNumber: "EC 4",
                        villageName: "munjanahalli",
                        hasTodos: false,
                        isHighRisk: false
                    },
                    {
                        caseId: "11121",
                        womanName: "Wife 6",
                        husbandName: "Husband 6",
                        thaayiCardNumber: "TC Number 6",
                        ecNumber: "EC 5",
                        villageName: "munjanahalli",
                        hasTodos: true,
                        isHighRisk: false
                    }
                ]
            });
        },
        startANC: function (caseId) {
            window.location.href = "anc_detail.html";
        },
        startCommCare: function (formId) {
            alert("Start CommCare with form " + formId);
        },
        villages: function () {
            return JSON.stringify(
                [
                    {name: "All"},
                    {name: "munjanahalli"},
                    {name: "chikkabheriya"}
                ]
            )
        },
        saveAppliedVillageFilter: function (village) {
        },
        appliedVillageFilter: function (defaultFilterValue) {
            return defaultFilterValue;
        }
    }
}
