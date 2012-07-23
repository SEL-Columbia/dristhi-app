package org.ei.drishti.service;

import org.ei.drishti.domain.ANM;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.repository.BeneficiaryRepository;
import org.ei.drishti.repository.EligibleCoupleRepository;

public class ANMService {
    private AllSettings allSettings;
    private final BeneficiaryRepository beneficiaryRepository;
    private final EligibleCoupleRepository eligibleCoupleRepository;

    public ANMService(AllSettings allSettings, BeneficiaryRepository beneficiaryRepository, EligibleCoupleRepository eligibleCoupleRepository) {
        this.allSettings = allSettings;
        this.beneficiaryRepository = beneficiaryRepository;
        this.eligibleCoupleRepository = eligibleCoupleRepository;
    }

    public ANM fetchDetails() {
        return new ANM(allSettings.fetchRegisteredANM(), eligibleCoupleRepository.count(), beneficiaryRepository.ancCount(), beneficiaryRepository.pncCount(), beneficiaryRepository.childCount());
    }
}
