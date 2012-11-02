function ANCList(ancListBridge) {
    var ancListRow = "anc";
    var showANCsFromAllVillages = "All";
    var villageFilterOption = "village";
    var highRiskContainer;
    var normalRiskContainer;

    var showANCsAndUpdateCount = function (cssIdentifierOfEachRow) {
        var highRiskANCs = $(highRiskContainer + " ." + cssIdentifierOfEachRow);
        var normalRiskANCs = $(normalRiskContainer + " ." + cssIdentifierOfEachRow);

        highRiskANCs.show();
        normalRiskANCs.show();

        $(highRiskContainer + " .count").text(highRiskANCs.length);
        $(normalRiskContainer + " .count").text(normalRiskANCs.length);

    }

    var populateANCs = function (ancs, container) {
        if (ancs.length == 0)
            $(container).hide();
        else {
            $(container + " .count").text(ancs.length);
            $(container).append(Handlebars.templates.anc_list(ancs));
        }
    }

    return {
        populateInto:function (cssIdentifierOfRootElement) {
            var ancs = ancListBridge.getANCs();
            highRiskContainer = cssIdentifierOfRootElement + " #highRiskContainer";
            normalRiskContainer = cssIdentifierOfRootElement + " #normalRiskContainer";

            populateANCs(ancs.highRisk, highRiskContainer);
            populateANCs(ancs.normalRisk, normalRiskContainer);
        },

        bindEveryItemToANCView:function (cssIdentifierOfRootElement, cssIdentifierOfEveryListItem) {
            $(cssIdentifierOfRootElement).on("click", cssIdentifierOfEveryListItem, function (event) {
                ancListBridge.delegateToANCDetail($(this).data("caseid"));
            });
        },
        bindItemToCommCare:function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                ancListBridge.delegateToCommCare($(this).data("form"));
            })
        },
        populateVillageFilter:function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).append(Handlebars.templates.filter_by_village(ancListBridge.getVillages()));
        },
        bindToVillageFilter:function (cssIdentifierOfElement) {
            $(cssIdentifierOfElement).click(function () {
                var text = 'Show: ' + $(this).text();
                var filterToApply = $(this).data(villageFilterOption);

                $(this).closest('.dropdown').children('a.dropdown-toggle').text(text);
                if (filterToApply === showANCsFromAllVillages) {
                    showANCsAndUpdateCount(ancListRow);
                    return;
                }
                $("." + ancListRow).hide();
                showANCsAndUpdateCount(filterToApply);
                ancListBridge.delegateToSaveAppliedVillageFilter(filterToApply);
            });
        }
    };
}

function ANCListBridge() {
    var ancContext = window.context;
    if (typeof ancContext === "undefined" && typeof FakeANCListContext !== "undefined") {
        ancContext = new FakeANCListContext();
    }

    return {
        getANCs:function () {
            return JSON.parse(ancContext.get());
        },

        delegateToANCDetail:function (caseId) {
            return ancContext.startANC(caseId);
        },
        delegateToCommCare:function (formId) {
            ancContext.startCommCare(formId);
        },
        getVillages:function () {
            return JSON.parse(ancContext.villages());
        },
        delegateToSaveAppliedVillageFilter:function (village) {
            return ancContext.saveAppliedVillageFilter(village);
        }
    };
}

function FakeANCListContext() {
    return {
        get:function () {
            return JSON.stringify({
                highRisk:[
                    {
                        caseId:"12345",
                        womanName:"Wife 1",
                        husbandName:"Husband 1",
                        thaayiCardNumber:"TC Number 1",
                        ecNumber:"EC 1",
                        villageName:"chikkabheriya",
                        hasTodos:true,
                        isHighRisk:true
                    },
                    {
                        caseId:"11111",
                        womanName:"Wife 2",
                        husbandName:"Husband 2",
                        thaayiCardNumber:"TC Number 2",
                        ecNumber:"EC 2",
                        villageName:"munjanahalli",
                        hasTodos:false,
                        isHighRisk:true
                    }
                ],
                normalRisk:[
                    {
                        caseId:"12355",
                        womanName:"Wife 4",
                        husbandName:"Husband 4",
                        thaayiCardNumber:"TC Number 4",
                        ecNumber:"EC 3",
                        villageName:"chikkabheriya",
                        hasTodos:true,
                        isHighRisk:false
                    },
                    {
                        caseId:"12355",
                        womanName:"Wife 5",
                        husbandName:"Husband 5",
                        thaayiCardNumber:"TC Number 5",
                        ecNumber:"EC 4",
                        villageName:"munjanahalli",
                        hasTodos:false,
                        isHighRisk:false
                    },
                    {
                        caseId:"11121",
                        womanName:"Wife 6",
                        husbandName:"Husband 6",
                        thaayiCardNumber:"TC Number 6",
                        ecNumber:"EC 5",
                        villageName:"munjanahalli",
                        hasTodos:true,
                        isHighRisk:false
                    }
                ]
            });
        },
        startANC:function (caseId) {
            window.location.href = "anc_detail.html";
        },
        startCommCare:function (formId) {
            alert("Start CommCare with form " + formId);
        },
        villages:function () {
            return JSON.stringify(
                [
                    {name:"All"},
                    {name:"munjanahalli"},
                    {name:"chikkabheriya"}
                ]
            )
        },
        saveAppliedVillageFilter:function (village) {
        }
    }
}
