/* Directives */
angular.module('smartRegistry.directives', [])
    .directive('childLastService', ['$filter', function ($filter) {
        'use strict';
        return {
            template: '<div ng-show="services_provided.length > 0"><span>{{service_date | date:\'dd/MM/yy\'}}</span><br/>{{service_name}}</div>',
            replace: true,
            restrict: 'E',
            scope: {
                services_provided: '=srBind'
            },
            link: function(scope, elm, attrs) {
                if(scope.services_provided.length > 0)
                {
                    var last_service = scope.services_provided.sort(function(a, b){
                        return a.date < b.date ?1:-1;
                    })[0];
                    scope.service_date = $filter('date')(last_service.date, 'dd/MM/yy');
                    scope.service_name = last_service.name;
                }
            }
        };
    }]);