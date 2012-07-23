function Home(homeBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.home(homeBridge.getANMInformation()));
        },

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
        getANMInformation: function () {
            return JSON.parse(homeContext.get());
        },
        delegateToECList: function () {
            return homeContext.startECList();
        }
    };
}

function FakeHomeContext() {
    return {
        get: function () {
            return "{\"anmName\": \"ANM X\", \"pncCount\": \"4\", \"ancCount\": \"5\", \"childCount\": \"6\", \"eligibleCoupleCount\": \"7\"}";
        },
        delegateToECList: function() {
            alert("You wanted to go to the EC list view.");
        }
    }
}
