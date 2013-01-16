package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.EligibleCoupleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.HashMap;

import static org.ei.drishti.util.ActionBuilder.*;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class EligibleCoupleServiceTest {
    @Mock
    private EligibleCoupleRepository eligibleCoupleRepository;

    private EligibleCoupleService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new EligibleCoupleService(eligibleCoupleRepository);
    }

    @Test
    public void shouldInsertUpdateAndDeleteECActionsBasedOnTheirType() throws Exception {
        Action firstCreateAction = actionForCreateEC("Case X", "Theresa 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", "PHC X");
        Action firstUpdateAction = actionForUpdateECDetails("Case X", mapOf("key", "value"));
        Action firstDeleteAction = actionForDeleteEC("Case Y");
        Action secondUpdateAction = actionForUpdateECDetails("Case Y", mapOf("key", "value"));
        Action secondCreateAction = actionForCreateEC("Case Z", "Theresa 2", "Husband 2", "EC Number 2", "Village 1", "SubCenter 1", "PHC X");
        Action secondDeleteAction = actionForDeleteEC("Case B");

        service.handleAction(firstCreateAction);
        service.handleAction(firstUpdateAction);
        service.handleAction(secondCreateAction);
        service.handleAction(secondUpdateAction);
        service.handleAction(firstDeleteAction);
        service.handleAction(secondDeleteAction);

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
        service.handleAction(unknownAction("eligibleCouple"));
    }
}
