function PNC(pncBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.pnc_detail(pncBridge.getCurrentPNC()));
        },

        bindEveryItemToCommCare: function (cssIdentifierOfRootElement, cssClassOfChildElement) {
            $(cssIdentifierOfRootElement).on("click", cssClassOfChildElement, function (event) {
                pncBridge.delegateToCommCare($(this).data("form"), $(this).data("caseid"));
            });
        },

        bindItemToCommCare: function(cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                ancBridge.delegateToCommCare($(this).data("form"), $(this).data("caseid"));
            })
        }
    };
}

function PNCBridge() {
    var pncContext = window.context;
    if (typeof pncContext === "undefined" && typeof FakePNCContext !== "undefined") {
        pncContext = new FakePNCContext();
    }

    return {
        getCurrentPNC: function () {
            return JSON.parse(pncContext.get());
        },

        delegateToCommCare: function (formId, caseId) {
            pncContext.startCommCare(formId, caseId);
        }
    };
}

function FakePNCContext() {
    return {
        startCommCare: function (formId, caseId) {
            alert("Start CommCare with form " + formId + " on case with caseId: " + caseId);
        },
        get: function () {
            return JSON.stringify({
                    womanName: "PNC 1",
                    caseId: "1234",
                    thaayiCardNumber: "TC Number 1",
                    location: {
                        villageName: "Village 1",
                        subcenter: "SubCenter 1"
                    },
                    pncDetails: {
                        isHighRisk: true,
                        riskDetail: "Anaemia (active): 21 months",
                        daysPostpartum: "23",
                        dateOfDelivery: "24/03/12",
                        deliveryComplications: ["Prolonged Labor"]

                    },
                    alerts: [
                        {
                            message: "Alert 1",
                            formToOpen: "PNC"
                        },
                        {
                            message: "Alert 2",
                            formToOpen: "PNC"
                        }
                    ],
                    todos: [
                        {
                            message: "PNC Task #2",
                            formToOpen: "PNC"
                        },
                        {
                            message: "PNC Visit #3",
                            formToOpen: "PNC"
                        }
                    ],
                    timelineEvents: [
                        {
                            title: "Event 1",
                            details: ["Detail 1", "Detail 2"],
                            date: "1y 2m ago"
                        },
                        {
                            title: "Event 2",
                            details: ["Detail 3", "Detail 4"],
                            date: "2m 3d ago"
                        }
                    ]
                }
            );
        }
    }
}
