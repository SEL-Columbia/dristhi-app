function Workplan(workplanBridge) {
    return {
        populateInto: function (cssIdentifierOfContentRootElement) {
            $(cssIdentifierOfContentRootElement).html(Handlebars.templates.workplan(workplanBridge.getWorkplanSummary()));
        },

        onAlertCheckboxClick: function (alertWhoseCheckboxWasClicked) {
            var alertItem = $(alertWhoseCheckboxWasClicked);
            workplanBridge.delegateToCommCare(alertItem.data("form"), alertItem.data("caseid"));
            workplanBridge.markAsCompleted(alertItem.data("caseid"), alertItem.data("visitcode"));
        }

    };
}

function WorkplanBridge() {
    var workplanContext = window.context;
    if (typeof workplanContext === "undefined" && typeof FakeWorkplanContext !== "undefined") {
        workplanContext = new FakeWorkplanContext();
    }

    return {
        getWorkplanSummary: function () {
            return JSON.parse(workplanContext.get());
        },

        delegateToCommCare: function (formId, caseId) {
            workplanContext.startCommCare(formId, caseId);
        },

        markAsCompleted: function (caseId, visitCode) {
            workplanContext.markTodoAsCompleted(caseId, visitCode);
        }
    };
}

function FakeWorkplanContext() {
    return {
        startCommCare: function (formId, caseId) {
            alert("Start CommCare with form " + formId + " on case with caseId: " + caseId);
        },
        markTodoAsCompleted: function (caseId, visitCode) {
            console.log("markAsCompleted " + caseId + " " + visitCode);
        },
        get: function () {
            return JSON.stringify({
                overdue: [
                    {
                        caseId : "CASE-X",
                        beneficiaryName: "Napa",
                        formToOpen: "PNC_SERVICES",
                        visitCode: "PNC 1",
                        description: "OPV due",
                        dueDate: "2012-10-24"
                    },
                    {
                        caseId : "CASE-Y",
                        beneficiaryName: "Salinas",
                        formToOpen: "ANC_SERVICES",
                        visitCode: "ANC 1",
                        description: "ANC due",
                        dueDate: "2012-10-24"
                    }
                ],
                upcoming: [
                    {
                        caseId : "CASE-Z",
                        beneficiaryName: "Balboa",
                        formToOpen: "ANC_SERVICES",
                        visitCode: "PNC 1",
                        description: "TT 1 due",
                        dueDate: "2012-10-24"
                    }
                ],
                completed: [
                    {
                        caseId : "CASE-X",
                        beneficiaryName: "Balboa",
                        formToOpen: "PNC_SERVICES",
                        visitCode: "PNC 1",
                        description: "IFA due",
                        dueDate: "2012-10-24"
                    },
                    {
                        caseId : "CASE-X",
                        beneficiaryName: "Karishma",
                        formToOpen: "PNC_SERVICES",
                        description: "HEP B1 due",
                        visitCode: "PNC 1",
                        dueDate: "2012-10-24"
                    },
                    {
                        caseId : "CASE-X",
                        beneficiaryName: "Nethravati",
                        formToOpen: "PNC_SERVICES",
                        visitCode: "PNC 1",
                        description: "IFA follow up due",
                        dueDate: "2012-10-24"
                    }
                ]
            });
        }
    }
}
