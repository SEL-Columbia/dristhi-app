package org.ei.drishti.repository;

import org.ei.drishti.domain.*;
import org.ei.drishti.dto.Action;
import org.ei.drishti.dto.AlertPriority;
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
        } else if ("closeAlert".equals(action.type())) {
            repository.markAlertAsClosed(action.caseID(), action.get("visitCode"));
        } else if ("deleteAllAlerts".equals(action.type())) {
            repository.deleteAllAlertsForCase(action.caseID());
        } else {
            Log.logWarn("Unknown type in alert action: " + action);
        }
    }

    public void deleteAllAlerts() {
        repository.deleteAllAlerts();
    }

    public List<Alert> fetchAllActiveAlertsForCase(String caseId) {
        return repository.allActiveAlertsForCase(caseId);
    }

    private void createAlert(Action action) {
        BeneficiaryType type = BeneficiaryType.from(action.get("beneficiaryType"));

        if (mother.equals(type)) {
            Mother mom = allBeneficiaries.findMother(action.caseID());
            EligibleCouple couple = allEligibleCouples.findByCaseID(mom.ecCaseId());
            repository.createAlert(new Alert(action.caseID(),couple.wifeName(), couple.village(), action.get("visitCode"), mom.thaayiCardNumber(), AlertPriority.from(action.get("alertPriority")), action.get("startDate"), action.get("expiryDate"), AlertStatus.open));
        } else if (child.equals(type)) {
            Child kid = allBeneficiaries.findChild(action.caseID());
            EligibleCouple couple = allEligibleCouples.findByCaseID(kid.ecCaseId());
            repository.createAlert(new Alert(action.caseID(),couple.wifeName(), couple.village(), action.get("visitCode"), kid.thaayiCardNumber(), AlertPriority.from(action.get("alertPriority")), action.get("startDate"), action.get("expiryDate"), AlertStatus.open));
        } else {
            Log.logWarn("Unknown beneficiary type to add alert for: " + action);
        }
    }
}
