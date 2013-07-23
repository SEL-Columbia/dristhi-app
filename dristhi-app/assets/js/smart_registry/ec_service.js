angular.module("smartRegistry.services")
    .service('ECService', function (SmartHelper) {
        return {
            preProcess: function (clients) {
                clients.forEach(function (client) {
                        // calculate age from DOB
                        client.calculatedAge = SmartHelper.ageFromDOB(
                            new Date(Date.parse(client.dateOfBirth)), new Date());
                        client.children.forEach(function(child){
                           child.calulatedAge = SmartHelper.childsAge(
                               new Date(Date.parse(child.dateOfBirth)), new Date());
                        });
                    }
                );
            }
        }
    });
