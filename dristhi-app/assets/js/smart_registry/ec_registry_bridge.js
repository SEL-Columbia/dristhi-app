function ECRegistryBridge() {
    var context = window.context;
    if (typeof context === "undefined" && typeof FakeECSmartRegistryContext !== "undefined") {
        context = new FakeECSmartRegistryContext();
    }

    return {
        getClients: function () {
            return JSON.parse(context.get());
        }
    };
}

function FakeECSmartRegistryContext() {
    return {
        get: function () {
            return JSON.stringify([
                {
                    "entityId": "entity 1",
                    "entityIdToSavePhoto": "entity 1",
                    "ec_number": "2",
                    "fp_method": "male_sterilization",
                    "husband_name": "Manikyam",
                    "village": "Bherya",
                    "name": "Ammulu",
                    "thayi": "",
                    "isHighPriority": true,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "2013-02-25",
                    "economicStatus": "bpl",
                    "caste": "sc",
                    "photo_path": "../../img/woman-placeholder.png",
                    "is_youngest_child_under_two": true,
                    "youngest_child_age": 18,
                    "complication_date": "2012-05-23",
                    "sterilizationSideEffect": "severe_pain fever",
                    alerts: [
                        {
                            id: "id 1",
                            name: 'Male Sterilization Followup',
                            date: '26/05',
                            status: 'inProcess'
                        }
                    ]
                },
                {
                    "entityId": "entity 2",
                    "entityIdToSavePhoto": "entity 2",
                    "ec_number": "99",
                    "husband_name": "Ramakrishnegowda",
                    "village": "Bherya",
                    "name": "Sukanya",
                    "thayi": "",
                    "isHighPriority": false,
                    "side_effects": "poops a bit",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '20',
                    "num_pregnancies": '0',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "14/03/12",
                    "caste": "st",
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: [
                        {
                            id: "id 2",
                            name: 'Female Sterilization Followup',
                            date: '24/07',
                            status: 'urgent'
                        }
                    ]
                },
                {
                    "entityId": "entity 3",
                    "entityIdToSavePhoto": "entity 3",
                    "ec_number": "9",
                    "fp_method": "iud",
                    "husband_name": "Umesh",
                    "village": "Bherya",
                    "name": "Amrutha",
                    "thayi": "369258",
                    "isHighPriority": false,
                    "side_effects": "poops a ton",
                    "days_due": "2013/01/01",
                    "due_message": "due message 112",
                    "age": '26',
                    "num_pregnancies": '1',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '2',
                    "num_abortions": '0',
                    "family_planning_method_change_date": "02/12/13",
                    "economicStatus": "apl",
                    "photo_path": "../../img/woman-placeholder.png",
                    "iudPlace": "phc",
                    "iudPerson": "lhv",
                    alerts: [
                        {
                            id: "id 3",
                            name: 'IUD Followup 2',
                            date: '2012-05-18',
                            status: 'upcoming'
                        }
                    ]
                },
                {
                    "entityId": "entity 4",
                    "entityIdToSavePhoto": "entity 4",
                    "ec_number": "1",
                    "fp_method": "condom",
                    "husband_name": "Anji",
                    "village": "Chikkabherya",
                    "name": "Anitha",
                    "thayi": "2539641",
                    "isHighPriority": true,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "21/06/12",
                    "photo_path": "../../img/woman-placeholder.png",
                    "numberOfCondomsSupplied": "20",
                    alerts: [
                        {
                            id: "id 4",
                            name: 'OCP Refill',
                            date: '25/07',
                            status: 'urgent'
                        },
                        {
                            id: "id 5",
                            name: 'Condom Refill',
                            date: '24/07',
                            status: 'normal'
                        }
                    ]
                }
            ]);
        }
    };
}