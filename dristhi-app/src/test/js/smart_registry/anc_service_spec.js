describe('ANC Service', function () {

    var ancSrvc;

    beforeEach(module("smartRegistry.services"));
    beforeEach(inject(function (ANCService) {
        ancSrvc = ANCService;
    }));

    it("should load clients from service", function () {
        var clients = ancSrvc.getClients();

        expect(clients.length).toBeGreaterThan(0);
    });

    describe("Pre-process Clients", function () {
        it("should create a next reminder with normal or urgent alert when it exists.", function () {
            var clients = [
                {
                    name:'Carolyn',
                    alerts:[
                        {
                            name:'anc1',
                            date:'2012-10-24', // 2013-04-10T12:40:45.195Z ISO String
                            status:'normal' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
                        },
                        {
                            name:'tt2',
                            date:'2012-10-24', // 2013-04-10T12:40:45.195Z ISO String
                            status:'urgent' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
                        }
                    ],
                    services_provided:[]
                }
            ];
            var expectedVisits =
            {
                anc:
                {
                    next: 'anc1',
                    anc1:
                    {
                        status: 'btn-due',
                        visit_date: '2012-10-24'
                    }
                },
                tt:
                {
                    next: 'tt2',
                    tt2:
                    {
                        status: 'btn-past-due',
                        visit_date: '2012-10-24'
                    }
                },
                ifa:
                {
                    next: 'ifa1',
                    ifa1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                hb:
                {
                    next: 'hb1',
                    hb1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                delivery_plan:
                {
                    next: 'delivery_plan1',
                    delivery_plan1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should create an upcoming reminder when only a done service is specified.", function () {
            var clients = [
                {
                    name:'Carolyn',
                    alerts:[],
                    services_provided:[
                        {
                            name: 'anc2',
                            date: '2011-10-24',
                            data: {
                                bp: '120/79',
                                weight: '63'
                            }
                        }
                    ]
                }
            ];
            var expectedVisits =
            {
                anc:
                {
                    next: 'anc3',
                    anc3:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    },
                    previous: 'anc2',
                    anc2:
                    {
                        status: 'btn-done',
                        visit_date: '2011-10-24',
                        data:{
                            bp: '120/79',
                            weight: '63'
                        }
                    }
                },
                tt:{
                    next:'tt1',
                    tt1:{
                        status:'btn-upcoming',
                        visit_date:null
                    }
                },
                ifa:{
                    next:'ifa1',
                    ifa1:{
                        status:'btn-upcoming',
                        visit_date:null
                    }
                },
                hb:
                {
                    next: 'hb1',
                    hb1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                delivery_plan:
                {
                    next: 'delivery_plan1',
                    delivery_plan1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should create a previous reminder from an existing alert..", function () {
            var clients = [
                {
                    name:'Carolyn',
                    alerts:[
                        {
                            name:'anc1',
                            date:'2011-10-24', // 2013-04-10T12:40:45.195Z ISO String
                            status:'urgent' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
                        },
                        {
                            name:'anc3',
                            date:'2012-11-24', // 2013-04-10T12:40:45.195Z ISO String
                            status:'normal' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
                        }
                    ],
                    services_provided:[]
                }
            ];
            var expectedVisits =
            {
                anc:
                {
                    next: 'anc3',
                    anc3:
                    {
                        status: 'btn-due',
                        visit_date: '2012-11-24'
                    },
                    previous: 'anc1',
                    anc1:
                    {
                        status: 'btn-past-due',
                        visit_date: '2011-10-24'
                    }
                },
                tt:
                {
                    next: 'tt1',
                    tt1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                ifa:
                {
                    next: 'ifa1',
                    ifa1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                hb:
                {
                    next: 'hb1',
                    hb1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                delivery_plan:
                {
                    next: 'delivery_plan1',
                    delivery_plan1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should create upcoming for the first milestone if no alerts.", function () {
            var clients = [
                {
                    name:'Carolyn',
                    alerts:[],
                    services_provided:[]
                }
            ];
            var expectedVisits =
            {
                anc:
                {
                    next: 'anc1',
                    anc1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                tt:
                {
                    next: 'tt1',
                    tt1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                ifa:
                {
                    next: 'ifa1',
                    ifa1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                hb:
                {
                    next: 'hb1',
                    hb1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                delivery_plan:
                {
                    next: 'delivery_plan1',
                    delivery_plan1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should create a previous when the last milestone is specified as done", function(){
            var clients = [
                {
                    name:'Carolyn',
                    alerts:[],
                    services_provided:[
                        {
                            name: 'anc4',
                            date: '2012-05-23',
                            data:
                            {
                                bp: '100/50',
                                weight: '68'
                            }
                        }
                    ]
                }
            ];
            var expectedVisits =
            {
                anc:
                {
                    next: null,
                    previous: 'anc4',
                    anc4:
                    {
                        status: 'btn-done',
                        visit_date: '2012-05-23',
                        data:
                        {
                            bp: '100/50',
                            weight: '68'
                        }
                    }
                },
                tt:{
                    next:'tt1',
                    tt1:{
                        status:'btn-upcoming',
                        visit_date:null
                    }
                },
                ifa:{
                    next:'ifa1',
                    ifa1:{
                        status:'btn-upcoming',
                        visit_date:null
                    }
                },
                hb:
                {
                    next: 'hb1',
                    hb1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                delivery_plan:
                {
                    next: 'delivery_plan1',
                    delivery_plan1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should create visit data for each service_provided specified.", function(){
            var clients = [
                {
                    name:'Carolyn',
                    alerts:[],
                    services_provided:[
                        {
                            name: 'anc2',
                            date: '2011-10-24',
                            data: {
                                bp: '120/79',
                                weight: '63'
                            }
                        },
                        {
                            name: 'anc1',
                            date: '2011-08-24',
                            data: {
                                bp: '130/65',
                                weight: '67'
                            }
                        }
                    ]
                }
            ];
            var expectedVisits =
            {
                anc:
                {
                    next: 'anc3',
                    anc3:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    },
                    previous: 'anc2',
                    anc2:
                    {
                        status: 'btn-done',
                        visit_date: '2011-10-24',
                        data:{
                            bp: '120/79',
                            weight: '63'
                        }
                    },
                    anc1:
                    {
                        status: 'btn-done',
                        visit_date: '2011-08-24',
                        data:{
                            bp: '130/65',
                            weight: '67'
                        }
                    }
                },
                tt:{
                    next:'tt1',
                    tt1:{
                        status:'btn-upcoming',
                        visit_date:null
                    }
                },
                ifa:{
                    next:'ifa1',
                    ifa1:{
                        status:'btn-upcoming',
                        visit_date:null
                    }
                },
                hb:
                {
                    next: 'hb1',
                    hb1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                },
                delivery_plan:
                {
                    next: 'delivery_plan1',
                    delivery_plan1:
                    {
                        status: 'btn-upcoming',
                        visit_date: null
                    }
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

    });

});