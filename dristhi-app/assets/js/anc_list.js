function ANCList(ancListBridge) {
    var sliceLength = 20;

    var populateANCsInBatches = function (cssIdentifierOfContainerOfANCs, ancs, numberOfBatches, startIndex) {
        if (startIndex > numberOfBatches) return;

        $(cssIdentifierOfContainerOfANCs).append(Handlebars.templates.anc_list(ancs.slice(sliceLength * startIndex, sliceLength * (startIndex + 1))));
        setTimeout(function () {
            populateANCsInBatches(cssIdentifierOfContainerOfANCs, ancs, numberOfBatches, startIndex + 1);
        }, 1);
    }

    var populateANCsInSpecificContainer = function(cssIdentifierOfRootElement, cssIdentifierOfContainer, ancsToUse) {
        var cssIdentifierOfContainerOfANCS = cssIdentifierOfRootElement + " " + cssIdentifierOfContainer;

        $(cssIdentifierOfContainerOfANCS + " .count").text(ancsToUse.length);
        var numberOfBatches = (ancsToUse.length / sliceLength) + 1;
        populateANCsInBatches(cssIdentifierOfContainerOfANCS, ancsToUse, numberOfBatches, 0);
    };

    return {
        populateInto: function (cssIdentifierOfRootElement) {
            var ancs = ancListBridge.getANCs();

            populateANCsInSpecificContainer(cssIdentifierOfRootElement, "#highRiskContainer", ancs.highRisk);
            populateANCsInSpecificContainer(cssIdentifierOfRootElement, "#normalRiskContainer", ancs.normalRisk);
        },

        bindEveryItemToANCView: function (cssIdentifierOfRootElement, cssIdentifierOfEveryListItem) {
            $(cssIdentifierOfRootElement).on("click", cssIdentifierOfEveryListItem, function (event) {
                ancListBridge.delegateToANCDetail($(this).data("caseid"));
            });
        },
        bindItemToCommCare: function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                ancListBridge.delegateToCommCare($(this).data("form"));
            })
        }
    };
}

function ANCListBridge() {
    var ancContext = window.context;
    if (typeof ancContext === "undefined" && typeof FakeANCListContext !== "undefined") {
        ancContext = new FakeANCListContext();
    }

    return {
        getANCs: function () {
            return JSON.parse(ancContext.get());
        },

        delegateToANCDetail: function (caseId) {
            return ancContext.startANC(caseId);
        },
        delegateToCommCare: function (formId) {
            ancContext.startCommCare(formId);
        }
    };
}

function FakeANCListContext() {
    return {
        get: function() {
            return JSON.stringify({
                highRisk: [
                    {
                        caseId: "12345",
                        womanName: "Wife 1",
                        husbandName: "Husband 1",
                        thaayiCardNumber: "TC Number 1",
                        villageName: "Village 1",
                        hasTodos: true,
                        isHighRisk: true
                    },
                    {
                        caseId: "11111",
                        womanName: "Wife 2",
                        husbandName: "Husband 2",
                        thaayiCardNumber: "TC Number 2",
                        villageName: "Village 2",
                        hasTodos: false,
                        isHighRisk: true
                    }
                ],
                normalRisk: [
                    {
                        caseId: "12355",
                        womanName: "Wife 4",
                        husbandName: "Husband 4",
                        thaayiCardNumber: "TC Number 4",
                        villageName: "Village 1",
                        hasTodos: true,
                        isHighRisk: false
                    },
					{
                        caseId: "12355",
                        womanName: "Wife 5",
                        husbandName: "Husband 5",
                        thaayiCardNumber: "TC Number 5",
                        villageName: "Village 1",
                        hasTodos: false,
                        isHighRisk: false
                    },
                    {
                        caseId: "11121",
                        womanName: "Wife 6",
                        husbandName: "Husband 6",
                        thaayiCardNumber: "TC Number 6",
                        villageName: "Village 2",
                        hasTodos: true,
                        isHighRisk: false
                    }
                ]
            });
        },
        startANC: function(caseId) {
            window.location.href = "anc_detail.html";
        },
        startCommCare: function (formId) {
            alert("Start CommCare with form " + formId);
        }
    }
}
