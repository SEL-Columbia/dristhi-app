function ECList(ecListBridge) {
    var sliceLength = 20;

    var populateECsInBatches = function (cssIdentifierOfContainerOfECS, ecs, numberOfBatches, startIndex) {
        if (startIndex > numberOfBatches) return;

        $(cssIdentifierOfContainerOfECS).append(Handlebars.templates.ec_list(ecs.slice(sliceLength * startIndex, sliceLength * (startIndex + 1))));
        setTimeout(function () {
            populateECsInBatches(cssIdentifierOfContainerOfECS, ecs, numberOfBatches, startIndex + 1);
        }, 1);
    }

    var populateECsInSpecificContainer = function(cssIdentifierOfRootElement, cssIdentifierOfContainer, ecsToUse) {
        var cssIdentifierOfContainerOfECS = cssIdentifierOfRootElement + " " + cssIdentifierOfContainer;

        $(cssIdentifierOfContainerOfECS + " .count").text(ecsToUse.length);
        var numberOfBatches = (ecsToUse.length / sliceLength) + 1;
        populateECsInBatches(cssIdentifierOfContainerOfECS, ecsToUse, numberOfBatches, 0);
    };

    return {
        populateInto: function (cssIdentifierOfRootElement) {
            var ecs = ecListBridge.getECs();

            populateECsInSpecificContainer(cssIdentifierOfRootElement, "#highPriorityContainer", ecs.highPriority);
            populateECsInSpecificContainer(cssIdentifierOfRootElement, "#normalPriorityContainer", ecs.normalPriority);
        },

        bindEveryItemToECView: function (cssIdentifierOfRootElement, cssIdentifierOfEveryListItem) {
            $(cssIdentifierOfRootElement).on("click", cssIdentifierOfEveryListItem, function (event) {
                ecListBridge.delegateToECDetail($(this).data("caseid"));
            });
        },
        bindItemToCommCare: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                ecListBridge.delegateToCommCare($(this).data("form"));
            })
        }
    };
}

function ECListBridge() {
    var ecContext = window.context;
    if (typeof ecContext === "undefined" && typeof FakeECListContext !== "undefined") {
        ecContext = new FakeECListContext();
    }

    return {
        getECs: function () {
            return JSON.parse(ecContext.get());
        },
        delegateToECDetail: function (caseId) {
            return ecContext.startEC(caseId);
        },
        delegateToCommCare: function (formId) {
            ecContext.startCommCare(formId);
        }
    };
}

function FakeECListContext() {
    return {
        get: function() {
            return JSON.stringify({
                highPriority: [
                    {
                        caseId: "12345",
                        wifeName: "Wife 1",
                        husbandName: "Husband 1",
                        ecNumber: "EC Number 1",
                        villageName: "Village 1",
                        isHighPriority: true,
                        hasTodos: false
                    }
                ],
                normalPriority: [
                    {
                        caseId: "11111",
                        wifeName: "Wife 2",
                        husbandName: "Husband 2",
                        ecNumber: "EC Number 2",
                        villageName: "Village 2",
                        isHighPriority: false,
                        hasTodos: true

                    }
                ]
            });
        },
        startEC: function (caseId) {
            window.location.href = "ec_detail.html";
        },
        startCommCare: function (formId) {
            alert("Start CommCare with form " + formId);
        }
    }
}
