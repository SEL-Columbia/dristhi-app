package org.ei.drishti.repository;

import org.ei.drishti.domain.EligibleCouple;

import java.util.List;
import java.util.Map;

public class AllEligibleCouples {
    private EligibleCoupleRepository eligibleCoupleRepository;
    private final TimelineEventRepository timelineEventRepository;

    public AllEligibleCouples(EligibleCoupleRepository eligibleCoupleRepository, TimelineEventRepository timelineEventRepository) {
        this.eligibleCoupleRepository = eligibleCoupleRepository;
        this.timelineEventRepository = timelineEventRepository;
    }

    public List<EligibleCouple> all() {
        return eligibleCoupleRepository.allEligibleCouples();
    }

    public EligibleCouple findByCaseID(String caseId) {
        return eligibleCoupleRepository.findByCaseID(caseId);
    }

    public long count() {
        return eligibleCoupleRepository.count();
    }

    public long fpCount() {
        return eligibleCoupleRepository.fpCount();
    }

    public List<String> villages() {
        return eligibleCoupleRepository.villages();
    }

    public List<EligibleCouple> findByCaseIDs(List<String> caseIds) {
        return eligibleCoupleRepository.findByCaseIDs(caseIds.toArray(new String[caseIds.size()]));
    }

    public void updatePhotoPath(String caseId, String imagePath) {
        eligibleCoupleRepository.updatePhotoPath(caseId, imagePath);
    }

    public void close(String entityId) {
        eligibleCoupleRepository.close(entityId);
        timelineEventRepository.deleteAllTimelineEventsForEntity(entityId);
    }

    public void mergeDetails(String entityId, Map<String, String> details) {
        eligibleCoupleRepository.mergeDetails(entityId, details);
    }
}
