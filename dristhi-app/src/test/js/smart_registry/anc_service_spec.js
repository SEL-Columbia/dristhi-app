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
        it("should create a next_reminder with normal or urgent alert when it exists.", function () {
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
                    ]
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
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(JSON.stringify(clients[0].visits)).toBe(JSON.stringify(expectedVisits));
        });

        it("should create a next_reminder when a previous reminder is provided.", function () {
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
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(JSON.stringify(clients[0].visits)).toBe(JSON.stringify(expectedVisits));
        });

        it("should create an upcoming when only a done reminder is provided.", function () {
            var clients = [
                {
                    name:'Carolyn',
                    alerts:[
                        {
                            name:'anc2',
                            date:'2011-10-24', // 2013-04-10T12:40:45.195Z ISO String
                            status:'done' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
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
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(JSON.stringify(clients[0].visits)).toBe(JSON.stringify(expectedVisits));
        });

        it("should create upcoming for the first milestone if no alerts.", function () {
            var clients = [
                {
                    name:'Carolyn',
                    alerts:[]
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
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(JSON.stringify(clients[0].visits)).toBe(JSON.stringify(expectedVisits));
        });

    });


});