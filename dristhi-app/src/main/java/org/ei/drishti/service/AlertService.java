package org.ei.drishti.service;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.dto.Action;
import org.ei.drishti.dto.AlertStatus;
import org.ei.drishti.repository.AlertRepository;

import java.util.List;

public class AlertService {
    private AlertRepository repository;

    public AlertService(AlertRepository repository) {
        this.repository = repository;
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
        repository.createAlert(new Alert(action.caseID(), action.get("visitCode"),
                AlertStatus.from(action.get("alertStatus")), action.get("startDate"), action.get("expiryDate")));
    }

    public List<Alert> findByECIdAndAlertNames(String entityId, List<String> names) {
        return repository.findByECIdAndAlertNames(entityId, names);
    }

    public void changeAlertStatusToInProcess(String entityId, String alertName) {
        repository.changeAlertStatusToInProcess(entityId, alertName);
    }
}
