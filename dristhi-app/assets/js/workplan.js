function Workplan(workplanBridge, cssIdOf) {
    var ALL_VILLAGES_FILTER_OPTION = "All";
    var VILLAGE_FILTER_OPTION = "village";
    var appliedVillageFilter;
    var workplanSummary;

    var showTodoListAndUpdateCount = function (appliedVillageFilter) {
        var filteredOverdueTodoList;
        var filteredUpcomingTodoList;
        var filteredCompletedTodoList;

        if (appliedVillageFilter === ALL_VILLAGES_FILTER_OPTION) {
            filteredOverdueTodoList = workplanSummary.overdue;
            filteredUpcomingTodoList = workplanSummary.upcoming;
            filteredCompletedTodoList = workplanSummary.completed;
        }
        else {
            filteredOverdueTodoList = getTodoListForECsBelongingToVillage(workplanSummary.overdue, appliedVillageFilter);
            filteredUpcomingTodoList = getTodoListForECsBelongingToVillage(workplanSummary.upcoming, appliedVillageFilter);
            filteredCompletedTodoList = getTodoListForECsBelongingToVillage(workplanSummary.completed, appliedVillageFilter);
        }
        $(cssIdOf.overdueContainer).html(Handlebars.templates.overdue_todo(filteredOverdueTodoList));
        $(cssIdOf.upcomingContainer).html(Handlebars.templates.upcoming_todo(filteredUpcomingTodoList));
        $(cssIdOf.completedContainer).html(Handlebars.templates.completed_todo(filteredCompletedTodoList));

        $(cssIdOf.overdueTodosCount).text(filteredOverdueTodoList.length);
        $(cssIdOf.upcomingTodosCount).text(filteredUpcomingTodoList.length);
        $(cssIdOf.completedTodosCount).text(filteredCompletedTodoList.length);
    };

    var getTodoListForECsBelongingToVillage = function (todoList, village) {
        return jQuery.grep(todoList, function (todo, index) {
            return todo.villageName === village;
        });
    };

    var updateFilterIndicator = function (appliedFilter) {
        var text = "Show: " + formatText(appliedFilter);
        $(cssIdOf.appliedFilterIndicator).text(text);
    };

    var filterByVillage = function () {
        var filterToApply = $(this).data(VILLAGE_FILTER_OPTION);
        var filterIndicatorText = $(this).text();
        if (filterToApply === appliedVillageFilter)
            return;

        showTodoListAndUpdateCount(filterToApply);
        updateFilterIndicator(filterIndicatorText);
        appliedVillageFilter = filterToApply;
        workplanBridge.delegateToSaveAppliedVillageFilter(filterToApply);
    };

    return {
        populateInto:function () {
            appliedVillageFilter = workplanBridge.getAppliedVillageFilter(ALL_VILLAGES_FILTER_OPTION);
            workplanSummary = workplanBridge.getWorkplanSummary();
            $(cssIdOf.rootElement).append(Handlebars.templates.workplan(workplanSummary));
            showTodoListAndUpdateCount(appliedVillageFilter);
            updateFilterIndicator(appliedVillageFilter);
        },

        onAlertCheckboxClick:function (alertWhoseCheckboxWasClicked) {
            var alertItem = $(alertWhoseCheckboxWasClicked);
            workplanBridge.delegateToCommCare(alertItem.data("form"), alertItem.data("caseid"));
            workplanBridge.markAsCompleted(alertItem.data("caseid"), alertItem.data("visitcode"));
        },
        populateVillageFilter:function () {
            $(cssIdOf.villageFilter).append(Handlebars.templates.filter_by_village(workplanBridge.getVillages()));
        },
        bindToVillageFilter:function () {
            $(cssIdOf.villageFilterOptions).click(filterByVillage);
        }
    };
}

function WorkplanBridge() {
    var workplanContext = window.context;
    if (typeof workplanContext === "undefined" && typeof FakeWorkplanContext !== "undefined") {
        workplanContext = new FakeWorkplanContext();
    }

    return {
        getWorkplanSummary:function () {
            return JSON.parse(workplanContext.get());
        },

        delegateToCommCare:function (formId, caseId) {
            workplanContext.startCommCare(formId, caseId);
        },

        markAsCompleted:function (caseId, visitCode) {
            workplanContext.markTodoAsCompleted(caseId, visitCode);
        },
        getVillages:function () {
            return JSON.parse(workplanContext.villages());
        },
        delegateToSaveAppliedVillageFilter:function (village) {
            return workplanContext.saveAppliedVillageFilter(village);
        },
        getAppliedVillageFilter:function (defaultFilterValue) {
            return workplanContext.appliedVillageFilter(defaultFilterValue);
        }
    };
}

function FakeWorkplanContext() {
    return {
        startCommCare:function (formId, caseId) {
            alert("Start CommCare with form " + formId + " on case with caseId: " + caseId);
        },
        markTodoAsCompleted:function (caseId, visitCode) {
            console.log("markAsCompleted " + caseId + " " + visitCode);
        },
        get:function () {
            return JSON.stringify({
                overdue:[
                    {
                        caseId:"CASE-X",
                        beneficiaryName:"Napa",
                        husbandName:"Husband 1",
                        formToOpen:"PNC_SERVICES",
                        visitCode:"PNC 1",
                        description:"OPV",
                        dueDate:"2012-10-24",
                        villageName:"chikkabheriya"
                    },
                    {
                        caseId:"CASE-Y",
                        beneficiaryName:"Salinas",
                        husbandName:"Husband 2",
                        formToOpen:"ANC_SERVICES",
                        visitCode:"ANC 1",
                        villageName:"munjanahalli",
                        description:"ANC",
                        dueDate:"2012-10-24"
                    }
                ],
                upcoming:[
                    {
                        caseId:"CASE-Z",
                        beneficiaryName:"Balboa",
                        husbandName:"Husband 3",
                        formToOpen:"ANC_SERVICES",
                        visitCode:"PNC 1",
                        description:"TT 1",
                        dueDate:"2012-10-24",
                        villageName:"chikkabheriya"
                    }
                ],
                completed:[
                    {
                        caseId:"CASE-X",
                        beneficiaryName:"Balboa",
                        husbandName:"Husband 4",
                        formToOpen:"PNC_SERVICES",
                        villageName:"munjanahalli",
                        visitCode:"PNC 1",
                        description:"IFA",
                        dueDate:"2012-10-24"
                    },
                    {
                        caseId:"CASE-X",
                        beneficiaryName:"Karishma",
                        husbandName:"Husband 5",
                        formToOpen:"PNC_SERVICES",
                        description:"HEP B1",
                        villageName:"chikkabheriya",
                        visitCode:"PNC 1",
                        dueDate:"2012-10-24"
                    },
                    {
                        caseId:"CASE-X",
                        beneficiaryName:"Nethravati",
                        husbandName:"Husband 6",
                        formToOpen:"PNC_SERVICES",
                        visitCode:"PNC 1",
                        villageName:"munjanahalli",
                        description:"IFA follow up",
                        dueDate:"2012-10-24"
                    }
                ]
            });
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
        },
        appliedVillageFilter:function (defaultFilterValue) {
            return defaultFilterValue;
        }
    }
}
