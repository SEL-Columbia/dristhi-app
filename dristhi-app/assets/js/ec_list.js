function ECList(ecListBridge) {
    var ecListRow = "ec";
    var showECsFromAllVillages = "All";
    var villageFilterOption = "village";
    var highPriorityContainer;
    var normalPriorityContainer;

    var showEcsAndUpdateCount = function (cssIdentifierOfEachRow) {
        var highPriorityECs = $(highPriorityContainer + " ." + cssIdentifierOfEachRow);
        var normalPriorityECs = $(normalPriorityContainer + " ." + cssIdentifierOfEachRow);

        highPriorityECs.show();
        normalPriorityECs.show();

        $(highPriorityContainer + " .count").text(highPriorityECs.length);
        $(normalPriorityContainer + " .count").text(normalPriorityECs.length);

    }

    return {
        populateInto: function (cssIdentifierOfRootElement) {
            var ecs = ecListBridge.getECs();
            highPriorityContainer = cssIdentifierOfRootElement + " #highPriorityContainer";
            normalPriorityContainer = cssIdentifierOfRootElement + " #normalPriorityContainer";

            $(highPriorityContainer + " .count").text(ecs.highPriority.length);
            $(normalPriorityContainer + " .count").text(ecs.normalPriority.length);
            $(highPriorityContainer).append(Handlebars.templates.ec_list(ecs.highPriority));
            $(normalPriorityContainer).append(Handlebars.templates.ec_list(ecs.normalPriority));
        },

        bindEveryItemToECView: function (cssIdentifierOfRootElement, cssIdentifierOfEveryListItem) {
            $(cssIdentifierOfRootElement).on("click", cssIdentifierOfEveryListItem, function (event) {
                ecListBridge.delegateToECDetail($(this).data("caseid"));
            });
        },
        bindItemToCommCare: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                ecListBridge.delegateToCommCare($(this).data("form"));
            })
        },
        populateVillageFilter: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).append(Handlebars.templates.filter_by_village(ecListBridge.getVillages()));
        },
        bindToVillageFilter: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                var text = 'Show: '+ $(this).text();
                $(this).closest('.dropdown').children('a.dropdown-toggle').text(text);
                if ($(this).data(villageFilterOption) === showECsFromAllVillages) {
                    showEcsAndUpdateCount(ecListBridge);
                    return;
                }
                $("." + ecListRow).hide();
                showEcsAndUpdateCount($(this).data(villageFilterOption));
            });
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
                        hasTodos: false
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
                        hasTodos: true

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
        }
    };
}
