package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.util.Session;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.util.EasyMap.mapOf;

public class MotherRepositoryTest extends AndroidTestCase {
    private MotherRepository repository;
    private ChildRepository childRepository;
    private TimelineEventRepository timelineEventRepository;
    private AlertRepository alertRepository;
    private EligibleCoupleRepository eligibleCoupleRepository;
    private LocalDate today;

    @Override
    protected void setUp() throws Exception {
        today = DateUtil.today();
        timelineEventRepository = new TimelineEventRepository();
        alertRepository = new AlertRepository();
        childRepository = new ChildRepository(timelineEventRepository, alertRepository);

        repository = new MotherRepository(childRepository, timelineEventRepository, alertRepository);

        eligibleCoupleRepository = new EligibleCoupleRepository(repository, timelineEventRepository, alertRepository);

        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository, childRepository, timelineEventRepository, alertRepository, eligibleCoupleRepository);
    }

    public void testShouldInsertMother() throws Exception {
        Map<String, String> details = mapOf("some-key", "some-value");
        Mother mother = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08").withDetails(details);

        repository.add(mother);

        assertEquals(asList(mother), repository.allANCs());
        assertEquals(asList(TimelineEvent.forStartOfPregnancy("CASE X", "2012-06-08")), timelineEventRepository.allFor("CASE X"));
    }

    public void testShouldFetchANCAndCorrespondingEC() throws Exception {
        Map<String, String> details = mapOf("some-key", "some-value");
        EligibleCouple firstEligibleCouple = new EligibleCouple("EC Case 1", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", details);
        EligibleCouple secondEligibleCouple = new EligibleCouple("EC Case 2", "Wife 2", "Husband 2", "EC Number", "Village 2", "SubCenter 2", details);
        Mother mother = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08").withDetails(details);
        eligibleCoupleRepository.add(firstEligibleCouple);
        eligibleCoupleRepository.add(secondEligibleCouple);
        repository.add(mother);
        repository.add(new Mother("CASE Y", "EC Case 2", "TC 2", today.minusDays(300).toString()));


        List<Pair<Mother,EligibleCouple>> ancsWithEC = repository.allANCsWithEC();

        assertEquals(asList(Pair.of(mother, firstEligibleCouple)), ancsWithEC);
    }

    public void testShouldUpdateMotherDetails() throws Exception {
        Map<String, String> details = mapOf("some-key", "some-value");
        Mother mother = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08").withDetails(details);
        repository.add(mother);

        Map<String, String> newDetails = create("some-key", "some-new-value").put("some-other-key", "blah").map();
        repository.updateDetails("CASE X", newDetails);

        Mother expectedMotherWithNewDetails = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08").withDetails(newDetails);
        assertEquals(asList(expectedMotherWithNewDetails), repository.allANCs());
    }

    public void testShouldLoadAllANCsBasedOnType() throws Exception {
        repository.add(new Mother("CASE X", "EC Case 1", "TC 1", today.minusDays(100).toString()));
        repository.add(new Mother("CASE Y", "EC Case 2", "TC 2", today.minusDays(279).toString()));

        assertEquals(asList(new Mother("CASE X", "EC Case 1", "TC 1", today.minusDays(100).toString()), new Mother("CASE Y", "EC Case 2", "TC 2", today.minusDays(279).toString())), repository.allANCs());
    }

    public void testShouldSwitchWomanToPNC() throws Exception {
        repository.add(new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08"));
        repository.add(new Mother("CASE Y", "EC Case 2", "TC 2", "2012-06-08"));

        repository.switchToPNC("CASE X");

        assertEquals(asList(new Mother("CASE Y", "EC Case 2", "TC 2", "2012-06-08")), repository.allANCs());
        assertEquals(asList(new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08")), repository.allPNCs());
    }

    public void testShouldConsiderMothersAsBelongingToPNCAfterEDD() throws Exception {
        repository.add(new Mother("CASE X", "EC Case 1", "TC 1", today.minusDays(279).toString()));
        repository.add(new Mother("CASE Y", "EC Case 2", "TC 2", today.minusDays(280).toString()));
        repository.add(new Mother("CASE Z", "EC Case 3", "TC 3", today.minusDays(281).toString()));

        assertEquals(asList(new Mother("CASE X", "EC Case 1", "TC 1", today.minusDays(279).toString())), repository.allANCs());
        assertEquals(asList(new Mother("CASE Y", "EC Case 2", "TC 2", today.minusDays(280).toString()), new Mother("CASE Z", "EC Case 3", "TC 3", today.minusDays(281).toString())), repository.allPNCs());
    }

    public void testShouldFindAMotherByCaseId() throws Exception {
        repository.add(new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08"));
        repository.add(new Mother("CASE Y", "EC Case 2", "TC 2", "2012-06-08"));

        assertEquals(new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08"), repository.find("CASE X"));
        assertEquals(new Mother("CASE Y", "EC Case 2", "TC 2", "2012-06-08"), repository.find("CASE Y"));
        assertEquals(null, repository.find("CASE NOT FOUND"));
    }

    public void testShouldCountANCsAndPNCs() throws Exception {
        repository.add(new Mother("CASE X", "EC Case 1", "TC 1", today.minusDays(100).toString()));
        repository.add(new Mother("CASE Y", "EC Case 1", "TC 2", today.toString()));
        assertEquals(2, repository.ancCount());
        assertEquals(0, repository.pncCount());

        repository.add(new Mother("CASE Z", "EC Case 2", "TC 3", today.minusDays(300).toString()));
        assertEquals(2, repository.ancCount());
        assertEquals(1, repository.pncCount());

        repository.switchToPNC("CASE X");
        assertEquals(1, repository.ancCount());
        assertEquals(2, repository.pncCount());

        repository.close("CASE Y");
        assertEquals(0, repository.ancCount());
        assertEquals(2, repository.pncCount());

        repository.close("CASE NOT FOUND");
        assertEquals(0, repository.ancCount());
        assertEquals(2, repository.pncCount());
    }

    public void testShouldRemoveTimelineEventsWhenMotherIsClosed() throws Exception {
        Mother mother1 = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08");
        Mother mother2 = new Mother("CASE Y", "EC Case 1", "TC 2", "2012-06-08");

        repository.add(mother1);
        repository.add(mother2);

        repository.close(mother1.caseId());

        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor(mother1.caseId()));
        assertEquals(asList(TimelineEvent.forStartOfPregnancy(mother2.caseId(), "2012-06-08")), timelineEventRepository.allFor(mother2.caseId()));
    }

    public void testShouldRemoveAlertsWhenMotherIsClosed() throws Exception {
        Mother mother1 = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08");
        Mother mother2 = new Mother("CASE Y", "EC Case 1", "TC 2", "2012-06-08");

        repository.add(mother1);
        alertRepository.createAlert(new Alert("CASE X", "Theresa 1", "bherya", "ANC 1", "TC 1", AlertPriority.normal, "2012-01-01", "2012-01-11", open));
        repository.add(mother2);
        alertRepository.createAlert(new Alert("CASE Y", "Theresa 2", "bherya", "ANC 1", "TC 2", AlertPriority.normal, "2012-01-01", "2012-01-11", open));

        repository.close(mother1.caseId());

        assertEquals(asList(new Alert("CASE Y", "Theresa 2", "bherya", "ANC 1", "TC 2", AlertPriority.normal, "2012-01-01", "2012-01-11", open)), alertRepository.allAlerts());
    }

    public void testShouldRemoveChildrenAndTheirEntitiesWhenMotherIsClosed() throws Exception {
        Mother mother1 = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08");
        Mother mother2 = new Mother("CASE Y", "EC Case 1", "TC 2", "2012-06-08");

        repository.add(mother1);
        childRepository.addChildForMother(mother1, "CASE A", "2012-06-09", "female");
        childRepository.addChildForMother(mother1, "CASE B", "2012-06-09", "male");

        repository.add(mother2);
        childRepository.addChildForMother(mother2, "CASE C", "2012-06-10", "female");

        repository.close(mother1.caseId());

        assertEquals(asList(mother2), repository.allANCs());
        assertNull(childRepository.find("CASE A"));
        assertNull(childRepository.find("CASE B"));
        assertNotNull(childRepository.find("CASE C"));

        assertTrue(timelineEventRepository.allFor("CASE A").isEmpty());
        assertTrue(timelineEventRepository.allFor("CASE B").isEmpty());
        assertEquals(1, timelineEventRepository.allFor("CASE C").size());
    }

    public void testShouldCloseAllMothersForEC() throws Exception {
        Mother mother1 = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-08");
        Mother mother2 = new Mother("CASE Y", "EC Case 1", "TC 2", "2012-06-08");
        Mother mother3 = new Mother("CASE Z", "EC Case 2", "TC 3", "2012-06-08");

        repository.add(mother1);
        repository.add(mother2);
        repository.add(mother3);

        repository.closeAllCasesForEC("EC Case 1");

        assertEquals(asList(mother3), repository.allANCs());
    }
}
