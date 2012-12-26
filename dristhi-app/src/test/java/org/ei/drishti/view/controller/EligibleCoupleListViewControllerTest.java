package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.event.Event;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.util.Cache;
import org.ei.drishti.view.contract.EC;
import org.ei.drishti.view.contract.ECs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Map;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class EligibleCoupleListViewControllerTest {
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private AllSettings allSettings;
    @Mock
    private Context context;
    @Mock
    private CommCareClientService commCareClientService;

    private EligibleCoupleListViewController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new EligibleCoupleListViewController(allEligibleCouples, allBeneficiaries, allSettings, new Cache<String>(), context, commCareClientService);
    }

    @Test
    public void shouldSortECsByPriorityAndThenByName() throws Exception {
        EligibleCouple ecNormalPriority1 = new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", "Bherya SC", normalPriority());
        EligibleCouple ecNormalPriority2 = new EligibleCouple("EC Case 2", "woman b", "Husband B", "EC Number 2", "Bherya", "Bherya SC", normalPriority());
        EligibleCouple ecNormalPriority3 = new EligibleCouple("EC Case 3", "Woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", normalPriority());
        EligibleCouple ecHighPriority1 = new EligibleCouple("EC Case 4", "Woman D", "Husband D", "EC Number 4", "Bherya", "Bherya SC", highPriority());
        EligibleCouple ecHighPriority2 = new EligibleCouple("EC Case 5", "WOMAN E", "Husband E", "EC Number 5", "Bherya", "Bherya SC", highPriority());
        EligibleCouple ecHighPriority3 = new EligibleCouple("EC Case 6", "Woman F", "Husband F", "EC Number 6", "Bherya", "Bherya SC", highPriority());

        EC expectedECNormalPriority1 = new EC("EC Case 1", "Woman A", "Husband A", "Bherya", "EC Number 1", "12345", false, false);
        EC expectedECNormalPriority2 = new EC("EC Case 2", "woman b", "Husband B", "Bherya", "EC Number 2", "", false, false);
        EC expectedECNormalPriority3 = new EC("EC Case 3", "Woman C", "Husband C", "Bherya", "EC Number 3", "", false, false);
        EC expectedECHighPriority1 = new EC("EC Case 4", "Woman D", "Husband D", "Bherya", "EC Number 4", "4444", true, false);
        EC expectedECHighPriority2 = new EC("EC Case 5", "WOMAN E", "Husband E", "Bherya", "EC Number 5", "", true, false);
        EC expectedECHighPriority3 = new EC("EC Case 6", "Woman F", "Husband F", "Bherya", "EC Number 6", "", true, false);

        when(allEligibleCouples.all()).thenReturn(asList(ecHighPriority3, ecNormalPriority2, ecHighPriority1, ecNormalPriority3, ecNormalPriority1, ecHighPriority2));
        when(allBeneficiaries.findMotherByECCaseId("EC Case 1")).thenReturn(new Mother("MOTHER Case 1", "EC Case 1", "12345", "2012-12-12"));
        when(allBeneficiaries.findMotherByECCaseId("EC Case 4")).thenReturn(new Mother("MOTHER Case 4", "EC Case 4", "4444", "2012-12-22"));

        ECs ecs = new Gson().fromJson(controller.get(), TypeToken.get(ECs.class).getType());

        assertEquals(asList(expectedECNormalPriority1, expectedECNormalPriority2, expectedECNormalPriority3), ecs.normalPriority());
        assertEquals(asList(expectedECHighPriority1, expectedECHighPriority2, expectedECHighPriority3), ecs.highPriority());
    }

    @Test
    public void shouldCacheAllECsUntilNewDataIsFetched() throws Exception {
        controller.get();
        verify(allEligibleCouples, times(1)).all();
        controller.get();
        verify(allEligibleCouples, times(1)).all();
        Event.ON_DATA_FETCHED.notifyListeners(fetched);
        controller.get();
        verify(allEligibleCouples, times(2)).all();
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

    private Map<String, String> normalPriority() {
        return mapOf("isHighPriority", "0");
    }

    private Map<String, String> highPriority() {
        return mapOf("isHighPriority", "1");
    }
}
