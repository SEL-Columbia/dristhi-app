function Home(anmNavigation, homeBridge) {
    var bindToManualSync = function (identifierOfElement) {
        $(identifierOfElement).click(homeBridge.manualSync);
    };

    return {
        populateInto: function (cssIdentifierOfSidePanelElement, callbackToRunBeforeAnyAction) {
            anmNavigation.populateInto(cssIdentifierOfSidePanelElement, Handlebars.templates.home, callbackToRunBeforeAnyAction);
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
