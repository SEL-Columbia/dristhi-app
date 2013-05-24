angular.module("smartRegistry.services")
    .service('ANCService', function (SmartHelper) {
        var schedules =
            [
                {
                    name: "anc",
                    milestones: ["anc1", "anc2", "anc3", "anc4"]
                },
                {
                    name: "tt",
                    milestones: ["TT 1", "TT 2", "ttbooster"]
                },
                {
                    name: "ifa",
                    milestones: ["ifa1", "ifa2", "ifa3"]
                },
                {
                    name: "hb",
                    milestones: ["hb"]
                },
                {
                    name: 'delivery_plan',
                    milestones: ['delivery_plan1']
                }
            ];

        var alert_status = {
            NORMAL: "normal",
            URGENT: "urgent",
            DONE: "done",
            UPCOMING: "upcoming"
        };

        return {
            preProcessClients: function (clients) {
                clients.forEach(function (client) {
                        var visits = {};
                        schedules.forEach(function (schedule) {
                            var visit = {};
                            var alertsOfTypeCurrentSchedule = client.alerts.filter(function (alert) {
                                return schedule.milestones.indexOf(alert.name) > -1;
                            });

                            for (var i = schedule.milestones.length - 1; i > -1; i--) {
                                var milestone = schedule.milestones[i];
                                var milestone_alert = alertsOfTypeCurrentSchedule.find(function (schedule_alert) {
                                    return schedule_alert.name === milestone;
                                });
                                if (milestone_alert !== undefined) {
                                    var next_milestone = {};
                                    next_milestone.name = milestone_alert.name;
                                    next_milestone.status = milestone_alert.status;
                                    next_milestone.visit_date = milestone_alert.date;
                                    visit.next = next_milestone;
                                    visit[next_milestone.name] = {
                                        status: next_milestone.status,
                                        visit_date: next_milestone.visit_date
                                    };

                                    if (i > 0)// we are not the first milestone, so try to find a previous alert
                                    {
                                        for (var prev_idx = i - 1; prev_idx > -1; prev_idx--) {
                                            var prev_milestone_name = schedule.milestones[prev_idx];
                                            var prev_alert = alertsOfTypeCurrentSchedule.find(function (milestone_alert) {
                                                return milestone_alert.name === prev_milestone_name;
                                            });

                                            if (prev_alert !== undefined) {
                                                visit.previous = prev_alert.name;
                                                var previous_milestone = {};
                                                previous_milestone.status = prev_alert.status;
                                                previous_milestone.visit_date = prev_alert.date;
                                                visit[visit.previous] = previous_milestone;
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                }
                            }

                            var servicesOfTypeCurrentSchedule = client.services_provided.filter(function (service) {
                                return schedule.milestones.indexOf(service.name) !== -1;
                            });
                            for (var i = schedule.milestones.length - 1; i > -1; i--) {
                                var milestone_name = schedule.milestones[i];
                                var service_provided = servicesOfTypeCurrentSchedule.find(function (service) {
                                    return service.name === milestone_name;
                                });

                                if (service_provided !== undefined) {
                                    var service = {};
                                    service.status = alert_status.DONE;
                                    service.visit_date = service_provided.date;
                                    service.data = service_provided.data;
                                    visit[service_provided.name] = service;
                                    if (visit.next === undefined) {
                                        // if we are the last milestone, there is no next
                                        if (i + 1 === schedule.milestones.length) {
                                            visit.next = null;
                                        }
                                        else {
                                            var next_milestone = {};
                                            next_milestone.name = schedule.milestones[i + 1];
                                            next_milestone.status = alert_status.UPCOMING;
                                            next_milestone.visit_date = null;
                                            visit.next = next_milestone;
                                            visit[next_milestone.name] = {
                                                status: next_milestone.status,
                                                visit_date: next_milestone.visit_date
                                            };
                                        }
                                    }

                                    if (visit.previous === undefined) {
                                        visit.previous = service_provided.name;
                                    }
                                }
                            }
                            if (visit.next === undefined) {
                                var next_milestone = {};
                                next_milestone.name = schedule.milestones[0];
                                next_milestone.status = alert_status.UPCOMING;
                                next_milestone.visit_date = null;
                                visit.next = next_milestone;
                                visit[next_milestone.name] = {
                                    status: next_milestone.status,
                                    visit_date: next_milestone.visit_date
                                };
                            }
                            visits[schedule.name] = visit;
                        });
                        client.visits = visits;
                        // calculate days between today and EDD
                        var days_past_edd;
                        var edd_date = Date.parse(client.edd);
                        if (edd_date)
                            client.days_past_edd = Math.ceil(SmartHelper.daysBetween(new Date(edd_date), new Date()));
                    }
                );
                return clients;
            }
        }
    });
