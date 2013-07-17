function ChildRegistryBridge() {
    var context = window.context;
    if (typeof context === "undefined" && typeof FakeChildSmartRegistryContext !== "undefined") {
        context = new FakeChildSmartRegistryContext();
    }

    return {
        getClients: function () {
            return JSON.parse(context.get());
        }
    };
}

function FakeChildSmartRegistryContext() {
    return {
        get: function () {
            return JSON.stringify([
                {
                    entityId: "entity id 1",
                    entityIdToSavePhoto: "entity id 1",
                    gender: "Male",
                    weight: "4.8",
                    thayiCardNumber: '4636587',
                    name: 'Baby Carolyn',
                    motherName: 'Carolyn',
                    dob: '2013-05-03',
                    motherAge: '24',
                    fatherName: 'Billy Bob',
                    village: 'Chikkabherya',
                    locationStatus: "in_area",
                    economicStatus: "bpl",
                    caste: "sc",
                    isHighRisk: true,
                    photo_path: "../../img/woman-placeholder.png",
                    ecNumber: "1234",
                    alerts: [
                        {
                            name: 'bcg',
                            date: '2013-07-09',
                            status: 'complete'
                        },
                        {
                            name: 'opv_0',
                            date: '2013-07-09',
                            status: 'urgent'
                        },
                        {
                            name: 'pentavalent_2',
                            date: '2013-07-09',
                            status: 'urgent'
                        },
                        {
                            name: 'measles_booster',
                            date: '2013-07-09',
                            status: 'complete'
                        },
                        {
                            name: 'hepb_1',
                            date: '2013-07-01',
                            status: 'normal'
                        },
                        {
                            name: 'opvbooster',
                            date: '2013-07-09',
                            status: 'urgent'
                        },
                        {
                            name: 'dptbooster_1',
                            status: 'urgent',
                            date: '2012-07-13'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'PNC',
                            date: '2013-06-16',
                            data: {
                                day: 14
                            }
                        },
                        {
                            name: 'PNC',
                            date: '2013-06-18',
                            data: {
                                day: 18
                            }
                        },
                        {
                            name: 'Not PNC',
                            date: '2013-06-09',
                            data: {}
                        },
                        {
                            name: 'bcg',
                            date: '2013-07-01',
                            data: {}
                        },
                        {
                            name: 'hepb_0',
                            date: '2013-07-01',
                            data: {}
                        },
                        {
                            name: 'pentavalent_1',
                            date: '2013-07-01',
                            data: {}
                        },
                        {
                            name: 'measles',
                            date: '2013-07-01',
                            data: {}
                        },
                        {
                            name: 'dpt_0',
                            date: '2013-07-01',
                            data: {}
                        }
                    ]
                },
                {
                    entityId: "entity id 2",
                    entityIdToSavePhoto: "entity id 2",
                    gender: "Male",
                    weight: "4.8",
                    thayiCardNumber: '4636589',
                    name: '',
                    motherName: 'Sukanya',
                    dob: '2013-06-13',
                    motherAge: '24',
                    fatherName: 'Billy Bob',
                    village: 'Chikkabherya',
                    locationStatus: "out_of_area",
                    economicStatus: "apl",
                    caste: "st",
                    isHighRisk: false,
                    photo_path: "../../img/woman-placeholder.png",
                    ecNumber: "5678",
                    alerts: [
                        {
                            name: 'bcg',
                            status: 'urgent',
                            date: '2012-07-13'
                        },
                        {
                            name: 'opv_3',
                            status: 'normal',
                            date: '2012-07-13'
                        },
                        {
                            name: 'measles',
                            status: 'normal',
                            date: '2012-07-13'
                        },
                        {
                            name: 'opvbooster',
                            status: 'complete',
                            date: '2012-07-13'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'PNC',
                            date: '2013-06-16',
                            data: {
                                day: 14
                            }
                        },
                        {
                            name: 'PNC',
                            date: '2013-06-30',
                            data: {
                                day: 18
                            }
                        },
                        {
                            name: 'opv_1',
                            date: '2013-06-30',
                            data: {}
                        },
                        {
                            name: 'opvbooster',
                            date: '2013-06-30',
                            data: {}
                        }
                    ]
                },
                {
                    entityId: "entity id 2",
                    entityIdToSavePhoto: "entity id 2",
                    gender: "female",
                    weight: "4.8",
                    thayiCardNumber: '4636593',
                    name: 'Anu',
                    motherName: 'Amrutha',
                    dob: '2013-07-02',
                    motherAge: '35',
                    fatherName: 'Billy Bob',
                    village: 'Chikkabherya',
                    locationStatus: "out_of_area",
                    economicStatus: "apl",
                    caste: "st",
                    isHighRisk: false,
                    photo_path: "../../img/woman-placeholder.png",
                    ecNumber: "5678",
                    alerts: [
                        {
                            name: 'opv_2',
                            status: 'complete',
                            date: '2012-07-13'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'opv_2',
                            date: '2013-06-30',
                            data: {}
                        },
                        {
                            name: 'opv_1',
                            date: '2013-06-30',
                            data: {}
                        }
                    ]
                }
            ]);
        }
    };
}