package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.domain.Report;
import org.ei.drishti.domain.ReportIndicator;
import org.ei.drishti.view.contract.IndicatorReportDetail;

import static org.apache.commons.lang.StringUtils.isBlank;

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

        return new Gson().toJson(new IndicatorReportDetail(categoryDescription, ReportIndicator.valueOf(indicatorDetails.indicator()).description(),
                indicatorDetails.indicator(), annualTarget, indicatorDetails.monthlySummaries()));
    }
}
