function Workplan(workplanBridge) {
    return {
        populateInto: function (cssIdentifierOfContentRootElement) {
            $(cssIdentifierOfContentRootElement).html(Handlebars.templates.workplan(workplanBridge.getWorkplanSummary()));
        },
        bindItemToWorkplanDetailView: function (cssIdentifierOfWorkplanListElement, cssIdentifierOfEveryListItem) {
            $(cssIdentifierOfWorkplanListElement).on("click", cssIdentifierOfEveryListItem, function (event) {
                workplanBridge.delegateToWorkplanDetail($(this).data("village"));
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
        delegateToWorkplanDetail: function (village) {
            return workplanContext.startWorkplanDetail(village);
        }
    };
}

function FakeWorkplanContext() {
    return {
        get: function () {
            return "{\"totalAlertCount\": \"3\", \"totalReminderCount\": \"1\", \"villages\": [{\"name\": \"Gheredaha\", \"reminderCount\": \"2\", \"alertCount\": \"1\"}, {\"name\": \"Village 2\", \"reminderCount\": \"2\", \"alertCount\": \"0\"}]}";
        },
        startWorkplanDetail: function(village) {
            window.location.href = "workplan_detail.html";
        }
    }
}
