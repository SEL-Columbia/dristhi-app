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
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.util.ActionBuilder.*;
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
    public void shouldInsertAndDeleteECActionsBasedOnTheirType() throws Exception {
        Action firstCreateAction = actionForCreateEC("Case X", "Theresa 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1", "PHC X");
        Action firstDeleteAction = actionForDeleteEC("Case Y");
        Action secondCreateAction = actionForCreateEC("Case Z", "Theresa 2", "Husband 2", "EC Number 2", "IUD", "Village 1", "SubCenter 1", "PHC X");
        Action secondDeleteAction = actionForDeleteEC("Case B");

        allEligibleCouples.handleAction(firstCreateAction);
        allEligibleCouples.handleAction(secondCreateAction);
        allEligibleCouples.handleAction(firstDeleteAction);
        allEligibleCouples.handleAction(secondDeleteAction);

        InOrder inOrder = inOrder(eligibleCoupleRepository);
        inOrder.verify(eligibleCoupleRepository).add(new EligibleCouple("Case X", "Theresa 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1"));
        inOrder.verify(eligibleCoupleRepository).add(new EligibleCouple("Case Z", "Theresa 2", "Husband 2", "EC Number 2", "IUD", "Village 1", "SubCenter 1"));
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
        List<EligibleCouple> expectedCouples = Arrays.asList(new EligibleCouple("Case X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "village", "subcenter"), new EligibleCouple("Case Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "village", "subcenter"));
        when(eligibleCoupleRepository.allEligibleCouples()).thenReturn(expectedCouples);

        List<EligibleCouple> couples = allEligibleCouples.fetchAll();

        assertEquals(expectedCouples, couples);
    }

    @Test
    public void shouldDeleteAllEligibleCouples() throws Exception {
        allEligibleCouples.deleteAll();

        verify(eligibleCoupleRepository).deleteAllEligibleCouples();
    }
}
