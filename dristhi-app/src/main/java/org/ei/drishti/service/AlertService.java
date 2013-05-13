package org.ei.drishti.service;

import org.ei.drishti.domain.*;
import org.ei.drishti.dto.Action;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.dto.BeneficiaryType;
import org.ei.drishti.repository.AlertRepository;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.util.Log;

import java.util.List;

import static org.ei.drishti.dto.BeneficiaryType.child;
import static org.ei.drishti.dto.BeneficiaryType.mother;

public class AlertService {
    private AlertRepository repository;
    private AllBeneficiaries allBeneficiaries;
    private AllEligibleCouples allEligibleCouples;

    public AlertService(AlertRepository repository, AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples) {
        this.repository = repository;
        this.allBeneficiaries = allBeneficiaries;
        this.allEligibleCouples = allEligibleCouples;
    }

    public void create(Action action) {
        if (action.isActionActive() == null || action.isActionActive()) {
            createAlert(action);
        }
    }

    public void close(Action action) {
        repository.markAlertAsClosed(action.caseID(), action.get("visitCode"), action.get("completionDate"));
    }

    public void deleteAll(Action action) {
        repository.deleteAllAlertsForCase(action.caseID());
    }

    private void createAlert(Action action) {
        BeneficiaryType type = BeneficiaryType.from(action.get("beneficiaryType"));

        if (mother.equals(type)) {
            Mother mom = allBeneficiaries.findMother(action.caseID());
            EligibleCouple couple = allEligibleCouples.findByCaseID(mom.ecCaseId());
            repository.createAlert(new Alert(action.caseID(), couple.wifeName(), couple.husbandName(), couple.village(), action.get("visitCode"), mom.thaayiCardNumber(), AlertPriority.from(action.get("alertPriority")), action.get("startDate"), action.get("expiryDate"), AlertStatus.open));
        } else if (child.equals(type)) {
            Child kid = allBeneficiaries.findChild(action.caseID());
            Mother mom = allBeneficiaries.findMother(kid.motherCaseId());
            EligibleCouple momDad = allEligibleCouples.findByCaseID(mom.ecCaseId());
            repository.createAlert(new Alert(action.caseID(), "B/O " + momDad.wifeName(), momDad.husbandName(), momDad.village(), action.get("visitCode"), kid.thaayiCardNumber(), AlertPriority.from(action.get("alertPriority")), action.get("startDate"), action.get("expiryDate"), AlertStatus.open));
        } else {
            Log.logWarn("Unknown beneficiary type to add alert for: " + action);
        }
    }

    public List<Alert> findByECIdAndAlertNames(String entityId, List<String> names) {
        return repository.findByECIdAndAlertNames(entityId, names);
    }
}
