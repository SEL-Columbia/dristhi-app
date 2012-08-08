function PNCList(pncListBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.pnc_list(pncListBridge.getPNCs()));
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
            var pnc = JSON.parse(pncContext.get());
            return {"pnc": pnc};
        },
        delegateToPNCDetail: function (caseId) {
            return pncContext.startPNC(caseId);
        }
    };
}

function FakePNCListContext() {
    return {
        get: function() {
            return JSON.stringify([
                {
                    caseId: "12345",
                    womanName: "PNC 1",
                    thaayiCardNumber: "TC Number 1",
                    villageName: "Village 1",
                    isHighRisk: true
                },
                {
                    caseId: "11111",
                    womanName: "PNC 2",
                    thaayiCardNumber: "TC Number 2",
                    villageName: "Village 2",
                    isHighRisk: false
                }
            ]);
        },
        startPNC: function(caseId) {
            window.location.href = "pnc_detail.html";
        }
    }
}
