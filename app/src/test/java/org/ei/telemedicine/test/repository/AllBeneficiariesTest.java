package org.ei.telemedicine.test.repository;

import org.ei.telemedicine.domain.Child;
import org.ei.telemedicine.domain.Mother;
import org.ei.telemedicine.repository.AlertRepository;
import org.ei.telemedicine.repository.AllBeneficiaries;
import org.ei.telemedicine.repository.ChildRepository;
import org.ei.telemedicine.repository.DoctorRepository;
import org.ei.telemedicine.repository.MotherRepository;
import org.ei.telemedicine.repository.TestRepository;
import org.ei.telemedicine.repository.TimelineEventRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class AllBeneficiariesTest {
    @Mock
    private MotherRepository motherRepository;
    @Mock
    private ChildRepository childRepository;
    @Mock
    private AlertRepository alertRepository;
    @Mock
    private TimelineEventRepository timelineEventRepository;
    @Mock
    private TestRepository testRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private Child child;
    @Mock
    private Mother mother;

    private AllBeneficiaries allBeneficiaries;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allBeneficiaries = new AllBeneficiaries(motherRepository, childRepository, alertRepository, timelineEventRepository, testRepository, doctorRepository);
    }

    @Test
    public void shouldDeleteTimelineEventsAndAlertsWhileClosingMother() throws Exception {
        allBeneficiaries.closeMother("entity id 1");

        verify(alertRepository).deleteAllAlertsForEntity("entity id 1");
        verify(timelineEventRepository).deleteAllTimelineEventsForEntity("entity id 1");
    }

    @Test
    public void shouldDeleteTimelineEventsAndAlertsForAllMothersWhenECIsClosed() throws Exception {
        when(motherRepository.findAllCasesForEC("ec id 1"))
                .thenReturn(asList(new Mother("mother id 1", "ec id 1", "12345", "2012-12-12"), new Mother("mother id 2", "ec id 2", "123456", "2012-12-10")));

        allBeneficiaries.closeAllMothersForEC("ec id 1");

        verify(alertRepository).deleteAllAlertsForEntity("mother id 1");
        verify(alertRepository).deleteAllAlertsForEntity("mother id 2");
        verify(timelineEventRepository).deleteAllTimelineEventsForEntity("mother id 1");
        verify(timelineEventRepository).deleteAllTimelineEventsForEntity("mother id 2");
        verify(motherRepository).close("mother id 1");
        verify(motherRepository).close("mother id 2");
    }

    @Test
    public void shouldDeleteTimelineEventsAndAlertsWhenAChildIsClosed() throws Exception {
        when(childRepository.find("child id 1"))
                .thenReturn(new Child("child id 1", "mother id 1", "male", new HashMap<String, String>()));

        allBeneficiaries.closeChild("child id 1");

        verify(alertRepository).deleteAllAlertsForEntity("child id 1");
        verify(timelineEventRepository).deleteAllTimelineEventsForEntity("child id 1");
        verify(childRepository).close("child id 1");
    }

    @Test
    public void shouldNotFailClosingMotherWhenECIsClosedAndDoesNotHaveAnyMothers() throws Exception {
        when(motherRepository.findAllCasesForEC("ec id 1")).thenReturn(null);
        when(motherRepository.findAllCasesForEC("ec id 1")).thenReturn(Collections.<Mother>emptyList());

        allBeneficiaries.closeAllMothersForEC("ec id 1");

        verifyZeroInteractions(alertRepository);
        verifyZeroInteractions(timelineEventRepository);
        verify(motherRepository, times(0)).close(any(String.class));
    }

    @Test
    public void shouldDelegateToChildRepositoryWhenUpdateChildIsCalled() throws Exception {
        allBeneficiaries.updateChild(child);

        verify(childRepository).update(child);
    }

    @Test
    public void shouldDelegateToMotherRepositoryWhenUpdateMotherIsCalled() throws Exception {
        allBeneficiaries.updateMother(mother);

        verify(motherRepository).update(mother);
    }
}
