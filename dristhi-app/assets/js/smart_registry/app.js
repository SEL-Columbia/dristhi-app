angular.module("smartRegistry.controllers", []);
angular.module("smartRegistry.services", []);
angular.module("smartRegistry.filters", []);
angular.module("smartRegistry", ["ui.bootstrap", "smartRegistry.controllers", "smartRegistry.services",
    "smartRegistry.filters", "smartRegistry.directives"]).config(
    ['$routeProvider', '$dialogProvider', function ($routeProvider, $dialogProvider) {
        /*$routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'MyCtrl1'});
        $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'MyCtrl2'});
        $routeProvider.otherwise({redirectTo: '/view1'});*/
        $dialogProvider.options({backdropFade: false, dialogFade: false});
    }]);

if (Array.prototype.find === undefined) {
    Array.prototype.find = function (func) {
        var idx;
        for (var i = 0; i < this.length; i++) {
            if (func.call(this, this[i])) {
                idx = i;
                break;
            }
        }
        if (idx !== undefined) {
            return this[i];
        }
    };
}

