package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.view.contract.*;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.dto.AlertPriority.normal;
import static org.ei.drishti.dto.AlertPriority.urgent;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class EligibleCoupleDetailControllerTest {
    @Mock
    Context context;
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllTimelineEvents allTimelineEvents;
    @Mock
    private CommCareClientService commCareClientService;
    @Mock
    private AllAlerts allAlerts;

    private String caseId = "1234-5678-1234";
    private EligibleCoupleDetailController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        DateUtil.fakeIt(new LocalDate(2012, 5, 22));
        controller = new EligibleCoupleDetailController(context, caseId, allEligibleCouples, allAlerts, allTimelineEvents, commCareClientService);
    }

    @Test
    public void shouldGetANCDetailsAsJSON() {
        TimelineEvent pregnancyEvent = TimelineEvent.forStartOfPregnancyForEC(caseId, "TC 1", "2011-10-22");
        TimelineEvent ancEvent = TimelineEvent.forChangeOfFPMethod(caseId, "condom", "iud", "2011-10-22");
        TimelineEvent eventVeryCloseToCurrentDate = TimelineEvent.forChangeOfFPMethod(caseId, "iud", "condom", "2012-05-19");
        ProfileTodo todo = new ProfileTodo(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));
        ProfileTodo urgentTodo = new ProfileTodo(new Alert("Case X", "Theresa", "bherya", "TT 1", "Thaayi 1", urgent, "2012-02-02", "2012-02-11", open));

        HashMap<String, String> details = new HashMap<String, String>();
        details.put("ashaName", "Shiwani");
        details.put("isHighPriority", "1");

        when(allEligibleCouples.findByCaseID(caseId)).thenReturn(new EligibleCouple("EC CASE 1", "Woman 1", "Husband 1", "EC Number 1", "Village 1", "Subcenter 1", details));
        when(allAlerts.fetchAllActiveAlertsForCase(caseId)).thenReturn(asList(asList(todo), asList(urgentTodo)));
        when(allTimelineEvents.forCase(caseId)).thenReturn(asList(pregnancyEvent, ancEvent, eventVeryCloseToCurrentDate));

        ECDetail expectedDetail = new ECDetail(caseId, "Village 1", "Subcenter 1", "EC Number 1", true, null, new ArrayList<Child>(), new CoupleDetails("Woman 1", "Husband 1"), details)
            .addTodos(asList(todo))
            .addUrgentTodos(asList(urgentTodo))
            .addTimelineEvents(asList(eventFor(eventVeryCloseToCurrentDate, "3d ago"), eventFor(ancEvent, "6m 1m ago"), eventFor(pregnancyEvent, "6m 1m ago")));

        String actualJson = controller.get();
        ECDetail actualDetail = new Gson().fromJson(actualJson, ECDetail.class);

        assertEquals(expectedDetail, actualDetail);
    }

    private org.ei.drishti.view.contract.TimelineEvent eventFor(TimelineEvent pregnancyEvent, String expectedRelativeTime) {
        return new org.ei.drishti.view.contract.TimelineEvent(pregnancyEvent.type(), pregnancyEvent.title(), new String[]{pregnancyEvent.detail1(), pregnancyEvent.detail2()}, expectedRelativeTime);
    }
}