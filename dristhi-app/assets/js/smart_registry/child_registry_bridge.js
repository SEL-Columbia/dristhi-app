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
                    gender: "Male",
                    weight: "4.8",
                    thaayiCardNumber: '4636587',
                    name: 'Baby Carolyn',
                    motherName: 'Carolyn',
                    dob: '2013-06-13',
                    motherAge: '24',
                    fatherName: 'Billy Bob',
                    village: 'Chikkabherya',
                    locationStatus: "out_of_area",
                    economicStatus: "bpl",
                    caste: "sc",
                    isHighRisk: true,
                    photo_path: "../../img/woman-placeholder.png",
                    ecNumber: "1234",
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
                    ]
                }
            ]);
        }
    };
}