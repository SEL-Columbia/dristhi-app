package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.apache.commons.lang3.tuple.Pair;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.event.Event;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.util.Cache;
import org.ei.drishti.view.contract.ANC;
import org.ei.drishti.view.contract.ANCs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.util.EasyMap.create;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class ANCListViewControllerTest {
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private CommCareClientService commCareClientService;
    @Mock
    private Context context;

    private ANCListViewController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new ANCListViewController(context, allBeneficiaries, allEligibleCouples, new Cache<String>(), commCareClientService);
    }

    @Test
    public void shouldSortBothNormalAndHighANCsByName() throws Exception {
        when(allBeneficiaries.allANCsWithEC()).thenReturn(asList(
                Pair.of(new Mother("Case 3", "EC Case 3", "TC 3", "2032-03-03").withDetails(create("isHighRisk", "no").put("deliveryPlace", "Bherya DC").map()),
                        new EligibleCouple("EC Case 3", "woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", new HashMap<String, String>())),
                Pair.of(new Mother("Case 4", "EC Case 4", "TC 4", "2032-03-03").withDetails(create("isHighRisk", "yes").put("deliveryPlace", "Bherya DC").map()),
                        new EligibleCouple("EC Case 4", "Woman D", "Husband D", "EC Number 4", "Bherya", "Bherya SC", new HashMap<String, String>())),
                Pair.of(new Mother("Case 1", "EC Case 1", "TC 1", "2012-01-01").withDetails(create("isHighRisk", "no").put("deliveryPlace", "Bherya DC").map()),
                        new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", "Bherya SC", new HashMap<String, String>())),
                Pair.of(new Mother("Case 2", "EC Case 2", "TC 2", "2022-02-02").withDetails(create("isHighRisk", "yes").put("deliveryPlace", "Bherya DC").map()),
                        new EligibleCouple("EC Case 2", "woman B", "Husband B", "EC Number 2", "Bherya", "Bherya SC", new HashMap<String, String>()))));

        ANCs ancs = new Gson().fromJson(controller.get(), new TypeToken<ANCs>() {
        }.getType());

        assertEquals(asList(new ANC("Case 1", "TC 1", "Woman A", "Husband A", "Bherya", "EC Number 1", false), new ANC("Case 3", "TC 3", "woman C", "Husband C", "Bherya", "EC Number 3", false)), ancs.normalRisk());
        assertEquals(asList(new ANC("Case 2", "TC 2", "woman B", "Husband B", "Bherya", "EC Number 2", true), new ANC("Case 4", "TC 4", "Woman D", "Husband D", "Bherya", "EC Number 4", true)), ancs.highRisk());
    }

    @Test
    public void shouldCacheAllECsUntilNewDataIsFetched() throws Exception {
        controller.get();
        verify(allBeneficiaries, times(1)).allANCsWithEC();
        controller.get();
        verify(allBeneficiaries, times(1)).allANCsWithEC();
        Event.ON_DATA_FETCHED.notifyListeners(fetched);
        controller.get();
        verify(allBeneficiaries, times(2)).allANCsWithEC();
    }
}
