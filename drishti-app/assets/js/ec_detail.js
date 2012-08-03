function EC(ecBridge) {
    return {
        populateInto: function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.ec_detail(ecBridge.getCurrentEC()));
        },

        bindToContacts: function (element) {
            $(element).click(function () {
                ecBridge.delegateToContacts();
            });
        },

        bindEveryTodoToCommCare: function (element) {
            $(element).click(function () {
                ecBridge.delegateToCommCare();
            });
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

        delegateToContacts: function () {
            ecContext.startContacts();
        },

        delegateToCommCare: function () {
            ecContext.startCommCare();
        }
    };
}

function FakeECContext() {
    return {
        startContacts: function () {
            alert("Start contacts");
        },
        startCommCare: function () {
            alert("Start CommCare");
        },
        get: function () {
            return JSON.stringify({
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
                    currentMethod: "IUD",
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
                    timeline: [
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
