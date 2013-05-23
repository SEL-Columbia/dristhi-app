package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllBeneficiariesTest {
    @Mock
    private MotherRepository motherRepository;
    @Mock
    private ChildRepository childRepository;
    @Mock
    private AlertRepository alertRepository;
    @Mock
    private TimelineEventRepository timelineEventRepository;

    private AllBeneficiaries allBeneficiaries;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allBeneficiaries = new AllBeneficiaries(motherRepository, childRepository, alertRepository, timelineEventRepository);
    }

    @Test
    public void shouldDeleteTimelineEventsAndAlertsWhileClosingMother() throws Exception {
        allBeneficiaries.closeMother("entity id 1");

        verify(alertRepository).deleteAllAlertsForEntity("entity id 1");
        verify(timelineEventRepository).deleteAllTimelineEventsForEntity("entity id 1");
    }
}
