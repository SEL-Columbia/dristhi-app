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
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.view.contract.*;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.ei.drishti.dto.AlertStatus.normal;
import static org.ei.drishti.dto.AlertStatus.urgent;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class PNCDetailControllerTest {
    @Mock
    Context context;
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private AllTimelineEvents allTimelineEvents;
    @Mock
    private AllAlerts allAlerts;

    private String caseId = "1234-5678-1234";
    private PNCDetailController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        DateUtil.fakeIt(new LocalDate(2012, 8, 1));
        controller = new PNCDetailController(context, caseId, allEligibleCouples, allBeneficiaries, allAlerts, allTimelineEvents);
    }

    @Test
    public void shouldGetPNCDetailsAsJSON() throws Exception {
        TimelineEvent pregnancyEvent = TimelineEvent.forStartOfPregnancy(caseId, "2011-10-21");
        TimelineEvent ancEvent = TimelineEvent.forANCCareProvided(caseId, "2", "2011-12-22", new HashMap<String, String>());
        TimelineEvent eventVeryCloseToCurrentDate = TimelineEvent.forANCCareProvided(caseId, "2", "2012-07-29", new HashMap<String, String>());
        ProfileTodo todo = new ProfileTodo(new Alert("Case X", "PNC", "PNC 1", normal, "2012-01-01", "2012-01-11"));
        ProfileTodo urgentTodo = new ProfileTodo(new Alert("Case X", "PNC", "TT 1", urgent, "2012-02-02", "2012-02-11"));

        HashMap<String, String> details = new HashMap<String, String>();
        details.put("ashaName", "Shiwani");
        details.put("isHighRisk", "yes");
        details.put("highRiskReason", "Anaemia");

        when(allBeneficiaries.findMotherWithOpenStatus(caseId)).thenReturn(new Mother(caseId, "EC CASE 1", "TC 1", "2012-07-28").withDetails(details));
        HashMap<String, String> ecDetails = new HashMap<String, String>();
        ecDetails.put("caste", "c_others");
        ecDetails.put("economicStatus", "apl");
        when(allEligibleCouples.findByCaseID("EC CASE 1")).thenReturn(new EligibleCouple("EC CASE 1", "Woman 1", "Husband 1", "EC Number 1", "Village 1", "Subcenter 1", ecDetails).withPhotoPath("photo path"));
        when(allAlerts.fetchAllActiveAlertsForCase(caseId)).thenReturn(asList(asList(todo), asList(urgentTodo)));
        when(allTimelineEvents.forCase(caseId)).thenReturn(asList(pregnancyEvent, ancEvent, eventVeryCloseToCurrentDate));

        PNCDetail expectedDetail = new PNCDetail(caseId, "TC 1",
                new CoupleDetails("Woman 1", "Husband 1", "EC Number 1", false).withCaste("c_others").withEconomicStatus("apl").withPhotoPath("photo path"),
                new LocationDetails("Village 1", "Subcenter 1"),
                new PregnancyOutcomeDetails("2012-07-28", 4))
                .addTimelineEvents(asList(eventFor(eventVeryCloseToCurrentDate, "3d ago"), eventFor(ancEvent, "7m 1w ago"), eventFor(pregnancyEvent, "9m 2w ago")))
                .addTodos(asList(todo))
                .addUrgentTodos(asList(urgentTodo))
                .addExtraDetails(details);

        String actualJson = controller.get();

        PNCDetail actualDetail = new Gson().fromJson(actualJson, PNCDetail.class);
        assertEquals(expectedDetail, actualDetail);
    }

    private org.ei.drishti.view.contract.TimelineEvent eventFor(TimelineEvent pregnancyEvent, String expectedRelativeTime) {
        return new org.ei.drishti.view.contract.TimelineEvent(pregnancyEvent.type(), pregnancyEvent.title(), new String[]{pregnancyEvent.detail1(), pregnancyEvent.detail2()}, expectedRelativeTime);
    }
}
