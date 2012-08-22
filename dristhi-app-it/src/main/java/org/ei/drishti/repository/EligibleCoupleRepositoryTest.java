package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.util.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

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
        HashMap<String, String> details = new HashMap<String, String>();
        details.put("Hello", "There");
        details.put("Also", "This");
        EligibleCouple eligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "IUD", "Village 1", "SubCenter 1", details);

        repository.add(eligibleCouple);

        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());
    }

    public void testShouldDeleteEligibleCoupleFromRepositoryBasedOnCaseID() throws Exception {
        EligibleCouple eligibleCouple = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2", new HashMap<String, String>());

        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1", new HashMap<String, String>()));
        repository.add(eligibleCouple);

        repository.close("CASE X");
        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());

        repository.close("CASE DOES NOT MATCH");
        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());

        repository.close("CASE Y");
        assertEquals(new ArrayList<EligibleCouple>(), repository.allEligibleCouples());
    }

    public void testShouldDeleteCorrespondingAlertsWhenDeletingEC() throws Exception {
        Alert alert = new Alert("CASE Y", "Wife 2", "Village 2", "FP 2", "EC Number 2", 1, "2012-01-01", "2012-01-11");

        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1", new HashMap<String, String>()));
        alertRepository.createAlert(new Alert("CASE X", "Wife 1", "Village 1", "FP 1", "EC Number 1", 1, "2012-01-01", "2012-01-11"));

        repository.add(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2", new HashMap<String, String>()));
        alertRepository.createAlert(alert);

        repository.close("CASE X");

        assertEquals(asList(alert), alertRepository.allAlerts());
    }

    public void testShouldDeleteCorrespondingTimelineEventsWhenDeletingEC() throws Exception {
        TimelineEvent event = TimelineEvent.forStartOfPregnancy("CASE Y", "2012-01-01");

        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1", new HashMap<String, String>()));
        timelineEventRepository.add(TimelineEvent.forStartOfPregnancy("CASE X", "2012-01-01"));

        repository.add(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2", new HashMap<String, String>()));
        timelineEventRepository.add(event);

        repository.close("CASE X");

        assertEquals(emptyList(), timelineEventRepository.allFor("CASE X"));
        assertEquals(asList(event), timelineEventRepository.allFor("CASE Y"));
    }

    public void testShouldDeleteCorrespondingChildrenAndTheirDependenciesWhenDeletingAnEC() throws Exception {
        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1", new HashMap<String, String>()));

        Mother mother = new Mother("CASE Y", "CASE X", "TC 1", "2012-01-01");
        motherRepository.add(mother);
        motherRepository.add(new Mother("CASE Z", "CASE X", "TC 2", "2012-01-01"));
        childRepository.addChildForMother(mother, "CASE C1", "2012-06-08", "female");

        EligibleCouple ecWhoIsNotClosed = new EligibleCouple("CASE A", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2", new HashMap<String, String>());
        Mother motherWhoIsNotClosed = new Mother("CASE B", "CASE A", "TC 2", "2012-01-01");
        repository.add(ecWhoIsNotClosed);
        motherRepository.add(motherWhoIsNotClosed);

        repository.close("CASE X");

        assertEquals(asList(ecWhoIsNotClosed), repository.allEligibleCouples());
        assertEquals(asList(motherWhoIsNotClosed), motherRepository.allANCs());
        assertEquals(emptyList(), childRepository.all());
        assertEquals(emptyList(), timelineEventRepository.allFor("CASE X"));
        assertEquals(emptyList(), timelineEventRepository.allFor("CASE Y"));
        assertEquals(emptyList(), timelineEventRepository.allFor("CASE Z"));
        assertEquals(emptyList(), timelineEventRepository.allFor("CASE C1"));

        assertEquals(asList(TimelineEvent.forStartOfPregnancy("CASE B", "2012-01-01")), timelineEventRepository.allFor("CASE B"));
    }

    public void testFindECByCaseID() throws Exception {
        EligibleCouple ec = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1", new HashMap<String, String>());
        EligibleCouple anotherEC = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2", new HashMap<String, String>());

        repository.add(ec);
        repository.add(anotherEC);

        assertEquals(ec, repository.findByCaseID("CASE X"));
        assertEquals(null, repository.findByCaseID("CASE NOTFOUND"));
    }

    public void testShouldGetCountOfECsInRepo() throws Exception {
        EligibleCouple ec = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1", new HashMap<String, String>());
        EligibleCouple anotherEC = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2", new HashMap<String, String>());
        EligibleCouple yetAnotherEC = new EligibleCouple("CASE Z", "Wife 3", "Husband 3", "EC Number 3", "IUD", "Village 3", "SubCenter 3", new HashMap<String, String>());

        repository.add(ec);
        repository.add(anotherEC);

        assertEquals(2, repository.count());

        repository.add(yetAnotherEC);
        assertEquals(3, repository.count());

        repository.close(yetAnotherEC.caseId());
        assertEquals(2, repository.count());
    }
}
