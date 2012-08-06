package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.util.Session;

import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class EligibleCoupleRepositoryTest extends AndroidTestCase {

    private EligibleCoupleRepository repository;
    private AlertRepository alertRepository;
    private TimelineEventRepository timelineEventRepository;
    private BeneficiaryRepository beneficiaryRepository;

    @Override
    protected void setUp() throws Exception {
        alertRepository = new AlertRepository();
        timelineEventRepository = new TimelineEventRepository();
        beneficiaryRepository = new BeneficiaryRepository(timelineEventRepository, alertRepository);
        repository = new EligibleCoupleRepository(alertRepository, timelineEventRepository, beneficiaryRepository);
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository, alertRepository, timelineEventRepository, beneficiaryRepository);
    }

    public void testShouldInsertEligibleCoupleIntoRepository() throws Exception {
        EligibleCouple eligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "IUD", "Village 1", "SubCenter 1");

        repository.add(eligibleCouple);

        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());
    }

    public void testShouldDeleteEligibleCoupleFromRepositoryBasedOnCaseID() throws Exception {
        EligibleCouple eligibleCouple = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2");

        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1"));
        repository.add(eligibleCouple);

        repository.delete("CASE X");
        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());

        repository.delete("CASE DOES NOT MATCH");
        assertEquals(asList(eligibleCouple), repository.allEligibleCouples());

        repository.delete("CASE Y");
        assertEquals(new ArrayList<EligibleCouple>(), repository.allEligibleCouples());
    }

    public void testShouldDeleteAllEligibleCouplesFromRepository() throws Exception {
        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1"));
        repository.add(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2"));

        repository.deleteAllEligibleCouples();

        assertEquals(new ArrayList<EligibleCouple>(), repository.allEligibleCouples());
    }

    public void testShouldDeleteCorrespondingAlertsWhenDeletingEC() throws Exception {
        Alert alert = new Alert("CASE Y", "Wife 2", "Village 2", "FP 2", "EC Number 2", 1, "2012-01-01");

        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1"));
        alertRepository.createAlert(new Alert("CASE X", "Wife 1", "Village 1", "FP 1", "EC Number 1", 1, "2012-01-01"));

        repository.add(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2"));
        alertRepository.createAlert(alert);

        repository.delete("CASE X");

        assertEquals(asList(alert), alertRepository.allAlerts());
    }

    public void testShouldDeleteCorrespondingTimelineEventsWhenDeletingEC() throws Exception {
        TimelineEvent event = TimelineEvent.forStartOfPregnancy("CASE Y", "2012-01-01");

        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1"));
        timelineEventRepository.add(TimelineEvent.forStartOfPregnancy("CASE X", "2012-01-01"));

        repository.add(new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2"));
        timelineEventRepository.add(event);

        repository.delete("CASE X");

        assertEquals(emptyList(), timelineEventRepository.allFor("CASE X"));
        assertEquals(asList(event), timelineEventRepository.allFor("CASE Y"));
    }

    public void testShouldDeleteCorrespondingBeneficiariesAndTheirDependenciesWhenDeletingAnEC() throws Exception {
        repository.add(new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1"));
        beneficiaryRepository.addMother(new Beneficiary("CASE Y", "CASE X", "TC 1", "2012-01-01"));
        beneficiaryRepository.addMother(new Beneficiary("CASE Z", "CASE X", "TC 2", "2012-01-01"));
        timelineEventRepository.add(TimelineEvent.forStartOfPregnancy("CASE Y", "2012-01-01"));

        repository.add(new EligibleCouple("CASE A", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2"));
        beneficiaryRepository.addMother(new Beneficiary("CASE B", "CASE A", "TC 2", "2012-01-01"));
        timelineEventRepository.add(TimelineEvent.forStartOfPregnancy("CASE B", "2012-01-01"));

        repository.delete("CASE X");

        assertEquals(asList(new Beneficiary("CASE B", "CASE A", "TC 2", "2012-01-01")), beneficiaryRepository.allANCs());
        assertEquals(emptyList(), timelineEventRepository.allFor("CASE Y"));

        assertEquals(asList(TimelineEvent.forStartOfPregnancy("CASE B", "2012-01-01")), timelineEventRepository.allFor("CASE B"));
    }

    public void testFindECByCaseID() throws Exception {
        EligibleCouple ec = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1");
        EligibleCouple anotherEC = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2");

        repository.add(ec);
        repository.add(anotherEC);

        assertEquals(ec, repository.findByCaseID("CASE X"));
        assertEquals(null, repository.findByCaseID("CASE NOTFOUND"));
    }

    public void testShouldGetCountOfECsInRepo() throws Exception {
        EligibleCouple ec = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "SubCenter 1");
        EligibleCouple anotherEC = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "IUD", "Village 2", "SubCenter 2");
        EligibleCouple yetAnotherEC = new EligibleCouple("CASE Z", "Wife 3", "Husband 3", "EC Number 3", "IUD", "Village 3", "SubCenter 3");

        repository.add(ec);
        repository.add(anotherEC);

        assertEquals(2, repository.count());

        repository.add(yetAnotherEC);
        assertEquals(3, repository.count());

        repository.delete(yetAnotherEC.caseId());
        assertEquals(2, repository.count());
    }
}
