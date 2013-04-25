angular.module("smartRegistry.controllers")
    .controller("fpRegisterController", function ($scope) {
        $scope.bridge = new FPRegistryBridge();
        $scope.client_type = "woman";
        $scope.clients = $scope.bridge.getFPClients();

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
        $scope.sortByPriority = function (client) {
            return !client.isHighPriority;
        };

        $scope.defaultVillageOptions = {
            type: "filterVillage",
            options: [
                {
                    label: "All",
                    id: "",
                    handler: ""
                }
            ]
        };
        $scope.defaultVillageFilterHandler = "filterByVillageName";

        $scope.defaultVillage = $scope.defaultVillageOptions.options[0];
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
                    id: "filterByFPMethodBeingUsed",
                    handler: "filterByFPMethodBeingUsed"
                },
                {
                    label: "Condom",
                    id: "condom",
                    handler: "filterByFPMethod"
                },
                {
                    label: "DMPA/Injectable",
                    id: "dmpa_injectable",
                    handler: "filterByFPMethod"
                },
                {
                    label: "IUD",
                    id: "iud",
                    handler: "filterByFPMethod"
                },
                {
                    label: "OCP",
                    id: "ocp",
                    handler: "filterByFPMethod"
                },
                {
                    label: "Female Sterilization",
                    id: "female_sterilization",
                    handler: "filterByFPMethod"
                },
                {
                    label: "Male Sterilization",
                    id: "male_sterilization",
                    handler: "filterByFPMethod"
                },
                {
                    label: "Others",
                    id: "others",
                    handler: "filterByFPMethodOther"
                }
            ]
        };
        $scope.ecsWithoutFPMethodServiceModeOptions = {
            type: "filterFPMethod",
            options: [
                {
                    label: "All EC",
                    id: "filterByNoFPMethod",
                    handler: "filterByNoFPMethod"
                },
                {
                    label: "High Priority",
                    id: "hp",
                    handler: "filterByPriority"
                },
                {
                    label: "2+ Children",
                    id: "2+_Children",
                    handler: "filterByNumberOfChildrenGreaterThanOne"
                },
                {
                    label: "1 Child",
                    id: "1_Child",
                    handler: "filterByNumberOfChildrenEqualToOne"
                }

            ]
        };
        $scope.defaultFPMethodOption = $scope.ecsWithFPMethodServiceModeOptions.options[0];
        $scope.serviceModeOption = $scope.defaultFPMethodOption;
        var fpMethodTemplate = "fp_methods";
        var hpECWithoutFPTemplate = "ec_without_fp";
        $scope.filterByPriority = function (client) {
            $scope.contentTemplate = hpECWithoutFPTemplate;
            return !doesClientUseFpMethod(client) && client.isHighPriority;
        };
        $scope.filterByNumberOfChildrenGreaterThanOne = function (client) {
            $scope.contentTemplate = hpECWithoutFPTemplate;
            return !doesClientUseFpMethod(client) && client.num_living_children >= "2";
        };
        $scope.filterByNumberOfChildrenEqualToOne = function (client) {
            $scope.contentTemplate = hpECWithoutFPTemplate;
            return !doesClientUseFpMethod(client) && client.num_living_children === "1";
        };
        $scope.filterByFPMethod = function (client, optionId) {
            $scope.contentTemplate = fpMethodTemplate;
            return client.fp_method === optionId;
        };
        $scope.filterByFPMethodOther = function (client) {
            $scope.contentTemplate = fpMethodTemplate;
            return client.fp_method === "lam"
                || client.fp_method === "traditional"
                || client.fp_method === "centchroman";
        };
        $scope.filterByNoFPMethod = function (client) {
            $scope.contentTemplate = hpECWithoutFPTemplate;
            return !doesClientUseFpMethod(client);
        };
        $scope.filterByFPMethodBeingUsed = function (client) {
            $scope.contentTemplate = fpMethodTemplate;
            return doesClientUseFpMethod(client);
        };
        var doesClientUseFpMethod = function (client) {
            return (client.fp_method && client.fp_method !== "none");
        };

        $scope.currentOptions = null;
        $scope.currentFPOption = null;
        $scope.contentTemplate = fpMethodTemplate;
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

        $scope.searchFilterString = "";
        $scope.searchCriteria = function (client, searchFilterString) {
            return (client.name.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0
                || client.ec_number.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0
                || client.thayi.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0);
        };

        $scope.reloadPhoto = function (entityId, photoPath) {
            $scope.$apply(function () {
                $scope.clients.filter(function (client) {
                    return client.entity_id === entityId;
                })[0]['photo_path'] = photoPath;
            });
        };

        pageView.onReloadPhoto(function (entityId, photoPath) {
            $scope.reloadPhoto(entityId, photoPath);
        });
    });
