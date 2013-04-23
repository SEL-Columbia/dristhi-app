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

    var bindToFPSmartRegistry = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            anmNavigationBridge.delegateToFPSmartRegistry();
        });
    };

    var bindToANCSmartRegistry = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            window.location = "smart_registry/anc_register.html";
        });
    };

    var bindToLaunchForm = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function (e) {
            anmNavigationBridge.delegateToFormLaunchView($(e.currentTarget).data("formname"), $(e.currentTarget).data("entityid"));
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
            //bindToWorkplan(callbackToRunBeforeAnyAction, "#workplanButton");
            bindToReports(callbackToRunBeforeAnyAction, "#reportsButton");

            bindToEligibleCoupleList(callbackToRunBeforeAnyAction, "#eligibleCoupleMenuOption");
            //bindToANCList(callbackToRunBeforeAnyAction, "#ancMenuOption");
            bindToPNCList(callbackToRunBeforeAnyAction, "#pncMenuOption");
            bindToChildList(callbackToRunBeforeAnyAction, "#childMenuOption");
            bindToFPSmartRegistry(callbackToRunBeforeAnyAction, "#fpSmartRegistryOption");
            bindToANCSmartRegistry(callbackToRunBeforeAnyAction, "#ancSmartRegistryOption");
            bindToLaunchForm(callbackToRunBeforeAnyAction, "#ecRegistration");
            bindToLaunchForm(callbackToRunBeforeAnyAction, "#familyPlanningUpdate");
            bindToLaunchForm(callbackToRunBeforeAnyAction, "#fpComplications");
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
        delegateToFPSmartRegistry: function () {
            return anmNavigationContext.startFPSmartRegistry();
        },
        delegateToANCSmartRegistry: function () {
            return anmNavigationContext.startANCSmartRegistry();
        },
        delegateToFormLaunchView: function (formName, entityId) {
            return anmNavigationContext.startFormActivity(formName, entityId);
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
                eligibleCoupleCount: "7",
                fpCount: "12"
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
        startFPSmartRegistry: function () {
            window.location = "smart_registry/fp_register.html";
        },
        startANCSmartRegistry: function () {
            window.location = "smart_registry/anc_register.html";
        },
        startFormActivity: function (formName, entityId) {
            alert("Launching form: " + formName);
        }
    }
}
