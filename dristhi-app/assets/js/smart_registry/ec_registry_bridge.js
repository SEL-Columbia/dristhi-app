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
                    "entityId":"entity 16",
                    "name":"Gayathri",
                    "husbandName":"Raju",
                    "age":'32',
                    //calculate this
                    "village":"bherya",
                    "ecNumber":"16",
                    "locationStatus":"out_of_area",
                    "economicStatus":"bpl",
                    "caste":"sc",
                    "isHighPriority":false,
                    "numPregnancies":'2',
                    "parity":'2',
                    "numLivingChildren":'2',
                    "numStillbirths":'0',
                    "numAbortions":'0',
                    "fpMethod":"centchroman",
                    "familyPlanningMethodChangeDate":"25/04/13",
                    "iudPlace":"phc",
                    "iudPerson":"lhv",
                    "numberOfCondomsSupplied":"30",
                    "numberOfCentchromanPillsDelivered":"20",
                    "numberOfOCPDelivered":"1",
                    "photo_path":"../../img/woman-placeholder.png",
                    "children":[
                        {
                            "id":"guid 1",
                            "gender":"boy",
                            "age":"6 months"         //calculate
                        }
                    ],
                    "status":{
                        "type":"ec", //ec, fp, anc, pnc, pnc/fp
                        "date":"2013-01-01",
                        "lmp":"2013-01-02",
                        "edd":"2013-10-02"
                    }
                },
                {
                    "entityId":"entity 19",
                    "name":"Anu",
                    "husbandName":"Hemanth",
                    "age":'27',
                    //calculate this
                    "village":"bherya",
                    "ecNumber":"19",
                    "locationStatus":"out_of_area",
                    "economicStatus":"bpl",
                    "caste":"sc",
                    "isHighPriority":false,
                    "numPregnancies":'2',
                    "parity":'2',
                    "numLivingChildren":'2',
                    "numStillbirths":'0',
                    "numAbortions":'0',
                    "fpMethod":"centchroman",
                    "familyPlanningMethodChangeDate":"25/04/13",
                    "iudPlace":"phc",
                    "iudPerson":"lhv",
                    "numberOfCondomsSupplied":"30",
                    "numberOfCentchromanPillsDelivered":"20",
                    "numberOfOCPDelivered":"1",
                    "photo_path":"../../img/woman-placeholder.png",
                    "children":[
                        {
                            "id":"guid 2",
                            "gender":"boy",
                            "age":"4 years"         //calculate
                        }
                    ],
                    "status":{
                        "type":"anc", //ec, fp, anc, pnc, pnc/fp
                        "date":"2013-02-01",
                        "lmp":"2013-04-02",
                        "edd":"2013-10-02"
                    }
                },
                {
                    "entityId":"entity 23",
                    "name":"Bibi",
                    "husbandName":"Nandisha",
                    "age":'27',
                    //calculate this
                    "village":"bherya",
                    "ecNumber":"23",
                    "locationStatus":"out_of_area",
                    "economicStatus":"bpl",
                    "caste":"sc",
                    "isHighPriority":false,
                    "numPregnancies":'2',
                    "parity":'2',
                    "numLivingChildren":'2',
                    "numStillbirths":'0',
                    "numAbortions":'0',
                    "familyPlanningMethodChangeDate":"25/04/13",
                    "iudPlace":"phc",
                    "iudPerson":"lhv",
                    "numberOfCondomsSupplied":"30",
                    "numberOfCentchromanPillsDelivered":"20",
                    "numberOfOCPDelivered":"1",
                    "photo_path":"../../img/woman-placeholder.png",
                    "children":[
                        {
                            "id":"guid 1",
                            "gender":"girl",
                            "age":"2 months"         //calculate
                        }
                    ],
                    "status":{
                        "type":"pnc/fp", //ec, fp, anc, pnc, pnc/fp
                        "date":"2013-02-01",
                        "lmp":"2013-04-02",
                        "delivery":"2013-10-02"
                    }
                },
                {
                    "entityId":"entity 23",
                    "name":"Bibi",
                    "husbandName":"Nandisha",
                    "age":'27',
                    //calculate this
                    "village":"bherya",
                    "ecNumber":"23",
                    "locationStatus":"in_area",
                    "economicStatus":"apl",
                    "fpMethod":"iud",
                    "caste":"st",
                    "isHighPriority":false,
                    "numPregnancies":'2',
                    "parity":'2',
                    "numLivingChildren":'2',
                    "numStillbirths":'0',
                    "numAbortions":'0',
                    "familyPlanningMethodChangeDate":"25/04/13",
                    "iudPlace":"phc",
                    "iudPerson":"lhv",
                    "numberOfCondomsSupplied":"30",
                    "numberOfCentchromanPillsDelivered":"20",
                    "numberOfOCPDelivered":"1",
                    "photo_path":"../../img/woman-placeholder.png",
                    "children":[
                        {
                            "id":"guid 1",
                            "gender":"girl",
                            "age":"2 months"         //calculate
                        },
                        {
                            "id":"guid 2",
                            "gender":"boy",
                            "age":"4 years"         //calculate
                        }
                    ],
                    "status":{
                        "type":"pnc", //ec, fp, anc, pnc, pnc/fp
                        "date":"2013-02-01",
                        "lmp":"2013-04-02",
                        "delivery":"2013-10-02"
                    }
                }
            ]);
        }
    };
}