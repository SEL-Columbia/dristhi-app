package org.ei.drishti.repository;

import org.ei.drishti.domain.EligibleCouple;

import java.util.List;

public class AllEligibleCouples {
    private EligibleCoupleRepository repository;

    public AllEligibleCouples(EligibleCoupleRepository eligibleCoupleRepository) {
        this.repository = eligibleCoupleRepository;
    }

    public List<EligibleCouple> all() {
        return repository.allEligibleCouples();
    }

    public EligibleCouple findByCaseID(String caseId) {
        return repository.findByCaseID(caseId);
    }

    public long count() {
        return repository.count();
    }

    public List<String> villages() {
        return repository.villages();
    }

    public List<EligibleCouple> findByCaseIDs(List<String> caseIds) {
        return repository.findByCaseIDs(caseIds.toArray(new String[caseIds.size()]));
    }

    public void updatePhotoPath(String caseId, String imagePath) {
        repository.updatePhotoPath(caseId, imagePath);
    }
}
