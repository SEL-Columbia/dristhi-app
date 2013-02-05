function PNCList(pncListBridge, cssIdOf) {
    var listView;

    var searchCriteria = function (pnc, searchString) {
        return (pnc.womanName.toUpperCase().indexOf(searchString) == 0
            || pnc.ecNumber.toUpperCase().indexOf(searchString) == 0
            || pnc.thaayiCardNumber.toUpperCase().indexOf(searchString) == 0);
    }

    var villageFilterCriteria = function (pnc, appliedVillageFilter) {
        return pnc.villageName === appliedVillageFilter;
    }

    return {
        populateInto: function () {
            listView = new ListView(cssIdOf, Handlebars.templates.pnc_list, pncListBridge.getPNCs(), searchCriteria, villageFilterCriteria);

            var appliedVillageFilter = pncListBridge.getAppliedVillageFilter(listView.ALL_VILLAGES_FILTER_OPTION);
            listView.filterByVillage(appliedVillageFilter, appliedVillageFilter);
        },
        bindEveryItemToPNCView: function () {
            $(cssIdOf.rootElement).on("click", cssIdOf.everyListItem, function () {
                pncListBridge.delegateToPNCDetail($(this).data("caseid"));
            });
        },
        populateVillageFilter: function () {
            listView.populateVillageFilter(pncListBridge.getVillages());
        },
        bindToVillageFilter: function () {
            listView.bindVillageFilterOptions();
        },
        bindSearchEvents: function () {
            listView.bindSearchEvents();
        }
    };
}

function PNCListBridge() {
    var pncContext = window.context;
    if (typeof pncContext === "undefined" && typeof FakePNCListContext !== "undefined") {
        pncContext = new FakePNCListContext();
    }

    return {
        getPNCs: function () {
            return JSON.parse(pncContext.get());
        },
        delegateToPNCDetail: function (caseId) {
            return pncContext.startPNC(caseId);
        },
        getVillages: function () {
            return JSON.parse(pncContext.villages());
        },
        delegateToSaveAppliedVillageFilter: function (village) {
            return pncContext.saveAppliedVillageFilter(village);
        },
        getAppliedVillageFilter: function (defaultFilterValue) {
            return pncContext.appliedVillageFilter(defaultFilterValue);
        }
    };
}

function FakePNCListContext() {
    return {
        get: function () {
            return JSON.stringify({
                priority: [
                    {
                        caseId: "12345",
                        womanName: "Wife 1",
                        husbandName: "Husband 1",
                        thaayiCardNumber: "TC Number 1",
                        villageName: "chikkabheriya",
                        hasTodos: true,
                        ecNumber: "EC 1",
                        isHighRisk: true
                    },
                    {
                        caseId: "11111",
                        womanName: "Wife 2",
                        husbandName: "Husband 2",
                        thaayiCardNumber: "TC Number 2",
                        villageName: "munjanahalli",
                        ecNumber: "EC 2",
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
                        villageName: "chikkabheriya",
                        ecNumber: "EC 3",
                        hasTodos: true,
                        isHighRisk: false
                    },
                    {
                        caseId: "12355",
                        womanName: "Wife 5",
                        husbandName: "Husband 5",
                        thaayiCardNumber: "TC Number 5",
                        villageName: "munjanahalli",
                        ecNumber: "EC 4",
                        hasTodos: false,
                        isHighRisk: false
                    },
                    {
                        caseId: "11121",
                        womanName: "Wife 6",
                        husbandName: "Husband 6",
                        thaayiCardNumber: "TC Number 6",
                        ecNumber: "EC 5",
                        villageName: "chikkabheriya",
                        hasTodos: true,
                        isHighRisk: false
                    }
                ]
            });
        },
        startPNC: function (caseId) {
            window.location.href = "pnc_detail.html";
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
