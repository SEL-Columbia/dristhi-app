package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.dto.Action;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.dto.BeneficiaryType;
import org.ei.drishti.repository.AlertRepository;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.HashMap;

import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.dto.AlertPriority.normal;
import static org.ei.drishti.dto.AlertPriority.urgent;
import static org.ei.drishti.util.ActionBuilder.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AlertServiceTest {
    @Mock
    private AlertRepository alertRepository;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private AllEligibleCouples allEligibleCouples;

    private AlertService alertService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        alertService = new AlertService(alertRepository, allBeneficiaries, allEligibleCouples);
    }

    @Test
    public void shouldAddAnAlertIntoAlertRepositoryForMotherCreateAlertAction() throws Exception {
        Action actionForMother = setupActionForMotherCreateAlert("Case X", normal, "ANC 1", "2012-01-01", "2012-01-22", "Husband 1");

        alertService.handleAction(actionForMother);

        verify(alertRepository).createAlert(new Alert("Case X", "Theresa Case X", "Husband 1", "Village Case X", "ANC 1", "Thaayi Case X", normal, "2012-01-01", "2012-01-22", open));
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldNotDoAnythingIfActionIsInactive() throws Exception {
        Action actionForMother = new Action("Case X", "alert", "createAlert", new HashMap<String, String>(), "0", false, new HashMap<String, String>());

        alertService.handleAction(actionForMother);

        verifyZeroInteractions(alertRepository);
    }

    @Test
    public void shouldAddAnAlertIntoAlertRepositoryForChildCreateAlertAction() throws Exception {
        Action actionForMother = setupActionForChildCreateAlert("Case X", urgent, "ANC 1", "2012-01-01", "2012-01-22");

        alertService.handleAction(actionForMother);

        verify(alertRepository).createAlert(new Alert("Case X", "B/O Theresa Case X", "Husband 1", "Village Case X", "ANC 1", "Thaayi Case X", urgent, "2012-01-01", "2012-01-22", open));
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldMarkAlertAsClosedInRepositoryForCloseActions() throws Exception {
        Action firstAction = actionForCloseAlert("Case X", "ANC 1", "2012-01-01", "0");
        Action secondAction = actionForCloseAlert("Case Y", "ANC 2", "2012-01-01", "0");

        alertService.handleAction(firstAction);
        alertService.handleAction(secondAction);

        verify(alertRepository).markAlertAsClosed("Case X", "ANC 1", "2012-01-01");
        verify(alertRepository).markAlertAsClosed("Case Y", "ANC 2", "2012-01-01");
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldDeleteAllFromRepositoryForDeleteAllActions() throws Exception {
        Action firstAction = actionForDeleteAllAlert("Case X");
        Action secondAction = actionForDeleteAllAlert("Case Y");

        alertService.handleAction(firstAction);
        alertService.handleAction(secondAction);

        verify(alertRepository).deleteAllAlertsForCase("Case X");
        verify(alertRepository).deleteAllAlertsForCase("Case Y");
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldNotFailIfActionTypeIsNotExpected() throws Exception {
        alertService.handleAction(unknownAction("alert"));
    }

    @Test
    public void shouldUpdateDeleteAndDeleteAllAlertActionsBasedOnTheirType() throws Exception {
        Action firstCreateAction = setupActionForMotherCreateAlert("Case X", normal, "ANC 1", "2012-01-01", "2012-01-11", "Husband 1");
        Action firstCloseAction = actionForCloseAlert("Case Y", "ANC 2", "2012-01-01", "0");
        Action secondCreateAction = setupActionForMotherCreateAlert("Case Z", normal, "ANC 2", "2012-01-01", "2012-01-22", "Husband 2");
        Action deleteAllAction = actionForDeleteAllAlert("Case A");
        Action secondCloseAction = actionForCloseAlert("Case B", "ANC 3", "2012-01-01", "0");

        alertService.handleAction(firstCreateAction);
        alertService.handleAction(firstCloseAction);
        alertService.handleAction(secondCreateAction);
        alertService.handleAction(deleteAllAction);
        alertService.handleAction(secondCloseAction);

        InOrder inOrder = inOrder(alertRepository);
        inOrder.verify(alertRepository).createAlert(new Alert("Case X", "Theresa Case X", "Husband 1", "Village Case X", "ANC 1", "Thaayi Case X", normal, "2012-01-01", "2012-01-11", open));
        inOrder.verify(alertRepository).markAlertAsClosed("Case Y", "ANC 2", "2012-01-01");
        inOrder.verify(alertRepository).createAlert(new Alert("Case Z", "Theresa Case Z", "Husband 2", "Village Case Z", "ANC 2", "Thaayi Case Z", normal, "2012-01-01", "2012-01-22", open));
        inOrder.verify(alertRepository).deleteAllAlertsForCase("Case A");
        inOrder.verify(alertRepository).markAlertAsClosed("Case B", "ANC 3", "2012-01-01");
        verifyNoMoreInteractions(alertRepository);
    }

    private Action setupActionForMotherCreateAlert(String caseID, AlertPriority priority, String visitCode, String startDate, String expiryDate, String husbandName) {
        Action action = actionForCreateAlert(caseID, priority.value(), BeneficiaryType.mother.value(), visitCode, startDate, expiryDate, "0");
        when(allBeneficiaries.findMother(caseID)).thenReturn(new Mother(caseID, "EC " + caseID, "Thaayi " + caseID, "2012-05-05"));
        when(allEligibleCouples.findByCaseID("EC " + caseID)).thenReturn(new EligibleCouple("EC " + caseID, "Theresa " + caseID, husbandName, "EC Number 1", "Village " + caseID, "SubCenter", new HashMap<String, String>()));
        return action;
    }

    private Action setupActionForChildCreateAlert(String caseID, AlertPriority priority, String visitCode, String startDate, String expiryDate) {
        Action action = actionForCreateAlert(caseID, priority.value(), BeneficiaryType.child.value(), visitCode, startDate, expiryDate, "0");
        when(allBeneficiaries.findChild(caseID)).thenReturn(new Child(caseID, "Mother " + caseID, "Thaayi " + caseID, "2012-05-05", "female", new HashMap<String, String>()));
        when(allBeneficiaries.findMother("Mother " + caseID)).thenReturn(new Mother("Mother " + caseID, "EC " + caseID, "Thaayi " + caseID, "2012-05-05"));
        when(allEligibleCouples.findByCaseID("EC " + caseID)).thenReturn(new EligibleCouple("EC " + caseID, "Theresa " + caseID, "Husband 1", "EC Number 1", "Village " + caseID, "SubCenter", new HashMap<String, String>()));
        return action;
    }
}
