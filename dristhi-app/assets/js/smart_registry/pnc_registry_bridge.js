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
                    fp_method: "ocp",
                    iudPlace: "PNC",
                    iudPerson: "",
                    family_planning_method_change_date: '2013-08-13',
                    numberOfCondomsSupplied: null,
                    numberOfOCPDelivered: "8",
                    numberOfCentchromanPillsDelivered: null,
                    deliveryDate: "2013-06-01",
                    deliveryPlace:"PNC",
                    deliveryType: "Normal", //TODO: what are the options
                    deliveryComplications: "hemorrhage, placenta_previa, prolonged_or_obstructed_labour",
                    otherDeliveryComplications: "",
                    alerts: [
                        {
                            name: 'PNC',
                            date: '2013-06-03',
                            status: 'normal'
                        }
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
                            name: 'Not PNC',
                            date: '2013-06-09',
                            data: {}
                        }
                    ]
                },
                {
                    entityId: "entity id 1",
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
                    locationStatus: "out_of_area",
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
                        },
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