package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Alert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.dto.AlertPriority.normal;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllAlertsTest {
    @Mock
    private AlertRepository alertRepository;

    private AllAlerts allAlerts;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allAlerts = new AllAlerts(alertRepository);
    }

    @Test
    public void shouldDeleteAllAlerts() throws Exception {
        allAlerts.deleteAllAlerts();

        verify(alertRepository).deleteAllAlerts();
    }

    @Test
    public void shouldFetchAllAlertsFromRepository() throws Exception {
        List<Alert> expectedAlerts = Arrays.asList(new Alert("Case X", "Theresa 1", "Husband 1", "bherya", "ANC 1", "Thaayi 1", normal,
                "2012-01-01", "2012-01-11", open), new Alert("Case Y", "Theresa 2", "Husband 2", "bherya", "ANC 2", "Thaayi 2", normal, "2012-01-01", "2012-01-22", open));
        when(alertRepository.allAlerts()).thenReturn(expectedAlerts);

        List<Alert> alerts = allAlerts.fetchAll();

        assertEquals(expectedAlerts, alerts);
    }
}
