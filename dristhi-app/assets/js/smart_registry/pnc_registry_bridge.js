function PNCRegistryBridge() {
    var context = window.context;
    if (typeof context === "undefined" && typeof FakePNCSmartRegistryContext !== "undefined") {
        context = new FakePNCSmartRegistryContext();
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

function FakePNCSmartRegistryContext() {
    return {
        get: function () {
            return JSON.stringify([
                {
                    entityId: "entity id 1",
                    entityIdToSavePhoto: "entity id 1",
                    ec_number: '314',
                    village: 'Chikkabherya',
                    name: 'Carolyn',
                    thayi: '4636587',
                    age: '24',
                    womanDOB: '1980-02-25',
                    husband_name: 'Billy Bob',
                    photo_path: "../../img/woman-placeholder.png",
                    isHighPriority: true,
                    isHighRisk: true,
                    locationStatus: "out_of_area",
                    economicStatus: "bpl",
                    caste: "sc",
                    iudPlace: "PNC",
                    iudPerson: "",
                    family_planning_method_change_date: '2013-08-13',
                    numberOfCondomsSupplied: null,
                    numberOfOCPDelivered: "8",
                    numberOfCentchromanPillsDelivered: null,
                    deliveryDate: "2013-06-15",
                    deliveryPlace:"PRIVATE_FACILITY",
                    deliveryComplications: "hemorrhage placenta_previa prolonged_or_obstructed_labour hemorrhage placenta_previa prolonged_or_obstructed_labour",
                    deliveryType: "Normal", //TODO: what are the options
                    otherDeliveryComplications: "",
                    alerts: [
                        {
                            name: 'PNC',
                            date: '2013-06-26',
                            status: 'normal'
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
                        }
                    ],
                    children: [
                        {
                            gender: 'female',
                            weight: '3.5'
                        }
                    ]
                },
                {
                    entityId: "entity id 2",
                    entityIdToSavePhoto: "entity id 2",
                    ec_number: '314',
                    village: 'Chikkabherya',
                    name: 'Radhia',
                    thayi: '3728256',
                    age: '21',
                    womanDOB: '1981-06-18',
                    husband_name: 'Bob Billy',
                    photo_path: "../../img/woman-placeholder.png",
                    isHighPriority: false,
                    isHighRisk: false,
                    locationStatus: "",
                    economicStatus: "apl",
                    caste: "st",
                    fp_method: "ocp",
                    iudPlace: "PNC",
                    iudPerson: "",
                    family_planning_method_change_date: '2013-08-13',
                    numberOfCondomsSupplied: null,
                    numberOfOCPDelivered: "10",
                    numberOfCentchromanPillsDelivered: null,
                    deliveryDate: "2013-05-13",
                    deliveryPlace:"PNC",
                    deliveryType: "Normal", //TODO: what are the options
                    deliveryComplications: "",
                    otherDeliveryComplications: "",
                    alerts: [

                    ],
                    services_provided: [
                        {
                            name: 'PNC',
                            date: '2013-06-03',
                            data: {
                                day: 14
                            }
                        },
                        {
                            name: 'PNC',
                            date: '2013-06-07',
                            data: {
                                day: 18
                            }
                        },
                        {
                            name: 'PNC',
                            date: '2013-06-09',
                            data: {
                                day: 7
                            }
                        }
                    ]
                },
                {
                    entityId: "entity id 3",
                    entityIdToSavePhoto: "entity id 3",
                    ec_number: '314',
                    village: 'Chikkabherya',
                    name: 'Carolyn',
                    thayi: '4636587',
                    age: '24',
                    womanDOB: '1982-02-25',
                    husband_name: 'Billy Bob',
                    photo_path: "../../img/woman-placeholder.png",
                    isHighPriority: true,
                    isHighRisk: false,
                    locationStatus: "out_of_area",
                    economicStatus: "bpl",
                    caste: "st",
                    fp_method: "condom",
                    iudPlace: "PNC",
                    iudPerson: "",
                    family_planning_method_change_date: '2013-08-13',
                    numberOfCondomsSupplied: "20",
                    numberOfOCPDelivered: null,
                    numberOfCentchromanPillsDelivered: null,
                    deliveryDate: "2013-06-10",
                    deliveryPlace:"PNC",
                    deliveryType: "Normal", //TODO: what are the options
                    deliveryComplications: "hemorrhage placenta_previa prolonged_or_obstructed_labour",
                    otherDeliveryComplications: "",
                    alerts: [
                        {
                            name: 'PNC',
                            date: '2013-06-30',
                            status: 'normal'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'PNC',
                            date: '2013-06-12',
                            data: {
                                day: 2
                            }
                        },
                        {
                            name: 'PNC',
                            date: '2013-06-18',
                            data: {
                                day: 8
                            }
                        }
                    ],
                    children: [
                        {
                            gender: 'female'
                        }
                    ]
                },
                {
                    entityId: "entity id 4",
                    entityIdToSavePhoto: "entity id 4",
                    ec_number: '314',
                    village: 'Chikkabherya',
                    name: 'Carolyn 4',
                    thayi: '4636587',
                    age: '24',
                    womanDOB: '1982-02-25',
                    husband_name: 'Billy Bob',
                    photo_path: "../../img/woman-placeholder.png",
                    isHighPriority: false,
                    isHighRisk: false,
                    locationStatus: "",
                    economicStatus: "BPL",
                    caste: "sc",
                    fp_method: "ocp",
                    iudPlace: "PNC",
                    iudPerson: "",
                    family_planning_method_change_date: '2013-08-13',
                    numberOfCondomsSupplied: null,
                    numberOfOCPDelivered: "8",
                    numberOfCentchromanPillsDelivered: null,
                    deliveryDate: "2013-06-18",
                    deliveryPlace:"PNC",
                    deliveryType: "Normal", //TODO: what are the options
                    deliveryComplications: "hemorrhage placenta_previa prolonged_or_obstructed_labour",
                    otherDeliveryComplications: "",
                    alerts: [
                        {
                            name: 'PNC',
                            date: '2013-06-30',
                            status: 'normal'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'PNC',
                            date: '2013-06-19',
                            data: {
                                day: 2
                            }
                        }
                    ],
                    children: [
                        {
                            gender: 'female',
                            weight: '3.5'
                        }
                    ]
                },
                {
                    entityId: "entity id 5",
                    entityIdToSavePhoto: "entity id 5",
                    ec_number: '314',
                    village: 'Chikkabherya',
                    name: 'Carolyn',
                    thayi: '4636587',
                    age: '24',
                    womanDOB: '1982-02-25',
                    husband_name: 'Billy Bob',
                    photo_path: "../../img/woman-placeholder.png",
                    isHighPriority: true,
                    isHighRisk: false,
                    locationStatus: "out_of_area",
                    economicStatus: "bpl",
                    caste: "st",
                    fp_method: "condom",
                    iudPlace: "PNC",
                    iudPerson: "",
                    family_planning_method_change_date: '2013-08-13',
                    numberOfCondomsSupplied: null,
                    numberOfOCPDelivered: "8",
                    numberOfCentchromanPillsDelivered: null,
                    deliveryDate: "2013-06-10",
                    deliveryPlace:"PNC",
                    deliveryType: "Normal", //TODO: what are the options
                    deliveryComplications: "hemorrhage placenta_previa prolonged_or_obstructed_labour",
                    otherDeliveryComplications: "",
                    alerts: [
                        {
                            name: 'PNC',
                            date: '2013-06-30',
                            status: 'normal'
                        }
                    ],
                    services_provided: [
                        {
                            name: 'PNC',
                            date: '2013-06-12',
                            data: {
                                day: 2
                            }
                        },
                        {
                            name: 'PNC',
                            date: '2013-06-18',
                            data: {
                                day: 8
                            }
                        }
                    ],
                    children: [
                        {
                            gender: 'female',
                            weight: '3.5'
                        }
                    ]
                },
                {
                    entityId: "entity id 6",
                    entityIdToSavePhoto: "entity id 6",
                    ec_number: '314',
                    village: 'Chikkabherya',
                    name: 'Radhia',
                    thayi: '3728256',
                    age: '21',
                    womanDOB: '1981-06-18',
                    husband_name: 'Bob Billy',
                    photo_path: "../../img/woman-placeholder.png",
                    isHighPriority: false,
                    isHighRisk: false,
                    locationStatus: "",
                    economicStatus: "apl",
                    caste: "st",
                    fp_method: "ocp",
                    iudPlace: "PNC",
                    iudPerson: "",
                    family_planning_method_change_date: '2013-08-13',
                    numberOfCondomsSupplied: null,
                    numberOfOCPDelivered: "10",
                    numberOfCentchromanPillsDelivered: null,
                    deliveryDate: "2013-06-16",
                    deliveryPlace:"PNC",
                    deliveryType: "Normal", //TODO: what are the options
                    deliveryComplications: "",
                    otherDeliveryComplications: "",
                    alerts: [

                    ],
                    services_provided: [
                        {
                            name: 'PNC',
                            date: '2013-06-17',
                            data: {
                                day: 14
                            }
                        },
                        {
                            name: 'PNC',
                            date: '2013-06-19',
                            data: {
                                day: 18
                            }
                        }
                    ]
                },
                {
                    entityId: "entity id 7",
                    entityIdToSavePhoto: "entity id 7",
                    ec_number: '314',
                    village: 'Chikkabherya',
                    name: 'Radhia',
                    thayi: '3728256',
                    age: '21',
                    womanDOB: '1981-06-18',
                    husband_name: 'Bob Billy',
                    photo_path: "../../img/woman-placeholder.png",
                    isHighPriority: false,
                    isHighRisk: false,
                    locationStatus: "",
                    economicStatus: "apl",
                    caste: "st",
                    fp_method: "ocp",
                    iudPlace: "PNC",
                    iudPerson: "",
                    family_planning_method_change_date: '2013-08-13',
                    numberOfCondomsSupplied: null,
                    numberOfOCPDelivered: "10",
                    numberOfCentchromanPillsDelivered: null,
                    deliveryDate: "2013-05-19",
                    deliveryPlace:"PNC",
                    deliveryType: "Normal", //TODO: what are the options
                    deliveryComplications: "",
                    otherDeliveryComplications: "",
                    alerts: [

                    ],
                    services_provided: [
                        {
                            "data": {
                                "pncVisitNumber": "1"
                            },
                            "date": "2013-05-23",
                            "name": "PNC"
                        },
                        {
                            "data": {
                                "pncVisitNumber": "2"
                            },
                            "date": "2013-05-20",
                            "name": "PNC"
                        },
                        {
                            "data": {
                                "pncVisitNumber": "3"
                            },
                            "date": "2013-05-21",
                            "name": "PNC"
                        },
                        {
                            "data": {
                                "pncVisitNumber": "4"
                            },
                            "date": "2013-05-22",
                            "name": "PNC"
                        },
                        {
                            "data": {
                                "pncVisitNumber": "5"
                            },
                            "date": "2013-05-23",
                            "name": "PNC"
                        },
                        {
                            "data": {
                                "pncVisitNumber": "6"
                            },
                            "date": "2013-05-24",
                            "name": "PNC"
                        },
                        {
                            "data": {
                                "pncVisitNumber": "7"
                            },
                            "date": "2013-05-25",
                            "name": "PNC"
                        },
                        {
                            "data": {
                                "pncVisitNumber": "8"
                            },
                            "date": "2013-05-26",
                            "name": "PNC"
                        }
                    ]
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