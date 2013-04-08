angular.module("smartRegistry.controllers")
    .controller("listViewController", ["$scope", function ($scope) {
        $scope.sort = function (option) {
            $scope.currentSortOption = option;
            $scope.sortList = $scope[option.handler];
            $scope.sortDescending = option.sortDescending;
        };

        $scope.sortByName = function (item) {
            return item.name;
        };

        $scope.filterVillage = function (option) {
            $scope.villageFilterOption = option;
        };

        $scope.filterList = function (client) {
            var searchCondition = true;
            var villageCondition = true;
            var serviceModeCondition = true;
            if ($scope.searchFilterString) {
                searchCondition = $scope.searchCriteria(client, $scope.searchFilterString);
            }
            if ($scope.villageFilterOption.handler) {
                villageCondition = $scope.villageFilterCriteria(client, $scope.villageFilterOption);
            }
            if ($scope.serviceModeOption.handler) {
                var handlerMethod = $scope[$scope.serviceModeOption.handler];
                serviceModeCondition = handlerMethod(client, $scope.serviceModeOption.id);
            }
            return villageCondition && searchCondition && serviceModeCondition;
        };

        $scope.onModalOptionClick = function (option, type) {
            $scope[type](option);
            $scope.isModalOpen = false;
        };

        $scope.openModal = function (option) {
            $scope.isModalOpen = true;
            $scope.currentOptions = option;
        };
    }]);