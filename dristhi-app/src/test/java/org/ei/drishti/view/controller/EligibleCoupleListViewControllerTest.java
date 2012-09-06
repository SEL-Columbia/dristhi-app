package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.view.contract.EC;
import org.ei.drishti.view.contract.ECs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class EligibleCoupleListViewControllerTest {
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private Context context;
    @Mock
    private CommCareClientService commCareClientService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldSortECsByPriorityAndThenByName() throws Exception {
        EligibleCouple ecNormalPriority1 = new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", "Bherya SC", normalPriority());
        EligibleCouple ecNormalPriority2 = new EligibleCouple("EC Case 2", "woman b", "Husband B", "EC Number 2", "Bherya", "Bherya SC", normalPriority());
        EligibleCouple ecNormalPriority3 = new EligibleCouple("EC Case 3", "Woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", normalPriority());
        EligibleCouple ecHighPriority1 = new EligibleCouple("EC Case 4", "Woman D", "Husband D", "EC Number 4", "Bherya", "Bherya SC", highPriority());
        EligibleCouple ecHighPriority2 = new EligibleCouple("EC Case 5", "WOMAN E", "Husband E", "EC Number 5", "Bherya", "Bherya SC", highPriority());
        EligibleCouple ecHighPriority3 = new EligibleCouple("EC Case 6", "Woman F", "Husband F", "EC Number 6", "Bherya", "Bherya SC", highPriority());

        EC expectedECNormalPriority1 = new EC("EC Case 1", "Woman A", "Husband A", "Bherya", "EC Number 1", false, false);
        EC expectedECNormalPriority2 = new EC("EC Case 2", "woman b", "Husband B", "Bherya", "EC Number 2", false, false);
        EC expectedECNormalPriority3 = new EC("EC Case 3", "Woman C", "Husband C", "Bherya", "EC Number 3", false, false);
        EC expectedECHighPriority1 = new EC("EC Case 4", "Woman D", "Husband D", "Bherya", "EC Number 4", true, false);
        EC expectedECHighPriority2 = new EC("EC Case 5", "WOMAN E", "Husband E", "Bherya", "EC Number 5", true, false);
        EC expectedECHighPriority3 = new EC("EC Case 6", "Woman F", "Husband F", "Bherya", "EC Number 6", true, false);

        when(allEligibleCouples.all()).thenReturn(asList(ecHighPriority3, ecNormalPriority2, ecHighPriority1, ecNormalPriority3, ecNormalPriority1, ecHighPriority2));

        EligibleCoupleListViewController controller = new EligibleCoupleListViewController(allEligibleCouples, context, commCareClientService);
        ECs ecs = new Gson().fromJson(controller.get(), new TypeToken<ECs>() { }.getType());

        assertEquals(asList(expectedECNormalPriority1, expectedECNormalPriority2, expectedECNormalPriority3), ecs.normalPriority());
        assertEquals(asList(expectedECHighPriority1, expectedECHighPriority2, expectedECHighPriority3), ecs.highPriority());
    }

    private Map<String, String> normalPriority() {
        return mapOf("isHighPriority", "0");
    }

    private Map<String, String> highPriority() {
        return mapOf("isHighPriority", "1");
    }
}
