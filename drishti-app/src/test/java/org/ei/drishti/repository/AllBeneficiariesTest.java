package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Beneficiary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.domain.BeneficiaryStatus.PREGNANT;
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
        Action action = actionForCreateBeneficiary();
        allBeneficiaries.handleAction(action);
        verify(beneficiaryRepository).addMother(new Beneficiary("Case X", "ecCaseId", "thaayiCardNumber", PREGNANT, "referenceDate"));

        action = actionForUpdateBeneficiary();
        allBeneficiaries.handleAction(action);
        verify(beneficiaryRepository).updateDeliveryStatus(action.caseID(), action.get("status"));

        action = actionForCreateChildBeneficiary();
        allBeneficiaries.handleAction(action);
        verify(beneficiaryRepository).addChild(action.caseID(), action.get("referenceDate"), action.get("motherCaseId"));
    }

    private Action actionForCreateChildBeneficiary() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("referenceDate", "referenceDate");
        data.put("motherCaseId", "motherCaseId");
        return new Action("Case X", "child", "createChildBeneficiary", data, "0");
    }

    private Action actionForUpdateBeneficiary() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("status", "pregnant");
        return new Action("Case X", "child", "updateBeneficiary", data, "0");
    }

    private Action actionForCreateBeneficiary() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("status", "pregnant");
        data.put("referenceDate", "referenceDate");
        data.put("ecCaseId", "ecCaseId");
        data.put("thaayiCardNumber", "thaayiCardNumber");
        return new Action("Case X", "child", "createBeneficiary", data, "0");
    }
}
