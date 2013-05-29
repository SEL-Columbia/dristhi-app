package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.ServiceProvided;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.*;
import org.ei.drishti.util.ActionBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.ei.drishti.domain.ServiceProvided.forTTDose;
import static org.ei.drishti.domain.TimelineEvent.forIFATabletsProvided;
import static org.ei.drishti.domain.TimelineEvent.forTTShotProvided;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class MotherServiceTest {
    @Mock
    private MotherRepository motherRepository;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private EligibleCoupleRepository eligibleCoupleRepository;
    @Mock
    private AllTimelineEvents allTimelineEvents;
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private ServiceProvidedService serviceProvidedService;

    private MotherService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new MotherService(motherRepository, allBeneficiaries, allEligibleCouples, allTimelineEvents, serviceProvidedService);
    }

    @Test
    public void shouldRegisterANC() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("motherId")).thenReturn("mother id 1");
        when(submission.getFieldValue("thayiCardNumber")).thenReturn("thayi 1");
        when(submission.getFieldValue("referenceDate")).thenReturn("2012-01-01");

        service.registerANC(submission);

        allTimelineEvents.add(TimelineEvent.forStartOfPregnancy("mother id 1", "2012-01-01"));
        allTimelineEvents.add(TimelineEvent.forStartOfPregnancyForEC("entity id 1", "thayi 1", "2012-01-01"));
    }

    @Test
    public void shouldRegisterOutOfAreaANC() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("motherId")).thenReturn("mother id 1");
        when(submission.getFieldValue("thayiCardNumber")).thenReturn("thayi 1");
        when(submission.getFieldValue("referenceDate")).thenReturn("2012-01-01");

        service.registerOutOfAreaANC(submission);

        allTimelineEvents.add(TimelineEvent.forStartOfPregnancy("mother id 1", "2012-01-01"));
        allTimelineEvents.add(TimelineEvent.forStartOfPregnancyForEC("entity id 1", "thayi 1", "2012-01-01"));
    }

    @Test
    public void shouldHandleANCVisitForMother() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("ancVisitDate")).thenReturn("2013-01-01");
        when(submission.getFieldValue("ancVisitNumber")).thenReturn("2");
        when(submission.getFieldValue("weight")).thenReturn("21");
        when(submission.getFieldValue("bpDiastolic")).thenReturn("80");
        when(submission.getFieldValue("bpSystolic")).thenReturn("90");
        when(submission.getFieldValue("temperature")).thenReturn("98.5");

        service.ancVisit(submission);

        verify(allTimelineEvents).add(TimelineEvent.forANCCareProvided("entity id 1", "2", "2013-01-01",
                create("bpDiastolic", "80").put("bpSystolic", "90").put("temperature", "98.5").put("weight", "21").map()));
    }

    @Test
    public void shouldNotDoAnythingWhenANCIsClosedAndMotherDoesNotExist() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(allBeneficiaries.findMother("entity id 1")).thenReturn(null);

        service.close(submission);

        verify(allBeneficiaries, times(0)).closeMother("entity id 1");
        verifyZeroInteractions(allEligibleCouples);
    }

    @Test
    public void shouldCloseECWhenMotherIsClosedAndReasonIsDeath() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("closeReason")).thenReturn("death_of_woman");
        when(allBeneficiaries.findMother("entity id 1")).thenReturn(new Mother("entity id 1", "ec entity id 1", "thayi 1", "2013-01-01"));

        service.close(submission);

        verify(allEligibleCouples).close("ec entity id 1");
    }

    @Test
    public void shouldCloseECWhenMotherIsClosedAndReasonIsPermanentRelocation() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("closeReason")).thenReturn("relocation_permanent");
        when(allBeneficiaries.findMother("entity id 1")).thenReturn(new Mother("entity id 1", "ec entity id 1", "thayi 1", "2013-01-01"));

        service.close(submission);

        verify(allEligibleCouples).close("ec entity id 1");
    }

    @Test
    public void shouldNotCloseECWhenMotherIsClosedForOtherReasons() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("closeReason")).thenReturn("other_reason");
        when(allBeneficiaries.findMother("entity id 1")).thenReturn(new Mother("entity id 1", "ec entity id 1", "thayi 1", "2013-01-01"));

        service.close(submission);

        verifyZeroInteractions(allEligibleCouples);
    }

    @Test
    public void shouldHandleTTProvided() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("ttDose")).thenReturn("ttbooster");
        when(submission.getFieldValue("ttDate")).thenReturn("2013-01-01");

        service.ttProvided(submission);

        verify(allTimelineEvents).add(forTTShotProvided("entity id 1", "ttbooster", "2013-01-01"));
        verify(serviceProvidedService).add(forTTDose("entity id 1", "ttbooster", "2013-01-01"));
    }

    @Test
    public void shouldAddTimelineEventWhenIFAProvided() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("numberOfIFATabletsGiven")).thenReturn("100");
        when(submission.getFieldValue("ifaTabletsDate")).thenReturn("2013-02-01");

        service.ifaTabletsGiven(submission);

        verify(allTimelineEvents).add(forIFATabletsProvided("entity id 1", "100", "2013-02-01"));
    }

    @Test
    public void shouldNotAddTimelineEventWhenIFATabletsAreNotProvided() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("numberOfIFATabletsGiven")).thenReturn("0");
        when(submission.getFieldValue("ifaTabletsDate")).thenReturn("2013-02-01");

        service.ifaTabletsGiven(submission);

        verifyZeroInteractions(allTimelineEvents);
    }

    @Test
    public void shouldHandleHBTest() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("hbLevel")).thenReturn("11");
        when(submission.getFieldValue("hbTestDate")).thenReturn("2013-01-01");

        service.hbTest(submission);

        verify(serviceProvidedService).add(new ServiceProvided("entity id 1", "HB Test", "2013-01-01", mapOf("hbLevel", "11")));
    }

    @Test
    public void shouldHandleUpdateANCOutcomeAction() throws Exception {
        String caseId = "Case Mother X";
        Action action = ActionBuilder.actionForUpdateANCOutcome(caseId, mapOf("some-key", "some-value"));

        service.updateANCOutcome(action);

        verify(motherRepository).switchToPNC(caseId);
        verify(motherRepository).updateDetails(caseId, mapOf("some-key", "some-value"));
    }

    @Test
    public void shouldHandlePNCVisitActionForMother() throws Exception {
        String caseId = "Case Mother X";
        Action action = ActionBuilder.actionForMotherPNCVisit(caseId, mapOf("some-key", "some-value"));

        service.pncVisitHappened(action);

        verify(allTimelineEvents).add(TimelineEvent.forMotherPNCVisit(caseId, "1", "2012-01-01", mapOf("some-key", "some-value")));
        verify(motherRepository).updateDetails(caseId, mapOf("some-key", "some-value"));
    }
}
