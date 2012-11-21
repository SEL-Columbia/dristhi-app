package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Report;
import org.ei.drishti.dto.Action;
import org.ei.drishti.util.ActionBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllReportsTest {
    @Mock
    private ReportRepository repository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldDelegateActionToReportRepository() throws Exception {
        Action iudAction = ActionBuilder.actionForReport("IUD", "40");
        AllReports allReports = new AllReports(repository);

        allReports.handleAction(iudAction);

        verify(repository).update(new Report("IUD", "40", "some-month-summary-json"));
    }
}
