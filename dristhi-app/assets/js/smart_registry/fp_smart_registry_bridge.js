function FPSmartRegistryBridge() {
    var fpSmartRegistryContext = window.context;
    if (typeof fpSmartRegistryContext === "undefined" && typeof FakeFPSmartRegistryContext !== "undefined") {
        fpSmartRegistryContext = new FakeFPSmartRegistryContext();
    }

    return {
        getFPClients: function () {
            return JSON.parse(fpSmartRegistryContext.get());
        }
    };
}

function FakeFPSmartRegistryContext() {
    return {
        get: function () {
            return JSON.stringify([
                {"ec_number": "2", "fp_method": "female_sterilization", "husband_name": "Manikyam", "village": "basavanapura", "name": "Ammulu", "thayi": "", "isHighPriority": false},
                {"ec_number": "9", "fp_method": "iud", "husband_name": "Umesh", "village": "basavanapura", "name": "Amrutha", "thayi": "369258", "isHighPriority": false},
                {"ec_number": "1", "fp_method": "condom", "husband_name": "Anji", "village": "chikkahalli", "name": "Anitha", "thayi": "2539641", "isHighPriority": true},
                {"ec_number": "7", "fp_method": "male_sterilization", "husband_name": "Hemanth", "village": "somanahalli_colony", "name": "Anu", "thayi": "", "isHighPriority": false},
                {"ec_number": "10", "fp_method": "female_sterilization", "husband_name": "Nandisha", "village": "basavanapura", "name": "Bibi", "thayi": "", "isHighPriority": false},
                {"ec_number": "3", "fp_method": "none", "husband_name": "Biju Nayak", "village": "basavanapura", "name": "Bindu", "thayi": "1234567", "isHighPriority": false},
                {"ec_number": "4", "fp_method": "none", "husband_name": "Naresh", "village": "basavanapura", "name": "Devi", "thayi": "235689", "isHighPriority": false},
                {"ec_number": "11", "fp_method": "none", "husband_name": "Suresh", "village": "basavanapura", "name": "Kavitha", "thayi": "123456", "isHighPriority": false},
                {"ec_number": "13", "fp_method": "female_sterilization", "husband_name": "Kalyan", "village": "basavanapura", "name": "Lakshmi", "thayi": "12369", "isHighPriority": false},
                {"ec_number": "13", "fp_method": "none", "husband_name": "vinod", "village": "basavanapura", "name": "Latha", "thayi": "147285", "isHighPriority": false},
                {"ec_number": "8", "fp_method": "female_sterilization", "husband_name": "Raja", "village": "somanahalli_colony", "name": "Mahithi", "thayi": "", "isHighPriority": false},
                {"ec_number": "12", "fp_method": "none", "husband_name": "Naresh", "village": "basavanapura", "name": "Raji", "thayi": "258399", "isHighPriority": false},
                {"ec_number": "1", "fp_method": "condom", "husband_name": "Raja", "village": "basavanapura", "name": "Rani", "thayi": "48666", "isHighPriority": false}
            ]);
        }
    };
}