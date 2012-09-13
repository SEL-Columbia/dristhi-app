function PNC(pncBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.pnc_detail(pncBridge.getCurrentPNC()));
        },

        bindEveryItemToCommCare: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                pncBridge.delegateToCommCare($(this).data("form"), $(this).data("caseid"));
            })
        },

        onAlertCheckboxClick: function (alertWhoseCheckboxWasClicked) {
            var alertItem = $(alertWhoseCheckboxWasClicked);
            pncBridge.delegateToCommCare(alertItem.data("form"), alertItem.data("caseid"));
            pncBridge.markAsCompleted(alertItem.data("caseid"), alertItem.data("visitcode"));
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
        },

        markAsCompleted: function (caseId, visitCode) {
            pncContext.markTodoAsCompleted(caseId, visitCode);
        }
    };
}

function FakePNCContext() {
    return {
        startCommCare: function (formId, caseId) {
            alert("Start CommCare with form " + formId + " on case with caseId: " + caseId);
        },
        markTodoAsCompleted: function (caseId, visitCode) {
            console.log("markAsCompleted " + caseId + " " + visitCode);
        },
        get: function () {
            return JSON.stringify({
                    caseId: "1234",
                    thaayiCardNumber: "TC Number 1",
                    coupleDetails: {
                        womanName: "Woman 1",
                        husbandName: "Husband 1"
                    },
                    location: {
                        villageName: "village 1",
                        subcenter: "SubCenter 1"
                    },
                    pncDetails: {
                        daysPostpartum: "23",
                        dateOfDelivery: "2012-10-24"
                    },
                    details: {
                        deliveryPlace: "Bherya PHC",
                        ashaName: "Shiwani",
                        ashaPhoneNumber: "987654321",
                        isHighRisk: true,
                        riskDetail: "Anaemia (active): 21 months",
                        deliveryComplications: "prolonged_labour something_bad"
                    },
                    urgentTodos: [
                        {
                            message: "PNC Visit 1",
                            formToOpen: "PNC_SERVICES",
                            isCompleted: true,
                            visitCode: "PNC 1",
                            todoDate: "2012-10-24"
                        },
                        {
                            message: "PNC Visit 2",
                            formToOpen: "PNC_SERVICES",
                            isCompleted: false,
                            visitCode: "PNC 2",
                            todoDate: "2012-10-24"
                        }
                    ],
                    todos: [
                        {
                            message: "Child Immunization 1",
                            formToOpen: "CHILD_IMMUNIZATION",
                            isCompleted: true,
                            visitCode: "VISIT_CODE 1",
                            todoDate: "2012-10-24"
                        },
                        {
                            message: "Child Immunization 2",
                            formToOpen: "CHILD_IMMUNIZATION",
                            isCompleted: false,
                            visitCode: "VISIT_CODE 2",
                            todoDate: "2012-10-24"
                        }
                    ],
                    timelineEvents: [
                        {
                            title: "Event 1",
                            details: ["Detail 1", "Detail 2"],
                            status: "upcoming",
                            date: "1y 2m ago"
                        },
                        {
                            title: "Event 2",
                            details: ["Detail 3", "Detail 4"],
                            status: "overdue",
                            date: "2m 3d ago"
                        },
                        {
                            title: "Event 3",
                            details: ["Detail 5", "Detail 6"],
                            status: "done",
                            date: "2m 3d ago"
                        }
                    ]
                }
            );
        }
    }
}
