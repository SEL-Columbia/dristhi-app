package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.util.EasyMap;
import org.ei.drishti.view.contract.*;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.dto.AlertPriority.normal;
import static org.ei.drishti.dto.AlertPriority.urgent;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.ei.drishti.util.EasyMap.mapOf;

@RunWith(RobolectricTestRunner.class)
public class ANCDetailControllerTest {
    @Mock
    Context context;
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private AllTimelineEvents allTimelineEvents;
    @Mock
    private CommCareClientService commCareClientService;
    @Mock
    private AllAlerts allAlerts;

    private String caseId = "1234-5678-1234";
    private ANCDetailController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        DateUtil.fakeIt(new LocalDate(2012, 8, 1));
        controller = new ANCDetailController(context, caseId, allEligibleCouples, allBeneficiaries, allAlerts, allTimelineEvents, commCareClientService);
    }

    @Test
    public void shouldGetANCDetailsAsJSON() {
        TimelineEvent pregnancyEvent = TimelineEvent.forStartOfPregnancy(caseId, "2011-10-21");
        TimelineEvent ancEvent = TimelineEvent.forANCCareProvided(caseId, "2", "2011-12-22", new HashMap<String, String>());
        TimelineEvent eventVeryCloseToCurrentDate = TimelineEvent.forANCCareProvided(caseId, "2", "2012-07-29", new HashMap<String, String>());
        ProfileTodo todo = new ProfileTodo(new Alert("Case X", "Theresa", "Husband 1", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));
        ProfileTodo urgentTodo = new ProfileTodo(new Alert("Case X", "Theresa", "Husband 1", "bherya", "TT 1", "Thaayi 1", urgent, "2012-02-02", "2012-02-11", open));

        HashMap<String, String> details = new HashMap<String, String>();
        details.put("ashaName", "Shiwani");

        when(allBeneficiaries.findMother(caseId)).thenReturn(new Mother(caseId, "EC CASE 1", "TC 1", "2011-10-22").withDetails(details));
        Map<String,String> ecDetails = mapOf("caste", "st");
        ecDetails.put("economicStatus", "bpl");
        when(allEligibleCouples.findByCaseID("EC CASE 1")).thenReturn(new EligibleCouple("EC CASE 1", "Woman 1", "Husband 1", "EC Number 1", "Village 1", "Subcenter 1", ecDetails));
        when(allAlerts.fetchAllActiveAlertsForCase(caseId)).thenReturn(asList(asList(todo), asList(urgentTodo)));
        when(allTimelineEvents.forCase(caseId)).thenReturn(asList(pregnancyEvent, ancEvent, eventVeryCloseToCurrentDate));

        ANCDetail expectedDetail = new ANCDetail(caseId, "TC 1",
                new CoupleDetails("Woman 1", "Husband 1", "EC Number 1", false).withCaste("st").withEconomicStatus("bpl"),
                new LocationDetails("Village 1", "Subcenter 1"),
                new PregnancyDetails("9", "2012-07-28", 4))
                .addTimelineEvents(asList(eventFor(eventVeryCloseToCurrentDate, "3d ago"), eventFor(ancEvent, "7m 1w ago"), eventFor(pregnancyEvent, "9m 2w ago")))
                .addTodos(asList(todo))
                .addUrgentTodos(asList(urgentTodo))
                .addExtraDetails(details);

        String actualJson = controller.get();
        ANCDetail actualDetail = new Gson().fromJson(actualJson, ANCDetail.class);

        assertEquals(expectedDetail, actualDetail);
    }

    private org.ei.drishti.view.contract.TimelineEvent eventFor(TimelineEvent pregnancyEvent, String expectedRelativeTime) {
        return new org.ei.drishti.view.contract.TimelineEvent(pregnancyEvent.type(), pregnancyEvent.title(), new String[]{pregnancyEvent.detail1(), pregnancyEvent.detail2()}, expectedRelativeTime);
    }
}
