angular.module("smartRegistry.controllers")
    .controller("fpRegisterController", function ($scope) {
        var fpSmartRegistryBridge = new FPRegistryBridge();
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

        $scope.sortByPriority = function (item) {
            return !item.isHighPriority;
        };

        $scope.villageOptions = {
            type: "filterVillage",
            options: [
                {
                    label: "All",
                    id: "",
                    handler: ""
                },
                {
                    label: "Bherya",
                    id: "bherya",
                    handler: "filterByVillageName"
                },
                {
                    label: "Chikkahalli",
                    id: "chikkahalli",
                    handler: "filterByVillageName"
                },
                {
                    label: "Somanahalli Colony",
                    id: "somanahalli_colony",
                    handler: "filterByVillageName"
                },
                {
                    label: "Chikkabherya",
                    id: "chikkabherya",
                    handler: "filterByVillageName"
                },
                {
                    label: "Basavanapura",
                    id: "basavanapura",
                    handler: "filterByVillageName"
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
        $scope.filterByVillageName = function (client, villageOption) {
            return (client.village.toUpperCase() === villageOption.id.toUpperCase());
        };

        $scope.defaultFPOptions = $scope.ecsWithFPMethodServiceModeOptions;
        $scope.ecsWithFPMethodServiceModeOptions = {
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

        $scope.ecsWithoutFPMethodServiceModeOptions = {
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

        $scope.defaultFPMethodOption = $scope.ecsWithFPMethodServiceModeOptions.options[0];
        $scope.serviceModeOption = $scope.defaultFPMethodOption;

        $scope.searchFilterString = "";

        var fpMethodTemplate = "fp_methods";
        var hpECWithoutFPTemplate = "ec_without_fp";
        var defaultContentTemplate = fpMethodTemplate;

        $scope.highPriorityFilter = function (client) {
            $scope.contentTemplate = hpECWithoutFPTemplate;
            return !doesClientUseFpMethod(client) && client.isHighPriority;
        };

        $scope.twoPlusChildrenFilter = function (client) {
            $scope.contentTemplate = hpECWithoutFPTemplate;
            return !doesClientUseFpMethod(client) && client.num_living_children >= "2";
        };

        $scope.oneChildFilter = function (client) {
            $scope.contentTemplate = hpECWithoutFPTemplate;
            return !doesClientUseFpMethod(client) && client.num_living_children === "1";
        };

        $scope.fpMethodFilter = function (client, optionId) {
            $scope.contentTemplate = fpMethodTemplate;
            return client.fp_method === optionId;
        };

        $scope.otherFpMethodFilter = function (client) {
            $scope.contentTemplate = fpMethodTemplate;
            return client.fp_method === "lam"
                || client.fp_method === "traditional"
                || client.fp_method === "centchroman";
        };

        $scope.allECsWithoutFP = function (client) {
            $scope.contentTemplate = hpECWithoutFPTemplate;
            return !doesClientUseFpMethod(client);
        };

        $scope.allECsWithFP = function (client) {
            $scope.contentTemplate = fpMethodTemplate;
            return doesClientUseFpMethod(client);
        };

        var doesClientUseFpMethod = function (client) {
            return (client.fp_method && client.fp_method !== "none");
        };

        $scope.currentOptions = null;
        $scope.currentFPOption = null;

        $scope.isModalOpen = false;

        $scope.isFPModalOpen = false;
        $scope.isFPMethodsOptionSelected = true;

        $scope.filterFPMethod = function (option) {
            $scope.serviceModeOption = option;
        };

        $scope.selectFPMethodOption = function (fpMethodOptionSelected) {
            $scope.isFPMethodsOptionSelected = fpMethodOptionSelected;
            $scope.isFPPrioritizationOptionSelected = !fpMethodOptionSelected;
        };

        $scope.onFPModalOptionClick = function (option, type) {
            $scope[type](option);
            $scope.isFPModalOpen = false;
        };

        $scope.openFPModal = function (option) {
            $scope.isFPModalOpen = true;
            $scope.isModalOpen = false;
            $scope.currentFPOption = option;
        };

        $scope.searchCriteria = function (client, searchFilterString) {
            return (client.name.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0
                || client.ec_number.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0
                || client.thayi.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0);
        };

        $scope.contentTemplate = defaultContentTemplate;
    });
