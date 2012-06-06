package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.util.Session;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.ChildStatus.UNBORN;

public class BeneficiaryRepositoryTest extends AndroidTestCase {
    private BeneficiaryRepository repository;

    @Override
    protected void setUp() throws Exception {
        repository = new BeneficiaryRepository();
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository);
    }

    public void testShouldInsertEligibleCoupleIntoRepository() throws Exception {
        repository.add(new Action("CASE X", "createPregnancy", dataForCreateAction("TC 1", "EC Case 1"), "0"));

        assertEquals(asList(new Beneficiary("CASE X", "EC Case 1", "TC 1", UNBORN)), repository.allBeneficiaries());
    }

    private Map<String, String> dataForCreateAction(String thaayiCardNumber, String ecCaseId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("ecCaseId", ecCaseId);
        map.put("thaayiCardNumber", thaayiCardNumber);
        map.put("status", UNBORN.value());
        return map;
    }
}
