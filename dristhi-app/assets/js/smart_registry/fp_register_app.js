function fpRegisterController($scope) {
    $scope.sortOptions = {
        type: "sort",
        options: [
            {
                label: "Name (A to Z)",
                handler: "name"

            },
            {
                label: "HP",
                handler: "hp"
            }
        ]};
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
            })};
    $scope.defaultVillage = $scope.villageOptions.options[0];
    $scope.villageFilterOption = $scope.defaultVillage;

    $scope.fpMethodOptions = {
        type: "filterFPMethod",
        options: [
            {
                label: "All Methods",
                handler: ""
            },
            {
                label: "Condom",
                handler: "condom"
            },
            {
                label: "DMPA/Injectable",
                handler: "dmpa_injectable"
            },
            {
                label: "IUD",
                handler: "iud"
            },
            {
                label: "OCP",
                handler: "ocp"
            },
            {
                label: "Female Sterilization",
                handler: "female_sterilization"
            },
            {
                label: "Male Sterilization",
                handler: "male_sterilization"
            },
            {
                label: "Others (LAM, Traditional, Centrocrhoman)",
                handler: "others"
            }

        ]
    };
    $scope.defaultFPMethodOption = $scope.fpMethodOptions.options[0];
    $scope.fpMethodFilterOption = $scope.defaultFPMethodOption;

    $scope.searchFilterString = "";

    $scope.filterList = function (patient) {
        var searchCondition = true;
        var villageCondition = true;
        var fpMethodCondition = true;
        if ($scope.searchFilterString != "") {
            searchCondition = (patient.name == $scope.searchFilterString);
        }
        if ($scope.villageFilterOption.handler != "") {
            villageCondition = (patient.village == $scope.villageFilterOption.handler);
        }
        if ($scope.fpMethodFilterOption.handler != "") {
            fpMethodCondition = (patient.fp_method == $scope.fpMethodFilterOption.handler);
        }
        return villageCondition && searchCondition && fpMethodCondition;
    };
    $scope.currentOptions = null;
    $scope.isModalOpen = false;

    $scope.defaultSortOption = $scope.sortOptions.options[0];
    $scope.currentSortOption = $scope.defaultSortOption;
    $scope.sortList = $scope.sortByName;
    $scope.patients = [
        {
            village: 'Chikkabherya',
            name: 'Carolyn',
            thayi: '4',
            ec_number: '314',
            age: '24',
            husband_name: 'Reger_H',
            fp_method: 'ocp',
            fp_method_message: 'lorem ipsum',
            side_effects: 'poops a lot',
            num_pregnancies: '3',
            num_livebirths: '2',
            num_living_children: '1',
            num_stillbirths: '1',
            num_abortions: '1',
            days_due: '3',
            due_message: 'Follow Up',
            isHighPriority: false
        },
        {
            village: 'Bherya',
            name: 'Roger',
            thayi: '1',
            ec_number: '31',
            age: '30',
            husband_name: 'Reger_H',
            fp_method: 'iud',
            side_effects: 'poops a lot',
            num_pregnancies: '3',
            num_livebirths: '2',
            num_living_children: '1',
            num_stillbirths: '1',
            num_abortions: '2',
            days_due: '3',
            due_message: 'Follow Up',
            isHighPriority: true
        },
        {
            village: 'Bherya',
            name: 'Roger1',
            thayi: '1',
            ec_number: '31',
            age: '30',
            husband_name: 'Reger_H',
            fp_method: 'iud',
            side_effects: 'poops a lot',
            num_pregnancies: '3',
            num_livebirths: '2',
            num_living_children: '1',
            num_stillbirths: '1',
            num_abortions: '2',
            days_due: '3',
            due_message: 'Follow Up',
            isHighPriority: false
        }
    ];

    $scope.villages = ["Bherya", "Chikkabherya"];

    $scope.openModal = function (options) {
        $scope.isModalOpen = true;
        $scope.currentOptions = options;
    };

    $scope.sort = function (option) {
        $scope.currentSortOption = option;
        if (option.handler == "name") {
            $scope.sortList = $scope.sortByName;
        } else if (option.handler == "hp") {
            $scope.sortList = $scope.sortByPriority;
        }
    };
    $scope.filterVillage = function (option) {
        $scope.villageFilterOption = option;
    };

    $scope.sortByName = function (item) {
        return item.name;
    };

    $scope.sortByPriority = function (item) {
        return !item.isHighPriority;
    };

    $scope.filterFPMethod = function (option) {
        $scope.fpMethodFilterOption = option;
    }
    $scope.onModalOptionClick = function (option, type) {
        if (type === "sort") {
            $scope.sort(option);
        } else if (type === "filterVillage") {
            $scope.filterVillage(option);
        } else if (type === "filterFPMethod") {
            $scope.filterFPMethod(option);
        }
        $scope.isModalOpen = false;
    };

    $scope.close = function () {
        $scope.isModalOpen = false;
    };
}
