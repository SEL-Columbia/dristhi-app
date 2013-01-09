package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.util.Session;

import java.util.*;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.util.EasyMap.mapOf;

public class ChildRepositoryTest extends AndroidTestCase {
    private ChildRepository repository;
    private TimelineEventRepository timelineEventRepository;
    private AlertRepository alertRepository;

    private static final Map<String, String> EXTRA_DETAILS = mapOf("some-key", "some-value");

    @Override
    protected void setUp() throws Exception {
        timelineEventRepository = new TimelineEventRepository();
        alertRepository = new AlertRepository();
        repository = new ChildRepository(timelineEventRepository, alertRepository);

        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository, timelineEventRepository, alertRepository);
    }

    public void testShouldInsertChildForExistingMother() throws Exception {
        repository.add(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));

        assertEquals(asList(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS)), repository.all());
        assertEquals(asList(TimelineEvent.forChildBirthInChildProfile("CASE A", "2012-06-09", new HashMap<String, String>())), timelineEventRepository.allFor("CASE A"));
    }

    public void testShouldFetchAllOpenChildren() throws Exception {
        Child firstChild = new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS);
        Child secondChild = new Child("CASE B", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS);
        Child closedChild = new Child("CASE C", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS).setIsClosed(true);
        repository.add(firstChild);
        repository.add(secondChild);
        repository.add(closedChild);

        List<Child> children = repository.all();

        assertTrue(children.contains(firstChild));
        assertTrue(children.contains(secondChild));
        assertFalse(children.contains(closedChild));
    }

    public void testShouldFetchChildrenByTheirOwnCaseId() throws Exception {
        repository.add(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));
        repository.add(new Child("CASE B", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS));
        repository.add(new Child("CASE C", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS).setIsClosed(true));

        assertEquals(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS), repository.find("CASE A"));
        assertEquals(new Child("CASE B", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS), repository.find("CASE B"));
        assertEquals(new Child("CASE C", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS).setIsClosed(true), repository.find("CASE C"));
    }

    public void testShouldCountChildren() throws Exception {
        assertEquals(0, repository.count());

        repository.add(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));
        assertEquals(1, repository.count());

        repository.add(new Child("CASE B", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));
        assertEquals(2, repository.count());

        repository.add(new Child("CASE C", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS).setIsClosed(true));
        assertEquals(2, repository.count());

        repository.close("CASE B");
        assertEquals(1, repository.count());
    }

    public void testShouldMarkAsClosedWhenAChildIsClosed() throws Exception {
        Child firstChild = new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS);
        Child secondChild = new Child("CASE B", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS);

        repository.add(firstChild);
        repository.add(secondChild);
        alertRepository.createAlert(new Alert("CASE A", "Child 1", "Husband 1", "Bherya 1", "ANC 1", "TC 1", AlertPriority.normal, "2012-01-01", "2012-01-11", open));
        alertRepository.createAlert(new Alert("CASE B", "Child 2", "Husband 2", "Bherya 1", "ANC 1", "TC 1", AlertPriority.normal, "2012-01-01", "2012-01-11", open));

        repository.close("CASE A");

        assertEquals(firstChild.setIsClosed(true), repository.find(firstChild.caseId()));
        assertEquals(secondChild, repository.find(secondChild.caseId()));
        assertEquals(asList(new Alert("CASE B", "Child 2", "Husband 2", "Bherya 1", "ANC 1", "TC 1", AlertPriority.normal, "2012-01-01", "2012-01-11", open)), alertRepository.allAlerts());
    }

    public void testShouldDeleteCorrespondingTimelineEventsWhenAChildIsDeleted() throws Exception {
        repository.add(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));
        repository.add(new Child("CASE B", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS));

        repository.close("CASE A");

        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE A"));
        assertEquals(asList(TimelineEvent.forChildBirthInChildProfile("CASE B", "2012-06-10", new HashMap<String, String>())), timelineEventRepository.allFor("CASE B"));
    }

    public void testShouldDeleteAllChildrenAndTheirDependentEntitiesForAGivenMother() throws Exception {
        Child firstChild = new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS);
        Child secondChild = new Child("CASE B", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS);
        repository.add(firstChild);
        repository.add(secondChild);
        repository.add(new Child("CASE C", "CASE Y", "TC 2", "2012-06-09", "female", EXTRA_DETAILS));

        repository.closeAllCasesForMother("CASE X");

        assertEquals(asList(new Child("CASE C", "CASE Y", "TC 2", "2012-06-09", "female", EXTRA_DETAILS)), repository.all());
        assertEquals(firstChild.setIsClosed(true), repository.find(firstChild.caseId()));
        assertEquals(secondChild.setIsClosed(true), repository.find(secondChild.caseId()));
        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE A"));
        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE B"));
        assertEquals(asList(TimelineEvent.forChildBirthInChildProfile("CASE C", "2012-06-09", new HashMap<String, String>())), timelineEventRepository.allFor("CASE C"));
    }

    public void testShouldUpdateMotherDetails() throws Exception {
        Map<String, String> details = mapOf("some-key", "some-value");
        Child child = new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", details);
        repository.add(child);

        Map<String, String> newDetails = create("some-key", "some-new-value").put("some-other-key", "blah").map();
        repository.updateDetails("CASE A", newDetails);

        Child expectedChildWithNewDetails = new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", newDetails);
        assertEquals(asList(expectedChildWithNewDetails), repository.all());
    }

    public void testShouldFindAllChildrenByCaseIds(){
        Child child1 = new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS);
        Child child2 = new Child("CASE B", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS);
        Child child3 = new Child("CASE C", "CASE Y", "TC 2", "2012-06-09", "female", EXTRA_DETAILS).setIsClosed(true);
        repository.add(child1);
        repository.add(child2);
        repository.add(child3);

        List<Child> childrenByCaseIds = repository.findChildrenByCaseIds("CASE A", "CASE C");

        assertEquals(asList(child1,child3), childrenByCaseIds);
    }
}
