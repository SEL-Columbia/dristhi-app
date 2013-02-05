function ListView(cssIdOf, listTemplate, beneficiaryLists, searchCriteria, villageFilterCriteria) {
    var VILLAGE_FILTER_OPTION = "village";
    var ALL_VILLAGES_FILTER_OPTION = "All";
    var appliedVillageFilter = "NONE";
    var inSearchMode = false;

    var populateListBasedOnAppliedFilters = function () {
        var searchString = $(cssIdOf.searchBox).val().toUpperCase();
        var priorityListItems = filterListBy(beneficiaryLists.priority, searchString);
        var normalListItems = filterListBy(beneficiaryLists.normal, searchString);

        populateBeneficiaries(priorityListItems, cssIdOf.priorityContainer, cssIdOf.priorityListContainer, cssIdOf.priorityBeneficiaryCount);
        populateBeneficiaries(normalListItems, cssIdOf.normalContainer, cssIdOf.normalListContainer, cssIdOf.normalBeneficiaryCount);
        setDisplayStatusOfNoItemIndicator(priorityListItems, normalListItems);
    };

    var populateBeneficiaries = function (beneficiaries, cssIdOfListContainer, cssIdOfListItemsContainer, idOfBeneficiariesCount) {
        if (beneficiaries.length === 0) {
            $(cssIdOfListContainer).hide();
        }
        else {
            $(cssIdOfListItemsContainer).html(listTemplate(beneficiaries));
            $(idOfBeneficiariesCount).text(beneficiaries.length);
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
    };

    var updateFilterIndicator = function (appliedFilter) {
        var text = "Show: " + appliedFilter;
        $(cssIdOf.appliedFilterIndicator).text(text);
    };

    var expandSearchBox = function () {
        $(cssIdOf.sidePanelButton).hide();
        $(cssIdOf.addButton).hide();
        $(cssIdOf.cancelSearchButton).show();
        $(cssIdOf.searchContainer).addClass(cssIdOf.expandedSearchContainerClass);
        inSearchMode = true;
    };

    var cancelSearchBox = function () {
        $(cssIdOf.sidePanelButton).show();
        $(cssIdOf.cancelSearchButton).hide();
        $(cssIdOf.addButton).show();
        $(cssIdOf.searchContainer).removeClass(cssIdOf.expandedSearchContainerClass);
        $(cssIdOf.searchBox).val('');
        inSearchMode = false;

        setTimeout(function () {
            populateListBasedOnAppliedFilters();
        }, 50);
    };

    var filterListBy = function (beneficiaries, searchString) {
        var searchResults = beneficiaries;

        if (searchString) {
            searchResults = jQuery.grep(beneficiaries, function (beneficiary) {
                return searchCriteria(beneficiary, searchString);
            });
        }

        if (appliedVillageFilter != ALL_VILLAGES_FILTER_OPTION) {
            searchResults = jQuery.grep(searchResults, function (beneficiary) {
                return villageFilterCriteria(beneficiary, appliedVillageFilter);
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
        VILLAGE_FILTER_OPTION: VILLAGE_FILTER_OPTION,
        ALL_VILLAGES_FILTER_OPTION: ALL_VILLAGES_FILTER_OPTION,
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
        populateVillageFilter: function (villages) {
            $(cssIdOf.villageFilter).html(Handlebars.templates.filter_by_village(villages));
        },
        bindVillageFilterOptions: function () {
            $(cssIdOf.villageFilterOptions).click(filterByVillageHandler);
        }
    }
}
