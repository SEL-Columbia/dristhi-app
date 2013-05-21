function ANCRegistryBridge() {
    var context = window.context;
    if (typeof context === "undefined" && typeof FakeANCSmartRegistryContext !== "undefined") {
        context = new FakeANCSmartRegistryContext();
    }

    return {
        getClients: function () {
            return JSON.parse(context.get());
        },
        getVillages: function () {
            return JSON.parse(context.villages());
        }
    };
}

function FakeANCSmartRegistryContext() {
    return {
        get: function () {
            return JSON.stringify([
                {
                    village: 'Chikkabherya',
                    name: 'Carolyn',
                    thayi: '4636587',
                    ec_number: '314',
                    age: '24',
                    husband_name: 'Billy Bob',
                    weeks_pregnant: '18',
                    edd: '2013-04-28T00:00:00.000Z',
                    lmp: '25/3/13',
                    alerts: [
                        {
                            name: 'anc1',
                            date: '24/07',
                            status: 'urgent'
                        },
                        {
                            name: 'tt2',
                            date: '24/07',
                            status: 'urgent'
                        },
                        {
                            name: 'ifa2',
                            date: '26/05',
                            status: 'normal'
                        },
                        {
                            name: 'delivery_plan1',
                            date: '2012-05-18',
                            status: 'urgent'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'tt1',
                            date: '04/04',
                            data: {
                                dose: '80'
                            }
                        },
                        {
                            name: 'ifa1',
                            date: '04/04',
                            data: {
                                dose: 100
                            }
                        },
                        {
                            name: 'hb',
                            data: [
                                {date: '2012-05-27', level: 14},
                                {date: '2012-06-13', level: 10},
                                {date: '2012-05-27', level: 18},
                                {date: '2012-05-27', level: 12}
                            ]
                        }
                    ],
                    days_due: '3',
                    due_message: 'Follow Up',
                    isHighPriority: true,
                    locationStatus: "out_of_area",
                    "photo_path": "../../img/woman-placeholder.png",
                    isHighRisk: true,
                    caste: "sc"
                },
                {
                    village: 'Chikkabherya',
                    name: 'Roger',
                    thayi: '4636587',
                    ec_number: '314',
                    age: '24',
                    husband_name: 'Jacck',
                    weeks_pregnant: '24',
                    edd: '2012-04-11T00:00:00.000Z',
                    lmp: '25/3/13',
                    alerts: [
                        {
                            name: 'anc2',
                            date: '24/07',
                            status: 'normal'
                        },
                        {
                            name: 'tt1',
                            date: '26/05',
                            status: 'urgent'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'anc1',
                            date: '04/04',
                            data: {
                                bp: '120/80',
                                weight: '55'
                            }
                        },
                        {
                            name: 'ifa1',
                            date: '04/04',
                            data: {
                                dose: 100
                            }
                        }
                    ],
                    days_due: '3',
                    due_message: 'Follow Up',
                    isHighPriority: true,
                    "photo_path": "../../img/woman-placeholder.png",
                    locationStatus: "left_the_place"
                },
                {
                    village: 'Bherya',
                    name: 'Larry',
                    thayi: '4636587',
                    ec_number: '314',
                    age: '24',
                    husband_name: 'Dickson',
                    weeks_pregnant: '2',
                    edd: '2013-09-11T00:00:00.000Z',
                    lmp: '25/3/13',
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: [
                        {
                            name: 'anc2',
                            date: '24/05',
                            status: 'normal'
                        },
                        {
                            name: 'tt1',
                            date: '26/05',
                            status: 'normal'
                        },
                        {
                            name: 'ifa2',
                            date: '26/05',
                            status: 'urgent'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'anc1',
                            date: '04/04',
                            data: {
                                bp: '120/80',
                                weight: '55'
                            }
                        },
                        {
                            name: 'ifa1',
                            date: '04/04',
                            data: {
                                dose: 100
                            }
                        },
                        {
                            name: 'hb',
                            data: [
                                {date: '2012-05-27', level: 6}
                            ]
                        },
                        {
                            name: 'delivery_plan1',
                            date: '2012-09-13',
                            data: {
                                asha_name: 'Robin',
                                companion: 'Matt',
                                delivery_site: null,
                                transport: null,
                                contact_no: '9403292920',
                                review_risks: null
                            }
                        }
                    ],
                    days_due: '3',
                    due_message: 'Follow Up',
                    isHighPriority: true,
                    locationStatus: "in_area",
                    economicStatus: "bpl",
                    caste: "st"
                },
                {
                    village: 'Bherya',
                    name: 'Ukanga',
                    thayi: '4636587',
                    ec_number: '315',
                    age: '27',
                    husband_name: 'Harshit',
                    weeks_pregnant: '2',
                    edd: '2013-06-08T00:00:00.000Z',
                    "photo_path": "../../img/woman-placeholder.png",
                    lmp: '25/3/13',
                    alerts: [
                        {
                            name: 'tt2',
                            date: '26/05',
                            status: 'normal'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'anc2',
                            date: '04/08',
                            data: {
                                bp: '115/90',
                                weight: '98'
                            }
                        },
                        {
                            name: 'anc4',
                            date: '04/08',
                            data: {
                                bp: '120/90',
                                weight: '95'
                            }
                        },
                        {
                            name: 'tt1',
                            date: '04/04',
                            data: {
                                dose: 100
                            }
                        },
                        {
                            name: 'ifa1',
                            date: '2012-04-24',
                            data: {
                                dose: 50
                            }
                        },
                        {
                            name: 'ifa2',
                            date: '2012-05-24',
                            data: {
                                dose: 80
                            }
                        },
                        {
                            name: 'ifa3',
                            date: '2012-06-17',
                            data: {
                                dose: 100
                            }
                        },
                        {
                            name: 'hb',
                            data: [
                                {date: '2012-05-27', level: 14},
                                {date: '2012-06-13', level: 10}
                            ]
                        }
                    ],
                    days_due: '3',
                    due_message: 'Follow Up',
                    isHighPriority: false,
                    locationStatus: "in_area",
                    days_past_edd: 3
                },
                {
                    village: 'Bherya',
                    name: 'Moses',
                    thayi: '4636543',
                    "photo_path": "../../img/woman-placeholder.png",
                    ec_number: '429',
                    age: '35',
                    husband_name: 'Kiran',
                    weeks_pregnant: '5',
                    edd: '2013-05-11T00:00:00.000Z',
                    lmp: '25/3/13',
                    alerts: [
                        {
                            name: 'tt2',
                            date: '26/05',
                            status: 'normal'
                        },
                        {
                            name: 'delivery_plan1',
                            date: '26/05',
                            status: 'normal'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'anc1',
                            date: '04/04',
                            data: {
                                bp: '120/80',
                                weight: '95'
                            }
                        },
                        {
                            name: 'anc2',
                            date: '04/08',
                            data: {
                                bp: '115/90',
                                weight: '98'
                            }
                        },
                        {
                            name: 'tt1',
                            date: '04/04',
                            data: {
                                dose: 100
                            }
                        },
                        {
                            name: 'ifa1',
                            date: '2012-04-24',
                            data: {
                                dose: 50
                            }
                        },
                        {
                            name: 'ifa2',
                            date: '2012-05-24',
                            data: {
                                dose: 20
                            }
                        },
                        {
                            name: 'hb',
                            data: [
                                {date: '2012-06-13', level: 10}
                            ]
                        }
                    ],
                    days_due: '3',
                    due_message: 'Follow Up',
                    isHighPriority: false,
                    locationStatus: "in_area"
                }
            ]);
        },
        villages: function () {
            return JSON.stringify(
                [
                    {name: "bherya"},
                    {name: "chikkahalli"},
                    {name: "somanahalli_colony"},
                    {name: "chikkabherya"},
                    {name: "basavanapura"}
                ]
            )
        }
    };
}