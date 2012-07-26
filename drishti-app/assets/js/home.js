function Home(homeBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.home(homeBridge.getANMInformation()));
        },

        bindToEligibleCoupleList: function (identifierOfElement) {
            $(identifierOfElement).click(function () {
                homeBridge.delegateToECList();
            });
        },

        bindToPage: function (identifierOfElement, pageToGoTo) {
            $(identifierOfElement).click(function () {
                window.location.href = pageToGoTo;
            });
        },

        bindToWorkplan: function (identifierOfElement, pageToGoTo) {
            $(identifierOfElement).click(function () {
                homeBridge.delegateToWorkplan();
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
        },
        delegateToWorkplan: function () {
            return homeContext.startWorkplan();
        }
    };
}

function FakeHomeContext() {
    return {
        get: function () {
            return "{\"anmName\": \"ANM X\", \"pncCount\": \"4\", \"ancCount\": \"5\", \"childCount\": \"6\", \"eligibleCoupleCount\": \"7\"}";
        },
        startECList: function () {
            window.location.href = "ec_list.html";
        },
        startWorkplan: function () {
            window.location.href = "workplan.html";
        }
    }
}
