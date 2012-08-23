function Home(homeBridge) {
    return {
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
        }
    };
}

function FakeHomeContext() {
    return {
        pageHasFinishedLoading: function () {
        }
    }
}
