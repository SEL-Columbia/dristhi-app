angular.module("smartRegistry.services")
    .service('ECService', function (SmartHelper) {
        return {
            preProcess: function (clients) {
                clients.forEach(function (client) {
                        client.children.forEach(function(child){
                           child.calulatedAge = SmartHelper.childsAge(Date.parse(child.dateOfBirth), new Date());
                        });
                    }
                );
            }
        }
    });
