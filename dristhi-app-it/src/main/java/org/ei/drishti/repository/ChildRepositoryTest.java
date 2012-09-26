package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.*;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.util.Session;

import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.AlertStatus.open;

public class ChildRepositoryTest extends AndroidTestCase {
    private ChildRepository repository;
    private TimelineEventRepository timelineEventRepository;
    private AlertRepository alertRepository;

    @Override
    protected void setUp() throws Exception {
        timelineEventRepository = new TimelineEventRepository();
        alertRepository = new AlertRepository();
        repository = new ChildRepository(timelineEventRepository, alertRepository);

        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository, timelineEventRepository, alertRepository);
    }

    public void testShouldInsertChildForExistingMother() throws Exception {
        Mother mother = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        repository.addChildForMother(mother, "CASE A", "2012-06-09", "female");

        assertEquals(asList(new Child("CASE A", "CASE X", "TC 1", "2012-06-09")), repository.all());
        assertEquals(asList(TimelineEvent.forChildBirth("CASE A", "2012-06-09", "female")), timelineEventRepository.allFor("CASE A"));
    }

    public void testShouldNotInsertChildForANonExistingMother() throws Exception {
        repository.addChildForMother(null, "CASE A", "2012-06-09", "female");

        assertEquals(new ArrayList<Child>(), repository.all());
    }

    public void testShouldFetchChildrenByTheirOwnCaseId() throws Exception {
        Mother mother = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        repository.addChildForMother(mother, "CASE A", "2012-06-09", "female");
        repository.addChildForMother(mother, "CASE B", "2012-06-10", "female");

        assertEquals(new Child("CASE A", "CASE X", "TC 1", "2012-06-09"), repository.find("CASE A"));
        assertEquals(new Child("CASE B", "CASE X", "TC 1", "2012-06-10"), repository.find("CASE B"));
    }

    public void testShouldCountChildren() throws Exception {
        assertEquals(0, repository.childCount());

        Mother mother = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        repository.addChildForMother(mother, "CASE A", "2012-06-09", "female");
        assertEquals(1, repository.childCount());

        repository.addChildForMother(mother, "CASE B", "2012-06-09", "female");
        assertEquals(2, repository.childCount());

        repository.close("CASE B");
        assertEquals(1, repository.childCount());
    }

    public void testShouldDeleteCorrespondingAlertsWhenAChildIsDeleted() throws Exception {
        Mother mother = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        repository.addChildForMother(mother, "CASE A", "2012-06-09", "female");
        alertRepository.createAlert(new Alert("CASE A", "Child 1", "Husband 1", "Bherya 1", "ANC 1", "TC 1", AlertPriority.normal, "2012-01-01", "2012-01-11", open));

        repository.addChildForMother(mother, "CASE B", "2012-06-10", "female");
        alertRepository.createAlert(new Alert("CASE B", "Child 2", "Husband 2", "Bherya 1", "ANC 1", "TC 1", AlertPriority.normal, "2012-01-01", "2012-01-11", open));

        repository.close("CASE A");

        assertEquals(asList(new Alert("CASE B", "Child 2", "Husband 2", "Bherya 1", "ANC 1", "TC 1", AlertPriority.normal, "2012-01-01", "2012-01-11", open)), alertRepository.allAlerts());
    }

    public void testShouldDeleteCorrespondingTimelineEventsWhenAChildIsDeleted() throws Exception {
        Mother mother = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        repository.addChildForMother(mother, "CASE A", "2012-06-09", "female");
        repository.addChildForMother(mother, "CASE B", "2012-06-10", "female");

        repository.close("CASE A");

        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE A"));
        assertEquals(asList(TimelineEvent.forChildBirth("CASE B", "2012-06-10", "female")), timelineEventRepository.allFor("CASE B"));
    }

    public void testShouldDeleteAllChildrenAndTheirDependentEntitiesForAGivenMother() throws Exception {
        Mother mother1 = new Mother("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        Mother mother2 = new Mother("CASE Y", "EC Case 2", "TC 2", "2012-06-01");
        repository.addChildForMother(mother1, "CASE A", "2012-06-09", "female");
        repository.addChildForMother(mother1, "CASE B", "2012-06-09", "female");
        repository.addChildForMother(mother2, "CASE C", "2012-06-09", "female");

        repository.closeAllCasesForMother("CASE X");

        assertEquals(asList(new Child("CASE C", "CASE Y", "TC 2", "2012-06-09")), repository.all());

        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE A"));
        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE B"));
        assertEquals(asList(TimelineEvent.forChildBirth("CASE C", "2012-06-09", "female")), timelineEventRepository.allFor("CASE C"));
    }
}
