angular.module("smartRegistry.controllers")
    .controller("ancRegisterController", function ($scope) {
        $scope.clients = [
            {
                village: 'Chikkabherya',
                name: 'Carolyn',
                thayi: '4636587',
                ec_number: '314',
                age: '24',
                husband_name: 'Billy Bob',
                weeks_pregnant: '18',
                edd: '2012-06-11T00:00:00.000Z',
                lmp: '25/3/13',
                anc_visits: [
                    {visit: '1', date: '04/04'},
                    {visit: '2', date: '04/08'},
                    {visit: '3', date: '04/09'}
                ],
                tt: [
                    {tt: '1', date: '04/04'},
                    {tt: '2', date: '04/08'}
                ],
                ifa: {dose: '100', date: '04/04'},
                days_due: '3',
                due_message: 'Follow Up',
                isHighPriority: false,
                locationStatus: "out_of_area"
            },
            {
                village: 'Chikkabherya',
                name: 'Roger',
                thayi: '4636587',
                ec_number: '314',
                age: '24',
                husband_name: 'Jacck',
                weeks_pregnant: '24',
                edd: '2012-04-11T00:00:00.000Z',
                lmp: '25/3/13',
                anc_visits: [
                    {visit: '1', date: '04/04'},
                    {visit: '2', date: '04/08'}
                ],
                tt: [
                    {tt: '1', date: '04/04'},
                    {tt: '2', date: '04/08'}
                ],
                ifa: {dose: '100', date: '04/04'},
                days_due: '3',
                due_message: 'Follow Up',
                isHighPriority: true,
                locationStatus: "left_the_place"
            },
            {
                village: 'Bherya',
                name: 'Larry',
                thayi: '4636587',
                ec_number: '314',
                age: '24',
                husband_name: 'Dickson',
                weeks_pregnant: '2',
                edd: '2013-05-11T00:00:00.000Z',
                lmp: '25/3/13',
                anc_visits: [
                    {visit: '1', date: '04/04'},
                    {visit: '2', date: '04/08'}
                ],
                tt: [
                    {tt: '1', date: '04/04'},
                    {tt: '2', date: '04/08'}
                ],
                ifa: {dose: '100', date: '04/04'},
                days_due: '3',
                due_message: 'Follow Up',
                isHighPriority: false,
                locationStatus: "in_area"
            },
            {
                village: 'Bherya',
                name: 'Ukanga',
                thayi: '4636587',
                ec_number: '315',
                age: '27',
                husband_name: 'Harshit',
                weeks_pregnant: '2',
                edd: '2013-05-11T00:00:00.000Z',
                lmp: '25/3/13',
                anc_visits: [
                    {visit: '1', date: '04/04'},
                    {visit: '2', date: '04/08'}
                ],
                tt: [
                    {tt: '1', date: '04/04'},
                    {tt: '2', date: '04/08'}
                ],
                ifa: {dose: '100', date: '04/04'},
                days_due: '3',
                due_message: 'Follow Up',
                isHighPriority: false,
                locationStatus: "in_area"
            }

        ];

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
                    handler: "villageFilterAll"
                },
                {
                    label: "Bherya",
                    handler: "Bherya"
                },
                {
                    label: "Chikkabherya",
                    handler: "Chikkabherya"
                },
                {
                    label: "O/A",
                    id: "out_of_area",
                    handler: "villageFilterByStatus"
                },
                {
                    label: "L/P",
                    id: "left_the_place",
                    handler: "villageFilterByStatus"
                }
            ]
        };
        $scope.defaultVillage = $scope.villageOptions.options[0];
        $scope.villageFilterOption = $scope.defaultVillage;
        $scope.villageFilterAll = function (client, optionId) {
            return client.locationStatus != "left_the_place";
        };
        $scope.villageFilterByStatus = function (client, optionId) {
            return client.locationStatus == optionId;
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

        $scope.villageFilterCriteria = function (client, villageFilterOption) {
            var handler = villageFilterOption.handler;
            if ($scope.hasOwnProperty(handler) && typeof($scope[handler]) == "function") {
                return $scope[handler](client, $scope.villageFilterOption.id);
            }
            else {
                return (client.village == handler && client.locationStatus != "left_the_place");
            }
        };

        $scope.changeContentBasedOnServiceMode = function (client, serviceModeOptionId) {
            $scope.contentTemplate = serviceModeOptionId;
            return true;
        };

        $scope.currentOptions = null;

        $scope.isModalOpen = false;
    });
