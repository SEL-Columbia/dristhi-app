function Child(childBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.child_detail(childBridge.getCurrentChild()));
        },

        bindEveryItemToCommCare: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                childBridge.delegateToCommCare($(this).data("form"), $(this).data("caseid"));
            })
        },

        onAlertCheckboxClick: function (alertWhoseCheckboxWasClicked) {
            var alertItem = $(alertWhoseCheckboxWasClicked);
            childBridge.delegateToCommCare(alertItem.data("form"), alertItem.data("caseid"));
            childBridge.markAsCompleted(alertItem.data("caseid"), alertItem.data("visitcode"));
        }
    };
}

function ChildBridge() {
    var childContext = window.context;
    if (typeof childContext === "undefined" && typeof FakeChildContext !== "undefined") {
        childContext = new FakeChildContext();
    }

    return {
        getCurrentChild: function () {
            return JSON.parse(childContext.get());
        },

        delegateToCommCare: function (formId, caseId) {
            childContext.startCommCare(formId, caseId);
        },

        markAsCompleted: function (caseId, visitCode) {
            childContext.markTodoAsCompleted(caseId, visitCode);
        }
    };
}

function FakeChildContext() {
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
                        wifeName: "Mother 1",
                        husbandName: "Husband 1",
                        ecNumber: "EC Number 1",
                        isInArea: true
                    },
                    location: {
                        villageName: "village 1",
                        subcenter: "SubCenter 1"
                    },
                    childDetails: {
                        age: "2 days",
                        dateOfBirth: "2012-10-24",
                        gender: "female"
                    },
                    details: {
                        deliveryPlace: "Bherya PHC",
                        ashaName: "Shiwani",
                        ashaPhoneNumber: "987654321",
                        isHighRisk: true,
                        highRiskReason: "    obstructed_labor     eclampsia spontaneous_abortion     ",
                        childWeight: "4.3"
                    },
                    urgentTodos: [
                        {
                            message: "Child Visit 1",
                            formToOpen: "Child_SERVICES",
                            isCompleted: true,
                            visitCode: "Child 1",
                            todoDate: "2012-10-24"
                        },
                        {
                            message: "Child Visit 2",
                            formToOpen: "Child_SERVICES",
                            isCompleted: false,
                            visitCode: "Child 2",
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
                            type: "PREGNANCY",
                            date: "1y 2m ago"
                        },
                        {
                            title: "Event 2",
                            details: ["Detail 1", "Detail 2"],
                            type: "FPCHANGE",
                            date: "1y 2m ago"
                        },
                        {
                            title: "Event 3",
                            details: ["Detail 3", "Detail 4"],
                            type: "ANCVISIT",
                            date: "2m 3d ago"
                        }
                    ]
                }
            );
        }
    }
}
