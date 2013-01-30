function ECList(ecListBridge, cssIdOf) {
    var ALL_VILLAGES_FILTER_OPTION = "All";
    var VILLAGE_FILTER_OPTION = "village";
    var allECs;
    var appliedVillageFilter;

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

        populateECs(filteredHighPriorityECs, cssIdOf.highPriorityContainer, cssIdOf.highPriorityListContainer, cssIdOf.highPriorityECsCount);
        populateECs(filteredNormalPriorityECs, cssIdOf.normalPriorityContainer, cssIdOf.normalPriorityListContainer, cssIdOf.normalPriorityECsCount);
    };

    var populateECs = function (ecs, cssIdOfListContainer, cssIdOfListItemsContainer, idOfECsCount) {
        if (ecs.length === 0)
            $(cssIdOfListContainer).hide();
        else {
            $(cssIdOfListItemsContainer).html(Handlebars.templates.ec_list(ecs));
            $(idOfECsCount).text(ecs.length);
            $(cssIdOfListContainer).show();
        }
    };

    var getECsBelongingToVillage = function (ecs, village) {
        return jQuery.grep(ecs, function (ec, index) {
            return ec.villageName === village;
        });
    };

    var updateFilterIndicator = function (appliedFilter) {
        var text = "Show: " + appliedFilter;
        $(cssIdOf.appliedFilterIndicator).text(text);
    };

    var filterByVillage = function () {
        var filterToApply = $(this).data(VILLAGE_FILTER_OPTION);
        if (filterToApply === appliedVillageFilter)
            return;

        showECsAndUpdateCount(filterToApply);
        updateFilterIndicator($(this).text());
        appliedVillageFilter = filterToApply;
        ecListBridge.delegateToSaveAppliedVillageFilter(filterToApply);
    };

    var expandSearchBox = function () {
        $(cssIdOf.sidePanelButton).hide();
        $(cssIdOf.registerECButton).hide();
        $(cssIdOf.cancelSearchButton).show();
        $(cssIdOf.searchContainer).addClass(cssIdOf.expandedSearchContainerClass);
    };

    var cancelSearchBox = function () {
        $(cssIdOf.sidePanelButton).show();
        $(cssIdOf.cancelSearchButton).hide();
        $(cssIdOf.registerECButton).show();
        $(cssIdOf.searchContainer).removeClass(cssIdOf.expandedSearchContainerClass);
    };

    return {
        populateInto: function () {
            allECs = ecListBridge.getECs();
            var defaultOption = 0;
            if (ecListBridge.length > 1) {
                defaultOption = 1;
            }

            appliedVillageFilter = ecListBridge.getAppliedVillageFilter(ecListBridge.getVillages()[defaultOption].name);
            showECsAndUpdateCount(appliedVillageFilter);
            updateFilterIndicator(formatText(appliedVillageFilter));
        },
        bindEveryItemToECView: function () {
            $(cssIdOf.rootElement).on("click", cssIdOf.everyListItem, function (event) {
                ecListBridge.delegateToECDetail($(this).data("caseid"));
            });
        },
        bindItemToCommCare: function () {
            $(cssIdOf.commCareItems).click(function () {
                ecListBridge.delegateToCommCare($(this).data("form"));
            })
        },
        populateVillageFilter: function () {
            $(cssIdOf.villageFilter).html(Handlebars.templates.filter_by_village(ecListBridge.getVillages()));
        },
        bindToVillageFilter: function () {
            $(cssIdOf.villageFilterOptions).click(filterByVillage);
        },
        bindToSearchBox: function () {
            $(cssIdOf.searchBox).click(expandSearchBox);
            $(cssIdOf.cancelSearchButton).click(cancelSearchBox);
        }
    };
}

function ECListBridge() {
    var ecContext = window.context;
    if (typeof ecContext === "undefined" && typeof FakeECListContext !== "undefined") {
        ecContext = new FakeECListContext();
    }

    return {
        getECs: function () {
            return JSON.parse(ecContext.get());
        },
        delegateToECDetail: function (caseId) {
            return ecContext.startEC(caseId);
        },
        delegateToCommCare: function (formId) {
            ecContext.startCommCare(formId);
        },
        getVillages: function () {
            return JSON.parse(ecContext.villages());
        },
        delegateToSaveAppliedVillageFilter: function (village) {
            return ecContext.saveAppliedVillageFilter(village);
        },
        getAppliedVillageFilter: function (defaultFilterValue) {
            return ecContext.appliedVillageFilter(defaultFilterValue);
        }
    };
}

function FakeECListContext() {
    return {
        get: function () {
            return JSON.stringify({
                highPriority: [
                    {
                        caseId: "12345",
                        wifeName: "Wife 1",
                        husbandName: "Husband 1",
                        ecNumber: "EC Number 1",
                        villageName: "munjanahalli",
                        isHighPriority: true,
                        hasTodos: false,
                        thayiCardNumber: ""
                    }
                ],
                normalPriority: [
                    {
                        caseId: "11111",
                        wifeName: "Wife 2",
                        husbandName: "Husband 2",
                        ecNumber: "EC Number 2",
                        villageName: "chikkabheriya",
                        isHighPriority: false,
                        hasTodos: true,
                        thayiCardNumber: "12345"

                    }
                ]
            });
        },
        startEC: function (caseId) {
            window.location.href = "ec_detail.html";
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
    };
}
