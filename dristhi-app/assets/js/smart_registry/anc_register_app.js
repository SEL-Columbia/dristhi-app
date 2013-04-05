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
            edd: '2012-06-11T00:00:00.000Z',
            lmp: '25/3/13',
            anc_visits:[
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
            thayi: '4',
            ec_number: '314',
            age: '24',
            husbanad_name: 'Reger_H',
            weeks_pregnant: '2',
            edd: '2012-04-11T00:00:00.000Z',
            lmp: '25/3/13',
            anc_visits:[
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
            thayi: '4',
            ec_number: '314',
            age: '24',
            husbanad_name: 'Reger_H',
            weeks_pregnant: '2',
            edd: '2013-05-11T00:00:00.000Z',
            lmp: '25/3/13',
            anc_visits:[
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
//    - Name
//        - EDD
//        - HRP
//        - Due
//        - In-area/Out of area/Left the place

    $scope.sortOptions = {
        type: "sort",
        options: [
            {
                label: "Name (A to Z)",
                handler: "sortByName",
                reverseSort: false
            },
            {
                label: "EDD",
                handler: "sortByEDD",
                reverseSort: true
            },
            {
                label: "HRP",
                handler: "sortByPriority",
                reverseSort: false
            },
            {
                label: "Due Date",
                handler: "sortByDueDate",
                reverseSort: false
            }
        ]
    };

    $scope.defaultSortOption = $scope.sortOptions.options[0];
    $scope.currentSortOption = $scope.defaultSortOption;
    $scope.sortList = $scope.sortByName;
    $scope.reverseSort = true;

    $scope.sort = function (option) {
        $scope.currentSortOption = option;
        $scope.sortList = $scope[option.handler];
        $scope.reverseSort = option.reverseSort;
    };

    $scope.sortByName = function (item) {
        return item.name;
    };

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
                label: "Out of Area",
                id: "out_of_area",
                handler: "villageFilterByStatus"
            },
            {
                label:"Left the Place",
                id: "left_the_place",
                handler:"villageFilterByStatus"
            }
        ]
    };
    $scope.defaultVillage = $scope.villageOptions.options[0];
    $scope.villageFilterOption = $scope.defaultVillage;
    $scope.villageFilterAll = function(client, optionId){
        return client.locationStatus != "left_the_place";
    };
    $scope.villageFilterByStatus = function(client, optionId){
        return client.locationStatus == optionId;
    };


    //  filters
//    Overview,  ANC Visits, hB/IFA, ANC Visits / TT,  Delivery Plan
//
    $scope.ancServiceOptions = {
        type: "ancService",
        options: [
            {
                label: "Overview",
                handler: "overview"
            },
            {
                label: "ANC Visits",
                handler: "visits"
            },
            {
                label: "hB/IFA",
                handler: "hb_ifa"
            },
            {
                label: "ANC Visits/TT",
                handler: "tt"
            },
            {
                label: "Delivery Plan",
                handler: "delivery"
            }
        ]
    };
    $scope.locationStatusMapping = {
        "out_of_area": 3,
        "left_the_place": 4
    }

    $scope.defaultAncServiceOption = $scope.ancServiceOptions.options[0];
    $scope.ancServiceOption = $scope.defaultAncServiceOption;




    $scope.filterVillage = function (option) {
        $scope.villageFilterOption = option;
    };

    $scope.ancService = function (option) {
        $scope.ancServiceOption = option;
    };

    $scope.searchFilterString = "";

//    set the default

    $scope.contentTemplate =  $scope.ancServiceOptions.options[0].handler;

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
            var handler = $scope.villageFilterOption.handler;
            if($scope.hasOwnProperty(handler) && typeof($scope[handler]) == "function")
            {
                villageCondition = $scope[handler](client, $scope.villageFilterOption.id);
            }
            else
            {
                villageCondition = (client.village == handler);
            }
        }
        if ($scope.ancServiceOption.handler) {
            $scope.contentTemplate = $scope.ancServiceOption.handler;
        }
        return villageCondition && searchCondition;
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


}
