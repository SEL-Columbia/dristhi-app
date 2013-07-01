function ANMNavigationPanel(anmNavigationBridge) {

    var populateDataInto = function (cssIdentifierOfRootElement, displayTemplate) {
        Handlebars.registerPartial("anm_navigation", Handlebars.templates.anm_navigation);
        $(cssIdentifierOfRootElement).html(displayTemplate(anmNavigationBridge.getANMInformation()));
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
            anmNavigationBridge.delegateToANCSmartRegistry();
        });
    };

    var bindToPNCSmartRegistry = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            anmNavigationBridge.delegateToPNCSmartRegistry();
        });
    };

    var bindToChildSmartRegistry = function (callbackToRunBeforeAnyAction, identifierOfElement) {
        runWithCallBack(callbackToRunBeforeAnyAction, identifierOfElement, function () {
            anmNavigationBridge.delegateToChildSmartRegistry();
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
            bindToReports(callbackToRunBeforeAnyAction, "#reportsButton");
            bindToEligibleCoupleList(callbackToRunBeforeAnyAction, "#eligibleCoupleMenuOption");
            bindToChildSmartRegistry(callbackToRunBeforeAnyAction, "#childMenuOption");
            bindToFPSmartRegistry(callbackToRunBeforeAnyAction, "#fpSmartRegistryOption");
            bindToANCSmartRegistry(callbackToRunBeforeAnyAction, "#ancSmartRegistryOption");
            bindToPNCSmartRegistry(callbackToRunBeforeAnyAction, "#pncSmartRegistryOption");
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
        delegateToChildList: function () {
            return anmNavigationContext.startChildList();
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
        delegateToPNCSmartRegistry: function () {
            return anmNavigationContext.startPNCSmartRegistry();
        },
        delegateToChildSmartRegistry: function () {
            return anmNavigationContext.startChildSmartRegistry();
        },
        takePhoto: function (entityId, entityType) {
            return anmNavigationContext.takePhoto(entityId, entityType);
        },
        goBack: function () {
            anmNavigationContext.goBack();
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
                fpCount: "4"
            });
        },
        startECList: function () {
            window.location.href = "ec_list.html";
        },
        startChildList: function () {
            window.location.href = "child_list.html";
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
        startPNCSmartRegistry: function () {
            window.location = "smart_registry/pnc_register.html";
        },
        startChildSmartRegistry: function () {
            window.location = "smart_registry/child_register.html";
        },
        takePhoto: function (entityId, entityType) {
            alert("Taking photo for:" + entityId + " of type: " + entityType);
        },
        goBack: function () {
            window.location.href = "../home.html";
        }
    }
}
