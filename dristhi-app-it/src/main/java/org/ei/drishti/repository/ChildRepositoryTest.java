package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.util.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        repository.addChild(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));

        assertEquals(asList(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS)), repository.all());
        assertEquals(asList(TimelineEvent.forChildBirthInChildProfile("CASE A", "2012-06-09", new HashMap<String, String>())), timelineEventRepository.allFor("CASE A"));
    }

    public void testShouldFetchChildrenByTheirOwnCaseId() throws Exception {
        repository.addChild(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));
        repository.addChild(new Child("CASE B", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS));

        assertEquals(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS), repository.find("CASE A"));
        assertEquals(new Child("CASE B", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS), repository.find("CASE B"));
    }

    public void testShouldCountChildren() throws Exception {
        assertEquals(0, repository.count());

        repository.addChild(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));
        assertEquals(1, repository.count());

        repository.addChild(new Child("CASE B", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));
        assertEquals(2, repository.count());

        repository.close("CASE B");
        assertEquals(1, repository.count());
    }

    public void testShouldDeleteCorrespondingAlertsWhenAChildIsDeleted() throws Exception {
        repository.addChild(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));

        alertRepository.createAlert(new Alert("CASE A", "Child 1", "Husband 1", "Bherya 1", "ANC 1", "TC 1", AlertPriority.normal, "2012-01-01", "2012-01-11", open));

        repository.addChild(new Child("CASE B", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS));
        alertRepository.createAlert(new Alert("CASE B", "Child 2", "Husband 2", "Bherya 1", "ANC 1", "TC 1", AlertPriority.normal, "2012-01-01", "2012-01-11", open));

        repository.close("CASE A");

        assertEquals(asList(new Alert("CASE B", "Child 2", "Husband 2", "Bherya 1", "ANC 1", "TC 1", AlertPriority.normal, "2012-01-01", "2012-01-11", open)), alertRepository.allAlerts());
    }

    public void testShouldDeleteCorrespondingTimelineEventsWhenAChildIsDeleted() throws Exception {
        repository.addChild(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));
        repository.addChild(new Child("CASE B", "CASE X", "TC 1", "2012-06-10", "female", EXTRA_DETAILS));

        repository.close("CASE A");

        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE A"));
        assertEquals(asList(TimelineEvent.forChildBirthInChildProfile("CASE B", "2012-06-10", new HashMap<String, String>())), timelineEventRepository.allFor("CASE B"));
    }

    public void testShouldDeleteAllChildrenAndTheirDependentEntitiesForAGivenMother() throws Exception {
        repository.addChild(new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));
        repository.addChild(new Child("CASE B", "CASE X", "TC 1", "2012-06-09", "female", EXTRA_DETAILS));
        repository.addChild(new Child("CASE C", "CASE Y", "TC 2", "2012-06-09", "female", EXTRA_DETAILS));

        repository.closeAllCasesForMother("CASE X");

        assertEquals(asList(new Child("CASE C", "CASE Y", "TC 2", "2012-06-09", "female", EXTRA_DETAILS)), repository.all());

        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE A"));
        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE B"));
        assertEquals(asList(TimelineEvent.forChildBirthInChildProfile("CASE C", "2012-06-09", new HashMap<String, String>())), timelineEventRepository.allFor("CASE C"));
    }

    public void testShouldUpdateMotherDetails() throws Exception {
        Map<String, String> details = mapOf("some-key", "some-value");
        Child child = new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", details);
        repository.addChild(child);

        Map<String, String> newDetails = create("some-key", "some-new-value").put("some-other-key", "blah").map();
        repository.updateDetails("CASE A", newDetails);

        Child expectedChildWithNewDetails = new Child("CASE A", "CASE X", "TC 1", "2012-06-09", "female", newDetails);
        assertEquals(asList(expectedChildWithNewDetails), repository.all());
    }

}
