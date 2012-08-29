function EC(ecBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.ec_detail(ecBridge.getCurrentEC()));
        },

        bindItemToCommCare: function(cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                ecBridge.delegateToCommCare($(this).data("form"), $(this).data("caseid"));
            })
        }
    };
}

function ECBridge() {
    var ecContext = window.context;
    if (typeof ecContext === "undefined" && typeof FakeECContext !== "undefined") {
        ecContext = new FakeECContext();
    }

    return {
        getCurrentEC: function () {
            return JSON.parse(ecContext.get());
        },

        delegateToCommCare: function (formId, caseId) {
            ecContext.startCommCare(formId, caseId);
        }
    };
}

function FakeECContext() {
    return {
        startCommCare: function (formId, caseId) {
            alert("Start CommCare for case " + caseId + " with form "+ formId);
        },
        get: function () {
            return JSON.stringify({
                    caseId: "CASE X",
                    wifeName: "Wife 1",
                    ecNumber: "EC Number 1",
                    village: "Village 1",
                    subcenter: "SubCenter 1",
                    isHighPriority: true,
                    alerts: [
                        {
                            message: "Alert 1"
                        },
                        {
                            message: "Alert 2"
                        }
                    ],
                    children: [
                        {
                            isFemale: true,
                            age: "1 year"
                        },
                        {
                            isFemale: false,
                            age: "2 years"
                        }
                    ],
                    timelineEvents: [
                        {
                            title: "Event 1",
                            details: ["Detail 1", "Detail 2"],
                            date: "1y 2m ago",
                            status: "overdue"
                        },
                        {
                            title: "Event 2",
                            details: ["Detail 3", "Detail 4"],
                            date: "2m 3d ago",
                            status: "upcoming"
                        },
                        {
                            title: "Event 3",
                            details: ["Detail 4", "Detail 5"],
                            date: "2m 3d ago",
                            status: "done"
                        }
                    ],
                    details: {
                        currentMethod: "Condom",
                        headOfHousehold: "Head Person",
                        religion: "Religion X",
                        pregnancies: "3"
                    }
                }
            );
        }
    }
}
