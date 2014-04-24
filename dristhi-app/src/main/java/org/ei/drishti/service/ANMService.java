package org.ei.drishti.service;

import org.ei.drishti.domain.ANM;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.repository.AllSharedPreferences;

public class ANMService {
    private AllSettings allSettings;
    private AllSharedPreferences allSharedPreferences;
    private AllBeneficiaries allBeneficiaries;
    private AllEligibleCouples allEligibleCouples;

    public ANMService(AllSettings allSettings, AllSharedPreferences allSharedPreferences, AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples) {
        this.allSettings = allSettings;
        this.allBeneficiaries = allBeneficiaries;
        this.allEligibleCouples = allEligibleCouples;
    }

    public ANM fetchDetails() {
        return new ANM(allSharedPreferences.fetchRegisteredANM(), allEligibleCouples.count(), allEligibleCouples.fpCount(),
                allBeneficiaries.ancCount(), allBeneficiaries.pncCount(), allBeneficiaries.childCount());
    }
}
