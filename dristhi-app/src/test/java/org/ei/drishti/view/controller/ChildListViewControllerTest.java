package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.event.Event;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.util.Cache;
import org.ei.drishti.view.contract.Children;
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
public class ChildListViewControllerTest {
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private AllSettings allSettings;
    @Mock
    private Context context;
    private ChildListViewController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new ChildListViewController(context, allBeneficiaries, allEligibleCouples, allSettings, new Cache<String>());
    }

    @Test
    public void shouldSortBothNormalAndHighANCsByName() throws Exception {
        when(allBeneficiaries.allChildren()).thenReturn(asList(
                new Child("Case 1", "Mother Case 1", "TC 1", "2032-01-01", "male", create("isHighRiskChild", "no").put("deliveryPlace", "Bherya DC").map()),
                new Child("Case 2", "Mother Case 2", "TC 2", "2032-02-02", "female", create("isHighRiskChild", "yes").put("deliveryPlace", "Bherya DC").map())));
        when(allEligibleCouples.findByCaseID("EC Case 1")).thenReturn(new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", "Bherya SC", new HashMap<String, String>()));
        when(allEligibleCouples.findByCaseID("EC Case 2")).thenReturn(new EligibleCouple("EC Case 2", "woman B", "Husband B", "EC Number 2", "Bherya", "Bherya SC", new HashMap<String, String>()));
        when(allBeneficiaries.findMother("Mother Case 1")).thenReturn(new Mother("Mother Case 1", "EC Case 1", "TC 1", "2031-12-12"));
        when(allBeneficiaries.findMother("Mother Case 2")).thenReturn(new Mother("Mother Case 2", "EC Case 2", "TC 2", "2031-11-12"));

        Children children = new Gson().fromJson(controller.get(), new TypeToken<Children>() {
        }.getType());

        assertEquals(asList(new org.ei.drishti.view.contract.Child("Case 1", "TC 1", "Woman A", "Husband A", "EC Number 1", "Bherya", false)), children.normalRisk());
        assertEquals(asList(new org.ei.drishti.view.contract.Child("Case 2", "TC 2", "woman B", "Husband B", "EC Number 2", "Bherya", true)), children.highRisk());
    }

    @Test
    public void shouldCacheAllECsUntilNewDataIsFetched() throws Exception {
        controller.get();
        verify(allBeneficiaries, times(1)).allChildren();
        controller.get();
        verify(allBeneficiaries, times(1)).allChildren();
        Event.ON_DATA_FETCHED.notifyListeners(fetched);
        controller.get();
        verify(allBeneficiaries, times(2)).allChildren();
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
