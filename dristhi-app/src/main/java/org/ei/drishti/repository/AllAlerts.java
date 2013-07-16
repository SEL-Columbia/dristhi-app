package org.ei.drishti.repository;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.view.contract.ProfileTodo;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.ei.drishti.dto.AlertStatus.urgent;

public class AllAlerts {
    private AlertRepository repository;

    public AllAlerts(AlertRepository repository) {
        this.repository = repository;
    }

    public List<List<ProfileTodo>> fetchAllActiveAlertsForCase(String caseId) {
        return classifyTodosBasedOnUrgency(repository.allActiveAlertsForCase(caseId));
    }

    public void markAsCompleted(String caseId, String visitCode, String completionDate) {
        repository.markAlertAsClosed(caseId, visitCode, completionDate);
    }

    public void changeAlertStatusToInProcess(String entityId, String alertName) {
        repository.changeAlertStatusToInProcess(entityId, alertName);
    }

    private List<List<ProfileTodo>> classifyTodosBasedOnUrgency(List<Alert> alerts) {
        List<ProfileTodo> todos = new ArrayList<ProfileTodo>();
        List<ProfileTodo> urgentTodos = new ArrayList<ProfileTodo>();
        for (Alert alert : alerts) {
            if (urgent.equals(alert.status())) {
                urgentTodos.add(new ProfileTodo(alert));
            } else {
                todos.add(new ProfileTodo(alert));
            }
        }
        return asList(todos, urgentTodos);
    }
}
