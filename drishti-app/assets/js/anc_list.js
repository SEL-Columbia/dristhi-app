function ANCList(ancListBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.anc_list(ancListBridge.getANCs()));
        },
        bindEveryItemToANCView: function (cssIdentifierOfRootElement, cssIdentifierOfEveryListItem) {
            $(cssIdentifierOfRootElement).on("click", cssIdentifierOfEveryListItem, function (event) {
                ancListBridge.delegateToANCDetail($(this).data("caseid"));
            });
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
            var anc = JSON.parse(ancContext.get());
            return {"anc": anc};
        },
        delegateToANCDetail: function (caseId) {
            return ancContext.startANC(caseId);
        }
    };
}

function FakeANCListContext() {
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
                        hasTodos: true
                    },
                    {
                        caseId: "11111",
                        womanName: "Wife 2",
                        husbandName: "Husband 2",
                        thaayiCardNumber: "TC Number 2",
                        villageName: "Village 2",
                        hasTodos: false
                    }
                ],
                normalRisk: [
                    {
                        caseId: "12355",
                        womanName: "Wife 4",
                        husbandName: "Husband 4",
                        thaayiCardNumber: "TC Number 4",
                        villageName: "Village 1",
                        hasTodos: false
                    },
                    {
                        caseId: "11121",
                        womanName: "Wife 6",
                        husbandName: "Husband 6",
                        thaayiCardNumber: "TC Number 6",
                        villageName: "Village 2",
                        hasTodos: false
                    }
                ]
            });
        },
        startANC: function(caseId) {
            window.location.href = "anc_detail.html";
        }
    }
}
