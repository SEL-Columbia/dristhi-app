package org.ei.drishti.repository;

import org.ei.drishti.domain.Action;
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
            repository.update(action);
        } else if ("deleteAlert".equals(action.type())) {
            repository.deleteAlertsForVisitCodeOfCase(action);
        } else if ("deleteAllAlerts".equals(action.type())) {
            repository.deleteAllAlertsForCase(action);
        } else {
            Log.logWarn("Unknown type in alert action: " + action);
        }
    }

    public void deleteAllAlerts() {
        repository.deleteAllAlerts();
    }

    public List<String> uniqueLocations() {
        return repository.uniqueLocations();
    }
}
