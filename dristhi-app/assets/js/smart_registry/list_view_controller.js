angular.module("smartRegistry.controllers")
    .controller("listViewController", ["$scope", function ($scope) {
        $scope.navigationBridge = new ANMNavigationBridge();
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

        var capitalize = function (text) {
            return text.slice(0, 1).toUpperCase() + text.slice(1);
        };

        var formatText = function (unformattedText) {
            if (typeof unformattedText === "undefined" || unformattedText === null) {
                return "";
            }
            return capitalize(unformattedText).replace(/_/g, " ");
        };

        var addVillageNamesToFilterOptions = function () {
            var villageFilterOptions = $scope.defaultVillageOptions;
            var villages = $scope.bridge.getVillages();
            villages.forEach(function (village) {
                villageFilterOptions.options.push({
                    label: formatText(village.name),
                    id: village.name,
                    handler: $scope.defaultVillageFilterHandler
                });
            });
            return  villageFilterOptions;
        };

        $scope.villageOptions = addVillageNamesToFilterOptions();

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

        $scope.openForm = function (formName, entityId) {
            $scope.navigationBridge = new ANMNavigationBridge();
            $scope.navigationBridge.delegateToFormLaunchView(formName, entityId);
        };

        $scope.capturePicture = function (entityId, entityType) {
            $scope.navigationBridge.takePhoto(entityId, entityType);
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
    }]);