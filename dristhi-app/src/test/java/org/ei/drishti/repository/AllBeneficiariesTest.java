package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.dto.Action;
import org.ei.drishti.util.ActionBuilder;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.util.ActionBuilder.actionForUpdateBeneficiary;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllBeneficiariesTest {
    @Mock
    private ChildRepository childRepository;
    @Mock
    private MotherRepository motherRepository;
    @Mock
    private EligibleCoupleRepository eligibleCoupleRepository;
    @Mock
    private AllTimelineEvents allTimelineEvents;

    private AllBeneficiaries allBeneficiaries;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allBeneficiaries = new AllBeneficiaries(motherRepository, childRepository, allTimelineEvents, eligibleCoupleRepository);
    }

    @Test
    public void shouldHandleRegisterPregnancyForMother() throws Exception {
        Action action = ActionBuilder.actionForRegisterPregnancy("Case Mother X");
        allBeneficiaries.handleMotherAction(action);
        verify(motherRepository).add(new Mother("Case Mother X", "ecCaseId", "thaayiCardNumber", LocalDate.now().toString())
                .withDetails(mapOf("some-key", "some-field")));
    }

    @Test
    public void shouldHandleOutOfAreaANCRegistration() throws Exception {
        Action action = ActionBuilder.actionForOutOfAreaANCRegistration("Case Mother X");
        Map<String, String> details = mapOf("some-key", "some-field");

        allBeneficiaries.handleMotherAction(action);

        verify(eligibleCoupleRepository).add(new EligibleCouple("EC Case ID", "Wife 1", "Husband 1", "", "Village X", "SubCenter X", details).asOutOfArea());
        verify(motherRepository).add(new Mother("Case Mother X", "EC Case ID", "TC 1", "2012-09-17")
                .withDetails(details));
    }

    @Test
    public void shouldHandleUpdateDetailsForMother() throws Exception {
        Action action = ActionBuilder.actionForUpdateMotherDetails("Case Mother X", mapOf("some-key", "some-value"));

        allBeneficiaries.handleMotherAction(action);

        verify(motherRepository).updateDetails("Case Mother X", mapOf("some-key", "some-value"));
    }

    @Test
    public void shouldHandleANCCareProvidedForMother() throws Exception {
        LocalDate visitDate = LocalDate.now().minusDays(1);
        Action action = ActionBuilder.actionForANCCareProvided("Case Mother X", 1, 10, visitDate, "TT 1");

        allBeneficiaries.handleMotherAction(action);

        verify(allTimelineEvents).add(TimelineEvent.forANCCareProvided("Case Mother X", "1", visitDate.toString(), new HashMap<String, String>()));
        verify(allTimelineEvents).add(TimelineEvent.forIFATabletsProvided(action.caseID(), "10", visitDate.toString()));
        verify(allTimelineEvents).add(TimelineEvent.forTTShotProvided(action.caseID(), "TT 1", visitDate.toString()));
    }

    @Test
    public void shouldNotAddTimelineEventForServicesNotProvidedForMother() throws Exception {
        LocalDate visitDate = LocalDate.now().minusDays(1);
        Action action = ActionBuilder.actionForANCCareProvided("Case Mother X", 1, 0, visitDate, "");

        allBeneficiaries.handleMotherAction(action);

        verify(allTimelineEvents).add(TimelineEvent.forANCCareProvided("Case Mother X", "1", visitDate.toString(), new HashMap<String, String>()));
        verifyNoMoreInteractions(allTimelineEvents);
    }

    @Test
    public void shouldCloseANC() throws Exception {
        allBeneficiaries.handleMotherAction(actionForUpdateBeneficiary());

        verifyZeroInteractions(motherRepository);
    }

    @Test
    public void shouldHandleUpdateANCOutcomeAction() throws Exception {
        String caseId = "Case Mother X";
        Action action = ActionBuilder.actionForUpdateANCOutcome(caseId, mapOf("some-key", "some-value"));

        allBeneficiaries.handleMotherAction(action);

        verify(motherRepository).switchToPNC(caseId);
        verify(motherRepository).updateDetails(caseId, mapOf("some-key", "some-value"));
    }

    @Test
    public void shouldHandlePNCVisitActionForMother() throws Exception {
        String caseId = "Case Mother X";
        Action action = ActionBuilder.actionForMotherPNCVisit(caseId, mapOf("some-key", "some-value"));

        allBeneficiaries.handleMotherAction(action);

        verify(allTimelineEvents).add(TimelineEvent.forMotherPNCVisit(caseId, "1", "2012-01-01", mapOf("some-key", "some-value")));
        verify(motherRepository).updateDetails(caseId, mapOf("some-key", "some-value"));
    }

    @Test
    public void shouldHandleUpdateBirthPlanningForMother() throws Exception {
        Action action = ActionBuilder.updateBirthPlanning("Case Mother X", mapOf("aKey", "aValue"));

        allBeneficiaries.handleMotherAction(action);

        verify(motherRepository).updateDetails("Case Mother X", mapOf("aKey", "aValue"));
    }
}
