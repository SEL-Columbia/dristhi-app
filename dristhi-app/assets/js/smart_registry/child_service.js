angular.module("smartRegistry.services")
    .service('ChildService', function (SmartHelper) {
        var schedules =
            [
                {
                    name: "bcg",
                    milestones: ['bcg'],
                    services: ["bcg"],
                    is_list: false
                },
                {
                    name: "hepb",
                    milestones: [],
                    services: ["hepb_0"],
                    is_list: false
                },
                {
                    name: "opv",
                    milestones: ['opv_0', 'opv_1', 'opv_2', 'opv_3'],
                    services: ['opv_0', 'opv_1', 'opv_2', 'opv_3'],
                    is_list: false
                },
                {
                    name: "opvbooster",
                    milestones: ['opvbooster'],
                    services: ['opvbooster'],
                    is_list: false
                },
                {
                    name: "pentavalent",
                    milestones: ['pentavalent_1', 'pentavalent_2', 'pentavalent_3'],
                    services: ['pentavalent_1', 'pentavalent_2', 'pentavalent_3']
                },
                {
                    name: 'measles',
                    milestones: ['measles', 'measles_booster'],
                    services: ['measles', 'measles_booster']
                },
                {
                    name: "dpt",
                    milestones: ['dptbooster_1', 'dptbooster_2'],
                    services: ['dptbooster_1', 'dptbooster_2'],
                    is_list: false
                },
                {
                    name: "vitamin_a",
                    milestones: [],
                    services: ['vitamin_a'],
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
                            SmartHelper.preProcessSchedule(client, schedule)
                        });
                    }
                );
            }
        }
    });
