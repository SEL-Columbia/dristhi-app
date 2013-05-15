angular.module("smartRegistry.services")
    .service('FPService', function () {
        var constants = {
            CONDOM_REFILL: "Condom Refill",
            DPMA_INJECTABLE_REFILL: "DPMA Injectable Refill",
            OCP_REFILL: "OCP Refill",
            MALE_STERILIZATION_FOLLOW_UP: "Male Sterilization Followup",
            FEMALE_STERILIZATION_FOLLOW_UP: "Female Sterilization Followup",
            IUD_FOLLOW_UP_1: "IUD Followup 1",
            IUD_FOLLOW_UP_2: "IUD Followup 2",
            REFERRAL_FOLLOW_UP: "Referral Followup"
        };

        var refill_types = [
            constants.CONDOM_REFILL,
            constants.DPMA_INJECTABLE_REFILL,
            constants.OCP_REFILL
        ];

        var follow_up_types = [
            constants.MALE_STERILIZATION_FOLLOW_UP,
            constants.FEMALE_STERILIZATION_FOLLOW_UP,
            constants.IUD_FOLLOW_UP_1,
            constants.IUD_FOLLOW_UP_2
        ];

        var alert_name_to_fp_method_map = {};
        alert_name_to_fp_method_map[constants.CONDOM_REFILL] = "condom";
        alert_name_to_fp_method_map[constants.DPMA_INJECTABLE_REFILL] = "dpma";
        alert_name_to_fp_method_map[constants.OCP_REFILL] = "ocp";
        alert_name_to_fp_method_map[constants.MALE_STERILIZATION_FOLLOW_UP] = "male_sterilization";
        alert_name_to_fp_method_map[constants.FEMALE_STERILIZATION_FOLLOW_UP] = "female_sterilization";
        alert_name_to_fp_method_map[constants.IUD_FOLLOW_UP_1] = "iud";
        alert_name_to_fp_method_map[constants.IUD_FOLLOW_UP_2] = "iud";

        return {
            constants: constants,
            preProcessClients:function (clients) {
                clients.forEach(function (client) {
                    // find a referral alert if it exists
                    var referral_alert = client.alerts.find(function(a){
                        return a.name === constants.REFERRAL_FOLLOW_UP;
                    });

                    if(referral_alert !== undefined)
                    {
                        client.refill_follow_ups = {
                            name: referral_alert.name,
                            alert_index: client.alerts.indexOf(referral_alert),
                            type: "referral"
                        }
                    }
                    else{
                        // find a normal follow-up alert that matches the fp method
                        var follow_up_alert = client.alerts.find(function(a){
                            return follow_up_types.indexOf(a.name) > -1 &&
                                client.fp_method === alert_name_to_fp_method_map[a.name];
                        });

                        if(follow_up_alert !== undefined)
                        {
                            client.refill_follow_ups = {
                                name: follow_up_alert.name,
                                alert_index: client.alerts.indexOf(follow_up_alert),
                                type: "follow-up"
                            }
                        }
                        else
                        {
                            var refill_alert = client.alerts.find(function(a){
                                return refill_types.indexOf(a.name) > -1 &&
                                    client.fp_method === alert_name_to_fp_method_map[a.name];
                            });

                            if (refill_alert !== undefined) {
                                client.refill_follow_ups = {
                                    name:refill_alert.name,
                                    alert_index:client.alerts.indexOf(refill_alert),
                                    type: "refill"
                                }
                            }
                        }
                    }
                });
                return clients;
            }
        }
    });
