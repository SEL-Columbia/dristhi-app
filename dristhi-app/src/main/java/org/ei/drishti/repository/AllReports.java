package org.ei.drishti.repository;

import org.ei.drishti.domain.Report;
import org.ei.drishti.dto.Action;

public class AllReports {
    private ReportRepository repository;

    public AllReports(ReportRepository repository) {
        this.repository = repository;
    }

    public void handleAction(Action action) {
        repository.update(new Report(action.type(), action.get("annualTarget"), action.get("monthlySummaries")));
    }
}
