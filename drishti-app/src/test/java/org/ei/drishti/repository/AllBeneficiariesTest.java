package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.dto.Action;
import org.ei.drishti.util.ActionBuilder;
import org.joda.time.LocalDate;
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
        Action action = ActionBuilder.actionForCreateBeneficiary();
        allBeneficiaries.handleAction(action);
        String referenceDate = LocalDate.now().toString();
        verify(beneficiaryRepository).addMother(new Beneficiary("Case X", "ecCaseId", "thaayiCardNumber", referenceDate));

        action = ActionBuilder.actionForUpdateBeneficiary();
        allBeneficiaries.handleAction(action);
        verify(beneficiaryRepository).close("Case X");

        action = ActionBuilder.actionForCreateChildBeneficiary();
        allBeneficiaries.handleAction(action);
        verify(beneficiaryRepository).addChild("Case X", referenceDate, "motherCaseId", action.get("gender"));
    }
}
