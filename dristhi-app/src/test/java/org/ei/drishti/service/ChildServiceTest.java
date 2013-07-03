package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.ServiceProvided;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.repository.ChildRepository;
import org.ei.drishti.repository.MotherRepository;
import org.ei.drishti.util.ActionBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.TimelineEvent.forChildBirthInChildProfile;
import static org.ei.drishti.domain.TimelineEvent.forChildBirthInMotherProfile;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class ChildServiceTest {
    @Mock
    private AllTimelineEvents allTimelineEvents;
    @Mock
    private ChildRepository childRepository;
    @Mock
    private MotherRepository motherRepository;
    @Mock
    private ServiceProvidedService serviceProvidedService;
    private ChildService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new ChildService(motherRepository, childRepository, allTimelineEvents, serviceProvidedService);
    }

    @Test
    public void shouldHandlePNCVisitActionForChild() throws Exception {
        String caseId = "Case Child X";
        Action action = ActionBuilder.actionForChildPNCVisit(caseId, mapOf("some-key", "some-value"));

        service.pncVisit(action);

        verify(allTimelineEvents).add(TimelineEvent.forChildPNCVisit(caseId, "1", "2012-01-01", mapOf("some-key", "some-value")));
        verify(childRepository).updateDetails(caseId, mapOf("some-key", "some-value"));
    }

    @Test
    public void shouldUpdateEveryChildWhileRegistering() throws Exception {
        Child firstChild = new Child("Child X", "Mother X", "female", create("weight", "3").put("immunizationsGiven", "bcg opv_0").map());
        Child secondChild = new Child("Child Y", "Mother X", "female", create("weight", "4").put("immunizationsGiven", "bcg").map());
        when(motherRepository.findById("Mother X")).thenReturn(new Mother("Mother X", "EC 1", "TC 1", "2012-01-01").withDetails(mapOf("deliveryPlace", "phc")));
        when(childRepository.findByMotherCaseId("Mother X")).thenReturn(asList(firstChild, secondChild));
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("Mother X");

        service.register(submission);

        verify(childRepository).findByMotherCaseId("Mother X");
        verify(childRepository).update(firstChild.setIsClosed(false).setDateOfBirth("2012-01-01").setThayiCardNumber("TC 1"));
        verify(childRepository).update(secondChild.setIsClosed(false).setDateOfBirth("2012-01-01").setThayiCardNumber("TC 1"));
        verify(allTimelineEvents).add(forChildBirthInChildProfile("Child X", "2012-01-01", "3", "bcg opv_0"));
        verify(allTimelineEvents).add(forChildBirthInChildProfile("Child Y", "2012-01-01", "4", "bcg"));
        verify(allTimelineEvents, times(2)).add(forChildBirthInMotherProfile("Mother X", "2012-01-01", "female", "2012-01-01", "phc"));
        verifyNoMoreInteractions(childRepository);
    }

    @Test
    public void shouldUpdateEveryChildWhilePNCRegistration() throws Exception {
        Child firstChild = new Child("Child X", "Mother X", "female", create("weight", "3").put("immunizationsGiven", "bcg opv_0").map());
        Child secondChild = new Child("Child Y", "Mother X", "female", create("weight", "4").put("immunizationsGiven", "bcg").map());
        when(motherRepository.findAllCasesForEC("EC X")).thenReturn(asList(new Mother("Mother X", "EC X", "TC 1", "2012-01-01")));
        when(childRepository.findByMotherCaseId("Mother X")).thenReturn(asList(firstChild, secondChild));
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("EC X");
        when(submission.getFieldValue("deliveryPlace")).thenReturn("subcenter");

        service.pncRegistrationOA(submission);

        verify(childRepository).findByMotherCaseId("Mother X");
        verify(childRepository).update(firstChild.setIsClosed(false).setDateOfBirth("2012-01-01").setThayiCardNumber("TC 1"));
        verify(childRepository).update(secondChild.setIsClosed(false).setDateOfBirth("2012-01-01").setThayiCardNumber("TC 1"));
        verify(allTimelineEvents).add(forChildBirthInChildProfile("Child X", "2012-01-01", "3", "bcg opv_0"));
        verify(allTimelineEvents).add(forChildBirthInChildProfile("Child Y", "2012-01-01", "4", "bcg"));
        verify(allTimelineEvents, times(2)).add(forChildBirthInMotherProfile("Mother X", "2012-01-01", "female", "2012-01-01", "subcenter"));
        verifyNoMoreInteractions(childRepository);
    }

    @Test
    public void shouldAddTimelineEventsWhenChildImmunizationsAreUpdated() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("child id 1");
        when(submission.getFieldValue("immunizationsGiven")).thenReturn("bcg opv_0");
        when(submission.getFieldValue("immunizationDate")).thenReturn("2013-01-01");

        service.updateImmunizations(submission);

        verify(allTimelineEvents).add(TimelineEvent.forChildImmunization("child id 1", "bcg opv_0", "2013-01-01"
        ));
    }

    @Test
    public void shouldAddServiceProvidedWhenChildImmunizationsAreUpdated() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("child id 1");
        when(submission.getFieldValue("immunizationsGiven")).thenReturn("bcg opv_0");
        when(submission.getFieldValue("immunizationDate")).thenReturn("2013-01-01");

        service.updateImmunizations(submission);

        verify(serviceProvidedService).add(new ServiceProvided("child id 1", "bcg", "2013-01-01", null));
        verify(serviceProvidedService).add(new ServiceProvided("child id 1", "opv_0", "2013-01-01", null));
    }

    @Test
    public void shouldAddTimelineEventWhenChildIsRegisteredForEC() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("child id 1");
        when(submission.getFieldValue("motherId")).thenReturn("mother id 1");
        when(submission.getFieldValue("dateOfBirth")).thenReturn("2013-01-02");
        when(submission.getFieldValue("gender")).thenReturn("female");
        when(submission.getFieldValue("weight")).thenReturn("3");
        when(submission.getFieldValue("immunizationsGiven")).thenReturn("bcg opv_0");

        service.registerForEC(submission);

        verify(allTimelineEvents).add(forChildBirthInChildProfile("child id 1", "2013-01-02", "3", "bcg opv_0"));
        verify(allTimelineEvents).add(forChildBirthInMotherProfile("mother id 1", "2013-01-02", "female", null, null));
    }

    @Test
    public void shouldCloseChildRecordForDeleteChildAction() throws Exception {
        Action action = ActionBuilder.closeChild("Case X");

        service.delete(action);

        verify(childRepository).close("Case X");
    }
}
