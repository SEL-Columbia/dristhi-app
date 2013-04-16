angular.module("smartRegistry.controllers")
    .controller("listViewController", ["$scope", function ($scope) {
        $scope.sort = function (option) {
            $scope.currentSortOption = option;
            $scope.sortList = $scope[option.handler];
            $scope.sortDescending = option.sortDescending;
        };

        $scope.sortByName = function (client) {
            return client.name;
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
                var handlerMethod = $scope[$scope.villageFilterOption.handler];
                villageCondition = handlerMethod(client, $scope.villageFilterOption);
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

        $scope.numberOfClientsToShow = 10;

        $scope.loadAll = function () {
            $scope.numberOfClientsToShow = $scope.clients.length;
        };

        $scope.allClientsDisplayed = function (filteredClients) {
            return $scope.numberOfClientsToShow >= filteredClients.length;
        };
    }]);