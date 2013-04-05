function ancRegisterController($scope) {
    $scope.clients = [
        {
            village: 'Chikkabherya',
            name: 'Carolyn',
            thayi: '4',
            ec_number: '314',
            age: '24',
            husbanad_name: 'Reger_H',
            weeks_pregnant: '2',
            edd: '25/12/13',
            lmp: '25/3/13',
            anc_visits:[
                {visit: '1', date: '04/04'},
                {visit: '2', date: '04/08'}
            ],
            tt: [
                {tt: '1', date: '04/04'},
                {tt: '2', date: '04/08'}
            ],
            ifa: {dose: '100', date: '04/04'}
            days_due: '3',
            due_message: 'Follow Up',
            isHighPriority: false
        },
        {
            village: 'Chikkabherya',
            name: 'Roger',
            thayi: '4',
            ec_number: '314',
            age: '24',
            husbanad_name: 'Reger_H',
            weeks_pregnant: '2',
            edd: '25/12/13',
            lmp: '25/3/13',
            anc_visits:[
                {visit: '1', date: '04/04'},
                {visit: '2', date: '04/08'}
            ],
            tt: [
                {tt: '1', date: '04/04'},
                {tt: '2', date: '04/08'}
            ],
            ifa: {dose: '100', date: '04/04'}
            days_due: '3',
            due_message: 'Follow Up',
            isHighPriority: false
        },
        {
            village: 'Bherya',
            name: 'Larry',
            thayi: '4',
            ec_number: '314',
            age: '24',
            husbanad_name: 'Reger_H',
            weeks_pregnant: '2',
            edd: '25/12/13',
            lmp: '25/3/13',
            anc_visits:[
                {visit: '1', date: '04/04'},
                {visit: '2', date: '04/08'}
            ],
            tt: [
                {tt: '1', date: '04/04'},
                {tt: '2', date: '04/08'}
            ],
            ifa: {dose: '100', date: '04/04'}
            days_due: '3',
            due_message: 'Follow Up',
            isHighPriority: false
        },

    ];

    $scope.sortOptions = {
        type: "sort",
        options: [
            {
                label: "Name (A to Z)",
                handler: "sortByName"
            },
            {
                label: "HP",
                handler: "sortByPriority"
            }
        ]
    };
    $scope.defaultSortOption = $scope.sortOptions.options[0];
    $scope.currentSortOption = $scope.defaultSortOption;
    $scope.sortList = $scope.sortByName;

    $scope.sort = function (option) {
        $scope.currentSortOption = option;
        $scope.sortList = $scope[option.handler];
    };

    $scope.sortByName = function (item) {
        return item.name;
    };

    $scope.sortByPriority = function (item) {
        return !item.isHighPriority;
    };

    $scope.villageOptions = {
        type: "filterVillage",
        options: [
            {
                label: "All",
                handler: ""
            },
            {
                label: "Bherya",
                handler: "Bherya"
            },
            {
                label: "Chikkabherya",
                handler: "Chikkabherya"
            }
        ].sort(function (item1, item2) {
                if (item1.label < item2.label) {
                    return -1;
                }
                else if (item1.label > item2.label) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        )
    };
    $scope.defaultVillage = $scope.villageOptions.options[0];
    $scope.villageFilterOption = $scope.defaultVillage;


    //  filters
//    overview,  ANC Visits, ANC Visits / TT, hB/IFA, Delivery Plan

    $scope.ancServiceOptions = {
        type: "ancService",
        options: [
            {
                label: "Overview",
                handler: "ancOverview"
            },
            {
                label: "ANC Visits",
                handler: "ancVisits"
            },
            {
                label: "ANC Visits/TT",
                handler: "ancTT"
            },
            {
                label: "hB/IFA",
                handler: "ancHBIFA"
            },
            {
                label: "Delivery Plan",
                handler: "ancDelivery"
            }
        ]
    };

    $scope.defaultAncServiceOption = $scope.ancServiceOptions.options[0];
    $scope.ancServiceOption = $scope.defaultAncServiceOption;

    $scope.filterVillage = function (option) {
        $scope.villageFilterOption = option;
    };

    $scope.ancService = function (option) {
        $scope.ancServiceOption = option;
    };

    $scope.searchFilterString = "";

    var defaultContentTemplate = "overview";
    var overviewContentTemplate = "overview";



    $scope.filterList = function (client) {
        var searchCondition = true;
        var villageCondition = true;
        var ancServiceCondition = true;
        if ($scope.searchFilterString) {
            searchCondition = (client.name.toUpperCase().indexOf($scope.searchFilterString.toUpperCase()) === 0
                || client.ec_number.toUpperCase().indexOf($scope.searchFilterString.toUpperCase()) === 0
                || client.thayi.toUpperCase().indexOf($scope.searchFilterString.toUpperCase()) === 0);
        }
        if ($scope.villageFilterOption.handler) {
            villageCondition = (client.village == $scope.villageFilterOption.handler);
        }
        if ($scope.ancServiceOption.handler) {
            var handlerMethod = $scope[$scope.ancServiceOption.handler];
            ancServiceCondition = handlerMethod(client, $scope.ancServiceOption.id);
        }
        return villageCondition && searchCondition && ancServiceCondition;
    };



    $scope.highPriorityFilter = function (client) {
        $scope.contentTemplate = hpECWithoutFP;
        return !doesClientUseFpMethod(client) && client.isHighPriority;
    };

    $scope.twoPlusChildrenFilter = function (client) {
        $scope.contentTemplate = hpECWithoutFP;
        return !doesClientUseFpMethod(client) && client.num_living_children >= "2";
    };

    $scope.oneChildFilter = function (client) {
        $scope.contentTemplate = hpECWithoutFP;
        return !doesClientUseFpMethod(client) && client.num_living_children === "1";
    };

    $scope.fpMethodFilter = function (client, optionId) {
        $scope.contentTemplate = defaultContentTemplate;
        return client.fp_method === optionId;
    };

    $scope.otherFpMethodFilter = function (client) {
        $scope.contentTemplate = defaultContentTemplate;
        return client.fp_method === "lam"
            || client.fp_method === "traditional"
            || client.fp_method === "centchroman";
    };

    $scope.allECsWithoutFP = function (client) {
        $scope.contentTemplate = hpECWithoutFP;
        return !doesClientUseFpMethod(client);
    };

    $scope.allECsWithFP = function (client) {
        $scope.contentTemplate = defaultContentTemplate;
        return doesClientUseFpMethod(client);
    };

    var doesClientUseFpMethod = function (client) {
        return (client.fp_method && client.fp_method !== "none");
    };

    $scope.currentOptions = null;

    $scope.isModalOpen = false;

    $scope.isFPModalOpen = false;
    $scope.isFPMethodsOptionSelected = true;

    $scope.selectFPMethodOption = function (fpMethodOptionSelected) {
        $scope.isFPMethodsOptionSelected = fpMethodOptionSelected;
        $scope.isFPPrioritizationOptionSelected = !fpMethodOptionSelected;
    };

    $scope.onModalOptionClick = function (option, type) {
        $scope[type](option);
        $scope.isModalOpen = false;
    };

    $scope.onFPModalOptionClick = function (option, type) {
        $scope[type](option);
        $scope.isFPModalOpen = false;
    };

    $scope.openModal = function (option) {
        $scope.isModalOpen = true;
        $scope.currentOptions = option;
    };

    $scope.openFPModal = function (option) {
        $scope.isFPModalOpen = true;
        $scope.currentOptions = option;
    };

    $scope.close = function () {
        $scope.isModalOpen = false;
    };

    $scope.contentTemplate = defaultContentTemplate;
}
