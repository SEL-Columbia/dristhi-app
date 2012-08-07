function ECList(ecListBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.ec_list(ecListBridge.getECs()));
        },
        bindEveryItemToECView: function (cssIdentifierOfRootElement, cssIdentifierOfEveryListItem) {
            $(cssIdentifierOfRootElement).on("click", cssIdentifierOfEveryListItem, function (event) {
                ecListBridge.delegateToECDetail($(this).data("caseid"));
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
            var ec = JSON.parse(ecContext.get());
            return {"ec": ec};
        },
        delegateToECDetail: function (caseId) {
            return ecContext.startEC(caseId);
        }
    };
}

function FakeECListContext() {
    return {
        get: function() {
            return JSON.stringify([
                {
                    caseId: "12345",
                    wifeName: "Wife 1",
                    ecNumber: "EC Number 1",
                    village: "Village 1",
                    isHighRisk: true
                },
                {
                    caseId: "11111",
                    wifeName: "Wife 2",
                    ecNumber: "EC Number 2",
                    village: "Village 2",
                    isHighRisk: false
                }
            ]);
        },
        startEC: function(caseId) {
            window.location.href = "ec_detail.html";
        }
    }
}
