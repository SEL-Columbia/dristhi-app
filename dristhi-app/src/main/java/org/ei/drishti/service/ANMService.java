package org.ei.drishti.service;

import org.ei.drishti.domain.ANM;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;

public class ANMService {
    private AllSettings allSettings;
    private AllBeneficiaries allBeneficiaries;
    private AllEligibleCouples allEligibleCouples;

    public ANMService(AllSettings allSettings, AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples) {
        this.allSettings = allSettings;
        this.allBeneficiaries = allBeneficiaries;
        this.allEligibleCouples = allEligibleCouples;
    }

    public ANM fetchDetails() {
        return new ANM(allSettings.fetchRegisteredANM(), allEligibleCouples.count(), allEligibleCouples.fpCount(), allBeneficiaries.ancCount(), allBeneficiaries.pncCount(), allBeneficiaries.childCount());
    }
}
