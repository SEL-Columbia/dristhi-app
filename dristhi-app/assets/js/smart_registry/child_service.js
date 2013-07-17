angular.module("smartRegistry.services")
    .service('ChildService', function (SmartHelper) {
        var schedules =
            [
                {
                    name: "bcg",
                    milestones: ['bcg'],
                    services: ["BCG"],
                    is_list: true
                },
                {
                    name: "opv",
                    milestones: ['opv_0', 'opv_1', 'opv_2'],
                    services: ["OPV"],
                    is_list: true
                },
                {
                    name: "hepb",
                    milestones: ['hepb_0', 'hepb_1', 'hepb_2', 'hepb_3'],
                    services: ["Hepatitis"],
                    is_list: true
                }
            ];

        return {
            schedules: schedules,
            preProcess: function (clients) {
                clients.forEach(function (client) {
                        if(!client.visits)
                            client.visits = {};
                        schedules.forEach(function (schedule) {
                            SmartHelper.preProcessSchedule(client, schedule);
                        });
                    }
                );
            }
        };
    });
