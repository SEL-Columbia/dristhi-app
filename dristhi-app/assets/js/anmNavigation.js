function ANMNavigationPanel(anmNavigationBridge) {

    var populateDataInto = function (cssIdentifierOfRootElement, displayTemplate) {
        Handlebars.registerPartial("anm_navigation", Handlebars.templates.anm_navigation);
        $(cssIdentifierOfRootElement).html(displayTemplate(anmNavigationBridge.getANMInformation()));
    };

    var bindToWorkplan = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            anmNavigationBridge.delegateToWorkplan();
        });
    };

    var bindToReports = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            anmNavigationBridge.delegateToReports();
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

    var bindToChildList = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            anmNavigationBridge.delegateToChildList();
        });
    };

    var bindToLaunchForm = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function (e) {
            anmNavigationBridge.delegateToFormLaunchView($(e.currentTarget).data("formname"));
        });
    };

    var runWithCallBack = function (callbackToRunBeforeAnyAction, identifierOfElement, action) {
        $(identifierOfElement).click(function (e) {
            callbackToRunBeforeAnyAction();
            action(e);
        });
    };

    return {
        populateInto: function (cssIdentifierOfSidePanelElement, displayTemplate, callbackToRunBeforeAnyAction) {
            populateDataInto(cssIdentifierOfSidePanelElement, displayTemplate);
            bindToWorkplan(callbackToRunBeforeAnyAction, "#workplanButton");
            bindToReports(callbackToRunBeforeAnyAction, "#reportsButton");

            bindToEligibleCoupleList(callbackToRunBeforeAnyAction, "#eligibleCoupleMenuOption");
            bindToANCList(callbackToRunBeforeAnyAction, "#ancMenuOption");
            bindToPNCList(callbackToRunBeforeAnyAction, "#pncMenuOption");
            bindToChildList(callbackToRunBeforeAnyAction, "#childMenuOption");
            bindToLaunchForm(callbackToRunBeforeAnyAction, "#ecRegistration");
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
        delegateToChildList: function () {
            return anmNavigationContext.startChildList();
        },
        delegateToWorkplan: function () {
            return anmNavigationContext.startWorkplan();
        },
        delegateToReports: function () {
            return anmNavigationContext.startReports();
        },
        delegateToFormLaunchView: function (formName) {
            return anmNavigationContext.startFormActivity(formName);
        }
    };
}

function FakeANMNavigationContext() {
    return {
        get: function () {
            return JSON.stringify({
                anmName: "ANM X",
                pncCount: "4",
                ancCount: "5",
                childCount: "6",
                eligibleCoupleCount: "7"
            });
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
        startChildList: function () {
            window.location.href = "child_list.html";
        },
        startWorkplan: function () {
            window.location.href = "workplan.html";
        },
        startReports: function () {
            window.location.href = "reports.html";
        },
        startFormActivity: function (formName) {
            alert("Launching form: " + formName);
        }
    }
}
