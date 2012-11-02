function PNCList(pncListBridge, cssIdOf) {

    var ALL_VILLAGES_FILTER_OPTION = "All";
    var VILLAGE_FILTER_OPTION = "village";
    var allPNCs;

    var showPNCsAndUpdateCount = function (appliedVillageFilter) {
        var filteredHighRiskPNCs;
        var filteredNormalRiskPNCs;

        if (appliedVillageFilter === ALL_VILLAGES_FILTER_OPTION) {
            filteredHighRiskPNCs = allPNCs.highRisk;
            filteredNormalRiskPNCs = allPNCs.normalRisk;
        }
        else {
            filteredHighRiskPNCs = getPNCsBelongingToVillage(allPNCs.highRisk, appliedVillageFilter);
            filteredNormalRiskPNCs = getPNCsBelongingToVillage(allPNCs.normalRisk, appliedVillageFilter);
        }

        populatePNCs(filteredHighRiskPNCs, cssIdOf.highRiskContainer, cssIdOf.highRiskListContainer, cssIdOf.highRiskPNCsCount);
        populatePNCs(filteredNormalRiskPNCs, cssIdOf.normalRiskContainer, cssIdOf.normalRiskListContainer, cssIdOf.normalRiskPNCsCount);
    };

    var populatePNCs = function (pncs, cssIdOfListContainer, cssIdOfListItemsContainer, cssIdOfPNCsCount) {
        if (pncs.length === 0)
            $(cssIdOfListContainer).hide();
        else {
            $(cssIdOfListItemsContainer).html(Handlebars.templates.pnc_list(pncs));
            $(cssIdOfPNCsCount).text(pncs.length);
            $(cssIdOfListContainer).show();
        }
    };

    var getPNCsBelongingToVillage = function (pncs, village) {
        return jQuery.grep(pncs, function (pnc, index) {
            return pnc.villageName === village;
        });
    };

    var updateFilterIndicator = function (appliedFilter) {
        var text = "Show: " + appliedFilter;
        $(cssIdOf.appliedFilterIndicator).text(text);
    };

    var filterByVillage = function () {
        updateFilterIndicator($(this).text());

        var filterToApply = $(this).data(VILLAGE_FILTER_OPTION);
        showPNCsAndUpdateCount(filterToApply);
        pncListBridge.delegateToSaveAppliedVillageFilter(filterToApply);
    };

    return {
        populateInto:function () {
            allPNCs = pncListBridge.getPNCs();
            var appliedVillageFilter = pncListBridge.getAppliedVillageFilter(ALL_VILLAGES_FILTER_OPTION);
            showPNCsAndUpdateCount(appliedVillageFilter);
            updateFilterIndicator(formatText(appliedVillageFilter));
        },
        bindEveryItemToPNCView:function () {
            $(cssIdOf.rootElement).on("click", cssIdOf.everyListItem, function (event) {
                pncListBridge.delegateToPNCDetail($(this).data("caseid"));
            });
        },
        populateVillageFilter:function () {
            $(cssIdOf.villageFilter).html(Handlebars.templates.filter_by_village(pncListBridge.getVillages()));
        },
        bindToVillageFilter:function () {
            $(cssIdOf.villageFilterOptions).click(filterByVillage);
        }
    };
}

function PNCListBridge() {
    var pncContext = window.context;
    if (typeof pncContext === "undefined" && typeof FakePNCListContext !== "undefined") {
        pncContext = new FakePNCListContext();
    }

    return {
        getPNCs:function () {
            return JSON.parse(pncContext.get());
        },
        delegateToPNCDetail:function (caseId) {
            return pncContext.startPNC(caseId);
        },
        getVillages:function () {
            return JSON.parse(pncContext.villages());
        },
        delegateToSaveAppliedVillageFilter:function (village) {
            return pncContext.saveAppliedVillageFilter(village);
        },
        getAppliedVillageFilter:function (defaultFilterValue) {
            return pncContext.appliedVillageFilter(defaultFilterValue);
        }
    };
}

function FakePNCListContext() {
    return {
        get:function () {
            return JSON.stringify({
                highRisk:[
                    {
                        caseId:"12345",
                        womanName:"Wife 1",
                        husbandName:"Husband 1",
                        thaayiCardNumber:"TC Number 1",
                        villageName:"chikkabheriya",
                        hasTodos:true,
                        ecNumber:"EC 1",
                        isHighRisk:true
                    },
                    {
                        caseId:"11111",
                        womanName:"Wife 2",
                        husbandName:"Husband 2",
                        thaayiCardNumber:"TC Number 2",
                        villageName:"munjanahalli",
                        ecNumber:"EC 2",
                        hasTodos:false,
                        isHighRisk:true
                    }
                ],
                normalRisk:[
                    {
                        caseId:"12355",
                        womanName:"Wife 4",
                        husbandName:"Husband 4",
                        thaayiCardNumber:"TC Number 4",
                        villageName:"chikkabheriya",
                        ecNumber:"EC 3",
                        hasTodos:true,
                        isHighRisk:false
                    },
                    {
                        caseId:"12355",
                        womanName:"Wife 5",
                        husbandName:"Husband 5",
                        thaayiCardNumber:"TC Number 5",
                        villageName:"munjanahalli",
                        ecNumber:"EC 4",
                        hasTodos:false,
                        isHighRisk:false
                    },
                    {
                        caseId:"11121",
                        womanName:"Wife 6",
                        husbandName:"Husband 6",
                        thaayiCardNumber:"TC Number 6",
                        ecNumber:"EC 5",
                        villageName:"chikkabheriya",
                        hasTodos:true,
                        isHighRisk:false
                    }
                ]
            });
        },
        startPNC:function (caseId) {
            window.location.href = "pnc_detail.html";
        },
        villages:function () {
            return JSON.stringify(
                [
                    {name:"All"},
                    {name:"munjanahalli"},
                    {name:"chikkabheriya"}
                ]
            )
        },
        saveAppliedVillageFilter:function (village) {
        },
        appliedVillageFilter:function (defaultFilterValue) {
            return defaultFilterValue;
        }
    }
}
