function ListView(ecListBridge, cssIdOf, listTemplate, beneficiaryLists) {
    var VILLAGE_FILTER_OPTION = "village";
    var ALL_VILLAGES_FILTER_OPTION = "All";
    var appliedVillageFilter = "NONE";
    var inSearchMode = false;

    var populateListBasedOnAppliedFilters = function () {
        var searchString = $(cssIdOf.searchBox).val().toUpperCase();
        var priorityListItems = filterListBy(beneficiaryLists.priority, searchString);
        var normalListItems = filterListBy(beneficiaryLists.normal, searchString);

        populateECs(priorityListItems, cssIdOf.priorityContainer, cssIdOf.priorityListContainer, cssIdOf.priorityECsCount);
        populateECs(normalListItems, cssIdOf.normalContainer, cssIdOf.normalListContainer, cssIdOf.normalECsCount);
        setDisplayStatusOfNoItemIndicator(priorityListItems, normalListItems);
    };

    var populateECs = function (ecs, cssIdOfListContainer, cssIdOfListItemsContainer, idOfECsCount) {
        if (ecs.length === 0) {
            $(cssIdOfListContainer).hide();
        }
        else {
            $(cssIdOfListItemsContainer).html(listTemplate(ecs));
            $(idOfECsCount).text(ecs.length);
            $(cssIdOfListContainer).show();
        }
    };

    var filterByVillageHandler = function (e) {
        var selectedVillageDropdownOption = $(e.currentTarget);
        var filterToApply = selectedVillageDropdownOption.data(VILLAGE_FILTER_OPTION);
        var displayText = selectedVillageDropdownOption.text();
        filterByVillage(filterToApply, displayText);

        if (inSearchMode) {
            $(cssIdOf.searchBox).focus();
        }
    };

    var filterByVillage = function (filterToApply, displayText) {
        if (filterToApply === appliedVillageFilter)
            return;

        appliedVillageFilter = filterToApply;
        populateListBasedOnAppliedFilters();
        updateFilterIndicator(displayText);
        ecListBridge.delegateToSaveAppliedVillageFilter(filterToApply);
    };

    var updateFilterIndicator = function (appliedFilter) {
        var text = "Show: " + appliedFilter;
        $(cssIdOf.appliedFilterIndicator).text(text);
    };

    var expandSearchBox = function () {
        $(cssIdOf.sidePanelButton).hide();
        $(cssIdOf.registerECButton).hide();
        $(cssIdOf.cancelSearchButton).show();
        $(cssIdOf.searchContainer).addClass(cssIdOf.expandedSearchContainerClass);
        inSearchMode = true;
    };

    var cancelSearchBox = function () {
        $(cssIdOf.sidePanelButton).show();
        $(cssIdOf.cancelSearchButton).hide();
        $(cssIdOf.registerECButton).show();
        $(cssIdOf.searchContainer).removeClass(cssIdOf.expandedSearchContainerClass);
        $(cssIdOf.searchBox).val('');
        inSearchMode = false;

        setTimeout(function () {
            populateListBasedOnAppliedFilters();
        }, 50);
    };

    var filterListBy = function (ecs, searchString) {
        var searchResults = ecs;

        if (searchString) {
            searchResults = jQuery.grep(ecs, function (ec) {
                return (ec.wifeName.toUpperCase().indexOf(searchString) == 0
                    || ec.ecNumber.toUpperCase().indexOf(searchString) == 0
                    || ec.thayiCardNumber.toUpperCase().indexOf(searchString) == 0);
            });
        }

        if (appliedVillageFilter != ALL_VILLAGES_FILTER_OPTION) {
            searchResults = jQuery.grep(searchResults, function (ec) {
                return ec.villageName === appliedVillageFilter;
            });
        }

        return searchResults;
    };

    var setDisplayStatusOfNoItemIndicator = function (priorityListItems, normalListItems) {
        if (priorityListItems.length === 0 && normalListItems.length === 0) {
            $(cssIdOf.noItemIndicator).show();
        }
        else {
            $(cssIdOf.noItemIndicator).hide();
        }
    };

    return {
        filterByVillage: function (filterToApply, displayText) {
            filterByVillage(filterToApply, displayText);
        },
        bindSearchEvents: function () {
            $(cssIdOf.searchBox).click(expandSearchBox);
            $(cssIdOf.searchBox).keyup(populateListBasedOnAppliedFilters);
            $(cssIdOf.searchForm).submit(function () {
                return false;
            });
            $(cssIdOf.cancelSearchButton).click(cancelSearchBox);
        },
        populateVillageFilter: function () {
            $(cssIdOf.villageFilter).html(Handlebars.templates.filter_by_village(ecListBridge.getVillages()));
        },
        bindVillageFilterOptions : function () {
            $(cssIdOf.villageFilterOptions).click(filterByVillageHandler);
        }
    }
}
