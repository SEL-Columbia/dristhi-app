package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.BeneficiaryStatus;
import org.ei.drishti.util.Session;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.BeneficiaryStatus.*;

public class BeneficiaryRepositoryTest extends AndroidTestCase {
    private BeneficiaryRepository repository;

    @Override
    protected void setUp() throws Exception {
        repository = new BeneficiaryRepository();
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository);
    }

    public void testShouldInsertMother() throws Exception {
        repository.addMother(action("CASE X", "createPregnancy", dataForCreateAction("TC 1", "EC Case 1", PREGNANT.value())));

        assertEquals(asList(new Beneficiary("CASE X", "EC Case 1", "TC 1", PREGNANT)), repository.allBeneficiaries());
    }

    public void testShouldUpdateDeliveryStatusToAborted() throws Exception {
        assertStatusChange(PREGNANT, "abortion", ABORTED);
    }

    public void testShouldUpdateDeliveryStatusToDelivered() throws Exception {
        assertStatusChange(PREGNANT, "delivery", DELIVERED);
    }

    public void testShouldUpdateDeliveryStatusToDead() throws Exception {
        assertStatusChange(PREGNANT, "death", DEAD);
    }

    public void testShouldChangeMotherRecordToChildRecord() throws Exception {
        repository.addMother(action("CASE X", "createPregnancy", dataForCreateAction("TC 1", "EC Case 1", PREGNANT.value())));
        repository.addMother(action("CASE Y", "createPregnancy", dataForCreateAction("TC 2", "EC Case 2", PREGNANT.value())));

        repository.addChild(action("CASE NEW CHILD 1", "createChildBeneficiary", dataForCreateChild("CASE X")));

        assertEquals(asList(new Beneficiary("CASE NEW CHILD 1", "EC Case 1", "TC 1", BeneficiaryStatus.BORN), new Beneficiary("CASE Y", "EC Case 2", "TC 2", PREGNANT)), repository.allBeneficiaries());
    }

    private Map<String, String> dataForCreateChild(String motherCaseId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("motherCaseId", motherCaseId);
        return map;
    }

    private Map<String, String> dataForUpdateAction(String status) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", status);
        return map;
    }

    private void assertStatusChange(BeneficiaryStatus existingStatus, String newStatus, BeneficiaryStatus expectedStatus) {
        repository.addMother(action("CASE X", "createPregnancy", dataForCreateAction("TC 1", "EC Case 1", existingStatus.value())));
        repository.addMother(action("CASE Y", "createPregnancy", dataForCreateAction("TC 2", "EC Case 2", existingStatus.value())));

        repository.updateDeliveryStatus(action("CASE X", "updateBeneficiary", dataForUpdateAction(newStatus)));

        assertEquals(asList(new Beneficiary("CASE X", "EC Case 1", "TC 1", expectedStatus), new Beneficiary("CASE Y", "EC Case 2", "TC 2", existingStatus)), repository.allBeneficiaries());
    }

    private Action action(String caseId, String type, Map<String, String> data) {
        return new Action(caseId, "child", type, data, "0");
    }

    private Map<String, String> dataForCreateAction(String thaayiCardNumber, String ecCaseId, String status) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("ecCaseId", ecCaseId);
        map.put("thaayiCardNumber", thaayiCardNumber);
        map.put("status", status);
        return map;
    }
}
