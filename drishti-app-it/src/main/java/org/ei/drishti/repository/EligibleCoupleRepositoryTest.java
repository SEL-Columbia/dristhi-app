package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.util.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        repository.add(action("CASE X", "createEC", dataForCreateAction("Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1")));

        assertEquals(asList(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number")), repository.allEligibleCouples());
    }

    public void testShouldDeleteEligibleCoupleFromRepositoryBasedOnCaseID() throws Exception {
        repository.add(action("CASE X", "createEC", dataForCreateAction("Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1")));
        repository.add(action("CASE Y", "createEC", dataForCreateAction("Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2")));

        repository.delete(action("CASE X", "deleteEC", new HashMap<String, String>()));
        assertEquals(asList(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2")), repository.allEligibleCouples());

        repository.delete(action("CASE DOES NOT MATCH", "deleteEC", new HashMap<String, String>()));
        assertEquals(asList(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2")), repository.allEligibleCouples());

        repository.delete(action("CASE Y", "deleteEC", new HashMap<String, String>()));
        assertEquals(new ArrayList<EligibleCouple>(), repository.allEligibleCouples());
    }

    public void testShouldDeleteAllEligibleCouplesFromRepository() throws Exception {
        repository.add(action("CASE X", "createEC", dataForCreateAction("Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1")));
        repository.add(action("CASE Y", "createEC", dataForCreateAction("Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2")));

        repository.deleteAllEligibleCouples();

        assertEquals(new ArrayList<EligibleCouple>(), repository.allEligibleCouples());
    }

    private Action action(String caseId, String type, Map<String, String> data) {
        return new Action(caseId, "eligibleCouple", type, data, "0");
    }

    private Map<String, String> dataForCreateAction(String wife, String husband, String ecNumber, String village, String subCenter) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("wife", wife);
        map.put("husband", husband);
        map.put("ecNumber", ecNumber);
        map.put("village", village);
        map.put("subcenter", subCenter);
        return map;
    }
}
