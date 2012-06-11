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
        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1"));

        assertEquals(asList(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1")), repository.allEligibleCouples());
    }

    public void testShouldDeleteEligibleCoupleFromRepositoryBasedOnCaseID() throws Exception {
        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1"));
        repository.add(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2"));

        repository.delete("CASE X");
        assertEquals(asList(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2")), repository.allEligibleCouples());

        repository.delete("CASE DOES NOT MATCH");
        assertEquals(asList(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2")), repository.allEligibleCouples());

        repository.delete("CASE Y");
        assertEquals(new ArrayList<EligibleCouple>(), repository.allEligibleCouples());
    }

    public void testShouldDeleteAllEligibleCouplesFromRepository() throws Exception {
        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1"));
        repository.add(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2"));

        repository.deleteAllEligibleCouples();

        assertEquals(new ArrayList<EligibleCouple>(), repository.allEligibleCouples());
    }
}
