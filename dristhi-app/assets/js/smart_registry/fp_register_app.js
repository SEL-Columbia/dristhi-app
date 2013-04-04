function fpRegisterController($scope) {
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
            village: 'Chikkabherya',
            name: 'Carolyn1',
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
        },
        {
            village: 'Bherya',
            name: 'Kiran',
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
                label: "Others",
                handler: "others"
            }

        ]
    };
    $scope.defaultFPMethodOption = $scope.fpMethodOptions.options[0];
    $scope.fpMethodFilterOption = $scope.defaultFPMethodOption;
    $scope.filterVillage = function (option) {
        $scope.villageFilterOption = option;
    };
    $scope.filterFPMethod = function (option) {
        $scope.fpMethodFilterOption = option;
    };

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

    $scope.onModalOptionClick = function (option, type) {
        $scope[type](option);
        $scope.isModalOpen = false;
    };

    $scope.openModal = function (options) {
        $scope.isModalOpen = true;
        $scope.currentOptions = options;
    };

    $scope.close = function () {
        $scope.isModalOpen = false;
    };
}
