package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.*;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.util.Session;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.util.EasyMap.create;

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
        motherRepository = new MotherRepository(childRepository, timelineEventRepository, alertRepository);
        repository = new EligibleCoupleRepository(motherRepository, timelineEventRepository, alertRepository);
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository, alertRepository, timelineEventRepository, childRepository, motherRepository);
    }

    public void testShouldInsertEligibleCoupleIntoRepository() throws Exception {
        Map<String, String> details = create("Hello", "There").put("Also", "This").map();
        EligibleCouple eligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", details);

        repository.add(eligibleCouple);

        assertEquals(asList(eligibleCouple), repository.allInAreaEligibleCouples());
    }

    public void testShouldInsertOutOfAreaEligibleCoupleIntoRepository() throws Exception {
        Map<String, String> details = create("Hello", "There").put("Also", "This").map();
        EligibleCouple eligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", details).asOutOfArea();

        repository.add(eligibleCouple);

        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());
    }

    public void testShouldUpdateDetailsOfEligibleCoupleIntoRepository() throws Exception {
        Map<String, String> detailsBeforeUpdate = create("Key 1", "Value 1").put("currentMethod", "IUD").map();
        EligibleCouple eligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", detailsBeforeUpdate);

        repository.add(eligibleCouple);
        Map<String, String> detailsToBeUpdated = create("Key 1", "Value 1").put("currentMethod", "Condom").put("familyPlanningMethodChangeDate", "2012-03-03").put("Key 3", "Value 3").map();
        repository.updateDetails("CASE X", detailsToBeUpdated);

        assertEquals(asList(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", detailsToBeUpdated)), repository.allInAreaEligibleCouples());
        assertEquals(asList(TimelineEvent.forChangeOfFPMethod("CASE X", "IUD", "Condom", "2012-03-03")), timelineEventRepository.allFor("CASE X"));
    }

    public void testShouldNotAddATimelineEventForFPMethodChangeWhenItIsNotChanged() throws Exception {
        Map<String, String> detailsBeforeUpdate = create("Key 1", "Value 1").put("currentMethod", "IUD").put("familyPlanningMethodChangeDate", "2012-01-01").map();
        EligibleCouple eligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", detailsBeforeUpdate);

        repository.add(eligibleCouple);
        Map<String, String> detailsToBeUpdated = create("Key 1", "Value 1").put("somethingOtherThanCurrentMethod", "Blah").put("Key 3", "Value 3").map();
        repository.updateDetails("CASE X", detailsToBeUpdated);

        assertEquals(emptyList(), timelineEventRepository.allFor("CASE X"));
    }

    public void testShouldDeleteEligibleCoupleFromRepositoryBasedOnCaseID() throws Exception {
        EligibleCouple eligibleCouple = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());

        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>()));
        repository.add(eligibleCouple);

        repository.close("CASE X");
        assertEquals(asList(eligibleCouple), repository.allInAreaEligibleCouples());

        repository.close("CASE DOES NOT MATCH");
        assertEquals(asList(eligibleCouple), repository.allInAreaEligibleCouples());

        repository.close("CASE Y");
        assertEquals(new ArrayList<EligibleCouple>(), repository.allInAreaEligibleCouples());
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

    public void testShouldDeleteCorrespondingTimelineEventsWhenDeletingEC() throws Exception {
        TimelineEvent event = TimelineEvent.forStartOfPregnancy("CASE Y", "2012-01-01");

        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>()));
        timelineEventRepository.add(TimelineEvent.forStartOfPregnancy("CASE X", "2012-01-01"));

        repository.add(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>()));
        timelineEventRepository.add(event);

        repository.close("CASE X");

        assertEquals(emptyList(), timelineEventRepository.allFor("CASE X"));
        assertEquals(asList(event), timelineEventRepository.allFor("CASE Y"));
    }

    public void testShouldDeleteCorrespondingChildrenAndTheirDependenciesWhenDeletingAnEC() throws Exception {
        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>()));

        Mother mother = new Mother("CASE Y", "CASE X", "TC 1", "2012-01-01");
        motherRepository.add(mother);
        motherRepository.add(new Mother("CASE Z", "CASE X", "TC 2", "2012-01-01"));
        childRepository.addChildForMother(new Child("CASE C1", "CASE Y", "TC 1", "2012-06-08", "female", new HashMap<String, String>()));

        EligibleCouple ecWhoIsNotClosed = new EligibleCouple("CASE A", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());
        Mother motherWhoIsNotClosed = new Mother("CASE B", "CASE A", "TC 2", "2012-01-01");
        repository.add(ecWhoIsNotClosed);
        motherRepository.add(motherWhoIsNotClosed);

        repository.close("CASE X");

        assertEquals(asList(ecWhoIsNotClosed), repository.allInAreaEligibleCouples());
        assertEquals(asList(motherWhoIsNotClosed), motherRepository.allANCs());
        assertEquals(emptyList(), childRepository.all());
        assertEquals(emptyList(), timelineEventRepository.allFor("CASE X"));
        assertEquals(emptyList(), timelineEventRepository.allFor("CASE Y"));
        assertEquals(emptyList(), timelineEventRepository.allFor("CASE Z"));
        assertEquals(emptyList(), timelineEventRepository.allFor("CASE C1"));

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

    public void testShouldGetCountOfECsInRepo() throws Exception {
        EligibleCouple ec = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>());
        EligibleCouple anotherEC = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());
        EligibleCouple yetAnotherEC = new EligibleCouple("CASE Z", "Wife 3", "Husband 3", "EC Number 3", "Village 3", "SubCenter 3", new HashMap<String, String>());
        EligibleCouple outOfAreaEC = new EligibleCouple("CASE A", "Wife 4", "Husband 4", "", "Village 4", "SubCenter 4", new HashMap<String, String>()).asOutOfArea();

        repository.add(ec);
        repository.add(anotherEC);

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

        repository.add(ec);
        repository.add(anotherEC);
        repository.add(yetAnotherEC);
        repository.add(outOfAreaEC);

        List<String> villages = repository.villages();

        assertEquals(asList("Village1", "Village2"), villages);
    }

    public void testShouldNotFetchOutOfAreaECsWhenFetchingAllInAreaECs() throws Exception {
        EligibleCouple outOfAreaEC = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "", "Village 1", "SubCenter 1", new HashMap<String, String>()).asOutOfArea();
        EligibleCouple eligibleCouple = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 2", "SubCenter 2", new HashMap<String, String>());

        repository.add(outOfAreaEC);
        repository.add(eligibleCouple);

        assertEquals(asList(eligibleCouple), repository.allInAreaEligibleCouples());
    }

}
