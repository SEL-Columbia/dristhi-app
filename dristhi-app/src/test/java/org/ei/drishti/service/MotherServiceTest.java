package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.repository.EligibleCoupleRepository;
import org.ei.drishti.repository.MotherRepository;
import org.ei.drishti.util.ActionBuilder;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.util.ActionBuilder.actionForANCCareProvided;
import static org.ei.drishti.util.ActionBuilder.actionForCloseMother;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class MotherServiceTest {
    @Mock
    private MotherRepository motherRepository;
    @Mock
    private EligibleCoupleRepository eligibleCoupleRepository;
    @Mock
    private AllTimelineEvents allTimelineEvents;

    private MotherService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new MotherService(motherRepository, allTimelineEvents, eligibleCoupleRepository);
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
    public void shouldHandleRegisterPregnancyForMother() throws Exception {
        Action action = ActionBuilder.actionForRegisterPregnancy("Case Mother X");
        service.registerANC(action);
        verify(motherRepository).add(new Mother("Case Mother X", "ecCaseId", "thaayiCardNumber", LocalDate.now().toString())
                .withDetails(mapOf("some-key", "some-field")));
    }

    @Test
    public void shouldHandleOutOfAreaANCRegistration() throws Exception {
        Action action = ActionBuilder.actionForOutOfAreaANCRegistration("Case Mother X");
        Map<String, String> details = mapOf("some-key", "some-field");

        service.registerOutOfAreaANC(action);

        verify(eligibleCoupleRepository).add(new EligibleCouple("EC Case ID", "Wife 1", "Husband 1", "", "Village X", "SubCenter X", details).asOutOfArea());
        verify(motherRepository).add(new Mother("Case Mother X", "EC Case ID", "TC 1", "2012-09-17")
                .withDetails(details));
    }

    @Test
    public void shouldHandleUpdateDetailsForMother() throws Exception {
        Action action = ActionBuilder.actionForUpdateMotherDetails("Case Mother X", mapOf("some-key", "some-value"));

        service.update(action);

        verify(motherRepository).updateDetails("Case Mother X", mapOf("some-key", "some-value"));
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
    public void shouldCloseANC() throws Exception {
        service.close(actionForCloseMother("Case X"));

        verify(motherRepository).close("Case X");
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

    @Test
    public void shouldHandleUpdateBirthPlanningForMother() throws Exception {
        Action action = ActionBuilder.updateBirthPlanning("Case Mother X", mapOf("aKey", "aValue"));

        service.update(action);

        verify(motherRepository).updateDetails("Case Mother X", mapOf("aKey", "aValue"));
    }
}
