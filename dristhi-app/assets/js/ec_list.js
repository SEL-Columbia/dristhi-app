function ECList(villageBridge, ecListBridge, cssIdOf) {
    var listView;

    var searchCriteria = function (ec, searchString) {
        return (ec.wifeName.toUpperCase().indexOf(searchString) == 0
            || ec.ecNumber.toUpperCase().indexOf(searchString) == 0
            || ec.thayiCardNumber.toUpperCase().indexOf(searchString) == 0);
    };

    var villageFilterCriteria = function (ec, appliedVillageFilter) {
        return ec.villageName === appliedVillageFilter;
    };

    return {
        populateInto: function () {
            listView = new ListView(cssIdOf, Handlebars.templates.ec_list, ecListBridge.getECs(), searchCriteria, villageFilterCriteria);

            var defaultOption = 0;
            if (ecListBridge.length > 1) {
                defaultOption = 1;
            }

            var appliedVillageFilter = ecListBridge.getAppliedVillageFilter(villageBridge.getVillages()[defaultOption].name);
            listView.filterByVillage(appliedVillageFilter, appliedVillageFilter);
        },
        bindEveryItemToECView: function () {
            $(cssIdOf.rootElement).on("click", cssIdOf.everyListItem, function () {
                ecListBridge.delegateToECDetail($(this).data("caseid"));
            });
        },
        bindItemToForm: function () {
            $(cssIdOf.commCareItems).click(function () {
                ecListBridge.delegateToForm($(this).data("formname"), "");
            })
        },
        populateVillageFilter: function () {
            var villages = villageBridge.getVillages();
            villages.unshift({name: "All"});
            listView.populateVillageFilter(villages);
        },
        bindVillageFilterOptions: function () {
            listView.bindVillageFilterOptions();
            $(cssIdOf.villageFilterOptions).click(function () {
                ecListBridge.delegateToSaveAppliedVillageFilter($(this).data(listView.VILLAGE_FILTER_OPTION));
            });
        },
        bindSearchEvents: function () {
            listView.bindSearchEvents();
        },
        bindLoadAll: function () {
            listView.bindLoadAll();
        },
        bindToDefaultPhoto: function (defaultPhotoPath) {
            $(cssIdOf.womanPhoto).on('error', function (e) {
                e.currentTarget.src = defaultPhotoPath;
            });
        },
        reloadPhoto: function (caseId, photoPath) {
            $('div[data-caseId="' + caseId + '"] ' + cssIdOf.womanPhoto).attr('src', photoPath);
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
        delegateToForm: function (formName, entityId) {
            ecContext.startFormActivity(formName, entityId);
        },
        delegateToSaveAppliedVillageFilter: function (village) {
            return ecContext.saveAppliedVillageFilter(village);
        },
        getAppliedVillageFilter: function (defaultFilterValue) {
            return ecContext.appliedVillageFilter(defaultFilterValue);
        }
    };
}

function FakeECListContext() {
    return {
        get: function () {
            return JSON.stringify({
                priority: [
                    {
                        caseId: "12345",
                        wifeName: "Wife 1",
                        husbandName: "Husband 1",
                        ecNumber: "EC Number 1",
                        villageName: "munjanahalli",
                        isHighPriority: true,
                        hasTodos: false,
                        thayiCardNumber: ""
                    },
                    {
                        caseId: "12345777",
                        wifeName: "Wife 2",
                        husbandName: "Husband 2",
                        ecNumber: "EC Number 2",
                        villageName: "munjanahalli",
                        isHighPriority: true,
                        hasTodos: false,
                        thayiCardNumber: ""
                    }
                ],
                normal: [
                    {
                        caseId: "11111",
                        wifeName: "Wife 2",
                        husbandName: "Husband 2",
                        ecNumber: "EC Number 2",
                        villageName: "chikkabheriya",
                        isHighPriority: false,
                        hasTodos: true,
                        thayiCardNumber: "12345"

                    },
                    {
                        caseId: "1112323",
                        wifeName: "Wife 4",
                        husbandName: "Husband 4",
                        ecNumber: "EC Number 4",
                        villageName: "bherya",
                        isHighPriority: false,
                        hasTodos: true,
                        thayiCardNumber: "12345"

                    }
                ]
            });
        },
        startEC: function (caseId) {
            window.location.href = "ec_detail.html";
        },
        startFormActivity: function (formName, entityId) {
            alert("Start form " + formName);
        },
        saveAppliedVillageFilter: function (village) {
        },
        appliedVillageFilter: function (defaultFilterValue) {
            return defaultFilterValue;
        }
    };
}
