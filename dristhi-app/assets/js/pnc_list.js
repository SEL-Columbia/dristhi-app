function PNCList(pncListBridge) {
    var pncListRow = "pnc";
    var showPNCsFromAllVillages = "All";
    var villageFilterOption = "village";
    var highRiskContainer;
    var normalRiskContainer;

    var showPNCsAndUpdateCount = function (cssIdentifierOfEachRow) {
        var highRiskPNCs = $(highRiskContainer + " ." + cssIdentifierOfEachRow);
        var normalRiskPNCs = $(normalRiskContainer + " ." + cssIdentifierOfEachRow);

        highRiskPNCs.show();
        normalRiskPNCs.show();

        $(highRiskContainer + " .count").text(highRiskPNCs.length);
        $(normalRiskContainer + " .count").text(normalRiskPNCs.length);

    }

    var populatePNCs = function (pncs, container) {
        if(pncs.length == 0)
            $(container).hide();
        else
        {
            $(container + " .count").text(pncs.length);
            $(container).append(Handlebars.templates.pnc_list(pncs));
        }
    }


    return {
        populateInto: function (cssIdentifierOfRootElement) {
            var pncs = pncListBridge.getPNCs();
            highRiskContainer = cssIdentifierOfRootElement + " #highRiskContainer";
            normalRiskContainer = cssIdentifierOfRootElement + " #normalRiskContainer";

            populatePNCs(pncs.highRisk, highRiskContainer);
            populatePNCs(pncs.normalRisk, normalRiskContainer);
        },
        bindEveryItemToPNCView: function (cssIdentifierOfRootElement, cssIdentifierOfEveryListItem) {
            $(cssIdentifierOfRootElement).on("click", cssIdentifierOfEveryListItem, function (event) {
                pncListBridge.delegateToPNCDetail($(this).data("caseid"));
            });
        },
        populateVillageFilter: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).append(Handlebars.templates.filter_by_village(pncListBridge.getVillages()));
        },
        bindToVillageFilter: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                var text = 'Show: ' + $(this).text();
                $(this).closest('.dropdown').children('a.dropdown-toggle').text(text);
                if ($(this).data(villageFilterOption) === showPNCsFromAllVillages) {
                    showPNCsAndUpdateCount(pncListRow);
                    return;
                }
                $("." + pncListRow).hide();
                showPNCsAndUpdateCount($(this).data(villageFilterOption));
            });
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
        }
    };
}

function FakePNCListContext() {
    return {
        get: function () {
            return JSON.stringify({
                highRisk: [
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
                normalRisk: [
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
        }
    }
}
