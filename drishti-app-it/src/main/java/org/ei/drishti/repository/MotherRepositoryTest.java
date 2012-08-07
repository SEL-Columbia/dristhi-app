package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.util.Session;

import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;

public class MotherRepositoryTest extends AndroidTestCase {
    private MotherRepository repository;
    private BeneficiaryRepository childRepository;
    private TimelineEventRepository timelineEventRepository;
    private AlertRepository alertRepository;

    @Override
    protected void setUp() throws Exception {
        timelineEventRepository = new TimelineEventRepository();
        alertRepository = new AlertRepository();
        childRepository = new BeneficiaryRepository(timelineEventRepository, alertRepository);

        repository = new MotherRepository(childRepository, timelineEventRepository, alertRepository);

        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository, childRepository, timelineEventRepository, alertRepository);
    }

    public void testShouldInsertMother() throws Exception {
        repository.add(new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08"));

        assertEquals(asList(new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08")), repository.allANCs());
        assertEquals(asList(TimelineEvent.forStartOfPregnancy("CASE X", "2012-06-08")), timelineEventRepository.allFor("CASE X"));
    }

    public void testShouldLoadAllANCs() throws Exception {
        repository.add(new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08"));
        repository.add(new Beneficiary("CASE Y", "EC Case 2", "TC 2", "2012-06-08"));

        assertEquals(asList(new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08"), new Beneficiary("CASE Y", "EC Case 2", "TC 2", "2012-06-08")), repository.allANCs());
    }

    public void testShouldSwitchWomanToPNC() throws Exception {
        repository.add(new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08"));
        repository.add(new Beneficiary("CASE Y", "EC Case 2", "TC 2", "2012-06-08"));

        repository.switchToPNC("CASE X");

        assertEquals(asList(new Beneficiary("CASE Y", "EC Case 2", "TC 2", "2012-06-08")), repository.allANCs());
        assertEquals(asList(new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08")), repository.allPNCs());
    }

    public void testShouldFindAMotherByCaseId() throws Exception {
        repository.add(new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08"));
        repository.add(new Beneficiary("CASE Y", "EC Case 2", "TC 2", "2012-06-08"));

        assertEquals(new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08"), repository.find("CASE X"));
        assertEquals(new Beneficiary("CASE Y", "EC Case 2", "TC 2", "2012-06-08"), repository.find("CASE Y"));
        assertEquals(null, repository.find("CASE NOT FOUND"));
    }

    public void testShouldCountANCsAndPNCs() throws Exception {
        repository.add(new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08"));
        repository.add(new Beneficiary("CASE Y", "EC Case 1", "TC 2", "2012-06-08"));
        assertEquals(2, repository.ancCount());
        assertEquals(0, repository.pncCount());

        repository.add(new Beneficiary("CASE Z", "EC Case 2", "TC 3", "2012-06-08"));
        assertEquals(3, repository.ancCount());
        assertEquals(0, repository.pncCount());

        repository.switchToPNC("CASE X");
        assertEquals(2, repository.ancCount());
        assertEquals(1, repository.pncCount());

        repository.close("CASE Y");
        assertEquals(1, repository.ancCount());
        assertEquals(1, repository.pncCount());

        repository.close("CASE NOT FOUND");
        assertEquals(1, repository.ancCount());
        assertEquals(1, repository.pncCount());
    }

    public void testShouldRemoveTimelineEventsWhenMotherIsClosed() throws Exception {
        Beneficiary mother1 = new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08");
        Beneficiary mother2 = new Beneficiary("CASE Y", "EC Case 1", "TC 2", "2012-06-08");

        repository.add(mother1);
        repository.add(mother2);

        repository.close(mother1.caseId());

        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor(mother1.caseId()));
        assertEquals(asList(TimelineEvent.forStartOfPregnancy(mother2.caseId(), "2012-06-08")), timelineEventRepository.allFor(mother2.caseId()));
    }

    public void testShouldRemoveAlertsWhenMotherIsClosed() throws Exception {
        Beneficiary mother1 = new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08");
        Beneficiary mother2 = new Beneficiary("CASE Y", "EC Case 1", "TC 2", "2012-06-08");

        repository.add(mother1);
        alertRepository.createAlert(new Alert("CASE X", "Theresa 1", "bherya", "ANC 1", "TC 1", 1, "2012-01-01"));
        repository.add(mother2);
        alertRepository.createAlert(new Alert("CASE Y", "Theresa 2", "bherya", "ANC 1", "TC 2", 1, "2012-01-01"));

        repository.close(mother1.caseId());

        assertEquals(asList(new Alert("CASE Y", "Theresa 2", "bherya", "ANC 1", "TC 2", 1, "2012-01-01")), alertRepository.allAlerts());
    }

    public void testShouldRemoveChildrenAndTheirEntitiesWhenMotherIsClosed() throws Exception {
        Beneficiary mother1 = new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-08");
        Beneficiary mother2 = new Beneficiary("CASE Y", "EC Case 1", "TC 2", "2012-06-08");

        repository.add(mother1);
        childRepository.addChildForMother(mother1, "CASE A", "2012-06-09", "female");
        childRepository.addChildForMother(mother1, "CASE B", "2012-06-09", "male");

        repository.add(mother2);
        childRepository.addChildForMother(mother2, "CASE C", "2012-06-10", "female");

        repository.close(mother1.caseId());

        assertEquals(asList(mother2), repository.allANCs());
        assertNull(childRepository.findByCaseId("CASE A"));
        assertNull(childRepository.findByCaseId("CASE B"));
        assertNotNull(childRepository.findByCaseId("CASE C"));

        assertTrue(timelineEventRepository.allFor("CASE A").isEmpty());
        assertTrue(timelineEventRepository.allFor("CASE B").isEmpty());
        assertEquals(1, timelineEventRepository.allFor("CASE C").size());
    }
}
