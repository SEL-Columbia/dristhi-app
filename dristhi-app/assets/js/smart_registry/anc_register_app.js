angular.module("smartRegistry.controllers")
    .controller("ancRegisterController", function ($scope, ANCService) {
        $scope.clients = ANCService.preProcessClients(ANCService.getClients());

        $scope.sortOptions = {
            type: "sort",
            options: [
                {
                    label: "Name (A to Z)",
                    handler: "sortByName",
                    sortDescending: false
                },
                {
                    label: "EDD",
                    handler: "sortByEDD",
                    sortDescending: true
                },
                {
                    label: "HRP",
                    handler: "sortByPriority",
                    sortDescending: false
                },
                {
                    label: "Due Date",
                    handler: "sortByDueDate",
                    sortDescending: false
                }
            ]
        };

        $scope.defaultSortOption = $scope.sortOptions.options[0];
        $scope.currentSortOption = $scope.defaultSortOption;
        $scope.sortList = $scope.sortByName;
        $scope.sortDescending = true;

        $scope.sortByEDD = function (item) {
            return item.edd;
        };

        $scope.sortByDueDate = function (item) {
            return item.dueDate;
        };

        $scope.sortByPriority = function (item) {
            return !item.isHighPriority;
        };

        $scope.villageOptions = {
            type: "filterVillage",
            options: [
                {
                    label: "All",
                    id: "",
                    handler: "filterByInAreaLocationStatus"
                },
                {
                    label: "Bherya",
                    id: "bherya",
                    handler: "filterByVillageName"
                },
                {
                    label: "Chikkabherya",
                    id: "chikkabherya",
                    handler: "filterByVillageName"
                },
                {
                    label: "O/A",
                    id: "out_of_area",
                    handler: "filterByLocationStatus"
                },
                {
                    label: "L/P",
                    id: "left_the_place",
                    handler: "filterByLocationStatus"
                }
            ]
        };
        $scope.defaultVillage = $scope.villageOptions.options[0];
        $scope.villageFilterOption = $scope.defaultVillage;
        $scope.filterByInAreaLocationStatus = function (client, option) {
            return client.locationStatus !== "left_the_place";
        };
        $scope.filterByVillageName = function (client, option) {
            return client.village.toUpperCase() === option.id.toUpperCase();
        };
        $scope.filterByLocationStatus = function (client, option) {
            return client.locationStatus === option.id;
        };

        $scope.ancServiceOptions = {
            type: "ancService",
            options: [
                {
                    label: "Overview",
                    id: "overview",
                    handler: "changeContentBasedOnServiceMode"
                },
                {
                    label: "ANC Visits",
                    id: "visits",
                    handler: "changeContentBasedOnServiceMode"
                },
                {
                    label: "hB/IFA",
                    id: "hb_ifa",
                    handler: "changeContentBasedOnServiceMode"
                },
                {
                    label: "ANC Visits/TT",
                    id: "tt",
                    handler: "changeContentBasedOnServiceMode"
                },
                {
                    label: "Delivery Plan",
                    id: "delivery",
                    handler: "changeContentBasedOnServiceMode"
                }
            ]
        };
        $scope.locationStatusMapping = {
            "out_of_area": 3,
            "left_the_place": 4
        };

        $scope.defaultAncServiceOption = $scope.ancServiceOptions.options[0];
        $scope.serviceModeOption = $scope.defaultAncServiceOption;

        $scope.ancService = function (option) {
            $scope.serviceModeOption = option;
        };

        $scope.searchFilterString = "";

        $scope.contentTemplate = $scope.ancServiceOptions.options[0].id;

        $scope.searchCriteria = function (client, searchFilterString) {
            return (client.name.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0
                || client.ec_number.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0
                || client.thayi.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0);
        };

        $scope.changeContentBasedOnServiceMode = function (client, serviceModeOptionId) {
            $scope.contentTemplate = serviceModeOptionId;
            return true;
        };

        $scope.ancStats = function () {
            //var anc_visits = client.anc_visits;
            // max is 4 and we can have gaps in between
            // sort the keys
            angular.forEach(values, function (value, key) {

            });
            return 4;
        };

        $scope.currentOptions = null;

        $scope.isModalOpen = false;
    });
