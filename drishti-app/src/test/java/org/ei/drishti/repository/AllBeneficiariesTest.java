package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Action;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllBeneficiariesTest {
    @Mock
    private BeneficiaryRepository beneficiaryRepository;
    private AllBeneficiaries allBeneficiaries;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allBeneficiaries = new AllBeneficiaries(beneficiaryRepository);
    }

    @Test
    public void shouldHandleDifferentTypesOfActions() throws Exception {
        Action action = action("createBeneficiary");
        allBeneficiaries.handleAction(action);
        verify(beneficiaryRepository).addMother(action);

        action = action("updateBeneficiary");
        allBeneficiaries.handleAction(action);
        verify(beneficiaryRepository).updateDeliveryStatus(action);

        action = action("createChildBeneficiary");
        allBeneficiaries.handleAction(action);
        verify(beneficiaryRepository).addChild(action);
    }

    private Action action(String type) {
        return new Action("Case X", "something", type, null, null);
    }
}
