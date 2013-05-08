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

    var getVillageFilterOptions = function () {
        var villageFilterOptions = {
            type:$scope.defaultVillageOptions.type,
            options:$scope.defaultVillageOptions.options.slice(0)
        };
        var villages = $scope.bridge.getVillages();
        villages.forEach(function (village) {
            villageFilterOptions.options.push({
                label:formatText(village.name),
                id:village.name,
                handler:$scope.defaultVillageFilterHandler
            });
        });
        return  villageFilterOptions;
    };

    $scope.villageOptions = getVillageFilterOptions();

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

    $scope.closeModal = function (option) {
        $scope.isModalOpen = false;
        //$scope.currentOptions = null;
    };

    $scope.modalOpts = {
        backdropFade: true,
        dialogFade:true
    };

    $scope.openTestModal = function(msg){
        $scope.modal_message = msg;
        $scope.shouldBeOpen = true;
    };

    $scope.closeTestModal = function(){
        $scope.shouldBeOpen = false;
    };

    $scope.numberOfClientsToShow = 10;

    $scope.loadAll = function () {
        $scope.numberOfClientsToShow = $scope.clients.length;
    };

    $scope.allClientsDisplayed = function (filteredClients) {
        return $scope.numberOfClientsToShow >= filteredClients.length;
    };

    $scope.openForm = function (formName, entityId) {
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

    pageView.onReload(function () {
        $scope.$apply(function () {
            $scope.clients = $scope.bridge.getClients();
            $scope.villageOptions = getVillageFilterOptions();
        });
    });

    $scope.reportingPeriodStart = function (date_str) {
        var src_date;
        if (date_str === undefined)
            src_date = new Date();
        else
            src_date = new Date(Date.parse(date_str));

        var start_date = new Date(src_date.getTime());
        if (src_date.getDate() <= 25) {
            start_date.setMonth(start_date.getMonth() -1, 26)
        }
        else {
            start_date.setMonth(start_date.getMonth(), 26)
        }
        return start_date;
    };

    $scope.reportingPeriodEnd = function (date_str) {
        var src_date;
        if (date_str === undefined)
            src_date = new Date();
        else
            src_date = new Date(Date.parse(date_str));

        var end_date = new Date(src_date.getTime());
        if (src_date.getDate() <= 25) {
            end_date.setMonth(end_date.getMonth(), 25);
        }
        else {
            end_date.setMonth(end_date.getMonth() + 1, 25);
        }
        return end_date;
    };
}]);