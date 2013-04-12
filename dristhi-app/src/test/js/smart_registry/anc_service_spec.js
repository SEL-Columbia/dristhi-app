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
            var expectedUpcomingReminders =
            {
                anc:{
                    next:"anc1",
                    due_date:"2012-10-24",
                    status:"btn-due",
                    previous:null,
                    previous_date:null,
                    previous_status: null
                },
                tt:{
                    next:"tt2",
                    due_date: "2012-10-24",
                    status:"btn-past-due",
                    previous:null,
                    previous_date:null,
                    previous_status: null
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(JSON.stringify(clients[0].upcoming_reminders)).toBe(JSON.stringify(expectedUpcomingReminders));
        });

        it("should create a next_reminder with previous reminder.", function () {
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
            var expectedUpcomingReminders =
            {
                anc:{
                    next:"anc3",
                    due_date:"2012-11-24",
                    status:"btn-due",
                    previous:"anc1",
                    previous_date:"2011-10-24",
                    previous_status: "btn-past-due"
                },
                tt:{
                    next:"tt1",
                    due_date: null,
                    status:"btn-upcoming",
                    previous:null,
                    previous_date:null,
                    previous_status: null
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(JSON.stringify(clients[0].upcoming_reminders)).toBe(JSON.stringify(expectedUpcomingReminders));
        });

        it("should create an upcoming with a done reminder.", function () {
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
            var expectedUpcomingReminders =
            {
                anc:{
                    next:"anc3",
                    due_date:null,
                    status:"btn-upcoming",
                    previous:"anc2",
                    previous_date: "2011-10-24",
                    previous_status: "btn-done"
                },
                tt:{
                    next:"tt1",
                    due_date: null,
                    status:"btn-upcoming",
                    previous:null,
                    previous_date:null,
                    previous_status: null
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(JSON.stringify(clients[0].upcoming_reminders)).toBe(JSON.stringify(expectedUpcomingReminders));
        });

        it("should create upcoming for the first milestone if no alerts.", function () {
            var clients = [
                {
                    name:'Carolyn',
                    alerts:[]
                }
            ];
            var expectedUpcomingReminders =
            {
                anc:{
                    next:"anc1",
                    due_date:null,
                    status:"btn-upcoming",
                    previous: null,
                    previous_date: null,
                    previous_status: null
                },
                tt:{
                    next:"tt1",
                    due_date: null,
                    status:"btn-upcoming",
                    previous:null,
                    previous_date:null,
                    previous_status: null
                }
            };

            ancSrvc.preProcessClients(clients);

            expect(JSON.stringify(clients[0].upcoming_reminders)).toBe(JSON.stringify(expectedUpcomingReminders));
        });

    });


});