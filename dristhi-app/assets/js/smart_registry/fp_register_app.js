function fpRegisterController($scope) {
    var fpSmartRegistryBridge = new FPSmartRegistryBridge();
    $scope.clients = fpSmartRegistryBridge.getFPClients();

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
                handler: "bherya"
            },
            {
                label: "Chikkahalli",
                handler: "chikkahalli"
            },
            {
                label: "Somanahalli Colony",
                handler: "somanahalli_colony"
            },
            {
                label: "Chikkabherya",
                handler: "chikkabherya"
            },
            {
                label: "Basavanapura",
                handler: "basavanapura"
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

    $scope.defaultFPOptions = $scope.ecsWithFPMethodFilterOptions;
    $scope.ecsWithFPMethodFilterOptions = {
        type: "filterFPMethod",
        options: [
            {
                label: "All Methods",
                id: "allECsWithFP",
                handler: "allECsWithFP"
            },
            {
                label: "Condom",
                id: "condom",
                handler: "fpMethodFilter"
            },
            {
                label: "DMPA/Injectable",
                id: "dmpa_injectable",
                handler: "fpMethodFilter"
            },
            {
                label: "IUD",
                id: "iud",
                handler: "fpMethodFilter"
            },
            {
                label: "OCP",
                id: "ocp",
                handler: "fpMethodFilter"
            },
            {
                label: "Female Sterilization",
                id: "female_sterilization",
                handler: "fpMethodFilter"
            },
            {
                label: "Male Sterilization",
                id: "male_sterilization",
                handler: "fpMethodFilter"
            },
            {
                label: "Others",
                id: "others",
                handler: "otherFpMethodFilter"
            }

        ]
    };

    $scope.ecsWithoutFPMethodFilterOptions = {
        type: "filterFPMethod",
        options: [
            {
                label: "All EC",
                id: "allECsWithoutFP",
                handler: "allECsWithoutFP"
            },
            {
                label: "High Priority",
                id: "hp",
                handler: "highPriorityFilter"
            },
            {
                label: "2+ Children",
                id: "2+_Children",
                handler: "twoPlusChildrenFilter"
            },
            {
                label: "1 Child",
                id: "1_Child",
                handler: "oneChildFilter"
            }

        ]
    };

    $scope.defaultFPMethodOption = $scope.ecsWithFPMethodFilterOptions.options[0];
    $scope.fpMethodFilterOption = $scope.defaultFPMethodOption;
    $scope.filterVillage = function (option) {
        $scope.villageFilterOption = option;
    };

    $scope.filterFPMethod = function (option) {
        $scope.fpMethodFilterOption = option;
    };

    $scope.searchFilterString = "";

    var hpECWithoutFP = "ec_without_fp";
    var defaultContentTemplate = "fp_methods";


    $scope.filterList = function (client) {
        var searchCondition = true;
        var villageCondition = true;
        var fpMethodCondition = true;
        if ($scope.searchFilterString) {
            searchCondition = (client.name.toUpperCase().indexOf($scope.searchFilterString.toUpperCase()) === 0
                || client.ec_number.toUpperCase().indexOf($scope.searchFilterString.toUpperCase()) === 0
                || client.thayi.toUpperCase().indexOf($scope.searchFilterString.toUpperCase()) === 0);
        }
        if ($scope.villageFilterOption.handler) {
            villageCondition = (client.village.toUpperCase() === $scope.villageFilterOption.handler.toUpperCase());
        }
        if ($scope.fpMethodFilterOption.handler) {
            var handlerMethod = $scope[$scope.fpMethodFilterOption.handler];
            fpMethodCondition = handlerMethod(client, $scope.fpMethodFilterOption.id);
        }
        return villageCondition && searchCondition && fpMethodCondition;
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
        $scope.isFPModalOpen = false;
        $scope.currentOptions = option;
    };

    $scope.openFPModal = function (option) {
        $scope.isFPModalOpen = true;
        $scope.isModalOpen = false;
        $scope.currentOptions = option;
    };

    $scope.close = function () {
        $scope.isModalOpen = false;
    };

    $scope.contentTemplate = defaultContentTemplate;
}
