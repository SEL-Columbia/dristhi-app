package org.ei.telemedicine.view.controller;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import org.ei.telemedicine.domain.Report;
import org.ei.telemedicine.dto.MonthSummaryDatum;
import org.ei.telemedicine.view.activity.ReportIndicatorCaseListActivity;
import org.ei.telemedicine.view.contract.IndicatorReportDetail;

import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.telemedicine.AllConstants.*;

public class ReportIndicatorDetailViewController {
    private final Context context;
    private final Report indicatorDetails;
    private String categoryDescription;

    public ReportIndicatorDetailViewController(Context context, Report indicatorDetails, String categoryDescription) {
        this.context = context;
        this.indicatorDetails = indicatorDetails;
        this.categoryDescription = categoryDescription;
    }

    public String get() {
        String annualTarget = (isBlank(indicatorDetails.annualTarget())) ? "NA" : indicatorDetails.annualTarget();

        return new Gson().toJson(new IndicatorReportDetail(categoryDescription, indicatorDetails.reportIndicator().description(),
                indicatorDetails.reportIndicator().name(), annualTarget, indicatorDetails.monthlySummaries()));
    }

    public void startReportIndicatorCaseList(String month) {
        for (MonthSummaryDatum summary : indicatorDetails.monthlySummaries()) {
            if (summary.month().equals(month)) {
                Intent intent = new Intent(context.getApplicationContext(), ReportIndicatorCaseListActivity.class);
                intent.putExtra(MONTH, month);
                intent.putExtra(INDICATOR, indicatorDetails.reportIndicator().name());
                intent.putStringArrayListExtra(CASE_IDS, new ArrayList<String>(summary.externalIDs()));
                context.startActivity(intent);
                return;
            }
        }
    }
}
