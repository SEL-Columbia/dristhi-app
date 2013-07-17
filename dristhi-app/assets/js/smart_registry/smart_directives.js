'use strict';

/* Directives */


angular.module('smartRegistry.directives', [])
    .directive('srServiceButton', ['$filter', function ($filter) {
        return {
            templateUrl: 'directive_tpls/service_button.html',
            replace: true,
            restrict: 'E',
            scope: {
                schedule: '=srBind',
                clickFn: '&ngClick'
            },
            link: function(scope, elm, attrs) {
                elm.bind('click', scope.clickFn);
            }
        }
    }]);