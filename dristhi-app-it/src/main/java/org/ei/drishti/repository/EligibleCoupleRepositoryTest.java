package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.*;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.util.Session;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.util.EasyMap.mapOf;

public class EligibleCoupleRepositoryTest extends AndroidTestCase {
    private EligibleCoupleRepository repository;
    private AlertRepository alertRepository;
    private TimelineEventRepository timelineEventRepository;
    private ChildRepository childRepository;
    private MotherRepository motherRepository;

    @Override
    protected void setUp() throws Exception {
        alertRepository = new AlertRepository();
        timelineEventRepository = new TimelineEventRepository();
        childRepository = new ChildRepository(timelineEventRepository, alertRepository);
        motherRepository = new MotherRepository(timelineEventRepository, alertRepository);
        repository = new EligibleCoupleRepository(motherRepository, alertRepository);
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository, alertRepository,
                timelineEventRepository, childRepository, motherRepository);
    }

    public void testShouldInsertEligibleCoupleIntoRepository() throws Exception {
        Map<String, String> details = create("Hello", "There").put("Also", "This").map();
        EligibleCouple eligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", details);

        repository.add(eligibleCouple);

        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());
    }

    public void testShouldUpdateDetailsOfEligibleCoupleIntoRepository() throws Exception {
        Map<String, String> detailsBeforeUpdate = create("Key 1", "Value 1").put("currentMethod", "IUD").map();
        EligibleCouple eligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", detailsBeforeUpdate);

        repository.add(eligibleCouple);
        Map<String, String> detailsToBeUpdated = create("Key 1", "Value 1")
                .put("fpUpdate", "change_fp_method")
                .put("currentMethod", "Condom")
                .put("familyPlanningMethodChangeDate", "2012-03-03")
                .put("Key 3", "Value 3")
                .map();
        repository.updateDetails("CASE X", detailsToBeUpdated);

        assertEquals(asList(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", detailsToBeUpdated)),
                repository.allEligibleCouples());
    }

    public void testShouldMergeDetailsOfEligibleCoupleIntoRepository() throws Exception {
        Map<String, String> detailsBeforeUpdate = create("Key 1", "Value 1").put("currentMethod", "IUD").map();
        EligibleCouple eligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", detailsBeforeUpdate);
        repository.add(eligibleCouple);
        Map<String, String> detailsToBeUpdated = create("currentMethod", "Condom").put("familyPlanningMethodChangeDate", "2012-03-03").map();
        Map<String, String> expectedDetails = create("Key 1", "Value 1").put("currentMethod", "Condom").put("familyPlanningMethodChangeDate", "2012-03-03").map();

        repository.mergeDetails("CASE X", detailsToBeUpdated);

        assertEquals(asList(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", expectedDetails)),
                repository.allEligibleCouples());
    }

    public void testShouldMarkAsCloseEligibleCoupleFromRepositoryBasedOnCaseID() throws Exception {
        EligibleCouple firstEligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>());
        EligibleCouple secondEligibleCouple = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());

        repository.add(firstEligibleCouple);
        repository.add(secondEligibleCouple);

        repository.close("CASE X");
        assertEquals(firstEligibleCouple.setIsClosed(true), repository.findByCaseID(firstEligibleCouple.caseId()));
        assertEquals(secondEligibleCouple, repository.findByCaseID(secondEligibleCouple.caseId()));

        repository.close("CASE DOES NOT MATCH");
        assertEquals(firstEligibleCouple.setIsClosed(true), repository.findByCaseID(firstEligibleCouple.caseId()));
        assertEquals(secondEligibleCouple, repository.findByCaseID(secondEligibleCouple.caseId()));

        repository.close("CASE Y");
        assertEquals(firstEligibleCouple.setIsClosed(true), repository.findByCaseID(firstEligibleCouple.caseId()));
        assertEquals(secondEligibleCouple.setIsClosed(true), repository.findByCaseID(secondEligibleCouple.caseId()));
    }

    public void testShouldDeleteCorrespondingAlertsWhenDeletingEC() throws Exception {
        Alert alert = new Alert("CASE Y", "Wife 2", "Husband 2", "Village 2", "FP 2", "EC Number 2", AlertPriority.normal, "2012-01-01", "2012-01-11", open);

        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>()));
        alertRepository.createAlert(new Alert("CASE X", "Wife 1", "Husband 1", "Village 1", "FP 1", "EC Number 1", AlertPriority.normal, "2012-01-01", "2012-01-11", open));

        repository.add(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>()));
        alertRepository.createAlert(alert);

        repository.close("CASE X");

        assertEquals(asList(alert), alertRepository.allAlerts());
    }

    public void testShouldDeleteMotherWhenDeletingAnEC() throws Exception {
        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>()));

        Mother mother = new Mother("CASE Y", "CASE X", "TC 1", "2012-01-01");
        motherRepository.add(mother);
        motherRepository.add(new Mother("CASE Z", "CASE X", "TC 2", "2012-01-01"));

        EligibleCouple ecWhoIsNotClosed = new EligibleCouple("CASE A", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());
        Mother motherWhoIsNotClosed = new Mother("CASE B", "CASE A", "TC 2", "2012-01-01");
        repository.add(ecWhoIsNotClosed);
        motherRepository.add(motherWhoIsNotClosed);

        repository.close("CASE X");

        assertEquals(asList(ecWhoIsNotClosed), repository.allEligibleCouples());
        assertEquals(asList(motherWhoIsNotClosed), motherRepository.allANCs());
        assertEquals(emptyList(), timelineEventRepository.allFor("CASE Y"));
        assertEquals(emptyList(), timelineEventRepository.allFor("CASE Z"));

        assertEquals(asList(TimelineEvent.forStartOfPregnancy("CASE B", "2012-01-01")), timelineEventRepository.allFor("CASE B"));
    }

    public void testFindECByCaseID() throws Exception {
        EligibleCouple ec = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>());
        EligibleCouple anotherEC = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());

        repository.add(ec);
        repository.add(anotherEC);

        assertEquals(ec, repository.findByCaseID("CASE X"));
        assertEquals(null, repository.findByCaseID("CASE NOTFOUND"));
    }

    public void testShouldUpdatePhotoPathCase() throws Exception {
        EligibleCouple ec = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>());
        EligibleCouple anotherEC = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());
        repository.add(ec);
        repository.add(anotherEC);

        repository.updatePhotoPath("CASE X", "photo/path/");

        assertEquals("photo/path/", repository.findByCaseID("CASE X").photoPath());
        assertEquals(null, repository.findByCaseID("CASE Y").photoPath());
    }

    public void testFindECByCaseIDs() throws Exception {
        EligibleCouple ec = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>());
        EligibleCouple anotherEC = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());
        EligibleCouple yetAnotherEC = new EligibleCouple("CASE Z", "Wife 3", "Husband 3", "EC Number 3", "Village 3", "SubCenter 3", new HashMap<String, String>());
        EligibleCouple closedEC = new EligibleCouple("CASE B", "Wife 5", "Husband 5", "", "Village 5", "SubCenter 5", new HashMap<String, String>()).setIsClosed(true);

        repository.add(ec);
        repository.add(anotherEC);
        repository.add(yetAnotherEC);
        repository.add(closedEC);

        List<EligibleCouple> fetchedLists = repository.findByCaseIDs("CASE X", "CASE Z", "CASE B");
        assertTrue(fetchedLists.contains(ec));
        assertTrue(fetchedLists.contains(yetAnotherEC));
        assertTrue(fetchedLists.contains(closedEC));
    }

    public void testShouldGetCountOfECsInRepo() throws Exception {
        EligibleCouple ec = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>());
        EligibleCouple anotherEC = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());
        EligibleCouple yetAnotherEC = new EligibleCouple("CASE Z", "Wife 3", "Husband 3", "EC Number 3", "Village 3", "SubCenter 3", new HashMap<String, String>());
        EligibleCouple outOfAreaEC = new EligibleCouple("CASE A", "Wife 4", "Husband 4", "", "Village 4", "SubCenter 4", new HashMap<String, String>()).asOutOfArea();
        EligibleCouple closedEC = new EligibleCouple("CASE B", "Wife 5", "Husband 5", "", "Village 5", "SubCenter 5", new HashMap<String, String>()).setIsClosed(true);

        repository.add(ec);
        repository.add(anotherEC);
        repository.add(closedEC);

        assertEquals(2, repository.count());

        repository.add(yetAnotherEC);
        assertEquals(3, repository.count());

        repository.close(yetAnotherEC.caseId());
        assertEquals(2, repository.count());

        repository.add(outOfAreaEC);
        assertEquals(2, repository.count());
    }

    public void testShouldFetchUniqueVillagesInRepo() throws Exception {
        EligibleCouple ec = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village1", "SubCenter 1", new HashMap<String, String>());
        EligibleCouple anotherEC = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village2", "SubCenter 2", new HashMap<String, String>());
        EligibleCouple yetAnotherEC = new EligibleCouple("CASE Z", "Wife 3", "Husband 3", "EC Number 3", "Village2", "SubCenter 3", new HashMap<String, String>());
        EligibleCouple outOfAreaEC = new EligibleCouple("CASE A", "Wife 4", "Husband 4", "", "Village4", "SubCenter 4", new HashMap<String, String>()).asOutOfArea();
        EligibleCouple closedEC = new EligibleCouple("CASE B", "Wife 5", "Husband 5", "", "Village 5", "SubCenter 5", new HashMap<String, String>()).setIsClosed(true);

        repository.add(ec);
        repository.add(anotherEC);
        repository.add(yetAnotherEC);
        repository.add(outOfAreaEC);
        repository.add(closedEC);

        List<String> villages = repository.villages();

        assertEquals(asList("Village1", "Village2"), villages);
    }

    public void testShouldNotFetchOutOfAreaECsWhenFetchingAllInAreaECs() throws Exception {
        EligibleCouple outOfAreaEC = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "", "Village 1", "SubCenter 1", new HashMap<String, String>()).asOutOfArea();
        EligibleCouple eligibleCouple = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());

        repository.add(outOfAreaEC);
        repository.add(eligibleCouple);

        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());
    }

    public void testShouldNotFetchClosedECsWhenFetchingAllInAreaECs() throws Exception {
        EligibleCouple closedEC = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "", "Village 1", "SubCenter 1", new HashMap<String, String>()).setIsClosed(true);
        EligibleCouple eligibleCouple = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());

        repository.add(closedEC);
        repository.add(eligibleCouple);

        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());
    }

    public void testShouldGetCountOfECsWithFPInRepo() throws Exception {
        EligibleCouple ecNotUsingAnyFPMethod = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", mapOf("currentMethod", "none"));
        EligibleCouple anotherECNotUsingAnyFPMethod = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>());
        EligibleCouple ecUsingCondomFPMethod = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", mapOf("currentMethod", "condom"));
        EligibleCouple ecUsingIUDFPMethod = new EligibleCouple("CASE Z", "Wife 3", "Husband 3", "EC Number 3", "Village 3", "SubCenter 3", mapOf("currentMethod", "iud"));
        EligibleCouple outOfAreaEC = new EligibleCouple("CASE A", "Wife 4", "Husband 4", "", "Village 4", "SubCenter 4", mapOf("currentMethod", "condom"))
                .asOutOfArea();
        EligibleCouple closedEC = new EligibleCouple("CASE B", "Wife 5", "Husband 5", "", "Village 5", "SubCenter 5", mapOf("currentMethod", "iud"))
                .setIsClosed(true);

        repository.add(ecNotUsingAnyFPMethod);
        repository.add(anotherECNotUsingAnyFPMethod);
        repository.add(ecUsingCondomFPMethod);
        repository.add(closedEC);

        assertEquals(1, repository.fpCount());

        repository.add(ecUsingIUDFPMethod);
        assertEquals(2, repository.fpCount());

        repository.close(ecUsingIUDFPMethod.caseId());
        assertEquals(1, repository.fpCount());

        repository.add(outOfAreaEC);
        assertEquals(1, repository.fpCount());
    }
}
