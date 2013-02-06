function ANCList(ancListBridge, cssIdOf) {
    var listView;

    var searchCriteria = function (anc, searchString) {
        return (anc.womanName.toUpperCase().indexOf(searchString) == 0
            || anc.ecNumber.toUpperCase().indexOf(searchString) == 0
            || anc.thaayiCardNumber.toUpperCase().indexOf(searchString) == 0);
    }

    var villageFilterCriteria = function (anc, appliedVillageFilter) {
        return anc.villageName === appliedVillageFilter;
    }

    return {
        populateInto: function () {
            listView = new ListView(cssIdOf, Handlebars.templates.anc_list, ancListBridge.getANCs(), searchCriteria, villageFilterCriteria);

            var appliedVillageFilter = ancListBridge.getAppliedVillageFilter(listView.ALL_VILLAGES_FILTER_OPTION);
            listView.filterByVillage(appliedVillageFilter, appliedVillageFilter);
        },
        bindEveryItemToANCView: function () {
            $(cssIdOf.rootElement).on("click", cssIdOf.everyListItem, function () {
                ancListBridge.delegateToANCDetail($(this).data("caseid"));
            });
        },
        bindItemToCommCare: function () {
            $(cssIdOf.commCareItems).click(function () {
                ancListBridge.delegateToCommCare($(this).data("form"));
            })
        },
        populateVillageFilter: function () {
            listView.populateVillageFilter(ancListBridge.getVillages());
        },
        bindVillageFilterOptions: function () {
            listView.bindVillageFilterOptions();
        },
        bindSearchEvents: function () {
            listView.bindSearchEvents();
        },
        bindLoadAll: function () {
            listView.bindLoadAll();
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
                priority: [
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
                normal: [
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
