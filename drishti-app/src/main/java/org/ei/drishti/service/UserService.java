package org.ei.drishti.service;

import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;

public class UserService {
    private AllSettings allSettings;
    private AllAlerts allAlerts;
    private AllEligibleCouples allEligibleCouples;

    public UserService(AllSettings allSettings, AllAlerts allAlerts, AllEligibleCouples allEligibleCouples) {
        this.allSettings = allSettings;
        this.allAlerts = allAlerts;
        this.allEligibleCouples = allEligibleCouples;
    }

    public void changeUser() {
        allSettings.savePreviousFetchIndex("0");
        allAlerts.deleteAllAlerts();
        allEligibleCouples.deleteAll();
    }
}
