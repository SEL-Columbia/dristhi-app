describe('ANC Service', function () {
    var ancService, smartHelper;

    beforeEach(module("smartRegistry.services"));
    beforeEach(inject(function (ANCService, SmartHelper) {
        ancService = ANCService;
        smartHelper = SmartHelper;
    }));

    describe("Pre-process client", function () {
        it("should create a next visit from an existing alert", function () {
            var client = {
                alerts:[
                    {
                        name:"ANC 1",
                        date:'2012-10-24', // 2013-04-10T12:40:45.195Z ISO String
                        status:'normal'
                    }
                ],
                services_provided:[]
            };
            var expected_visits = {
                anc:{
                    next:{
                        name:'ANC 1',
                        status:'normal',
                        visit_date:'2012-10-24'
                    },
                    'ANC 1':{
                        status:'normal',
                        visit_date:'2012-10-24'
                    }
                }
            };

            var schedule = ancService.schedules[0];
            client.visits = {};
            ancService.preProcessSchedule(client, schedule);
            expect(client.visits).toEqual(expected_visits);
        });

        it("should not create a next visit if none is specified", function(){
            var client = {
                alerts:[],
                services_provided:[]
            };

            var expected_visits = {
                anc:{}
            };

            var schedule = ancService.schedules[0];
            client.visits = {};
            ancService.preProcessSchedule(client, schedule);
            expect(client.visits).toEqual(expected_visits);
        });

        it("should create a previous visit if one is specified", function(){
            var client = {
                alerts:[],
                services_provided:[
                    {
                        name: 'ANC 1',
                        date: '2011-10-24',
                        data: {
                            bp: '120/79',
                            weight: '63'
                        }
                    }
                ]
            };

            var expected_visits = {
                anc:{
                    'ANC 1': {
                        visit_date: '2011-10-24',
                        status: ancService.status.COMPLETE,
                        data: {
                            bp: '120/79',
                            weight: '63'
                        }
                    },
                    previous: {
                        name: 'ANC 1',
                        visit_date: '2011-10-24',
                        status: ancService.status.COMPLETE,
                        data: {
                            bp: '120/79',
                            weight: '63'
                        }
                    }
                }
            };

            var schedule = ancService.schedules[0];
            client.visits = {};
            ancService.preProcessSchedule(client, schedule);
            expect(client.visits).toEqual(expected_visits);
        });

        it("should NOT set a 'complete' alert as the previous if its also specified under services_provided", function(){
            var client = {
                alerts:[
                    {
                        name:'ANC 1',
                        status:'complete',
                        date:'2012-10-24'
                    }
                ],
                services_provided:[
                    {
                        name: 'ANC 1',
                        date: '2011-10-24',
                        data: {
                            bp: '120/79',
                            weight: '63'
                        }
                    }
                ]
            };

            var expected_visits = {
                anc:{
                    next: {
                        name: 'ANC 1',
                        status: 'complete',
                        visit_date: '2012-10-24'
                    },
                    'ANC 1': {
                        visit_date: '2011-10-24',
                        status: ancService.status.COMPLETE,
                        data: {
                            bp: '120/79',
                            weight: '63'
                        }
                    }
                }
            };

            var schedule = ancService.schedules[0];
            client.visits = {};
            ancService.preProcessSchedule(client, schedule);
            expect(client.visits).toEqual(expected_visits);
        });

        it("should turn schedules with a list_key defined into a list", function(){
            var client = {
                alerts: [
                    {
                        name: 'IFA 3',
                        date: '2012-06-24',
                        status: 'normal'
                    }
                ],
                services_provided: [
                    {
                        name: 'IFA',
                        date: '2012-05-13',
                        data: {}
                    },
                    {
                        name: 'IFA',
                        date: '2012-05-26',
                        data: {}
                    }
                ]
            };

            var expected_visits = {
                'ifa': {
                    next: {
                        name: 'IFA 3',
                        visit_date: '2012-06-24',
                        status: 'normal'
                    },
                    'IFA 3': {
                        visit_date: '2012-06-24',
                        status: 'normal'
                    },
                    'IFA': [
                        {visit_date: '2012-05-13', status: ancService.status.COMPLETE, data: {}},
                        {visit_date: '2012-05-26', status: ancService.status.COMPLETE, data: {}}
                    ],
                    previous: {
                        name: 'IFA',
                        visit_date: '2012-05-26',
                        status: 'complete',
                        data: {}
                    }
                }
            };

            var schedule = ancService.schedules[2];
            client.visits = {};
            ancService.preProcessSchedule(client, schedule);
            expect(client.visits).toEqual(expected_visits);
        });

        it("should prefer prefer the incomplete schedule over the complete one if multiple alerts from the same schedule exist", function(){
            var client = {
                alerts: [
                    {
                        name: 'IFA 2',
                        date: '2012-06-24',
                        status: 'urgent'
                    },
                    {
                        name: 'IFA 3',
                        date: '2012-06-24',
                        status: 'complete'
                    }
                ],
                services_provided: []
            };

            var expected_visits = {
                ifa: {
                    next: {
                        name: 'IFA 2',
                        visit_date: '2012-06-24',
                        status: 'urgent'
                    },
                    'IFA 2': {
                        visit_date: '2012-06-24',
                        status: 'urgent'
                    },
                    'IFA 3': {
                        visit_date: '2012-06-24',
                        status: 'complete'
                    }
                }
            };

            var ifa_schedule = ancService.schedules[2];
            client.visits = {};
            ancService.preProcessSchedule(client, ifa_schedule);
            expect(client.visits).toEqual(expected_visits);
        });
    });
});
