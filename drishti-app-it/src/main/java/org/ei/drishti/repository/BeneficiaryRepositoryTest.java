package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.BeneficiaryStatus;
import org.ei.drishti.util.Session;

import java.util.Date;

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
        repository.addMother(new Beneficiary("CASE X", "EC Case 1", "TC 1", PREGNANT, "2012-06-08"));

        assertEquals(asList(new Beneficiary("CASE X", "EC Case 1", "TC 1", PREGNANT, "2012-06-08")), repository.allBeneficiaries());
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
        repository.addMother(new Beneficiary("CASE X", "EC Case 1", "TC 1", PREGNANT, "2012-06-08"));
        repository.addMother(new Beneficiary("CASE Y", "EC Case 2", "TC 2", PREGNANT, "2012-06-08"));

        repository.addChild("CASE NEW CHILD 1", "2013-03-05", "CASE X");

        assertEquals(asList(new Beneficiary("CASE NEW CHILD 1", "EC Case 1", "TC 1", BeneficiaryStatus.BORN, "2013-03-05"), new Beneficiary("CASE Y", "EC Case 2", "TC 2", PREGNANT, "2012-06-08")), repository.allBeneficiaries());
    }

    public void testShouldFetchBeneficiariesByCaseId() throws Exception {
        repository.addMother(new Beneficiary("CASE X", "EC Case 1", "TC 1", PREGNANT, "2012-06-08"));
        repository.addMother(new Beneficiary("CASE Y", "EC Case 1", "TC 2", PREGNANT, "2012-06-08"));
        repository.addMother(new Beneficiary("CASE Z", "EC Case 2", "TC 3", PREGNANT, "2012-06-08"));

        assertEquals(asList(new Beneficiary("CASE X", "EC Case 1", "TC 1", BeneficiaryStatus.PREGNANT, "2012-06-08"), new Beneficiary("CASE Y", "EC Case 1", "TC 2", BeneficiaryStatus.PREGNANT, "2012-06-08")), repository.findByECCaseId("EC Case 1"));
        assertEquals(asList(new Beneficiary("CASE Z", "EC Case 2", "TC 3", BeneficiaryStatus.PREGNANT, "2012-06-08")), repository.findByECCaseId("EC Case 2"));
    }

    private void assertStatusChange(BeneficiaryStatus existingStatus, String newStatus, BeneficiaryStatus expectedStatus) {
        repository.addMother(new Beneficiary("CASE X", "EC Case 1", "TC 1", existingStatus, "2012-06-08"));
        repository.addMother(new Beneficiary("CASE Y", "EC Case 2", "TC 2", existingStatus, "2012-06-08"));

        repository.updateDeliveryStatus("CASE X", newStatus);

        assertEquals(asList(new Beneficiary("CASE X", "EC Case 1", "TC 1", expectedStatus, "2012-06-08"), new Beneficiary("CASE Y", "EC Case 2", "TC 2", existingStatus, "2012-06-08")), repository.allBeneficiaries());
    }
}
