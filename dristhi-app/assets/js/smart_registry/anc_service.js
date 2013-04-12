angular.module("smartRegistry.services")
    .service('ANCService', function () {
        var alert_status = {
            NORMAL:"normal",
            URGENT:"urgent",
            DONE:"done",
            UPCOMING:"upcoming"
        };

        var status_css_class = {};
        status_css_class[alert_status.NORMAL] = "btn-due";
        status_css_class[alert_status.URGENT] = "btn-past-due";
        status_css_class[alert_status.DONE] = "btn-done";
        status_css_class[alert_status.UPCOMING] = "btn-upcoming";

        Array.prototype.find = function(func)
        {
        };

        return {
            getClients:function () {
                var clients = [
                    {
                        village:'Chikkabherya',
                        name:'Carolyn',
                        thayi:'4636587',
                        ec_number:'314',
                        age:'24',
                        husband_name:'Billy Bob',
                        weeks_pregnant:'18',
                        edd:'2012-06-11T00:00:00.000Z',
                        lmp:'25/3/13',
                        alerts:[
                            {
                                name:'anc1',
                                date:'24/05', // 2013-04-10T12:40:45.195Z ISO String
                                status:'urgent' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
                            },
                            {
                                name:'anc2',
                                date:'24/07', // 2013-04-10T12:40:45.195Z ISO String
                                status:'urgent' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
                            },
                            {
                                name:'tt1',
                                date:'26/05',
                                status:'normal'
                            }
                        ],
                        anc_visits:[
                            {visit:'1', date:'04/04'},
                            {visit:'2', date:'04/08'},
                            {visit:'3', date:'04/09'}
                        ],
//                        next_anc_visit:{
//                            visit_date:'05/13',
//                            visit_number:'3',
//                            visit_status:'past-due' // one of upcoming, due, past-due or done if there are no more visits
//                        },
                        tt:[
                            {tt:'1', date:'04/04'},
                            {tt:'2', date:'04/08'}
                        ],
                        ifa:{dose:'100', date:'04/04'},
                        days_due:'3',
                        due_message:'Follow Up',
                        isHighPriority:false,
                        locationStatus:"out_of_area"
                    },
                    {
                        village:'Chikkabherya',
                        name:'Roger',
                        thayi:'4636587',
                        ec_number:'314',
                        age:'24',
                        husband_name:'Jacck',
                        weeks_pregnant:'24',
                        edd:'2012-04-11T00:00:00.000Z',
                        lmp:'25/3/13',
                        anc_visits:[
                            {visit:'1', date:'04/04'},
                            {visit:'2', date:'04/08'}
                        ],
//                        next_anc_visit:{
//                            visit_date:'05/13',
//                            visit_number:'3',
//                            visit_status:'due' // one of upcoming, due, past-due or done if there are no more visits
//                        },
                        tt:[
                            {tt:'1', date:'04/04'},
                            {tt:'2', date:'04/08'}
                        ],
                        ifa:{dose:'100', date:'04/04'},
                        days_due:'3',
                        due_message:'Follow Up',
                        isHighPriority:true,
                        locationStatus:"left_the_place"
                    },
                    {
                        village:'Bherya',
                        name:'Larry',
                        thayi:'4636587',
                        ec_number:'314',
                        age:'24',
                        husband_name:'Dickson',
                        weeks_pregnant:'2',
                        edd:'2013-05-11T00:00:00.000Z',
                        lmp:'25/3/13',
                        anc_visits:[
                            {visit:'1', date:'04/04'},
                            {visit:'2', date:'04/08'}
                        ],
//                        next_anc_visit:{
//                            visit_date:'05/13',
//                            visit_number:'3',
//                            visit_status:'upcoming' // one of upcoming, due, past-due or done if there are no more visits
//                        },
                        tt:[
                            {tt:'1', date:'04/04'},
                            {tt:'2', date:'04/08'}
                        ],
                        ifa:{dose:'100', date:'04/04'},
                        days_due:'3',
                        due_message:'Follow Up',
                        isHighPriority:false,
                        locationStatus:"in_area"
                    },
                    {
                        village:'Bherya',
                        name:'Ukanga',
                        thayi:'4636587',
                        ec_number:'315',
                        age:'27',
                        husband_name:'Harshit',
                        weeks_pregnant:'2',
                        edd:'2013-05-11T00:00:00.000Z',
                        lmp:'25/3/13',
                        anc_visits:[
                            {visit:'1', date:'04/04'},
                            {visit:'2', date:'04/08'}
                        ],
                        /*next_anc_visit:{
                         visit_date:'05/13',
                         visit_number:'3',
                         visit_status:'done' // one of upcoming, due, past-due or done if there are no more visits
                         },*/
                        tt:[
                            {tt:'1', date:'04/04'},
                            {tt:'2', date:'04/08'}
                        ],
                        ifa:{dose:'100', date:'04/04'},
                        days_due:'3',
                        due_message:'Follow Up',
                        isHighPriority:false,
                        locationStatus:"in_area"
                    }

                ];

                return clients;
            },
            preProcessClients:function (clients) {
                var schedules =
                    [
                        {
                            "name":"anc",
                            "milestones":["anc1", "anc2", "anc3", "anc4"]
                        },
                        {
                            "name":"tt",
                            "milestones":["tt1", "tt2"]
                        }
                    ];

                var next_reminders1 =
                {
                    "anc":{
                        "previous":"anc2",
                        "previous_date":"1-1-2013",
                        "next":"anc3",
                        "status":"upcoming"
                    },
                    "tt":{
                        "previous":"",
                        "next":"tt1",
                        "previous_date":""
                    }
                };

                var alert = {
                    name:'anc1',
                    start_date:'24/05', // 2013-04-10T12:40:45.195Z ISO String
                    completed_date:'24/05', // 2013-04-10T12:40:45.195Z ISO String
                    status:'done' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
                };

                // transform
                /*if normal or urgent alert exists
                 return that alert
                 if there is a done alert
                 if done is last
                 return done
                 else done + 1
                 if no done alert
                 return first
                 */
                clients.forEach(function (client) {
                        var next_reminders = {};
                        schedules.forEach(function (schedule) {
                            var next_reminder = {};
                            next_reminders[schedule.name] = next_reminder;

                            var alertsOfTypeCurrentSchedule = [];
                            alertsOfTypeCurrentSchedule = client.alerts.filter(function (alert) {
                                return schedule.milestones.indexOf(alert.name) > -1;
                            });

                            for (var i = schedule.milestones.length - 1; i > -1; i--) {
                                var milestone = schedule.milestones[i];
                                var milestone_alerts = alertsOfTypeCurrentSchedule.filter(function (alert) {
                                    return alert.name === milestone;
                                });
                                var milestone_alert;
                                if (milestone_alerts.length > 0) {
                                    milestone_alert = milestone_alerts[0];
                                    if (milestone_alert.status === alert_status.NORMAL || milestone_alert.status === alert_status.URGENT) {
                                        next_reminder.next = milestone_alert.name;
                                        next_reminder.due_date = milestone_alert.date;
                                        next_reminder.status = status_css_class[milestone_alert.status];

                                        next_reminder.previous = null;
                                        next_reminder.previous_date = null;
                                        next_reminder.previous_status = null;

                                        if (i === 0) {
                                            next_reminder.previous = null;
                                            next_reminder.previous_date = null;
                                        }
                                        else if(i > 0)
                                        {
                                            for(var prev_idx = i; prev_idx > 0; prev_idx--){
                                                prev_alerts = alertsOfTypeCurrentSchedule.filter(function(milestone_alert){
                                                   return milestone_alert.name === schedule.name + prev_idx;
                                                });

                                                if(prev_alerts.length > 0)
                                                {
                                                    next_reminder.previous = prev_alerts[0].name;
                                                    next_reminder.previous_date = prev_alerts[0].date;
                                                    next_reminder.previous_status = status_css_class[prev_alerts[0].status];
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    else if(milestone_alert.status === alert_status.DONE)
                                    {
                                        // check if we are on the last milestone
                                        if(i === schedule.milestones.length - 1)
                                        {
                                            next_reminder.next = milestone_alert.name;
                                            next_reminder.due_date = milestone_alert.date;
                                            next_reminder.status = status_css_class[milestone_alert.status];

                                            // find previous
                                            for(var prev_idx = i; prev_idx > 0; prev_idx--){
                                                prev_alerts = alertsOfTypeCurrentSchedule.filter(function(milestone_alert){
                                                    return milestone_alert.name === schedule.name + prev_idx;
                                                });

                                                if(prev_alerts.length > 0)
                                                {
                                                    next_reminder.previous = prev_alerts[0].name;
                                                    next_reminder.previous_date = prev_alerts[0].date;
                                                    next_reminder.previous_status = status_css_class[prev_alerts[0].status];
                                                    break;
                                                }
                                            }
                                        }
                                        else if(i < schedule.milestones.length - 1)
                                        {
                                            next_reminder.next = schedule.name + (i + 2);
                                            next_reminder.due_date = null;
                                            next_reminder.status = status_css_class[alert_status.UPCOMING];

                                            next_reminder.previous = milestone_alert.name;
                                            next_reminder.previous_date = milestone_alert.date;
                                            next_reminder.previous_status = status_css_class[milestone_alert.status];
                                        }
                                        break;
                                    }
                                }
                            }
                            if(next_reminder.status === undefined)
                            {
                                next_reminder.next = schedule.milestones[0];
                                next_reminder.due_date = null;
                                next_reminder.status = status_css_class[alert_status.UPCOMING];

                                next_reminder.previous = null;
                                next_reminder.previous_date = null;
                                next_reminder.previous_status = null;
                            }
                        });
                        client.upcoming_reminders = next_reminders;
                    }
                );
                return clients;
            }
        }
    })
;
