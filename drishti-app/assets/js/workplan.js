function Workplan(workplanBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.workplan(workplanBridge.getWorkplanSummary()));
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
        }
    };
}

function FakeWorkplanContext() {
    return {
        get: function () {
            return "{\"totalAlertCount\": \"3\", \"totalReminderCount\": \"1\", \"villages\": [{\"name\": \"Gheredaha\", \"reminderCount\": \"2\", \"alertCount\": \"1\"}, {\"name\": \"Village 2\", \"reminderCount\": \"2\", \"alertCount\": \"0\"}]}";
        }
    }
}
