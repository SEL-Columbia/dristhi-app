function ANC(ancBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.anc_detail(ancBridge.getCurrentANC()));
        },

        bindEveryItemToCommCare: function (cssIdentifierOfRootElement, cssClassOfChildElement) {
            $(cssIdentifierOfRootElement).on("click", cssClassOfChildElement, function (event) {
                ancBridge.delegateToCommCare($(this).data("caseid"), $(this).data("form"));
            });
        }
    };
}

function ANCBridge() {
    var ancContext = window.context;
    if (typeof ancContext === "undefined" && typeof FakeANCContext !== "undefined") {
        ancContext = new FakeANCContext();
    }

    return {
        getCurrentANC: function () {
            return JSON.parse(ancContext.get());
        },

        delegateToCommCare: function (caseId, formId) {
            ancContext.startCommCare(caseId, formId);
        }
    };
}

function FakeANCContext() {
    return {
        startCommCare: function (caseId, formId) {
            alert("Start CommCare with form " + formId + " on case with caseId: " + caseId);
        },
        get: function () {
            return JSON.stringify({
                    womanName: "Wife 1",
                    caseId: "1234",
                    thaayiCardNumber: "TC Number 1",
                    location: {
                        villageName: "Village 1",
                        subcenter: "SubCenter 1"
                    },
                    pregnancyDetails: {
                        isHighRisk: true,
                        riskDetail: "Anaemia (active): 21 months",
                        monthsPregnant: "7",
                        edd: "24/12/12"
                    },
                    facilityDetails: {
                        facility: "Broadway",
                        ashaName: "Shiwani",
                        contact: "----"
                    },
                    alerts: [
                        {
                            message: "Alert 1",
                            formToOpen: "ANC"
                        },
                        {
                            message: "Alert 2",
                            formToOpen: "ANC"
                        }
                    ],
                    todos: [
                        {
                            message: "IFA Tablet follow-up",
                            formToOpen: "ANC"
                        },
                        {
                            message: "ANC Visit #3",
                            formToOpen: "ANC"
                        }
                    ],
                    timelineEvents: [
                        {
                            title: "Event 1",
                            details: ["Detail 1", "Detail 2"],
                            done: false,
                            date: "1y 2m ago"
                        },
                        {
                            title: "Event 2",
                            details: ["Detail 3", "Detail 4"],
                            done: true,
                            date: "2m 3d ago"
                        }
                    ]
                }
            );
        }
    }
}
