function Workplan(workplanBridge) {
    var alert = "alert-row";
    var showAlertsFromAllVillages = "All";
    var villageFilterOption = "village";

    var showAlertsAndUpdateCount = function (cssIdentifierOfEachRow) {
        var alertsOverdue = $(".alert.overdue" + "." + cssIdentifierOfEachRow);
        var alertsUpcoming = $(".alert.upcoming" + "." + cssIdentifierOfEachRow);
        var alertsCompleted = $(".alert.completed" + "." + cssIdentifierOfEachRow);

        alertsOverdue.show();
        alertsUpcoming.show();
        alertsCompleted.show();

        $("#overdue-count").text(alertsOverdue.length);
        $("#upcoming-count").text(alertsUpcoming.length);
        $("#completed-count").text(alertsCompleted.length);
    }

    return {
        populateInto: function (cssIdentifierOfContentRootElement) {
            $(cssIdentifierOfContentRootElement).html(Handlebars.templates.workplan(workplanBridge.getWorkplanSummary()));
        },

        onAlertCheckboxClick: function (alertWhoseCheckboxWasClicked) {
            var alertItem = $(alertWhoseCheckboxWasClicked);
            workplanBridge.delegateToCommCare(alertItem.data("form"), alertItem.data("caseid"));
            workplanBridge.markAsCompleted(alertItem.data("caseid"), alertItem.data("visitcode"));
        },
        populateVillageFilter: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).append(Handlebars.templates.filter_by_village(workplanBridge.getVillages()));
        },
        bindToVillageFilter: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                var text = 'Show: '+ $(this).text();
                $(this).closest('.dropdown').children('a.dropdown-toggle').text(text);
                if ($(this).data(villageFilterOption) === showAlertsFromAllVillages) {
                    showAlertsAndUpdateCount(alert);
                    return;
                }
                $("." + alert).hide();
                showAlertsAndUpdateCount($(this).data(villageFilterOption));
            });
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
        },
        getVillages: function () {
            return JSON.parse(workplanContext.villages());
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
                        husbandName: "Husband 1",
                        formToOpen: "PNC_SERVICES",
                        visitCode: "PNC 1",
                        description: "OPV due",
                        dueDate: "2012-10-24",
                        villageName: "chikkabheriya"
                    },
                    {
                        caseId : "CASE-Y",
                        beneficiaryName: "Salinas",
                        husbandName: "Husband 2",
                        formToOpen: "ANC_SERVICES",
                        visitCode: "ANC 1",
                        villageName: "munjanahalli",
                        description: "ANC due",
                        dueDate: "2012-10-24"
                    }
                ],
                upcoming: [
                    {
                        caseId : "CASE-Z",
                        beneficiaryName: "Balboa",
                        husbandName: "Husband 3",
                        formToOpen: "ANC_SERVICES",
                        visitCode: "PNC 1",
                        description: "TT 1 due",
                        dueDate: "2012-10-24",
                        villageName: "chikkabheriya"
                    }
                ],
                completed: [
                    {
                        caseId : "CASE-X",
                        beneficiaryName: "Balboa",
                        husbandName: "Husband 4",
                        formToOpen: "PNC_SERVICES",
                        villageName: "munjanahalli",
                        visitCode: "PNC 1",
                        description: "IFA due",
                        dueDate: "2012-10-24"
                    },
                    {
                        caseId : "CASE-X",
                        beneficiaryName: "Karishma",
                        husbandName: "Husband 5",
                        formToOpen: "PNC_SERVICES",
                        description: "HEP B1 due",
                        villageName: "chikkabheriya",
                        visitCode: "PNC 1",
                        dueDate: "2012-10-24"
                    },
                    {
                        caseId : "CASE-X",
                        beneficiaryName: "Nethravati",
                        husbandName: "Husband 6",
                        formToOpen: "PNC_SERVICES",
                        visitCode: "PNC 1",
                        villageName: "munjanahalli",
                        description: "IFA follow up due",
                        dueDate: "2012-10-24"
                    }
                ]
            });
        },
        villages: function () {
            return JSON.stringify(
                [
                    {name: "All"},
                    {name: "munjanahalli"},
                    {name: "chikkabheriya"}
                ]
            )
        }
    }
}
