angular.module("smartRegistry.services")
    .service('ANCService', function () {
        this.something = "Something";
        var schedules =
            [
                {
                    name:"anc",
                    milestones:["anc1", "anc2", "anc3", "anc4"]
                },
                {
                    name:"tt",
                    milestones:["tt1", "tt2", "ttbooster"]
                },
                {
                    name:"ifa",
                    milestones:["ifa1", "ifa2", "ifa3"]
                },
                {
                    name: "hb",
                    milestones:["hb"]
                },
                {
                    name: 'delivery_plan',
                    milestones: ['delivery_plan1']
                }
            ];

        var alert_status = {
            NORMAL:"normal",
            URGENT:"urgent",
            DONE:"done",
            UPCOMING:"upcoming"
        };

        if(Array.prototype.find === undefined)
        {
            Array.prototype.find = function(func)
            {
                var idx;
                for(var i = 0; i < this.length; i++)
                {
                    if(func.call(this, this[i]))
                    {
                        idx = i;
                        break;
                    }
                }
                if(idx !== undefined)
                {
                    return this[i];
                }
            };
        }

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
                                date:'24/07',
                                status:'urgent'
                            },
                            {
                                name:'tt2',
                                date:'24/07',
                                status:'urgent'
                            },
                            {
                                name:'ifa2',
                                date:'26/05',
                                status:'normal'
                            },
                            {
                                name: 'delivery_plan1',
                                date: '2012-05-18',
                                status: 'urgent'
                            }
                        ],
                        services_provided:[
                            {
                                name: 'tt1',
                                date:'04/04',
                                data:{
                                    dose:'80'
                                }
                            },
                            {
                                name:'ifa1',
                                date:'04/04',
                                data:{
                                    dose:100
                                }
                            },
                            {
                                name: 'hb',
                                data:[
                                    {date: '2012-05-27', level: 14},
                                    {date: '2012-06-13', level: 10},
                                    {date: '2012-05-27', level: 18},
                                    {date: '2012-05-27', level: 12}
                                ]
                            }
                        ],
                        days_due:'3',
                        due_message:'Follow Up',
                        isHighPriority:true,
                        locationStatus:"out_of_area",
                        isHighRisk: true,
                        caste: "sc"
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
                        alerts:[
                            {
                                name:'anc2',
                                date:'24/07',
                                status:'normal'
                            },
                            {
                                name:'tt1',
                                date:'26/05',
                                status:'urgent'
                            }
                        ],
                        services_provided:[
                            {
                                name:'anc1',
                                date:'04/04',
                                data:{
                                    bp:'120/80',
                                    weight:'55'
                                }
                            },
                            {
                                name:'ifa1',
                                date:'04/04',
                                data:{
                                    dose:100
                                }
                            }
                        ],
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
                        alerts:
                        [
                            {
                                name:'anc2',
                                date:'24/05',
                                status:'normal'
                            },
                            {
                                name:'tt1',
                                date:'26/05',
                                status:'normal'
                            },
                            {
                                name:'ifa2',
                                date:'26/05',
                                status:'urgent'
                            }
                        ],
                        services_provided:
                        [
                            {
                                name: 'anc1',
                                date: '04/04',
                                data: {
                                    bp: '120/80',
                                    weight: '55'
                                }
                            },
                            {
                                name: 'ttbooster',
                                date: '04/04',
                                data: {
                                    dose: 50
                                }
                            },
                            {
                                name: 'ifa1',
                                date: '04/04',
                                data:
                                {
                                    dose: 100
                                }
                            },
                            {
                                name: 'hb',
                                data:[
                                    {date: '2012-05-27', level: 6}
                                ]
                            },
                            {
                                name: 'delivery_plan1',
                                date: '2012-09-13',
                                data:{
                                    asha_name: 'Robin',
                                    companion: 'Matt',
                                    delivery_site: null,
                                    transport: null,
                                    contact_no: '9403292920',
                                    review_risks: null
                                }
                            }
                        ],
                        days_due:'3',
                        due_message:'Follow Up',
                        isHighPriority:true,
                        locationStatus:"in_area",
                        economicStatus: "bpl",
                        caste: "st"
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
                        alerts:[
                            {
                                name:'tt2',
                                date:'26/05',
                                status:'normal'
                            }
                        ],
                        services_provided:[
                            {
                                name:'anc2',
                                date:'04/08',
                                data:{
                                    bp:'115/90',
                                    weight:'98'
                                }
                            },
                            {
                                name:'anc4',
                                date:'04/08',
                                data:{
                                    bp:'120/90',
                                    weight:'95'
                                }
                            },
                            {
                                name:'tt1',
                                date:'04/04',
                                data:{
                                    dose: 100
                                }
                            },
                            {
                                name:'ifa1',
                                date:'2012-04-24',
                                data:{
                                    dose:50
                                }
                            },
                            {
                                name:'ifa2',
                                date:'2012-05-24',
                                data:{
                                    dose:80
                                }
                            },
                            {
                                name:'ifa3',
                                date:'2012-06-17',
                                data:{
                                    dose:100
                                }
                            },
                            {
                                name: 'hb',
                                data:[
                                    {date: '2012-05-27', level: 14},
                                    {date: '2012-06-13', level: 10}
                                ]
                            }
                        ],
                        days_due:'3',
                        due_message:'Follow Up',
                        isHighPriority:false,
                        locationStatus:"in_area"
                    },
                    {
                        village:'Bherya',
                        name:'Moses',
                        thayi:'4636543',
                        ec_number:'429',
                        age:'35',
                        husband_name:'Kiran',
                        weeks_pregnant:'5',
                        edd:'2013-05-11T00:00:00.000Z',
                        lmp:'25/3/13',
                        alerts:[
                            {
                                name:'tt2',
                                date:'26/05',
                                status:'normal'
                            },
                            {
                                name: 'delivery_plan1',
                                date: '26/05',
                                status: 'normal'
                            }
                        ],
                        services_provided:[
                            {
                                name:'anc1',
                                date:'04/04',
                                data:{
                                    bp:'120/80',
                                    weight:'95'
                                }
                            },
                            {
                                name:'anc2',
                                date:'04/08',
                                data:{
                                    bp:'115/90',
                                    weight:'98'
                                }
                            },
                            {
                                name:'tt1',
                                date:'04/04',
                                data:{
                                    dose: 100
                                }
                            },
                            {
                                name:'ifa1',
                                date:'2012-04-24',
                                data:{
                                    dose:50
                                }
                            },
                            {
                                name:'ifa2',
                                date:'2012-05-24',
                                data:{
                                    dose:20
                                }
                            },
                            {
                                name: 'hb',
                                data:[
                                    {date: '2012-06-13', level: 10}
                                ]
                            }
                        ],
                        days_due:'3',
                        due_message:'Follow Up',
                        isHighPriority:false,
                        locationStatus:"in_area"
                    }
                ];

                return clients;
            },
            preProcessClients:function (clients) {
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

                                    if(i > 0)// we are not the first milestone, so try to find a previous alert
                                    {
                                        for(var prev_idx = i-1; prev_idx > -1; prev_idx--){
                                            var prev_milestone_name = schedule.milestones[prev_idx];
                                            var prev_alert = alertsOfTypeCurrentSchedule.find(function(milestone_alert){
                                               return milestone_alert.name === prev_milestone_name;
                                            });

                                            if(prev_alert !== undefined)
                                            {
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

                            var servicesOfTypeCurrentSchedule = client.services_provided.filter(function(service){
                                return schedule.milestones.indexOf(service.name) !== -1;
                            });
                            for (var i = schedule.milestones.length - 1; i > -1; i--) {
                                var milestone_name = schedule.milestones[i];
                                var service_provided = servicesOfTypeCurrentSchedule.find(function(service){
                                    return service.name === milestone_name;
                                });

                                if(service_provided !== undefined)
                                {
                                    var service = {};
                                    service.status = alert_status.DONE;
                                    service.visit_date = service_provided.date;
                                    service.data = service_provided.data;
                                    visit[service_provided.name] = service;
                                    if(visit.next === undefined)
                                    {
                                        // if we are the last milestone, there is no next
                                        if(i + 1 === schedule.milestones.length)
                                        {
                                            visit.next = null;
                                        }
                                        else
                                        {
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

                                    if(visit.previous === undefined)
                                    {
                                        visit.previous = service_provided.name;
                                    }
                                }
                            }
                            if(visit.next === undefined)
                            {
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
                    }
                );
                return clients;
            }
        }
    });
