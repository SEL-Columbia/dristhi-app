function ChildList(childListBridge, cssIdOf) {

    var ALL_VILLAGES_FILTER_OPTION = "All";
    var VILLAGE_FILTER_OPTION = "village";
    var allChildren;
    var appliedVillageFilter;

    var showChildrenAndUpdateCount = function (appliedVillageFilter) {
        var filteredHighRiskChildren;
        var filteredNormalRiskChildren;

        if (appliedVillageFilter === ALL_VILLAGES_FILTER_OPTION) {
            filteredHighRiskChildren = allChildren.highRisk;
            filteredNormalRiskChildren = allChildren.normalRisk;
        }
        else {
            filteredHighRiskChildren = getChildrenBelongingToVillage(allChildren.highRisk, appliedVillageFilter);
            filteredNormalRiskChildren = getChildrenBelongingToVillage(allChildren.normalRisk, appliedVillageFilter);
        }

        populateChildren(filteredHighRiskChildren, cssIdOf.highRiskContainer, cssIdOf.highRiskListContainer, cssIdOf.highRiskChildrenCount);
        populateChildren(filteredNormalRiskChildren, cssIdOf.normalRiskContainer, cssIdOf.normalRiskListContainer, cssIdOf.normalRiskChildrenCount);
    };

    var populateChildren = function (children, cssIdOfListContainer, cssIdOfListItemsContainer, idOfChildrenCount) {
        if (children.length === 0)
            $(cssIdOfListContainer).hide();
        else {
            $(cssIdOfListItemsContainer).html(Handlebars.templates.child_list(children));
            $(idOfChildrenCount).text(children.length);
            $(cssIdOfListContainer).show();
        }
    };

    var getChildrenBelongingToVillage = function (children, village) {
        return jQuery.grep(children, function (child, index) {
            return child.villageName === village;
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

        showChildrenAndUpdateCount(filterToApply);
        updateFilterIndicator($(this).text());
        appliedVillageFilter = filterToApply;
        childListBridge.delegateToSaveAppliedVillageFilter(filterToApply);
    };


    return {
        populateInto:function () {
            allChildren = childListBridge.getChildren();
            appliedVillageFilter = childListBridge.getAppliedVillageFilter(ALL_VILLAGES_FILTER_OPTION);
            showChildrenAndUpdateCount(appliedVillageFilter);
            updateFilterIndicator(formatText(appliedVillageFilter));
        },
        bindEveryItemToChildView:function () {
            $(cssIdOf.rootElement).on("click", cssIdOf.everyListItem, function (event) {
                childListBridge.delegateToChildDetail($(this).data("caseid"));
            });
        },
        populateVillageFilter:function () {
            $(cssIdOf.villageFilter).html(Handlebars.templates.filter_by_village(childListBridge.getVillages()));
        },
        bindToVillageFilter:function () {
            $(cssIdOf.villageFilterOptions).click(filterByVillage);
        }
    };
}

function ChildListBridge() {
    var childContext = window.context;
    if (typeof childContext === "undefined" && typeof FakeChildListContext !== "undefined") {
        childContext = new FakeChildListContext();
    }

    return {
        getChildren: function () {
            return JSON.parse(childContext.get());
        },
        delegateToChildDetail: function (caseId) {
            return childContext.startChild(caseId);
        },
        getVillages: function () {
            return JSON.parse(childContext.villages());
        },
        delegateToSaveAppliedVillageFilter:function (village) {
            return childContext.saveAppliedVillageFilter(village);
        },
        getAppliedVillageFilter:function (defaultFilterValue) {
            return childContext.appliedVillageFilter(defaultFilterValue);
        }
    };
}

function FakeChildListContext() {
    return {
        get: function () {
            return JSON.stringify({
                highRisk: [
                    {
                        caseId: "12345",
                        motherName: "Mother 1",
                        fatherName: "father 1",
                        thaayiCardNumber: "TC Number 1",
                        villageName: "chikkabheriya",
                        hasTodos: true,
                        ecNumber: "EC 1",
                        isHighRisk: true
                    },
                    {
                        caseId: "11111",
                        motherName: "Mother 2",
                        fatherName: "father 2",
                        thaayiCardNumber: "TC Number 2",
                        villageName: "munjanahalli",
                        ecNumber: "EC 2",
                        hasTodos: false,
                        isHighRisk: true
                    }
                ],
                normalRisk: [
                    {
                        caseId: "12355",
                        motherName: "Mother 4",
                        fatherName: "father 4",
                        thaayiCardNumber: "TC Number 4",
                        villageName: "chikkabheriya",
                        ecNumber: "EC 3",
                        hasTodos: true,
                        isHighRisk: false
                    },
                    {
                        caseId: "12355",
                        motherName: "Mother 5",
                        fatherName: "father 5",
                        thaayiCardNumber: "TC Number 5",
                        villageName: "munjanahalli",
                        ecNumber: "EC 4",
                        hasTodos: false,
                        isHighRisk: false
                    },
                    {
                        caseId: "11121",
                        motherName: "Mother 6",
                        fatherName: "father 6",
                        thaayiCardNumber: "TC Number 6",
                        ecNumber: "EC 5",
                        villageName: "chikkabheriya",
                        hasTodos: true,
                        isHighRisk: false
                    }
                ]
            });
        },
        startChild: function (caseId) {
            window.location.href = "child_detail.html";
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
        saveAppliedVillageFilter:function (village) {
        },
        appliedVillageFilter:function (defaultFilterValue) {
            return defaultFilterValue;
        }
    }
}
