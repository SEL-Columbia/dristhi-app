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

public class BeneficiaryRepositoryTest extends AndroidTestCase {
    private BeneficiaryRepository repository;
    private TimelineEventRepository timelineEventRepository;
    private AlertRepository alertRepository;

    @Override
    protected void setUp() throws Exception {
        timelineEventRepository = new TimelineEventRepository();
        alertRepository = new AlertRepository();
        repository = new BeneficiaryRepository(timelineEventRepository, alertRepository);

        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository, timelineEventRepository, alertRepository);
    }

    public void testShouldInsertChildForExistingMother() throws Exception {
        Beneficiary mother = new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        repository.addChildForMother(mother, "CASE A", "2012-06-09", "female");

        assertEquals(asList(new Beneficiary("CASE A", "CASE X", "TC 1", "2012-06-09")), repository.all());
        assertEquals(asList(TimelineEvent.forChildBirth("CASE A", "2012-06-09", "female")), timelineEventRepository.allFor("CASE A"));
    }

    public void testShouldNotInsertChildForANonExistingMother() throws Exception {
        repository.addChildForMother(null, "CASE A", "2012-06-09", "female");

        assertEquals(new ArrayList<Beneficiary>(), repository.all());
    }

    public void testShouldFetchBeneficiariesByTheirOwnCaseId() throws Exception {
        Beneficiary mother = new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        repository.addChildForMother(mother, "CASE A", "2012-06-09", "female");
        repository.addChildForMother(mother, "CASE B", "2012-06-10", "female");

        assertEquals(new Beneficiary("CASE A", "CASE X", "TC 1", "2012-06-09"), repository.findByCaseId("CASE A"));
        assertEquals(new Beneficiary("CASE B", "CASE X", "TC 1", "2012-06-10"), repository.findByCaseId("CASE B"));
    }

    public void testShouldCountChildren() throws Exception {
        assertEquals(0, repository.childCount());

        Beneficiary mother = new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        repository.addChildForMother(mother, "CASE A", "2012-06-09", "female");
        assertEquals(1, repository.childCount());

        repository.addChildForMother(mother, "CASE B", "2012-06-09", "female");
        assertEquals(2, repository.childCount());

        repository.close("CASE B");
        assertEquals(1, repository.childCount());
    }

    public void testShouldDeleteCorrespondingAlertsWhenAChildIsDeleted() throws Exception {
        Beneficiary mother = new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        repository.addChildForMother(mother, "CASE A", "2012-06-09", "female");
        alertRepository.createAlert(new Alert("CASE A", "Child 1", "Bherya 1", "ANC 1", "TC 1", 1, "2012-01-01"));

        repository.addChildForMother(mother, "CASE B", "2012-06-10", "female");
        alertRepository.createAlert(new Alert("CASE B", "Child 2", "Bherya 1", "ANC 1", "TC 1", 1, "2012-01-01"));

        repository.close("CASE A");

        assertEquals(asList(new Alert("CASE B", "Child 2", "Bherya 1", "ANC 1", "TC 1", 1, "2012-01-01")), alertRepository.allAlerts());
    }

    public void testShouldDeleteCorrespondingTimelineEventsWhenAChildIsDeleted() throws Exception {
        Beneficiary mother = new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        repository.addChildForMother(mother, "CASE A", "2012-06-09", "female");
        repository.addChildForMother(mother, "CASE B", "2012-06-10", "female");

        repository.close("CASE A");

        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE A"));
        assertEquals(asList(TimelineEvent.forChildBirth("CASE B", "2012-06-10", "female")), timelineEventRepository.allFor("CASE B"));
    }

    public void testShouldDeleteAllChildrenAndTheirDependentEntitiesForAGivenMother() throws Exception {
        Beneficiary mother1 = new Beneficiary("CASE X", "EC Case 1", "TC 1", "2012-06-01");
        Beneficiary mother2 = new Beneficiary("CASE Y", "EC Case 2", "TC 2", "2012-06-01");
        repository.addChildForMother(mother1, "CASE A", "2012-06-09", "female");
        repository.addChildForMother(mother1, "CASE B", "2012-06-09", "female");
        repository.addChildForMother(mother2, "CASE C", "2012-06-09", "female");

        repository.closeAllCasesForMother("CASE X");

        assertEquals(asList(new Beneficiary("CASE C", "CASE Y", "TC 2", "2012-06-09")), repository.all());

        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE A"));
        assertEquals(new ArrayList<TimelineEvent>(), timelineEventRepository.allFor("CASE B"));
        assertEquals(asList(TimelineEvent.forChildBirth("CASE C", "2012-06-09", "female")), timelineEventRepository.allFor("CASE C"));
    }
}
