package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.dto.Action;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.util.ActionBuilder.*;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllEligibleCouplesTest {
    @Mock
    private EligibleCoupleRepository eligibleCoupleRepository;
    private AllEligibleCouples allEligibleCouples;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allEligibleCouples = new AllEligibleCouples(eligibleCoupleRepository);
    }

    @Test
    public void shouldInsertUpdateAndDeleteECActionsBasedOnTheirType() throws Exception {
        Action firstCreateAction = actionForCreateEC("Case X", "Theresa 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", "PHC X");
        Action firstUpdateAction = actionForUpdateECDetails("Case X", mapOf("key", "value"));
        Action firstDeleteAction = actionForDeleteEC("Case Y");
        Action secondUpdateAction = actionForUpdateECDetails("Case Y", mapOf("key", "value"));
        Action secondCreateAction = actionForCreateEC("Case Z", "Theresa 2", "Husband 2", "EC Number 2", "Village 1", "SubCenter 1", "PHC X");
        Action secondDeleteAction = actionForDeleteEC("Case B");

        allEligibleCouples.handleAction(firstCreateAction);
        allEligibleCouples.handleAction(firstUpdateAction);
        allEligibleCouples.handleAction(secondCreateAction);
        allEligibleCouples.handleAction(secondUpdateAction);
        allEligibleCouples.handleAction(firstDeleteAction);
        allEligibleCouples.handleAction(secondDeleteAction);

        InOrder inOrder = inOrder(eligibleCoupleRepository);
        inOrder.verify(eligibleCoupleRepository).add(new EligibleCouple("Case X", "Theresa 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>()));
        inOrder.verify(eligibleCoupleRepository).updateDetails("Case X", mapOf("key", "value"));
        inOrder.verify(eligibleCoupleRepository).add(new EligibleCouple("Case Z", "Theresa 2", "Husband 2", "EC Number 2", "Village 1", "SubCenter 1", new HashMap<String, String>()));
        inOrder.verify(eligibleCoupleRepository).updateDetails("Case Y", mapOf("key", "value"));
        inOrder.verify(eligibleCoupleRepository).close("Case Y");
        inOrder.verify(eligibleCoupleRepository).close("Case B");
        verifyNoMoreInteractions(eligibleCoupleRepository);
    }

    @Test
    public void shouldNotFailIfActionTypeIsNotExpected() throws Exception {
        allEligibleCouples.handleAction(unknownAction("eligibleCouple"));
    }

    @Test
    public void shouldFetchAllAlertsFromRepository() throws Exception {
        List<EligibleCouple> expectedCouples = Arrays.asList(new EligibleCouple("Case X", "Wife 1", "Husband 1", "EC Number 1", "village", "subcenter", new HashMap<String, String>()),
                new EligibleCouple("Case Y", "Wife 2", "Husband 2", "EC Number 2", "village", "subcenter", new HashMap<String, String>()));
        when(eligibleCoupleRepository.allInAreaEligibleCouples()).thenReturn(expectedCouples);

        List<EligibleCouple> couples = allEligibleCouples.all();

        assertEquals(expectedCouples, couples);
    }
}
