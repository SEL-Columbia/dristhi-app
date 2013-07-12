package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.ServiceProvided;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.repository.ChildRepository;
import org.ei.drishti.repository.MotherRepository;
import org.ei.drishti.util.ActionBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.ServiceProvided.forChildImmunization;
import static org.ei.drishti.domain.TimelineEvent.*;
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
    @Mock
    private AllBeneficiaries allBeneficiaries;
    private ChildService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new ChildService(allBeneficiaries, motherRepository, childRepository, allTimelineEvents, serviceProvidedService);
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
        verify(allTimelineEvents, times(2)).add(forChildBirthInECProfile("EC 1", "2012-01-01", "female", "2012-01-01"));
        verify(serviceProvidedService).add(ServiceProvided.forChildImmunization("Child X", "bcg opv_0", "2012-01-01"));
        verify(serviceProvidedService).add(ServiceProvided.forChildImmunization("Child Y", "bcg", "2012-01-01"));
        verifyNoMoreInteractions(childRepository);
    }

    @Test
    public void shouldUpdateEveryChildWhilePNCRegistration() throws Exception {
        Child firstChild = new Child("Child X", "Mother X", "female", create("weight", "3").put("immunizationsGiven", "bcg opv_0").map());
        Child secondChild = new Child("Child Y", "Mother X", "female", create("weight", "4").put("immunizationsGiven", "bcg").map());
        Mother mother = new Mother("Mother X", "EC X", "TC 1", "2012-01-01");
        when(motherRepository.findAllCasesForEC("EC X")).thenReturn(asList(mother));
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
        verify(allTimelineEvents, times(2)).add(forChildBirthInECProfile("EC X", "2012-01-01", "female", "2012-01-01"));

        verify(serviceProvidedService).add(forChildImmunization("Child X", "bcg", "2012-01-01"));
        verify(serviceProvidedService).add(forChildImmunization("Child X", "opv_0", "2012-01-01"));
        verify(serviceProvidedService).add(forChildImmunization("Child Y", "bcg", "2012-01-01"));
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
        when(submission.entityId()).thenReturn("ec id 1");
        when(submission.getFieldValue("motherId")).thenReturn("mother id 1");
        when(submission.getFieldValue("childId")).thenReturn("child id 1");
        when(submission.getFieldValue("dateOfBirth")).thenReturn("2013-01-02");
        when(submission.getFieldValue("gender")).thenReturn("female");
        when(submission.getFieldValue("weight")).thenReturn("3");
        when(submission.getFieldValue("immunizationsGiven")).thenReturn("bcg opv_0");
        when(submission.getFieldValue("bcgDate")).thenReturn("2012-01-06");
        when(submission.getFieldValue("opv0Date")).thenReturn("2012-01-07");

        service.registerForEC(submission);

        verify(allTimelineEvents).add(forChildBirthInChildProfile("child id 1", "2013-01-02", "3", "bcg opv_0"));
        verify(allTimelineEvents).add(forChildBirthInMotherProfile("mother id 1", "2013-01-02", "female", null, null));
        verify(allTimelineEvents).add(forChildBirthInECProfile("ec id 1", "2013-01-02", "female", null));

        verify(serviceProvidedService).add(forChildImmunization("ec id 1", "bcg", "2012-01-06"));
        verify(serviceProvidedService).add(forChildImmunization("ec id 1", "opv_0", "2012-01-07"));
    }

    @Test
    public void shouldAddPNCVisitTimelineEventWhenPNCVisitHappens() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("mother id 1");
        when(submission.getFieldValue("pncVisitDay")).thenReturn("2");
        when(submission.getFieldValue("pncVisitDate")).thenReturn("2012-01-01");
        Child firstChild = new Child("child id 1", "Mother X", "female", create("weight", "3").put("temperature", "98").map());
        Child secondChild = new Child("child id 2", "Mother X", "female", create("weight", "4").put("temperature", "98.1").map());
        when(childRepository.findByMotherCaseId("mother id 1")).thenReturn(asList(firstChild, secondChild));

        service.pncVisitHappened(submission);

        verify(allTimelineEvents).add(TimelineEvent.forChildPNCVisit("child id 1", "2", "2012-01-01", "3", "98"));
        verify(allTimelineEvents).add(TimelineEvent.forChildPNCVisit("child id 2", "2", "2012-01-01", "4", "98.1"));
    }

    @Test
    public void shouldAddPNCVisitServiceProvidedWhenPNCVisitHappens() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("mother id 1");
        when(submission.getFieldValue("pncVisitDay")).thenReturn("2");
        when(submission.getFieldValue("pncVisitDate")).thenReturn("2012-01-01");
        Child firstChild = new Child("child id 1", "Mother X", "female", create("weight", "3").put("temperature", "98").map());
        Child secondChild = new Child("child id 2", "Mother X", "female", create("weight", "4").put("temperature", "98.1").map());
        when(childRepository.findByMotherCaseId("mother id 1")).thenReturn(asList(firstChild, secondChild));

        service.pncVisitHappened(submission);

        verify(serviceProvidedService).add(new ServiceProvided("child id 1", "PNC", "2012-01-01", mapOf("day", "2")));
        verify(serviceProvidedService).add(new ServiceProvided("child id 2", "PNC", "2012-01-01", mapOf("day", "2")));
    }

    @Test
    public void shouldCloseChildRecordForDeleteChildAction() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("child id 1");

        service.close(submission);

        verify(allBeneficiaries).closeChild("child id 1");
    }
}
