package org.ei.drishti.repository;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertAction;
import org.ei.drishti.exception.AlertTypeException;

import java.util.List;

public class AllAlerts {
    private AlertRepository repository;

    public AllAlerts(AlertRepository repository) {
        this.repository = repository;
    }

    public List<Alert> fetchAlerts(String visitCodePrefix) {
        return repository.alertsFor(visitCodePrefix);
    }

    public void saveNewAlerts(List<AlertAction> alertActions) {
        for (AlertAction alertAction : alertActions) {
            if ("create".equals(alertAction.type())) {
                repository.update(alertAction);
            } else if ("delete".equals(alertAction.type())) {
                repository.delete(alertAction);
            } else if ("deleteAll".equals(alertAction.type())) {
                repository.deleteAll(alertAction);
            } else {
                throw new AlertTypeException("Unknown type in alert action: " + alertAction);
            }
        }
    }

    public void deleteAllAlerts() {
        repository.deleteAllAlerts();
    }
}
