package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.*;
import org.ei.drishti.dto.Action;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.dto.BeneficiaryType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.dto.AlertPriority.normal;
import static org.ei.drishti.dto.AlertPriority.urgent;
import static org.ei.drishti.util.ActionBuilder.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllAlertsTest {
    @Mock
    private AlertRepository alertRepository;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private AllEligibleCouples allEligibleCouples;

    private AllAlerts allAlerts;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allAlerts = new AllAlerts(alertRepository, allBeneficiaries, allEligibleCouples);
    }

    @Test
    public void shouldAddAnAlertIntoAlertRepositoryForMotherCreateAlertAction() throws Exception {
        Action actionForMother = setupActionForMotherCreateAlert("Case X", normal, "ANC 1", "2012-01-01", "2012-01-22");

        allAlerts.handleAction(actionForMother);

        verify(alertRepository).createAlert(new Alert("Case X", "Theresa Case X", "Village Case X", "ANC 1", "Thaayi Case X", normal, "2012-01-01", "2012-01-22"));
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldAddAnAlertIntoAlertRepositoryForChildCreateAlertAction() throws Exception {
        Action actionForMother = setupActionForChildCreateAlert("Case X", urgent, "ANC 1", "2012-01-01", "2012-01-22");

        allAlerts.handleAction(actionForMother);

        verify(alertRepository).createAlert(new Alert("Case X", "Theresa Case X", "Village Case X", "ANC 1", "Thaayi Case X", urgent, "2012-01-01", "2012-01-22"));
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldDeleteAllAlerts() throws Exception {
        allAlerts.deleteAllAlerts();

        verify(alertRepository).deleteAllAlerts();
    }

    @Test
    public void shouldDeleteFromRepositoryForDeleteActions() throws Exception {
        Action firstAction = actionForDeleteAlert("Case X", "ANC 1", "0");
        Action secondAction = actionForDeleteAlert("Case Y", "ANC 2", "0");

        allAlerts.handleAction(firstAction);
        allAlerts.handleAction(secondAction);

        verify(alertRepository).deleteAlertsForVisitCodeOfCase("Case X", "ANC 1");
        verify(alertRepository).deleteAlertsForVisitCodeOfCase("Case Y", "ANC 2");
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldDeleteAllFromRepositoryForDeleteAllActions() throws Exception {
        Action firstAction = actionForDeleteAllAlert("Case X");
        Action secondAction = actionForDeleteAllAlert("Case Y");

        allAlerts.handleAction(firstAction);
        allAlerts.handleAction(secondAction);

        verify(alertRepository).deleteAllAlertsForCase("Case X");
        verify(alertRepository).deleteAllAlertsForCase("Case Y");
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldFindUniqueLocationsOfAlertsInDB() {
        List<VillageAlertSummary> summaries = Arrays.asList(new VillageAlertSummary("Location 1", 1), new VillageAlertSummary("Location 2", 1));
        when(alertRepository.summary()).thenReturn(summaries);

        assertEquals(summaries, allAlerts.villageSummary());
    }

    @Test
    public void shouldNotFailIfActionTypeIsNotExpected() throws Exception {
        allAlerts.handleAction(unknownAction("alert"));
    }

    @Test
    public void shouldUpdateDeleteAndDeleteAllAlertActionsBasedOnTheirType() throws Exception {
        Action firstCreateAction = setupActionForMotherCreateAlert("Case X", normal, "ANC 1", "2012-01-01", "2012-01-11");
        Action firstDeleteAction = actionForDeleteAlert("Case Y", "ANC 2", "0");
        Action secondCreateAction = setupActionForMotherCreateAlert("Case Z", normal, "ANC 2", "2012-01-01", "2012-01-22");
        Action deleteAllAction = actionForDeleteAllAlert("Case A");
        Action secondDeleteAction = actionForDeleteAlert("Case B", "ANC 3", "0");

        allAlerts.handleAction(firstCreateAction);
        allAlerts.handleAction(firstDeleteAction);
        allAlerts.handleAction(secondCreateAction);
        allAlerts.handleAction(deleteAllAction);
        allAlerts.handleAction(secondDeleteAction);

        InOrder inOrder = inOrder(alertRepository);
        inOrder.verify(alertRepository).createAlert(new Alert("Case X", "Theresa Case X", "Village Case X", "ANC 1", "Thaayi Case X", normal, "2012-01-01", "2012-01-11"));
        inOrder.verify(alertRepository).deleteAlertsForVisitCodeOfCase("Case Y", "ANC 2");
        inOrder.verify(alertRepository).createAlert(new Alert("Case Z", "Theresa Case Z", "Village Case Z", "ANC 2", "Thaayi Case Z", normal, "2012-01-01", "2012-01-22"));
        inOrder.verify(alertRepository).deleteAllAlertsForCase("Case A");
        inOrder.verify(alertRepository).deleteAlertsForVisitCodeOfCase("Case B", "ANC 3");
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldFetchAllAlertsFromRepository() throws Exception {
        List<Alert> expectedAlerts = Arrays.asList(new Alert("Case X", "Theresa 1", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11"), new Alert("Case Y", "Theresa 2", "bherya", "ANC 2", "Thaayi 2", normal, "2012-01-01", "2012-01-22"));
        when(alertRepository.allAlerts()).thenReturn(expectedAlerts);

        List<Alert> alerts = allAlerts.fetchAll();

        assertEquals(expectedAlerts, alerts);
    }

    @Test
    public void shouldFetchAllAlertsForAVillageFromRepository() throws Exception {
        List<Alert> expectedAlerts = Arrays.asList(new Alert("Case X", "Theresa 1", "bherya1", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11"), new Alert("Case Y", "Theresa 2", "bherya1", "ANC 2", "Thaayi 2", normal, "2012-01-01", "2012-01-22"));
        when(alertRepository.allForVillage("bherya1")).thenReturn(expectedAlerts);

        List<Alert> alerts = allAlerts.fetchAllForVillage("bherya1");

        assertEquals(expectedAlerts, alerts);
    }

    private Action setupActionForMotherCreateAlert(String caseID, AlertPriority priority, String visitCode, String startDate, String expiryDate) {
        Action action = actionForCreateAlert(caseID, priority.value(), BeneficiaryType.mother.value(), visitCode, startDate, expiryDate, "0");
        when(allBeneficiaries.findMother(caseID)).thenReturn(new Mother(caseID, "EC " + caseID, "Thaayi " + caseID, "2012-05-05"));
        when(allEligibleCouples.findByCaseID("EC " + caseID)).thenReturn(new EligibleCouple("EC " + caseID, "Theresa " + caseID, "Husband 1", "EC Number 1", "IUD", "Village " + caseID, "SubCenter", new HashMap<String, String>()));
        return action;
    }

    private Action setupActionForChildCreateAlert(String caseID, AlertPriority priority, String visitCode, String startDate, String expiryDate) {
        Action action = actionForCreateAlert(caseID, priority.value(), BeneficiaryType.child.value(), visitCode, startDate, expiryDate, "0");
        when(allBeneficiaries.findChild(caseID)).thenReturn(new Child(caseID, "EC " + caseID, "Thaayi " + caseID, "2012-05-05"));
        when(allEligibleCouples.findByCaseID("EC " + caseID)).thenReturn(new EligibleCouple("EC " + caseID, "Theresa " + caseID, "Husband 1", "EC Number 1", "IUD", "Village " + caseID, "SubCenter", new HashMap<String, String>()));
        return action;
    }
}
