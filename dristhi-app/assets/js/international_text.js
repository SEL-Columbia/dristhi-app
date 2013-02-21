if (!drishti)
    var drishti = {};
drishti.it = new InternationalText(new InternationalTextBridge());

function InternationalText(bridge) {
    var resourceStrings;

    return {
        getValue: function (key) {
            if (!resourceStrings)
                resourceStrings = JSON.parse(bridge.getResourceStrings());

            return resourceStrings[key];
        }
    };
}

function InternationalTextBridge() {
    var context = window.context;
    if (typeof context === "undefined" && typeof FakeInternationalisationContext() !== "undefined") {
        context = new FakeInternationalisationContext();
    }

    return {
        getResourceStrings: function () {
            return context.getResourceStrings();
        }

    };
}


function FakeInternationalisationContext() {
    return {
        getResourceStrings: function () {
            return JSON.stringify({
                "home_ec_label": "Eligible Couple",
                "home_anc_label": "ANC",
                "home_pnc_label": "PNC",
                "home_child_label": "Child",
                "home_workplan_label" : "Workplan",
                "home_report_label": "Reporting"
            });
        }
    }
}
