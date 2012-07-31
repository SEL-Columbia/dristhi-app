function ANMNavigationPanel(anmNavigationBridge) {
    var populateDataInto = function (cssIdentifierOfRootElement) {
        $(cssIdentifierOfRootElement).html(Handlebars.templates.sidepanel(anmNavigationBridge.getANMInformation()));
    };

    var bindToWorkplan = function (identifierOfElement) {
        $(identifierOfElement).click(function () {
            anmNavigationBridge.delegateToWorkplan();
        });
    };

    var bindToEligibleCoupleList = function (identifierOfElement) {
        $(identifierOfElement).click(function () {
            anmNavigationBridge.delegateToECList();
        });
    };

    var bindToPage = function (identifierOfElement, pageToGoTo) {
        $(identifierOfElement).click(function () {
            window.location.href = pageToGoTo;
        });
    };

    var bindToggleSidebar = function() {
        $(".navbar-brand-icon-holder").click(function () {
            $(".affected-by-sidepanel").addClass("sidepanel-active");
            $(".affected-by-sidepanel-container").addClass("container");
            $(".page").css('height', $(window).height())
        });
        $("#mainpanel-overlay").click(function() {
            $(".affected-by-sidepanel").removeClass("sidepanel-active");
            $(".affected-by-sidepanel-container").removeClass("container");
            $(".page").css('height', 'auto');
        });
    };

    return {
        populateInto: function(cssIdentifierOfSidePanelElement) {
            populateDataInto(cssIdentifierOfSidePanelElement);

            bindToWorkplan("#workplanButton");
            bindToPage("#myStatsButton", "my-stats.html");
            bindToPage("#inboxButton", "inbox.html");
            bindToPage("#reportsButton", "reports.html");

            bindToEligibleCoupleList("#eligibleCoupleMenuOption");

            bindToggleSidebar();
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
        delegateToWorkplan: function () {
            return anmNavigationContext.startWorkplan();
        }
    };
}

function FakeANMNavigationContext() {
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
