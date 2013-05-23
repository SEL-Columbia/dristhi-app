package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.*;
import org.ei.drishti.util.ActionBuilder;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;

import static org.ei.drishti.util.ActionBuilder.actionForANCCareProvided;
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

    private MotherService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new MotherService(motherRepository, allBeneficiaries, allEligibleCouples, allTimelineEvents);
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
    public void shouldHandleANCCareProvidedForMother() throws Exception {
        LocalDate visitDate = LocalDate.now().minusDays(1);
        Action action = actionForANCCareProvided("Case Mother X", 1, 10, visitDate, "TT 1");

        service.ancCareProvided(action);

        verify(allTimelineEvents).add(TimelineEvent.forANCCareProvided("Case Mother X", "1", visitDate.toString(), new HashMap<String, String>()));
        verify(allTimelineEvents).add(TimelineEvent.forIFATabletsProvided(action.caseID(), "10", visitDate.toString()));
        verify(allTimelineEvents).add(TimelineEvent.forTTShotProvided(action.caseID(), "TT 1", visitDate.toString()));
    }

    @Test
    public void shouldNotAddTimelineEventForServicesNotProvidedForMother() throws Exception {
        LocalDate visitDate = LocalDate.now().minusDays(1);
        Action action = actionForANCCareProvided("Case Mother X", 1, 0, visitDate, "");

        service.ancCareProvided(action);

        verify(allTimelineEvents).add(TimelineEvent.forANCCareProvided("Case Mother X", "1", visitDate.toString(), new HashMap<String, String>()));
        verifyNoMoreInteractions(allTimelineEvents);
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
