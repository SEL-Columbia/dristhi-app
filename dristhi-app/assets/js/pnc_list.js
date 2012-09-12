function PNCList(pncListBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            var pncs = pncListBridge.getPNCs();
            var highRiskContainer = cssIdentifierOfRootElement + " #highRiskContainer";
            var normalRiskContainer = cssIdentifierOfRootElement + " #normalRiskContainer";

            $(highRiskContainer + " .count").text(pncs.highRisk.length);
            $(normalRiskContainer + " .count").text(pncs.normalRisk.length);
            $(highRiskContainer).append(Handlebars.templates.pnc_list(pncs.highRisk));
            $(normalRiskContainer).append(Handlebars.templates.pnc_list(pncs.normalRisk));
        },
        bindEveryItemToPNCView: function (cssIdentifierOfRootElement, cssIdentifierOfEveryListItem) {
            $(cssIdentifierOfRootElement).on("click", cssIdentifierOfEveryListItem, function (event) {
                pncListBridge.delegateToPNCDetail($(this).data("caseid"));
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
        }
    };
}

function FakePNCListContext() {
    return {
        get: function() {
            return JSON.stringify({
                highRisk: [
                    {
                        caseId: "12345",
                        womanName: "Wife 1",
                        husbandName: "Husband 1",
                        thaayiCardNumber: "TC Number 1",
                        villageName: "Village 1",
                        hasTodos: true,
                        isHighRisk: true
                    },
                    {
                        caseId: "11111",
                        womanName: "Wife 2",
                        husbandName: "Husband 2",
                        thaayiCardNumber: "TC Number 2",
                        villageName: "Village 2",
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
                        villageName: "Village 1",
                        hasTodos: true,
                        isHighRisk: false
                    },
					{
                        caseId: "12355",
                        womanName: "Wife 5",
                        husbandName: "Husband 5",
                        thaayiCardNumber: "TC Number 5",
                        villageName: "Village 1",
                        hasTodos: false,
                        isHighRisk: false
                    },
                    {
                        caseId: "11121",
                        womanName: "Wife 6",
                        husbandName: "Husband 6",
                        thaayiCardNumber: "TC Number 6",
                        villageName: "Village 2",
                        hasTodos: true,
                        isHighRisk: false
                    }
                ]
            });
        },
        startPNC: function(caseId) {
            window.location.href = "pnc_detail.html";
        }
    }
}
