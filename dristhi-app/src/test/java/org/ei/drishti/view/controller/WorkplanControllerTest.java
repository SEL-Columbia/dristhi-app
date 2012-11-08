package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.view.contract.WorkplanContext;
import org.ei.drishti.view.contract.WorkplanTodo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.AlertStatus.closed;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.util.DateUtil.today;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class WorkplanControllerTest {
    @Mock
    AllAlerts allAlerts;
    @Mock
    private AllSettings allSettings;
    @Mock
    private Context context;
    @Mock
    private CommCareClientService commCareClientService;
    @Mock
    private AllEligibleCouples allEligibleCouples;
    private WorkplanController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new WorkplanController(allAlerts, allSettings, commCareClientService, allEligibleCouples, context);
    }

    @Test
    public void shouldGetWorkplan() throws Exception {
        Alert alert1 = new Alert("Case 1", "Napa", "Husband 1", "Village 1", "OPV", "Thaayi Card 1", AlertPriority.urgent, today().minusDays(1).toString(), "01/08/2012", open);
        Alert alert2 = new Alert("Case 2", "Salinas", "Husband 2", "Village 2", "ANC 1", "Thaayi Card 2", AlertPriority.urgent, today().minusDays(1).toString(), "02/08/2012", open);
        Alert alert3 = new Alert("Case 3", "Balboa", "Husband 3", "Village 1", "TT 1", "Thaayi Card 3", AlertPriority.normal, today().minusDays(1).toString(), "03/08/2012", open);
        Alert alert4 = new Alert("Case 4", "Balboa", "Husband 4", "Village 2", "IFA", "Thaayi Card 4", AlertPriority.normal, today().minusDays(1).toString(), "04/08/2012", closed);
        Alert alert5 = new Alert("Case 5", "Karishma", "Husband 5", "Village 2", "HEP B1", "Thaayi Card 4", AlertPriority.normal, today().minusDays(1).toString(), "05/08/2012", closed);
        Alert alert6 = new Alert("Case 6", "Nethravati", "Husband 6", "Village 2", "IFA follow up", "Thaayi Card 4", AlertPriority.normal, today().minusDays(1).toString(), "06/08/2012", closed);
        when(allAlerts.fetchAll()).thenReturn(asList(alert1, alert2, alert3, alert4, alert5, alert6));

        WorkplanTodo todo1 = new WorkplanTodo("Case 1", "Napa", "Husband 1", "OPV", "01/08/2012", "Village 1");
        WorkplanTodo todo2 = new WorkplanTodo("Case 2", "Salinas", "Husband 2", "ANC 1", "02/08/2012", "Village 2");
        WorkplanTodo todo3 = new WorkplanTodo("Case 3", "Balboa", "Husband 3", "TT 1", "03/08/2012", "Village 1");
        WorkplanTodo todo4 = new WorkplanTodo("Case 4", "Balboa", "Husband 4", "IFA", "04/08/2012", "Village 2");
        WorkplanTodo todo5 = new WorkplanTodo("Case 5", "Karishma", "Husband 5", "HEP B1", "05/08/2012", "Village 2");
        WorkplanTodo todo6 = new WorkplanTodo("Case 6", "Nethravati", "Husband 6", "IFA follow up", "06/08/2012", "Village 2");
        WorkplanContext expectedContext = new WorkplanContext(asList(todo1, todo2), asList(todo3), asList(todo4, todo5, todo6));

        String actualContext = controller.get();

        assertEquals(new Gson().toJson(expectedContext), actualContext);
    }

    @Test
    public void shouldSaveAppliedVillageFilter() throws Exception {
        controller.saveAppliedVillageFilter("munjanahalli");

        verify(allSettings).saveAppliedVillageFilter("munjanahalli");
    }

    @Test
    public void shouldGetAppliedVillageFilter() throws Exception {
        when(allSettings.appliedVillageFilter("All")).thenReturn("munjanahalli");

        String villageFilter = controller.appliedVillageFilter("All");

        assertEquals(villageFilter, "munjanahalli");

    }
}
