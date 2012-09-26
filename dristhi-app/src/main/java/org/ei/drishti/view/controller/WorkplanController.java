package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.view.contract.Village;
import org.ei.drishti.view.contract.WorkplanContext;
import org.ei.drishti.view.contract.WorkplanTodo;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static org.ei.drishti.dto.AlertPriority.normal;

public class WorkplanController {
    private AllAlerts allAlerts;
    private AllEligibleCouples allEligibleCouples;
    private Context context;
    private CommCareClientService commCareClientService;

    public WorkplanController(AllAlerts allAlerts, CommCareClientService commCareClientService, AllEligibleCouples allEligibleCouples, Context context) {
        this.allAlerts = allAlerts;
        this.allEligibleCouples = allEligibleCouples;
        this.context = context;
        this.commCareClientService = commCareClientService;
    }

    public String get() {
        List<Alert> alerts = allAlerts.fetchAll();
        List<WorkplanTodo> overdue = new ArrayList<WorkplanTodo>();
        List<WorkplanTodo> upcoming = new ArrayList<WorkplanTodo>();
        List<WorkplanTodo> completed = new ArrayList<WorkplanTodo>();

        for (Alert alert : alerts) {
            WorkplanTodo todo = new WorkplanTodo(alert.caseId(), alert.beneficiaryName(), alert.husbandName(), alert.visitCode(), alert.expiryDate(), alert.village());

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

    public void startCommCare(String formId, String caseId) {
        commCareClientService.start(context, formId, caseId);
    }

    public String villages() {
        List<Village> villagesList = new ArrayList<Village>();
        List<String> villages = allEligibleCouples.villages();
        villagesList.add(new Village("All"));
        for (String village : villages) {
            villagesList.add(new Village(village));
        }

        return new Gson().toJson(villagesList);
    }

    public void markTodoAsCompleted(String caseId, String visitCode) {
        allAlerts.markAsCompleted(caseId, visitCode, LocalDate.now().toString());
    }
}
