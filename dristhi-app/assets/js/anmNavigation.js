function ANMNavigationPanel(anmNavigationBridge) {
    var populateDataInto = function (cssIdentifierOfRootElement) {
        $(cssIdentifierOfRootElement).html(Handlebars.templates.sidepanel(anmNavigationBridge.getANMInformation()));
    };

    var bindToWorkplan = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            anmNavigationBridge.delegateToWorkplan();
        });
    };

    var bindToEligibleCoupleList = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            anmNavigationBridge.delegateToECList();
        });
    };

    var bindToANCList = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            anmNavigationBridge.delegateToANCList();
        });
    };

    var bindToPNCList = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            anmNavigationBridge.delegateToPNCList();
        });
    };

    var bindToPage = function (callbackToRunBeforeAnyAction, identifierOfElement, pageToGoTo) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            window.location.href = pageToGoTo;
        });
    };

    var runWithCallBack = function (callbackToRunBeforeAnyAction, identifierOfElement, action) {
        $(identifierOfElement).click(function () {
            callbackToRunBeforeAnyAction();
            action();
        });
    };

    return {
        populateInto: function (cssIdentifierOfSidePanelElement, callbackToRunBeforeAnyAction) {
            populateDataInto(cssIdentifierOfSidePanelElement);

            bindToWorkplan(callbackToRunBeforeAnyAction, "#workplanButton");
            bindToPage(callbackToRunBeforeAnyAction, "#myStatsButton", "my-stats.html");
            bindToPage(callbackToRunBeforeAnyAction, "#inboxButton", "inbox.html");
            bindToPage(callbackToRunBeforeAnyAction, "#reportsButton", "reports.html");

            bindToEligibleCoupleList(callbackToRunBeforeAnyAction, "#eligibleCoupleMenuOption");
            bindToANCList(callbackToRunBeforeAnyAction, "#ancMenuOption");
            bindToPNCList(callbackToRunBeforeAnyAction, "#pncMenuOption");
        }
    };
}

function ANMNavigationBridge() {
    var anmNavigationContext = window.navigationContext;
    if (typeof anmNavigationContext === "undefined" && typeof FakeANMNavigationContext !== "undefined") {
        anmNavigationContext = new FakeANMNavigationContext();
    }

    return {
        getANMInformation: function () {
            return JSON.parse(anmNavigationContext.get());
        },
        delegateToECList: function () {
            return anmNavigationContext.startECList();
        },
        delegateToANCList: function () {
            return anmNavigationContext.startANCList();
        },
        delegateToPNCList: function () {
            return anmNavigationContext.startPNCList();
        },
        delegateToWorkplan: function () {
            return anmNavigationContext.startWorkplan();
        }
    };
}

function FakeANMNavigationContext() {
    return {
        get: function () {
            return "{\"anmName\": \"ANM X\", \"pncCount\": \"4\", \"ancCount\": \"5\", \"childCount\": \"6\", \"eligibleCoupleCount\": \"7\"}";
        },
        startECList: function () {
            window.location.href = "ec_list.html";
        },
        startANCList: function () {
            window.location.href = "anc_list.html";
        },
        startPNCList: function () {
            window.location.href = "pnc_list.html";
        },
        startWorkplan: function () {
            window.location.href = "workplan.html";
        }
    }
}
