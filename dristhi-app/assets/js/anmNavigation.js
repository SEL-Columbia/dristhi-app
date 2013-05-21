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
            anmNavigationBridge.delegateToANCSmartRegistry();
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
            //bindToANCList(callbackToRunBeforeAnyAction, "#ancMenuOption");
            bindToPNCList(callbackToRunBeforeAnyAction, "#pncMenuOption");
            bindToChildList(callbackToRunBeforeAnyAction, "#childMenuOption");
            bindToFPSmartRegistry(callbackToRunBeforeAnyAction, "#fpSmartRegistryOption");
            bindToANCSmartRegistry(callbackToRunBeforeAnyAction, "#ancSmartRegistryOption");
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
        delegateToReports: function () {
            return anmNavigationContext.startReports();
        },
        delegateToFPSmartRegistry: function () {
            return anmNavigationContext.startFPSmartRegistry();
        },
        delegateToANCSmartRegistry: function () {
            return anmNavigationContext.startANCSmartRegistry();
        },
        takePhoto: function (entityId, entityType) {
            return anmNavigationContext.takePhoto(entityId, entityType);
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
        startANCList: function () {
            window.location.href = "anc_list.html";
        },
        startPNCList: function () {
            window.location.href = "pnc_list.html";
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
        takePhoto: function (entityId, entityType) {
            alert("Taking photo for:" + entityId + " of type: " + entityType);
        }
    }
}
