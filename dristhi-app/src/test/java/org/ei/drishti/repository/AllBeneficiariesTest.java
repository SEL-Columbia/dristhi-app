package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.dto.Action;
import org.ei.drishti.util.ActionBuilder;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllBeneficiariesTest {
    @Mock
    private ChildRepository childRepository;
    @Mock
    private MotherRepository motherRepository;
    @Mock
    private AllTimelineEvents allTimelineEvents;

    private AllBeneficiaries allBeneficiaries;
    private String referenceDate;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        referenceDate = LocalDate.now().toString();
        allBeneficiaries = new AllBeneficiaries(motherRepository, childRepository, allTimelineEvents);
    }

    @Test
    public void shouldHandleRegisterPregnancyForMother() throws Exception {
        Action action = ActionBuilder.actionForRegisterPregnancy("Case Mother X");
        allBeneficiaries.handleMotherAction(action);
        verify(motherRepository).add(new Mother("Case Mother X", "ecCaseId", "thaayiCardNumber", LocalDate.now().toString())
                .withExtraDetails(true, "Delivery Place")
                .withDetails(mapOf("some-key", "some-field")));
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
        Action action = ActionBuilder.actionForANCCareProvided("Case Mother X", 1, 10, visitDate);

        allBeneficiaries.handleMotherAction(action);

        verify(allTimelineEvents).add(TimelineEvent.forANCCareProvided("Case Mother X", "1", visitDate.toString()));
        verify(allTimelineEvents).add(TimelineEvent.forIFATabletsProvided(action.caseID(), "10", visitDate.toString()));
    }

    @Test
    public void shouldNotAddTimelineEventForServicesNotProvidedForMother() throws Exception {
        LocalDate visitDate = LocalDate.now().minusDays(1);
        Action action = ActionBuilder.actionForANCCareProvided("Case Mother X", 1, 0, visitDate);

        allBeneficiaries.handleMotherAction(action);

        verify(allTimelineEvents).add(TimelineEvent.forANCCareProvided("Case Mother X", "1", visitDate.toString()));
        verifyNoMoreInteractions(allTimelineEvents);
    }

    @Test
    public void shouldSwitchMotherToPNCWhenChildIsAdded() throws Exception {
        when(motherRepository.find("Case Mom")).thenReturn(new Mother("Case Mom", "EC Case 1", "TC 1", "2012-06-08"));
        Action childAction = ActionBuilder.actionForCreateChild("Case Mom");
        allBeneficiaries.handleChildAction(childAction);

        verify(motherRepository).switchToPNC("Case Mom");
        verify(childRepository).addChildForMother(new Mother("Case Mom", "EC Case 1", "TC 1", "2012-06-08"), "Case X", referenceDate, "female");
    }
}
