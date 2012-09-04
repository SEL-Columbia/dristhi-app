function PNC(pncBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.pnc_detail(pncBridge.getCurrentPNC()));
        },

        bindEveryChildItemToCommCare: function (cssIdentifierOfRootElement, cssClassOfChildElement) {
            $(cssIdentifierOfRootElement).on("click", cssClassOfChildElement, function (event) {
                pncBridge.delegateToCommCare($(this).data("form"), $(this).data("caseid"));
            });
        },

        bindEveryItemToCommCare: function(cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                pncBridge.delegateToCommCare($(this).data("form"), $(this).data("caseid"));
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
                        daysPostpartum: "23",
                        dateOfDelivery: "24/03/12"
                    },
                    details: {
                        deliveryPlace: "Bherya PHC",
                        ashaName: "Shiwani",
                        ashaPhoneNumber: "987654321",
                        isHighRisk: true,
                        riskDetail: "Anaemia (active): 21 months",
                        deliveryComplications: ["Prolonged Labor"]
                    },
                    urgentTodos: [
                        {
                            message: "PNC Visit 1",
                            formToOpen: "PNC_SERVICES",
                            isCompleted: true
                        },
                        {
                            message: "PNC Visit 2",
                            formToOpen: "PNC_SERVICES",
                            isCompleted: false
                        }
                    ],
                    todos: [
                        {
                            message: "Child Immunization 1",
                            formToOpen: "CHILD_IMMUNIZATION",
                            isCompleted: true
                        },
                        {
                            message: "Child Immunization 2",
                            formToOpen: "CHILD_IMMUNIZATION",
                            isCompleted: false
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
