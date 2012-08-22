package org.ei.drishti.repository;

import org.ei.drishti.domain.*;
import org.ei.drishti.dto.Action;
import org.ei.drishti.dto.BeneficiaryType;
import org.ei.drishti.util.Log;

import java.util.List;

import static org.ei.drishti.dto.BeneficiaryType.child;
import static org.ei.drishti.dto.BeneficiaryType.mother;

public class AllAlerts {
    private AlertRepository repository;
    private AllBeneficiaries allBeneficiaries;
    private AllEligibleCouples allEligibleCouples;

    public AllAlerts(AlertRepository repository, AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples) {
        this.repository = repository;
        this.allBeneficiaries = allBeneficiaries;
        this.allEligibleCouples = allEligibleCouples;
    }

    public List<Alert> fetchAll() {
        return repository.allAlerts();
    }

    public void handleAction(Action action) {
        if ("createAlert".equals(action.type())) {
            createAlert(action);
        } else if ("deleteAlert".equals(action.type())) {
            repository.deleteAlertsForVisitCodeOfCase(action.caseID(), action.get("visitCode"));
        } else if ("deleteAllAlerts".equals(action.type())) {
            repository.deleteAllAlertsForCase(action.caseID());
        } else {
            Log.logWarn("Unknown type in alert action: " + action);
        }
    }

    public void deleteAllAlerts() {
        repository.deleteAllAlerts();
    }

    public List<VillageAlertSummary> villageSummary() {
        return repository.summary();
    }

    public List<Alert> fetchAllFor(String villageName) {
        return repository.allAlertsFor(villageName);
    }

    private void createAlert(Action action) {
        BeneficiaryType type = BeneficiaryType.from(action.get("beneficiaryType"));

        if (mother.equals(type)) {
            Mother mom = allBeneficiaries.findMother(action.caseID());
            EligibleCouple couple = allEligibleCouples.findByCaseID(mom.ecCaseId());
            repository.createAlert(new Alert(action.caseID(),couple.wifeName(), couple.village(), action.get("visitCode"), mom.thaayiCardNumber(), 0, action.get("startDate"), action.get("expiryDate")));
        } else if (child.equals(type)) {
            Child kid = allBeneficiaries.findChild(action.caseID());
            EligibleCouple couple = allEligibleCouples.findByCaseID(kid.ecCaseId());
            repository.createAlert(new Alert(action.caseID(),couple.wifeName(), couple.village(), action.get("visitCode"), kid.thaayiCardNumber(), 0, action.get("startDate"), action.get("expiryDate")));
        } else {
            Log.logWarn("Unknown beneficiary type to add alert for: " + action);
        }
    }
}
