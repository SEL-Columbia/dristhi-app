describe('ANC Service', function () {
    var ancService, smartHelper;

    beforeEach(module("smartRegistry.services"));
    beforeEach(inject(function (ANCService, SmartHelper) {
        ancService = ANCService;
        smartHelper = SmartHelper;
    }));

    describe("Pre-process Clients", function () {
        it("should create a next reminder with normal or urgent alert when it exists.", function () {
            var clients = [
                {
                    name: 'Carolyn',
                    alerts: [
                        {
                            name: 'anc1',
                            date: '2012-10-24', // 2013-04-10T12:40:45.195Z ISO String
                            status: 'normal' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
                        },
                        {
                            name: 'tt2',
                            date: '2012-10-24', // 2013-04-10T12:40:45.195Z ISO String
                            status: 'urgent' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
                        }
                    ],
                    services_provided: []
                }
            ];
            var expectedVisits =
            {
                anc: {
                    next: {
                        name: 'anc1',
                        status: 'normal',
                        visit_date: '2012-10-24'
                    },
                    anc1: {
                        status: 'normal',
                        visit_date: '2012-10-24'
                    }
                },
                tt: {
                    next: {
                        name: 'tt2',
                        status: 'urgent',
                        visit_date: '2012-10-24'
                    },
                    tt2: {
                        status: 'urgent',
                        visit_date: '2012-10-24'
                    }
                },
                ifa: {
                    next: {
                        name: 'ifa1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    ifa1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                hb: {
                    next: {
                        name: 'hb',
                        status: 'upcoming',
                        visit_date: null
                    },
                    hb: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                delivery_plan: {
                    next: {
                        name: 'delivery_plan1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    delivery_plan1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                }
            };

            ancService.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should create an upcoming reminder when only a done service is specified.", function () {
            var clients = [
                {
                    name: 'Carolyn',
                    alerts: [],
                    services_provided: [
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
                anc: {
                    next: {
                        name: 'anc3',
                        status: 'upcoming',
                        visit_date: null
                    },
                    anc3: {
                        status: 'upcoming',
                        visit_date: null
                    },
                    previous: 'anc2',
                    anc2: {
                        status: 'done',
                        visit_date: '2011-10-24',
                        data: {
                            bp: '120/79',
                            weight: '63'
                        }
                    }
                },
                tt: {
                    next: {
                        name: 'tt1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    tt1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                ifa: {
                    next: {
                        name: 'ifa1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    ifa1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                hb: {
                    next: {
                        name: 'hb',
                        status: 'upcoming',
                        visit_date: null
                    },
                    hb: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                delivery_plan: {
                    next: {
                        name: 'delivery_plan1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    delivery_plan1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                }
            };

            ancService.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should create a previous reminder from an existing alert..", function () {
            var clients = [
                {
                    name: 'Carolyn',
                    alerts: [
                        {
                            name: 'anc1',
                            date: '2011-10-24', // 2013-04-10T12:40:45.195Z ISO String
                            status: 'urgent' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
                        },
                        {
                            name: 'anc3',
                            date: '2012-11-24', // 2013-04-10T12:40:45.195Z ISO String
                            status: 'normal' // normal, urgent, done - upcoming is JS side, based on the last visit and if the next one is available
                        }
                    ],
                    services_provided: []
                }
            ];
            var expectedVisits =
            {
                anc: {
                    next: {
                        name: 'anc3',
                        status: 'normal',
                        visit_date: '2012-11-24'
                    },
                    anc3: {
                        status: 'normal',
                        visit_date: '2012-11-24'
                    },
                    previous: 'anc1',
                    anc1: {
                        status: 'urgent',
                        visit_date: '2011-10-24'
                    }
                },
                tt: {
                    next: {
                        name: 'tt1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    tt1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                ifa: {
                    next: {
                        name: 'ifa1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    ifa1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                hb: {
                    next: {
                        name: 'hb',
                        status: 'upcoming',
                        visit_date: null
                    },
                    hb: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                delivery_plan: {
                    next: {
                        name: 'delivery_plan1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    delivery_plan1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                }
            };

            ancService.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should create upcoming for the first milestone if no alerts.", function () {
            var clients = [
                {
                    name: 'Carolyn',
                    alerts: [],
                    services_provided: []
                }
            ];
            var expectedVisits =
            {
                anc: {
                    next: {
                        name: 'anc1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    anc1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                tt: {
                    next: {
                        name: 'tt1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    tt1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                ifa: {
                    next: {
                        name: 'ifa1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    ifa1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                hb: {
                    next: {
                        name: 'hb',
                        status: 'upcoming',
                        visit_date: null
                    },
                    hb: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                delivery_plan: {
                    next: {
                        name: 'delivery_plan1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    delivery_plan1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                }
            };

            ancService.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should create a previous when the last milestone is specified as done", function () {
            var clients = [
                {
                    name: 'Carolyn',
                    alerts: [],
                    services_provided: [
                        {
                            name: 'anc4',
                            date: '2012-05-23',
                            data: {
                                bp: '100/50',
                                weight: '68'
                            }
                        }
                    ]
                }
            ];
            var expectedVisits =
            {
                anc: {
                    next: null,
                    previous: 'anc4',
                    anc4: {
                        status: 'done',
                        visit_date: '2012-05-23',
                        data: {
                            bp: '100/50',
                            weight: '68'
                        }
                    }
                },
                tt: {
                    next: {
                        name: 'tt1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    tt1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                ifa: {
                    next: {
                        name: 'ifa1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    ifa1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                hb: {
                    next: {
                        name: 'hb',
                        status: 'upcoming',
                        visit_date: null
                    },
                    hb: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                delivery_plan: {
                    next: {
                        name: 'delivery_plan1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    delivery_plan1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                }
            };

            ancService.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should create visit data for each service_provided specified.", function () {
            var clients = [
                {
                    name: 'Carolyn',
                    alerts: [],
                    services_provided: [
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
                anc: {
                    next: {
                        name: 'anc3',
                        status: 'upcoming',
                        visit_date: null
                    },
                    anc3: {
                        status: 'upcoming',
                        visit_date: null
                    },
                    previous: 'anc2',
                    anc2: {
                        status: 'done',
                        visit_date: '2011-10-24',
                        data: {
                            bp: '120/79',
                            weight: '63'
                        }
                    },
                    anc1: {
                        status: 'done',
                        visit_date: '2011-08-24',
                        data: {
                            bp: '130/65',
                            weight: '67'
                        }
                    }
                },
                tt: {
                    next: {
                        name: 'tt1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    tt1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                ifa: {
                    next: {
                        name: 'ifa1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    ifa1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                hb: {
                    next: {
                        name: 'hb',
                        status: 'upcoming',
                        visit_date: null
                    },
                    hb: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                delivery_plan: {
                    next: {
                        name: 'delivery_plan1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    delivery_plan1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                }
            };

            ancService.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should show an alert for hb even when a previous hb has been done", function () {
            var clients = [
                {
                    name: 'Carolyn',
                    alerts: [
                        {
                            name: 'hb',
                            date: '2012-03-14',
                            status: 'normal'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'hb',
                            data: [
                                {date: '2012-02-26', dose: '11'},
                                {date: '2012-03-15', dose: '9'}
                            ]
                        }
                    ]
                }
            ];
            var expectedVisits =
            {
                hb: {
                    next: {
                        name: 'hb',
                        status: 'normal',
                        visit_date: '2012-03-14'
                    },
                    previous: 'hb',
                    hb: {
                        status: 'done',
                        visit_date: undefined,
                        data: [
                            {date: '2012-02-26', dose: '11'},
                            {date: '2012-03-15', dose: '9'}
                        ]
                    }
                },
                anc: {
                    next: {
                        name: 'anc1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    anc1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                tt: {
                    next: {
                        name: 'tt1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    tt1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                ifa: {
                    next: {
                        name: 'ifa1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    ifa1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                },
                delivery_plan: {
                    next: {
                        name: 'delivery_plan1',
                        status: 'upcoming',
                        visit_date: null
                    },
                    delivery_plan1: {
                        status: 'upcoming',
                        visit_date: null
                    }
                }
            };

            ancService.preProcessClients(clients);

            expect(clients[0].visits).toEqual(expectedVisits);
        });

        it("should add a days_past_edd variable based on current date and edd", function () {
            var clients = [
                {
                    name: 'Carolyn',
                    edd: '2013-08-21',
                    alerts: [
                        {
                            name: 'hb',
                            date: '2012-03-14',
                            status: 'normal'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'hb',
                            data: [
                                {date: '2012-02-26', dose: '11'},
                                {date: '2012-03-15', dose: '9'}
                            ]
                        }
                    ]
                }
            ];

            var expectedDaysPastEDD = Math.ceil(
                smartHelper.daysBetween(new Date(Date.parse(clients[0].edd)), new Date()));
            ancService.preProcessClients(clients);
            expect(clients[0].days_past_edd).toBeDefined();
            expect(clients[0].days_past_edd).toEqual(expectedDaysPastEDD);
        });
    });
});
