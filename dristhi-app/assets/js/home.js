function Home(anmNavigation, homeBridge) {
    var populateDataInto = function (cssIdentifierOfRootElement) {
        Handlebars.registerPartial("anm_navigation", Handlebars.templates.anm_navigation);
        $(cssIdentifierOfRootElement).html(Handlebars.templates.home(anmNavigation.getANMInformation()));
    };

    var bindToManualSync = function (identifierOfElement) {
        $(identifierOfElement).click(homeBridge.manualSync);
    };

    return {
        populateInto: function (cssIdentifierOfSidePanelElement, callbackToRunBeforeAnyAction) {
            populateDataInto(cssIdentifierOfSidePanelElement);
            anmNavigation.populateInto(cssIdentifierOfSidePanelElement, callbackToRunBeforeAnyAction);
            bindToManualSync("#manualSync");
        },
        pageHasFinishedLoading: function () {
            homeBridge.pageHasFinishedLoading();
        }
    }
}

function HomeBridge() {
    var homeContext = window.context;
    if (typeof homeContext === "undefined" && typeof FakeHomeContext !== "undefined") {
        homeContext = new FakeHomeContext();
    }

    return {
        pageHasFinishedLoading: function () {
            return homeContext.pageHasFinishedLoading();
        },
        manualSync: function () {
            return homeContext.startManualSync();
        }

    };
}

function FakeHomeContext() {
    return {
        pageHasFinishedLoading: function () {
        },
        startManualSync: function () {
            alert("Sync initiated");
        }
    }
}
