package org.ei.drishti.service;

import org.apache.commons.lang.StringUtils;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.repository.EligibleCoupleRepository;
import org.ei.drishti.repository.MotherRepository;
import org.ei.drishti.util.IntegerUtil;

import static org.ei.drishti.domain.TimelineEvent.*;

public class MotherService {
    private MotherRepository motherRepository;
    private AllTimelineEvents allTimelines;
    private EligibleCoupleRepository eligibleCoupleRepository;

    public MotherService(MotherRepository motherRepository, AllTimelineEvents allTimelineEvents, EligibleCoupleRepository eligibleCoupleRepository) {
        this.motherRepository = motherRepository;
        this.allTimelines = allTimelineEvents;
        this.eligibleCoupleRepository = eligibleCoupleRepository;
    }

    public void handleAction(Action action) {
        if (action.type().equals("registerPregnancy")) {
            motherRepository.add(new Mother(action.caseID(), action.get("ecCaseId"), action.get("thaayiCardNumber"), action.get("referenceDate"))
                    .withDetails(action.details()));
        } else if (action.type().equals("close")) {
            motherRepository.close(action.caseID());
        } else if (action.type().equals("updateDetails") || action.type().equals("updateBirthPlanning")) {
            motherRepository.updateDetails(action.caseID(), action.details());
        } else if (action.type().equals("ancCareProvided")) {
            allTimelines.add(forANCCareProvided(action.caseID(), action.get("visitNumber"), action.get("visitDate"), action.details()));

            String numberOfIFATabletsProvided = action.get("numberOfIFATabletsProvided");
            if (numberOfIFATabletsProvided != null && Integer.parseInt(numberOfIFATabletsProvided) > 0) {
                allTimelines.add(forIFATabletsProvided(action.caseID(), numberOfIFATabletsProvided, action.get("visitDate")));
            }
            if (StringUtils.isNotBlank(action.get("ttDose"))) {
                allTimelines.add(forTTShotProvided(action.caseID(), action.get("ttDose"), action.get("visitDate")));
            }
        } else if (action.type().equals("registerOutOfAreaANC")) {
            eligibleCoupleRepository.add(new EligibleCouple(action.get("ecCaseId"), action.get("wife"), action.get("husband"), "", action.get("village"), action.get("subcenter"), action.details()).asOutOfArea());
            motherRepository.add(new Mother(action.caseID(), action.get("ecCaseId"), action.get("thaayiCardNumber"), action.get("referenceDate"))
                    .withDetails(action.details()));
        } else if (action.type().equals("updateANCOutcome")) {
            motherRepository.switchToPNC(action.caseID());
            motherRepository.updateDetails(action.caseID(), action.details());
        } else if (action.type().equals("pncVisitHappened")) {
            allTimelines.add(forMotherPNCVisit(action.caseID(), action.get("visitNumber"), action.get("visitDate"), action.details()));
            motherRepository.updateDetails(action.caseID(), action.details());

            String numberOfIFATabletsProvided = action.get("numberOfIFATabletsProvided");
            if (numberOfIFATabletsProvided != null && IntegerUtil.tryParse(numberOfIFATabletsProvided, 0) > 0) {
                allTimelines.add(forIFATabletsProvided(action.caseID(), numberOfIFATabletsProvided, action.get("visitDate")));
            }
        }
    }
}
