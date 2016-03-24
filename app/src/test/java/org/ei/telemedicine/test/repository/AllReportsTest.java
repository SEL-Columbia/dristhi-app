package org.ei.telemedicine.test.repository;

import org.ei.telemedicine.domain.Report;
import org.ei.telemedicine.dto.Action;
import org.ei.telemedicine.repository.AllReports;
import org.ei.telemedicine.repository.ReportRepository;
import org.ei.telemedicine.test.util.ActionBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.telemedicine.domain.ReportIndicator.CONDOM;
import static org.ei.telemedicine.domain.ReportIndicator.EARLY_ANC_REGISTRATIONS;
import static org.ei.telemedicine.domain.ReportIndicator.IUD;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AllReportsTest {
    @Mock
    private ReportRepository repository;
    private AllReports allReports;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allReports = new AllReports(repository);
    }

    @Test
    public void shouldDelegateActionToReportRepository() throws Exception {
        Action iudAction = ActionBuilder.actionForReport("IUD", "40");

        allReports.handleAction(iudAction);

        verify(repository).update(new Report("IUD", "40", "some-month-summary-json"));
    }

    @Test
    public void shouldGetReportsForGivenIndicators() throws Exception {
        List<Report> expectedReports = asList(new Report("IUD", "40", "some-month-summary-json"), new Report("ANC_LT_12", "30", "some-month-summary-json"));
        when(repository.allFor("IUD", "CONDOM", "ANC_LT_12")).thenReturn(expectedReports);

        List<Report> reports = allReports.allFor(asList(IUD, CONDOM, EARLY_ANC_REGISTRATIONS));

        assertEquals(expectedReports, reports);
    }
}
