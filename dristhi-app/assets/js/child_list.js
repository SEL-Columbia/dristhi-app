function ChildList(childListBridge) {
    var childListRow = "child";
    var showChildrenFromAllVillages = "All";
    var villageFilterOption = "village";
    var highRiskContainer;
    var normalRiskContainer;

    var showChildrenAndUpdateCount = function (cssIdentifierOfEachRow) {
        var highRiskChildren = $(highRiskContainer + " ." + cssIdentifierOfEachRow);
        var normalRiskChildren = $(normalRiskContainer + " ." + cssIdentifierOfEachRow);

        highRiskChildren.show();
        normalRiskChildren.show();

        $(highRiskContainer + " .count").text(highRiskChildren.length);
        $(normalRiskContainer + " .count").text(normalRiskChildren.length);
    };

    var populateChildren = function (children, container) {
        if(children.length == 0)
            $(container).hide();
        else
        {
            $(container + " .count").text(children.length);
            $(container).append(Handlebars.templates.child_list(children));
        }
    };


    return {
        populateInto: function (cssIdentifierOfRootElement) {
            var children = childListBridge.getChildren();
            highRiskContainer = cssIdentifierOfRootElement + " #highRiskContainer";
            normalRiskContainer = cssIdentifierOfRootElement + " #normalRiskContainer";

            populateChildren(children.highRisk, highRiskContainer);
            populateChildren(children.normalRisk, normalRiskContainer);
        },
        bindEveryItemToChildView: function (cssIdentifierOfRootElement, cssIdentifierOfEveryListItem) {
            $(cssIdentifierOfRootElement).on("click", cssIdentifierOfEveryListItem, function () {
                childListBridge.delegateToChildDetail($(this).data("caseid"));
            });
        },
        populateVillageFilter: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).append(Handlebars.templates.filter_by_village(childListBridge.getVillages()));
        },
        bindToVillageFilter: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                var text = 'Show: ' + $(this).text();
                $(this).closest('.dropdown').children('a.dropdown-toggle').text(text);
                if ($(this).data(villageFilterOption) === showChildrenFromAllVillages) {
                    showChildrenAndUpdateCount(childListRow);
                    return;
                }
                $("." + childListRow).hide();
                showChildrenAndUpdateCount($(this).data(villageFilterOption));
            });
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
        }
    }
}
