function FPRegistryBridge() {
    var context = window.context;
    if (typeof context === "undefined" && typeof FakeFPSmartRegistryContext !== "undefined") {
        context = new FakeFPSmartRegistryContext();
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

function FakeFPSmartRegistryContext() {
    return {
        get: function () {
            return JSON.stringify([
                {
                    "entity_id": "entity 1",
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
                    "entity_id": "entity 2",
                    "ec_number": "99",
                    "fp_method": "female_sterilization",
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
                    "entity_id": "entity 3",
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
                    "entity_id": "entity 4",
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
                },
                {
                    "entity_id": "entity 5",
                    "ec_number": "7",
                    "fp_method": "male_sterilization",
                    "husband_name": "Hemanth",
                    "village": "somanahalli_colony",
                    "name": "Anu",
                    "thayi": "",
                    "isHighPriority": false,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "25/02/13",
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: [
                        {
                            id: "id 6",
                            name: "Referral Followup",
                            date: "2012-05-28",
                            status: "normal"
                        }
                    ]
                },
                {
                    "entity_id": "entity 6",
                    "ec_number": "10",
                    "fp_method": "female_sterilization",
                    "husband_name": "Nandisha",
                    "village": "Bherya",
                    "name": "Bibi",
                    "thayi": "",
                    "isHighPriority": false,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "25/02/13",
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: []
                },
                {
                    "entity_id": "entity 7",
                    "ec_number": "3",
                    "fp_method": "none",
                    "husband_name": "Biju Nayak",
                    "village": "Bherya",
                    "name": "Bindu",
                    "thayi": "1234567",
                    "isHighPriority": false,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "25/02/13",
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: []
                },
                {
                    "entity_id": "entity 8",
                    "ec_number": "4",
                    "fp_method": "none",
                    "husband_name": "Naresh",
                    "village": "Bherya",
                    "name": "Devi",
                    "thayi": "235689",
                    "isHighPriority": false,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "25/02/13",
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: []
                },
                {
                    "entity_id": "entity 9",
                    "ec_number": "11",
                    "fp_method": "none",
                    "husband_name": "Suresh",
                    "village": "Bherya",
                    "name": "Kavitha",
                    "thayi": "123456",
                    "isHighPriority": false,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "25/02/13",
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: []
                },
                {
                    "entity_id": "entity 10",
                    "ec_number": "13",
                    "fp_method": "female_sterilization",
                    "husband_name": "Kalyan",
                    "village": "Bherya",
                    "name": "Lakshmi",
                    "thayi": "12369",
                    "isHighPriority": false,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "25/02/13",
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: []
                },
                {
                    "entity_id": "entity 11",
                    "ec_number": "13",
                    "fp_method": "none",
                    "husband_name": "vinod",
                    "village": "Bherya",
                    "name": "Latha",
                    "thayi": "147285",
                    "isHighPriority": false,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "25/02/13",
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: []
                },
                {
                    "entity_id": "entity 12",
                    "ec_number": "8",
                    "fp_method": "female_sterilization",
                    "husband_name": "Raja",
                    "village": "somanahalli_colony",
                    "name": "Mahithi",
                    "thayi": "",
                    "isHighPriority": false,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "25/02/13",
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: []
                },
                {
                    "entity_id": "entity 13",
                    "ec_number": "12",
                    "fp_method": "none",
                    "husband_name": "Naresh",
                    "village": "Chikkabherya",
                    "name": "Raji",
                    "thayi": "258399",
                    "isHighPriority": false,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "25/02/13",
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: []
                },
                {
                    "entity_id": "entity 14",
                    "ec_number": "1",
                    "fp_method": "condom",
                    "husband_name": "Raja",
                    "village": "bherya",
                    "name": "Rani",
                    "thayi": "48666",
                    "isHighPriority": false,
                    "side_effects": "poops a lot",
                    "days_due": "2013/01/01",
                    "due_message": "due message",
                    "age": '24',
                    "num_pregnancies": '3',
                    "parity": '2',
                    "num_living_children": '1',
                    "num_stillbirths": '1',
                    "num_abortions": '1',
                    "family_planning_method_change_date": "25/02/13",
                    "photo_path": "../../img/woman-placeholder.png",
                    alerts: []
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