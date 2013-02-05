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
import org.ei.drishti.util.Cache;
import org.ei.drishti.view.contract.Beneficiaries;
import org.ei.drishti.view.contract.PNC;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.util.EasyMap.create;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class PNCListViewControllerTest {
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private AllSettings allSettings;
    @Mock
    private Context context;
    private PNCListViewController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new PNCListViewController(context, allBeneficiaries, allEligibleCouples, allSettings, new Cache<String>());
    }

    @Test
    public void shouldSortBothNormalAndHighANCsByName() throws Exception {
        when(allBeneficiaries.allPNCs()).thenReturn(asList(
                new Mother("Case 3", "EC Case 3", "TC 3", "2032-03-03").withDetails(create("isHighRisk", "no").put("deliveryPlace", "Bherya DC").map()),
                new Mother("Case 4", "EC Case 4", "TC 4", "2032-03-03").withDetails(create("isHighRisk", "yes").put("deliveryPlace", "Bherya DC").map()),
                new Mother("Case 1", "EC Case 1", "TC 1", "2012-01-01").withDetails(create("isHighRisk", "no").put("deliveryPlace", "Bherya DC").map()),
                new Mother("Case 2", "EC Case 2", "TC 2", "2022-02-02").withDetails(create("isHighRisk", "yes").put("deliveryPlace", "Bherya DC").map())));
        when(allEligibleCouples.findByCaseID("EC Case 1")).thenReturn(new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", "Bherya SC", new HashMap<String, String>()));
        when(allEligibleCouples.findByCaseID("EC Case 2")).thenReturn(new EligibleCouple("EC Case 2", "woman B", "Husband B", "EC Number 2", "Bherya", "Bherya SC", new HashMap<String, String>()));
        when(allEligibleCouples.findByCaseID("EC Case 3")).thenReturn(new EligibleCouple("EC Case 3", "woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", new HashMap<String, String>()));
        when(allEligibleCouples.findByCaseID("EC Case 4")).thenReturn(new EligibleCouple("EC Case 4", "Woman D", "Husband D", "EC Number 4", "Bherya", "Bherya SC", new HashMap<String, String>()));

        Beneficiaries<PNC> pnCs = new Gson().fromJson(controller.get(), new TypeToken<Beneficiaries<PNC>>() {
        }.getType());

        assertEquals(asList(new PNC("Case 1", "TC 1", "Woman A", "Husband A", "EC Number 1", "Bherya", false), new PNC("Case 3", "TC 3", "woman C", "Husband C", "EC Number 3", "Bherya", false)), pnCs.normal());
        assertEquals(asList(new PNC("Case 2", "TC 2", "woman B", "Husband B", "EC Number 2", "Bherya", true), new PNC("Case 4", "TC 4", "Woman D", "Husband D", "EC Number 4", "Bherya", true)), pnCs.priority());
    }

    @Test
    public void shouldCacheAllECsUntilNewDataIsFetched() throws Exception {
        controller.get();
        verify(allBeneficiaries, times(1)).allPNCs();
        controller.get();
        verify(allBeneficiaries, times(1)).allPNCs();
        Event.ON_DATA_FETCHED.notifyListeners(fetched);
        controller.get();
        verify(allBeneficiaries, times(2)).allPNCs();
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
