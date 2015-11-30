package org.ei.telemedicine.test.service;

import android.test.AndroidTestCase;

import org.ei.telemedicine.domain.Child;
import org.ei.telemedicine.domain.Mother;
import org.ei.telemedicine.domain.ServiceProvided;
import org.ei.telemedicine.domain.TimelineEvent;
import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.domain.form.SubForm;
import org.ei.telemedicine.repository.AllAlerts;
import org.ei.telemedicine.repository.AllBeneficiaries;
import org.ei.telemedicine.repository.AllTimelineEvents;
import org.ei.telemedicine.repository.ChildRepository;
import org.ei.telemedicine.repository.MotherRepository;
import org.ei.telemedicine.service.ChildService;
import org.ei.telemedicine.service.ServiceProvidedService;
import org.ei.telemedicine.util.EasyMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.ei.telemedicine.domain.ServiceProvided.forChildIllnessVisit;
import static org.ei.telemedicine.domain.ServiceProvided.forChildImmunization;
import static org.ei.telemedicine.domain.ServiceProvided.forVitaminAProvided;
import static org.ei.telemedicine.domain.TimelineEvent.forChildBirthInChildProfile;
import static org.ei.telemedicine.domain.TimelineEvent.forChildBirthInECProfile;
import static org.ei.telemedicine.domain.TimelineEvent.forChildBirthInMotherProfile;
import static org.ei.telemedicine.util.EasyMap.create;
import static org.ei.telemedicine.util.EasyMap.mapOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
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
    private AllAlerts allAlerts;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private Child child;
    private ChildService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new ChildService(allBeneficiaries, motherRepository, childRepository, allTimelineEvents, serviceProvidedService, allAlerts);
    }

    @Test
    public void testShouldUpdateEveryOnlyNewlyBornChildrenWhileRegistering() throws Exception {
        Child firstChild = new Child("Child X", "Mother X", "female", create("weight", "3").put("immunizationsGiven", "bcg opv_0").map());
        Child secondChild = new Child("Child Y", "Mother X", "female", create("weight", "4").put("immunizationsGiven", "bcg").map());
        FormSubmission submission = mock(FormSubmission.class);
        SubForm subForm = mock(SubForm.class);
        when(motherRepository.findById("Mother X")).thenReturn(new Mother("Mother X", "EC 1", "TC 1", "2012-01-01"));
        when(childRepository.find("Child X")).thenReturn(firstChild);
        when(childRepository.find("Child Y")).thenReturn(secondChild);
        when(submission.entityId()).thenReturn("Mother X");
        when(submission.getFieldValue("referenceDate")).thenReturn("2012-01-01");
        when(submission.getFieldValue("deliveryPlace")).thenReturn("phc");
        when(submission.getSubFormByName("child_registration")).thenReturn(subForm);
        when(subForm.instances()).thenReturn(asList(mapOf("id", "Child X"), mapOf("id", "Child Y")));

        service.register(submission);

        verify(childRepository).find("Child X");
        verify(childRepository).find("Child Y");
        verify(childRepository).update(firstChild.setIsClosed(false).setDateOfBirth("2012-01-01").setThayiCardNumber("TC 1"));
        verify(childRepository).update(secondChild.setIsClosed(false).setDateOfBirth("2012-01-01").setThayiCardNumber("TC 1"));
        verify(allTimelineEvents).add(forChildBirthInChildProfile("Child X", "2012-01-01", "3", "bcg opv_0"));
        verify(allTimelineEvents).add(forChildBirthInChildProfile("Child Y", "2012-01-01", "4", "bcg"));
        verify(allTimelineEvents, times(2)).add(forChildBirthInMotherProfile("Mother X", "2012-01-01", "female", "2012-01-01", "phc"));
        verify(allTimelineEvents, times(2)).add(forChildBirthInECProfile("EC 1", "2012-01-01", "female", "2012-01-01"));
        verify(serviceProvidedService).add(ServiceProvided.forChildImmunization("Child X", "bcg", "2012-01-01"));
        verify(serviceProvidedService).add(ServiceProvided.forChildImmunization("Child X", "opv_0", "2012-01-01"));
        verify(serviceProvidedService).add(ServiceProvided.forChildImmunization("Child Y", "bcg", "2012-01-01"));
        verifyNoMoreInteractions(childRepository);
        verifyNoMoreInteractions(allTimelineEvents);
    }

    @Test
    public void testShouldDeleteRegisteredChildWhenDeliveryOutcomeIsStillBirth() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        SubForm subForm = mock(SubForm.class);
        when(submission.entityId()).thenReturn("Mother X");
        when(submission.getFieldValue("referenceDate")).thenReturn("2012-01-01");
        when(submission.getFieldValue("deliveryPlace")).thenReturn("phc");
        when(submission.getFieldValue("deliveryOutcome")).thenReturn("still_birth");
        when(submission.getSubFormByName("child_registration")).thenReturn(subForm);
        when(subForm.instances()).thenReturn(asList(mapOf("id", "Child X")));

        service.register(submission);

        verify(childRepository).delete("Child X");
        verifyNoMoreInteractions(childRepository);
        verifyNoMoreInteractions(allTimelineEvents);
        verifyNoMoreInteractions(serviceProvidedService);
    }

    @Test
    public void testShouldUpdateNewlyRegisteredChildrenDuringPNCRegistrationOA() throws Exception {
        Child firstChild = new Child("Child X", "Mother X", "female", create("weight", "3").put("immunizationsGiven", "bcg opv_0").map());
        Child secondChild = new Child("Child Y", "Mother X", "female", create("weight", "4").put("immunizationsGiven", "bcg").map());
        Mother mother = new Mother("Mother X", "EC X", "TC 1", "2012-01-02");
        when(motherRepository.findAllCasesForEC("EC X")).thenReturn(asList(mother));
        when(childRepository.find("Child X")).thenReturn(firstChild);
        when(childRepository.find("Child Y")).thenReturn(secondChild);
        FormSubmission submission = mock(FormSubmission.class);
        SubForm subForm = mock(SubForm.class);
        when(submission.entityId()).thenReturn("EC X");
        when(submission.getFieldValue("referenceDate")).thenReturn("2012-01-01");
        when(submission.getFieldValue("deliveryPlace")).thenReturn("subcenter");
        when(submission.getSubFormByName("child_registration_oa")).thenReturn(subForm);
        when(subForm.instances()).thenReturn(asList(mapOf("id", "Child X"), mapOf("id", "Child Y")));

        service.pncRegistrationOA(submission);

        verify(childRepository).find("Child X");
        verify(childRepository).find("Child Y");
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
    public void testShouldDeleteRegisteredChildWhenPNCRegistrationOAIsHandledAndDeliveryOutcomeIsStillBirth() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        SubForm subForm = mock(SubForm.class);
        when(submission.entityId()).thenReturn("Mother X");
        when(submission.getFieldValue("referenceDate")).thenReturn("2012-01-01");
        when(submission.getFieldValue("deliveryPlace")).thenReturn("phc");
        when(submission.getFieldValue("deliveryOutcome")).thenReturn("still_birth");
        when(submission.getSubFormByName("child_registration_oa")).thenReturn(subForm);
        when(subForm.instances()).thenReturn(asList(mapOf("id", "Child X")));

        service.pncRegistrationOA(submission);

        verify(childRepository).delete("Child X");
        verifyNoMoreInteractions(childRepository);
        verifyNoMoreInteractions(allTimelineEvents);
        verifyNoMoreInteractions(serviceProvidedService);
    }

    @Test
    public void testShouldCheckForEmptyInstanceInTheCaseOfStillBirth() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        SubForm subForm = mock(SubForm.class);
        when(submission.entityId()).thenReturn("Mother X");
        when(submission.getFieldValue("referenceDate")).thenReturn("2012-01-01");
        when(submission.getFieldValue("deliveryPlace")).thenReturn("phc");
        when(submission.getFieldValue("deliveryOutcome")).thenReturn("still_birth");
        when(submission.getSubFormByName("child_registration_oa")).thenReturn(subForm);
        when(subForm.instances()).thenReturn(new ArrayList<Map<String, String>>());

        service.pncRegistrationOA(submission);

        verifyNoMoreInteractions(childRepository);
        verifyNoMoreInteractions(allTimelineEvents);
        verifyNoMoreInteractions(serviceProvidedService);
    }

    @Test
    public void testShouldAddTimelineEventsWhenChildImmunizationsAreUpdated() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("child id 1");
        when(submission.getFieldValue("previousImmunizations")).thenReturn("bcg");
        when(submission.getFieldValue("immunizationsGiven")).thenReturn("bcg opv_0 pentavalent_0");
        when(submission.getFieldValue("immunizationDate")).thenReturn("2013-01-01");

        service.updateImmunizations(submission);

        verify(allTimelineEvents).add(TimelineEvent.forChildImmunization("child id 1", "opv_0", "2013-01-01"));
        verify(allTimelineEvents).add(TimelineEvent.forChildImmunization("child id 1", "pentavalent_0", "2013-01-01"));
        verifyNoMoreInteractions(allTimelineEvents);
    }

    @Test
    public void testShouldAddServiceProvidedWhenChildImmunizationsAreUpdated() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("child id 1");
        when(submission.getFieldValue("previousImmunizations")).thenReturn("bcg");
        when(submission.getFieldValue("immunizationsGiven")).thenReturn("bcg opv_0 pentavalent_0");
        when(submission.getFieldValue("immunizationDate")).thenReturn("2013-01-01");

        service.updateImmunizations(submission);

        verify(serviceProvidedService).add(new ServiceProvided("child id 1", "opv_0", "2013-01-01", null));
        verify(serviceProvidedService).add(new ServiceProvided("child id 1", "pentavalent_0", "2013-01-01", null));
        verifyNoMoreInteractions(serviceProvidedService);
    }

    @Test
    public void testShouldMarkRemindersAsInProcessWhenImmunizationsAreProvided() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("child id 1");
        when(submission.getFieldValue("previousImmunizations")).thenReturn("bcg");
        when(submission.getFieldValue("immunizationsGiven")).thenReturn("bcg opv_0 pentavalent_0");
        when(submission.getFieldValue("immunizationDate")).thenReturn("2013-01-01");

        service.updateImmunizations(submission);

        verify(allAlerts).changeAlertStatusToInProcess("child id 1", "opv_0");
        verify(allAlerts).changeAlertStatusToInProcess("child id 1", "pentavalent_0");
        verifyNoMoreInteractions(allAlerts);
    }

    @Test
    public void testShouldAddTimelineEventWhenChildIsRegisteredForEC() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        Mother mother = new Mother("mother id 1", "ec id 1", "thayi card number", "2013-01-01");
        when(submission.entityId()).thenReturn("ec id 1");
        when(submission.getFieldValue("motherId")).thenReturn("mother id 1");
        when(submission.getFieldValue("childId")).thenReturn("child id 1");
        when(submission.getFieldValue("dateOfBirth")).thenReturn("2013-01-02");
        when(submission.getFieldValue("gender")).thenReturn("female");
        when(submission.getFieldValue("weight")).thenReturn("3");
        when(submission.getFieldValue("immunizationsGiven")).thenReturn("bcg opv_0");
        when(submission.getFieldValue("bcgDate")).thenReturn("2012-01-06");
        when(submission.getFieldValue("opv0Date")).thenReturn("2012-01-07");
        when(submission.getFieldValue("shouldCloseMother")).thenReturn("");
        when(allBeneficiaries.findMother("mother id 1")).thenReturn(mother);

        service.registerForEC(submission);

        verify(allBeneficiaries).updateMother(mother.setIsClosed(true));
        verify(allTimelineEvents).add(forChildBirthInChildProfile("child id 1", "2013-01-02", "3", "bcg opv_0"));
        verify(allTimelineEvents).add(forChildBirthInMotherProfile("mother id 1", "2013-01-02", "female", null, null));
        verify(allTimelineEvents).add(forChildBirthInECProfile("ec id 1", "2013-01-02", "female", null));

        verify(serviceProvidedService).add(forChildImmunization("child id 1", "bcg", "2012-01-06"));
        verify(serviceProvidedService).add(forChildImmunization("child id 1", "opv_0", "2012-01-07"));
    }

    @Test
    public void testShouldNotCloseMotherWhenAnOpenANCAlreadyExistWhileRegisteringAChildForEC() throws Exception {
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
        when(submission.getFieldValue("shouldCloseMother")).thenReturn("false");

        service.registerForEC(submission);

        verify(allTimelineEvents).add(forChildBirthInChildProfile("child id 1", "2013-01-02", "3", "bcg opv_0"));
        verify(allTimelineEvents).add(forChildBirthInMotherProfile("mother id 1", "2013-01-02", "female", null, null));
        verify(allTimelineEvents).add(forChildBirthInECProfile("ec id 1", "2013-01-02", "female", null));

        verify(serviceProvidedService).add(forChildImmunization("child id 1", "bcg", "2012-01-06"));
        verify(serviceProvidedService).add(forChildImmunization("child id 1", "opv_0", "2012-01-07"));
    }

    @Test
    public void testShouldAddPNCVisitTimelineEventWhenPNCVisitHappens() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        SubForm subForm = mock(SubForm.class);
        when(submission.entityId()).thenReturn("mother id 1");
        when(submission.getFieldValue("pncVisitDay")).thenReturn("2");
        when(submission.getFieldValue("pncVisitDate")).thenReturn("2012-01-01");
        when(submission.getSubFormByName("child_pnc_visit")).thenReturn(subForm);
        when(subForm.instances()).thenReturn(
                asList(
                        create("id", "child id 1")
                                .put("weight", "3")
                                .put("temperature", "98")
                                .map(),
                        create("id", "child id 2")
                                .put("weight", "4")
                                .put("temperature", "98.1")
                                .map()));

        service.pncVisitHappened(submission);

        verify(allTimelineEvents).add(TimelineEvent.forChildPNCVisit("child id 1", "2", "2012-01-01", "3", "98"));
        verify(allTimelineEvents).add(TimelineEvent.forChildPNCVisit("child id 2", "2", "2012-01-01", "4", "98.1"));
    }

    @Test
    public void testShouldHandleStillBirthWhenPNCVisitHappens() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        SubForm subForm = mock(SubForm.class);
        when(submission.entityId()).thenReturn("mother id 1");
        when(submission.getFieldValue("pncVisitDay")).thenReturn("2");
        when(submission.getFieldValue("pncVisitDate")).thenReturn("2012-01-01");
        when(submission.getFieldValue("deliveryOutcome")).thenReturn("still_birth");
        when(submission.getSubFormByName("child_pnc_visit")).thenReturn(subForm);
        when(subForm.instances()).thenReturn(asList(create("id", "child id 1").map()));

        service.pncVisitHappened(submission);

        verify(childRepository).delete("child id 1");
        verifyNoMoreInteractions(allTimelineEvents);
    }

    @Test
    public void testShouldAddPNCVisitServiceProvidedWhenPNCVisitHappens() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        SubForm subForm = mock(SubForm.class);
        when(submission.entityId()).thenReturn("mother id 1");
        when(submission.getFieldValue("pncVisitDay")).thenReturn("2");
        when(submission.getFieldValue("pncVisitDate")).thenReturn("2012-01-01");
        when(submission.getSubFormByName("child_pnc_visit")).thenReturn(subForm);
        when(subForm.instances()).thenReturn(
                asList(mapOf("id", "child id 1"), mapOf("id", "child id 2")));

        service.pncVisitHappened(submission);

        verify(serviceProvidedService).add(new ServiceProvided("child id 1", "PNC", "2012-01-01", mapOf("day", "2")));
        verify(serviceProvidedService).add(new ServiceProvided("child id 2", "PNC", "2012-01-01", mapOf("day", "2")));
    }

    @Test
    public void testShouldCloseChildRecordForDeleteChildAction() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("child id 1");

        service.close(submission);

        verify(allBeneficiaries).closeChild("child id 1");
    }

    @Test
    public void testShouldUpdateIllnessForUpdateIllnessAction() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);

        when(submission.entityId()).thenReturn("child id 1");
        when(submission.getFieldValue("submissionDate")).thenReturn("2012-01-01");
        when(submission.getFieldValue("sickVisitDate")).thenReturn("2012-01-01");
        when(submission.getFieldValue("childSigns")).thenReturn("child signs");
        when(submission.getFieldValue("childSignsOther")).thenReturn("child signs other");
        when(submission.getFieldValue("reportChildDisease")).thenReturn("report child disease");
        when(submission.getFieldValue("reportChildDiseaseOther")).thenReturn("report child disease other");
        when(submission.getFieldValue("reportChildDiseaseDate")).thenReturn("report child disease date");
        when(submission.getFieldValue("reportChildDiseasePlace")).thenReturn("report child disease place");
        when(submission.getFieldValue("childReferral")).thenReturn("child referral");

        service.updateIllnessStatus(submission);

        Map<String, String> map = EasyMap.create("sickVisitDate", "2012-01-01")
                .put("childSignsOther", "child signs other")
                .put("childSigns", "child signs")
                .put("reportChildDisease", "report child disease")
                .put("reportChildDiseaseOther", "report child disease other")
                .put("reportChildDiseaseDate", "report child disease date")
                .put("reportChildDiseasePlace", "report child disease place")
                .put("childReferral", "child referral").map();

        verify(serviceProvidedService).add(forChildIllnessVisit("child id 1", "2012-01-01", map));
    }

    @Test
    public void testShouldUpdateVitaminADosagesForUpdateVitaminAProvidedAction() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);

        when(submission.entityId()).thenReturn("child id 1");
        when(submission.getFieldValue("vitaminADate")).thenReturn("2012-01-01");
        when(submission.getFieldValue("vitaminADose")).thenReturn("1");
        when(submission.getFieldValue("vitaminAPlace")).thenReturn("PHC");

        service.updateVitaminAProvided(submission);

        verify(serviceProvidedService).add(forVitaminAProvided("child id 1", "2012-01-01", "1", "PHC"));
    }

    @Test
    public void testShouldAddTimelineEventWhenChildIsRegisteredForOA() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("ec id 1");
        when(submission.getFieldValue("motherId")).thenReturn("mother id 1");
        when(submission.getFieldValue("id")).thenReturn("child id 1");
        when(submission.getFieldValue("dateOfBirth")).thenReturn("2013-01-02");
        when(submission.getFieldValue("gender")).thenReturn("female");
        when(submission.getFieldValue("weight")).thenReturn("3");
        when(submission.getFieldValue("immunizationsGiven")).thenReturn("bcg opv_0");
        when(submission.getFieldValue("bcgDate")).thenReturn("2012-01-06");
        when(submission.getFieldValue("opv0Date")).thenReturn("2012-01-07");
        when(submission.getFieldValue("thayiCardNumber")).thenReturn("1234567");
        when(allBeneficiaries.findChild("child id 1")).thenReturn(child);

        service.registerForOA(submission);

        verify(child).setThayiCardNumber("1234567");
        verify(allBeneficiaries).updateChild(child);
        verify(allTimelineEvents).add(forChildBirthInChildProfile("child id 1", "2013-01-02", "3", "bcg opv_0"));

        verify(serviceProvidedService).add(forChildImmunization("ec id 1", "bcg", "2012-01-06"));
        verify(serviceProvidedService).add(forChildImmunization("ec id 1", "opv_0", "2012-01-07"));
    }
}
