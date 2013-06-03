angular.module("smartRegistry.controllers")
    .controller("ancRegisterController", function ($scope, ANCService) {
        $scope.bridge = new ANCRegistryBridge();
        $scope.getClients = function () {
            return ANCService.preProcess($scope.bridge.getClients());
        };

        $scope.clients = $scope.getClients();

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

        $scope.defaultVillageOptions = {
            type: "filterVillage",
            options: [
                {
                    label: "All",
                    id: "",
                    handler: "filterByInAreaLocationStatus"
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

        $scope.defaultVillageFilterHandler = "filterByVillageName";

        $scope.defaultVillage = $scope.defaultVillageOptions.options[0];
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
                    label: "Hb/IFA",
                    id: "hb_ifa",
                    handler: "changeContentBasedOnServiceMode"
                },
                {
                    label: "TT",
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
            "out_of_area": 1,
            "left_the_place": 2
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

        $scope.sumIFATablets = function (ifaData) {
            var numTablets = 0;
            if(ifaData['IFA'] !== undefined)
            {
                ifaData['IFA'].forEach(function (ifa) {
                    numTablets += ifa.data['dose'] || 0;
                });
            }
            return numTablets;
        };

        $scope.openANCFormModal = function(clientEntityId) {
            $scope.currentClientEntityId = clientEntityId;
            $scope.isANCFormModalOpen = true;
        };

        $scope.closeANCFormModal = function() {
            $scope.isANCFormModalOpen = false;
        };

        $scope.weeksPregnant = function(client) {
            // get lmp data
            var lmp = Date.parse(client.lmp);
            if(lmp)
            {
                var lmp_date = new Date(lmp);
                var today = new Date();
                return Math.round((today - lmp_date) / 1000 / 60 / 60 / 24 / 7);
            }
        };

        $scope.microformSchedules = ['tt', 'ifa'];

        $scope.useMicroForm = function(schedule) {
            if($scope.microformSchedules.indexOf(schedule) !== -1)
            {
                return true;
            }
            return false;
        };

        $scope.milestoneForm = {
            'ANC 1': 'anc_visit',
            'ANC 2': 'anc_visit',
            'ANC 3': 'anc_visit',
            'ANC 4': 'anc_visit',
            'TT 1': 'tt_1',
            'TT 2': 'tt_2',
            'TT Booster': 'tt_booster',
            'IFA 1': 'schedule',
            'IFA 2': 'schedule',
            'IFA 3': 'schedule'
        };
    });
