package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.Context;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.EligibleCouple;

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
        Context.getInstance().session().setPassword("password");
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), "drishti.db" + new Date().getTime(), repository);
    }

    public void testShouldInsertEligibleCoupleIntoRepository() throws Exception {
        repository.add(new Action("CASE X", "createEC", dataForCreateAction("Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1"), "0"));

        assertEquals(asList(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number")), repository.allEligibleCouples());
    }

    public void testShouldDeleteEligibleCoupleFromRepositoryBasedOnCaseID() throws Exception {
        repository.add(new Action("CASE X", "createEC", dataForCreateAction("Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1"), "0"));
        repository.add(new Action("CASE Y", "createEC", dataForCreateAction("Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2"), "0"));

        repository.delete(new Action("CASE X", "deleteEC", new HashMap<String, String>(), "0"));
        assertEquals(asList(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2")), repository.allEligibleCouples());

        repository.delete(new Action("CASE DOES NOT MATCH", "deleteEC", new HashMap<String, String>(), "0"));
        assertEquals(asList(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2")), repository.allEligibleCouples());

        repository.delete(new Action("CASE Y", "deleteEC", new HashMap<String, String>(), "0"));
        assertEquals(new ArrayList<EligibleCouple>(), repository.allEligibleCouples());
    }

    public void testShouldDeleteAllEligibleCouplesFromRepository() throws Exception {
        repository.add(new Action("CASE X", "createEC", dataForCreateAction("Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1"), "0"));
        repository.add(new Action("CASE Y", "createEC", dataForCreateAction("Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2"), "0"));

        repository.deleteAllEligibleCouples();

        assertEquals(new ArrayList<EligibleCouple>(), repository.allEligibleCouples());
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
