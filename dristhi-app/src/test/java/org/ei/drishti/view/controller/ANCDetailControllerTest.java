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
import org.ei.drishti.view.contract.*;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.dto.AlertPriority.normal;
import static org.ei.drishti.dto.AlertPriority.urgent;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

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
        DateUtil.fakeIt(new LocalDate(2012, 5, 22));
        controller = new ANCDetailController(context, caseId, allEligibleCouples, allBeneficiaries, allAlerts, allTimelineEvents, commCareClientService);
    }

    @Test
    public void shouldGetANCDetailsAsJSON() {
        TimelineEvent pregnancyEvent = TimelineEvent.forStartOfPregnancy(caseId, "2011-10-22");
        Alert todo = new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open);
        Alert urgentTodo = new Alert("Case X", "Theresa", "bherya", "TT 1", "Thaayi 1", urgent, "2012-02-02", "2012-02-11", open);

        HashMap<String, String> details = new HashMap<String, String>();
        details.put("ashaName", "Shiwani");

        when(allBeneficiaries.findMother(caseId)).thenReturn(new Mother(caseId, "EC CASE 1", "TC 1", "2011-10-22").withExtraDetails(true, "District Hospital").withDetails(details));
        when(allEligibleCouples.findByCaseID("EC CASE 1")).thenReturn(new EligibleCouple("EC CASE 1", "Woman 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "Subcenter 1", new HashMap<String, String>()));
        when(allAlerts.fetchAllForCase(caseId)).thenReturn(asList(todo, urgentTodo));
        when(allTimelineEvents.forCase(caseId)).thenReturn(asList(pregnancyEvent));

        ANCDetail expectedDetail = new ANCDetail(caseId, "TC 1", "Woman 1",
                new LocationDetails("Village 1", "Subcenter 1"),
                new PregnancyDetails(true, "Anaemic", "7", "2012-07-28"),
                new FacilityDetails("District Hospital", "----", "Shiwani"))
                .addTimelineEvents(asList(new org.ei.drishti.view.contract.TimelineEvent(pregnancyEvent.type(), pregnancyEvent.title(), new String[]{pregnancyEvent.detail1(), pregnancyEvent.detail2()}, "6m 1m ago")))
                .addTodos(asList(new ProfileTodo(todo)))
                .addUrgentTodos(asList(new ProfileTodo(urgentTodo)));

        String actualJson = controller.get();
        ANCDetail actualDetail = new Gson().fromJson(actualJson, ANCDetail.class);

        assertEquals(expectedDetail, actualDetail);
    }
}
