package org.ei.telemedicine.test.view.controller;

import android.content.Context;
import com.google.gson.Gson;

import org.ei.telemedicine.domain.Report;
import org.ei.telemedicine.dto.MonthSummaryDatum;
import org.ei.telemedicine.view.contract.IndicatorReportDetail;
import org.ei.telemedicine.view.controller.ReportIndicatorDetailViewController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ReportIndicatorDetailViewControllerTest {
    @Mock
    private Context context;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldGetIndicatorReportsForGivenCategory() throws Exception {
        List<MonthSummaryDatum> monthlySummaries = asList(new MonthSummaryDatum("10", "2012", "2", "2", asList("123", "456")));
        Report iudReport = new Report("IUD", "40", new Gson().toJson(monthlySummaries));

        ReportIndicatorDetailViewController controller = new ReportIndicatorDetailViewController(context, iudReport, "Family Planning");
        String indicatorReportDetail = controller.get();

        String expectedIndicatorReports = new Gson().toJson(new IndicatorReportDetail("Family Planning", "IUD Adoption", "IUD", "40", monthlySummaries));
        assertEquals(expectedIndicatorReports, indicatorReportDetail);
    }

    @Test
    public void shouldShowNAIfAnnualTargetNotAvailable() throws Exception {
        List<MonthSummaryDatum> monthlySummaries = asList(new MonthSummaryDatum("6", "2012", "2", "2", asList("123", "456")),
                new MonthSummaryDatum("8", "2012", "2", "4", asList("321", "654")));
        Report iudReport = new Report("IUD", null, new Gson().toJson(monthlySummaries));

        ReportIndicatorDetailViewController controller = new ReportIndicatorDetailViewController(context, iudReport, "Family Planning");
        String indicatorReportDetail = controller.get();

        String expectedIndicatorReports = new Gson().toJson(new IndicatorReportDetail("Family Planning", "IUD Adoption", "IUD", "NA", monthlySummaries));
        assertEquals(expectedIndicatorReports, indicatorReportDetail);
    }

}
