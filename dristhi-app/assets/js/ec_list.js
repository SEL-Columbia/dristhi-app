function ECList(ecListBridge, cssIdOf) {
    var ALL_VILLAGES_FILTER_OPTION = "All";
    var VILLAGE_FILTER_OPTION = "village";

    var highPriorityContainer;
    var highPriorityListContainer;
    var normalPriorityContainer;
    var normalPriorityListContainer;
    var allECs;

    var showECsAndUpdateCount = function (appliedVillageFilter) {
        var filteredHighPriorityECs;
        var filteredNormalPriorityECs;

        if (appliedVillageFilter === ALL_VILLAGES_FILTER_OPTION) {
            filteredHighPriorityECs = allECs.highPriority;
            filteredNormalPriorityECs = allECs.normalPriority;
        }
        else {
            filteredHighPriorityECs = getECsBelongingToVillage(allECs.highPriority, appliedVillageFilter);
            filteredNormalPriorityECs = getECsBelongingToVillage(allECs.normalPriority, appliedVillageFilter);
        }

        populateECs(filteredHighPriorityECs, highPriorityContainer, highPriorityListContainer);
        populateECs(filteredNormalPriorityECs, normalPriorityContainer, normalPriorityListContainer);
    }

    var populateECs = function (ecs, listContainer, listItemsContainer) {
        if (ecs.length === 0)
            $(listContainer).hide();
        else {
            $(listItemsContainer).html(Handlebars.templates.ec_list(ecs));
            $(listContainer + " " + cssIdOf.ecCount).text(ecs.length);
            $(listContainer).show();
        }
    }

    var getECsBelongingToVillage = function (ecs, village) {
        return jQuery.grep(ecs, function (ec, index) {
            return ec.villageName === village;
        });
    }

    var updateFilterIndicator = function (appliedFilter) {
        var text = "Show: " + appliedFilter;
        $(cssIdOf.appliedFilterIndicator).text(text);
    }

    var filterByVillage = function (filteredBy) {
        updateFilterIndicator($(this).text());

        var filterToApply = $(this).data(VILLAGE_FILTER_OPTION);
        showECsAndUpdateCount(filterToApply);
        ecListBridge.delegateToSaveAppliedVillageFilter(filterToApply);
    }

    return {
        populateInto:function () {
            highPriorityContainer = cssIdOf.rootElement + " " + cssIdOf.highPriorityContainer;
            highPriorityListContainer = highPriorityContainer + " " + cssIdOf.highPriorityListContainer;
            normalPriorityContainer = cssIdOf.rootElement + " " + cssIdOf.normalPriorityContainer;
            normalPriorityListContainer = normalPriorityContainer + " " + cssIdOf.normalPriorityListContainer;

            allECs = ecListBridge.getECs();
            var appliedVillageFilter = ecListBridge.getAppliedVillageFilter(ecListBridge.getVillages()[1].name);
            showECsAndUpdateCount(appliedVillageFilter);
            updateFilterIndicator(capitalize(appliedVillageFilter));
        },

        bindEveryItemToECView:function () {
            $(cssIdOf.rootElement).on("click", cssIdOf.everyListItem, function (event) {
                ecListBridge.delegateToECDetail($(this).data("caseid"));
            });
        },
        bindItemToCommCare:function () {
            $(cssIdOf.commCareItems).click(function () {
                ecListBridge.delegateToCommCare($(this).data("form"));
            })
        },
        populateVillageFilter:function () {
            $(cssIdOf.villageFilter).append(Handlebars.templates.filter_by_village(ecListBridge.getVillages()));
        },
        bindToVillageFilter:function () {
            $(cssIdOf.villageFilterOptions).click(filterByVillage);
        }
    };
}

function ECListBridge() {
    var ecContext = window.context;
    if (typeof ecContext === "undefined" && typeof FakeECListContext !== "undefined") {
        ecContext = new FakeECListContext();
    }

    return {
        getECs:function () {
            return JSON.parse(ecContext.get());
        },
        delegateToECDetail:function (caseId) {
            return ecContext.startEC(caseId);
        },
        delegateToCommCare:function (formId) {
            ecContext.startCommCare(formId);
        },
        getVillages:function () {
            return JSON.parse(ecContext.villages());
        },
        delegateToSaveAppliedVillageFilter:function (village) {
            return ecContext.saveAppliedVillageFilter(village);
        },
        getAppliedVillageFilter:function (defaultFilterValue) {
            return ecContext.appliedVillageFilter(defaultFilterValue);
        }
    };
}

function FakeECListContext() {
    return {
        get:function () {
            return JSON.stringify({
                highPriority:[
                    {
                        caseId:"12345",
                        wifeName:"Wife 1",
                        husbandName:"Husband 1",
                        ecNumber:"EC Number 1",
                        villageName:"munjanahalli",
                        isHighPriority:true,
                        hasTodos:false,
                        thayiCardNumber:""
                    }
                ],
                normalPriority:[
                    {
                        caseId:"11111",
                        wifeName:"Wife 2",
                        husbandName:"Husband 2",
                        ecNumber:"EC Number 2",
                        villageName:"chikkabheriya",
                        isHighPriority:false,
                        hasTodos:true,
                        thayiCardNumber:"12345"

                    }
                ]
            });
        },
        startEC:function (caseId) {
            window.location.href = "ec_detail.html";
        },
        startCommCare:function (formId) {
            alert("Start CommCare with form " + formId);
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
    };
}
