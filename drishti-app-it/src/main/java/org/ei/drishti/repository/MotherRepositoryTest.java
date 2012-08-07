package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.util.Session;

import java.util.Date;

import static java.util.Arrays.asList;

public class MotherRepositoryTest extends AndroidTestCase {
    private MotherRepository repository;
    private TimelineEventRepository timelineEventRepository;
    private AlertRepository alertRepository;

    @Override
    protected void setUp() throws Exception {
        timelineEventRepository = new TimelineEventRepository();
        alertRepository = new AlertRepository();
        repository = new MotherRepository(timelineEventRepository, alertRepository);

        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository, timelineEventRepository, alertRepository);
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
}
