function Home(homeBridge) {
    return {
        bindToEligibleCoupleList: function (element) {
            $(element).click(function () {
                homeBridge.delegateToECList();
            });
        }
    };
}

function HomeBridge() {
    var homeContext = window.context;
    if (typeof homeContext === "undefined" && typeof FakeHomeContext !== "undefined") {
        homeContext = new FakeHomeContext();
    }

    return {
        delegateToECList: function () {
            return homeContext.startECList();
        }
    };
}

function FakeHomeContext() {
    return {
        delegateToECList: function() {
            alert("You wanted to go to the EC list view.");
        }
    }
}
