if (!drishti)
    var drishti = {};
drishti.it = new InternationalText(new InternationalTextBridge());

function InternationalText(bridge) {
    var internationalizedLabels;

    return {
        getLabel: function (key) {
            if (!internationalizedLabels)
                internationalizedLabels = JSON.parse(bridge.getInternationalizedLabels());

            return internationalizedLabels[key];
        }
    };
}

function InternationalTextBridge() {
    var context = window.internationalizationContext;
    if (typeof context === "undefined" && typeof FakeInternationalisationContext() !== "undefined") {
        context = new FakeInternationalisationContext();
    }

    return {
        getInternationalizedLabels: function () {
            return context.getInternationalizedLabels();
        }

    };
}


function FakeInternationalisationContext() {
    var language = "en";

    return {
        getInternationalizedLabels: function () {
            if (language === "en")
                return JSON.stringify({
                    "home_ec_label": "Eligible Couple",
                    "home_anc_label": "ANC",
                    "home_pnc_label": "PNC",
                    "home_child_label": "Child",
                    "home_workplan_label": "Workplan",
                    "home_report_label": "Reporting"
                });
            else
                return JSON.stringify({
                    "home_ec_label": "ಅರ್ಹ ದಂಪತಿಗಳು",
                    "home_anc_label": "ಎಎನ್ ಸಿ",
                    "home_pnc_label": "ಪಿಎನ್",
                    "home_child_label": "ಮಗು",
                    "home_workplan_label": "ಕಾರ್ಯ ಯೋಜನೆ",
                    "home_report_label": "ವರದಿ"
                });
        }
    }
}
