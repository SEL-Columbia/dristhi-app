package org.ei.drishti.repository;

import org.ei.drishti.domain.VillageAlertSummary;
import org.ei.drishti.dto.Action;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.util.Log;

import java.util.List;

public class AllAlerts {
    private AlertRepository repository;

    public AllAlerts(AlertRepository repository) {
        this.repository = repository;
    }

    public List<Alert> fetchAll() {
        return repository.allAlerts();
    }

    public void handleAction(Action action) {
        if ("createAlert".equals(action.type())) {
            repository.createAlert(new Alert(action.caseID(), action.get("beneficiaryName"), action.get("village"), action.get("visitCode"), action.get("thaayiCardNumber"), 0, action.get("dueDate")));
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
}
