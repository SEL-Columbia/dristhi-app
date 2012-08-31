package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.view.contract.WorkplanContext;
import org.ei.drishti.view.contract.WorkplanTodo;

import java.util.ArrayList;
import java.util.List;

import static org.ei.drishti.dto.AlertPriority.normal;

public class WorkplanController {
    private AllAlerts allAlerts;
    private Context context;

    public WorkplanController(AllAlerts allAlerts, Context context) {
        this.allAlerts = allAlerts;
        this.context = context;
    }

    public String get() {
        List<Alert> alerts = allAlerts.fetchAll();
        List<WorkplanTodo> overdue = new ArrayList<WorkplanTodo>();
        List<WorkplanTodo> upcoming = new ArrayList<WorkplanTodo>();
        List<WorkplanTodo> completed = new ArrayList<WorkplanTodo>();

        for (Alert alert : alerts) {
            WorkplanTodo todo = new WorkplanTodo(alert.beneficiaryName(), alert.visitCode(), alert.expiryDate());

            if (alert.isClosed()) {
                completed.add(todo);
            } else if (normal.equals(alert.priority())) {
                upcoming.add(todo);
            } else {
                overdue.add(todo);
            }
        }

        return new Gson().toJson(new WorkplanContext(overdue, upcoming, completed));
    }
}
