function Workplan(workplanBridge) {
    return {
        populateInto: function (cssIdentifierOfContentRootElement) {
            $(cssIdentifierOfContentRootElement).html(Handlebars.templates.workplan(workplanBridge.getWorkplanSummary()));
        },
        populateSidePanel: function (cssIdentifierOfSidePanelContainer) {
            $(cssIdentifierOfSidePanelContainer).html(Handlebars.templates.sidepanel({}));
        },
        bindItemToWorkplanDetailView: function (cssIdentifierOfWorkplanListElement, cssIdentifierOfEveryListItem) {
            $(cssIdentifierOfWorkplanListElement).on("click", cssIdentifierOfEveryListItem, function (event) {
                workplanBridge.delegateToWorkplanDetail($(this).data("village"));
            });
        },
        bindItemToToggleSidepanel: function (cssIdentifierOfSidePanelToggleElement) {
            $(cssIdentifierOfSidePanelToggleElement).click(function () {
                $(".affected-by-sidepanel").addClass("sidepanel-active");
            });
            $("#mainpanel-overlay").click(function() {
                $(".affected-by-sidepanel").removeClass("sidepanel-active");
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
