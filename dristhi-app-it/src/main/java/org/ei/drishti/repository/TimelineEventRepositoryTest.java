package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.util.Session;

import java.util.Collections;
import java.util.Date;

import static java.util.Arrays.asList;

public class TimelineEventRepositoryTest extends AndroidTestCase {
    private TimelineEventRepository repository;

    @Override
    protected void setUp() throws Exception {
        repository = new TimelineEventRepository();
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository);
    }

    public void testShouldInsertTimelineEvents() throws Exception {
        TimelineEvent event1 = TimelineEvent.forChildBirthInChildProfile("CASE X", "2012-12-12", "female");
        TimelineEvent event2 = TimelineEvent.forChildBirthInChildProfile("CASE X", "2012-01-01", "male");
        TimelineEvent event3 = TimelineEvent.forChildBirthInChildProfile("CASE Y", "2012-12-1", "female");
        repository.add(event1);
        repository.add(event2);
        repository.add(event3);

        assertEquals(asList(event1, event2), repository.allFor("CASE X"));
    }

    public void testShouldDeleteTimelineEventsByCaseId() throws Exception {
        TimelineEvent event1 = TimelineEvent.forChildBirthInChildProfile("CASE X", "2012-12-12", "female");
        TimelineEvent event2 = TimelineEvent.forChildBirthInChildProfile("CASE X", "2012-01-01", "male");
        TimelineEvent event3 = TimelineEvent.forChildBirthInChildProfile("CASE Y", "2012-12-1", "female");
        repository.add(event1);
        repository.add(event2);
        repository.add(event3);

        repository.deleteAllTimelineEventsForCase("CASE X");

        assertEquals(Collections.emptyList(), repository.allFor("CASE X"));
        assertEquals(asList(event3), repository.allFor("CASE Y"));
    }
}
