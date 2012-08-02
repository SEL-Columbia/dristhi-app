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
            return JSON.stringify([
                {
                    caseId: "12345",
                    womanName: "Wife 1",
                    thaayiCardNumber: "TC Number 1",
                    villageName: "Village 1",
                    isHighRisk: true
                },
                {
                    caseId: "11111",
                    womanName: "Wife 2",
                    thaayiCardNumber: "TC Number 2",
                    villageName: "Village 2",
                    isHighRisk: false
                }
            ]);
        },
        startANC: function(caseId) {
            alert('Should go to anc_detail for : ' + caseId);
//            window.location.href = "anc_detail.html";
        }
    }
}
