package org.ei.drishti.repository;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertAction;
import org.ei.drishti.util.Log;

import java.util.List;

public class AllAlerts {
    private AlertRepository repository;

    public AllAlerts(AlertRepository repository) {
        this.repository = repository;
    }

    public List<Alert> fetchAlerts() {
        return repository.allAlerts();
    }

    public void saveNewAlerts(List<AlertAction> alertActions) {
        for (AlertAction alertAction : alertActions) {
            if ("createAlert".equals(alertAction.type())) {
                repository.update(alertAction);
            } else if ("deleteAlert".equals(alertAction.type())) {
                repository.delete(alertAction);
            } else if ("deleteAllAlerts".equals(alertAction.type())) {
                repository.deleteAll(alertAction);
            } else {
                Log.logWarn("Unknown type in alert action: " + alertAction);
            }
        }
    }

    public void deleteAllAlerts() {
        repository.deleteAllAlerts();
    }
}
