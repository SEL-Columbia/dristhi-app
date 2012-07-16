package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.util.Session;

import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;

public class EligibleCoupleRepositoryTest extends AndroidTestCase {

    private EligibleCoupleRepository repository;

    @Override
    protected void setUp() throws Exception {
        repository = new EligibleCoupleRepository();
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository);
    }

    public void testShouldInsertEligibleCoupleIntoRepository() throws Exception {
        EligibleCouple eligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1");

        repository.add(eligibleCouple);

        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());
    }

    public void testShouldDeleteEligibleCoupleFromRepositoryBasedOnCaseID() throws Exception {
        EligibleCouple eligibleCouple = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2");

        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1"));
        repository.add(eligibleCouple);

        repository.delete("CASE X");
        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());

        repository.delete("CASE DOES NOT MATCH");
        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());

        repository.delete("CASE Y");
        assertEquals(new ArrayList<EligibleCouple>(), repository.allEligibleCouples());
    }

    public void testShouldDeleteAllEligibleCouplesFromRepository() throws Exception {
        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1"));
        repository.add(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2"));

        repository.deleteAllEligibleCouples();

        assertEquals(new ArrayList<EligibleCouple>(), repository.allEligibleCouples());
    }

    public void testFindECByCaseID() throws Exception {
        EligibleCouple ec = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1");
        EligibleCouple anotherEC = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2");

        repository.add(ec);
        repository.add(anotherEC);

        assertEquals(ec, repository.findByCaseID("CASE X"));
        assertEquals(null, repository.findByCaseID("CASE NOTFOUND"));
    }
}
