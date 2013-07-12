package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.domain.*;
import org.ei.drishti.domain.Child;
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
import org.mockito.Mock;

import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.ei.drishti.dto.AlertStatus.normal;
import static org.ei.drishti.dto.AlertStatus.urgent;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ChildDetailControllerTest {
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
    private ChildDetailController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        DateUtil.fakeIt(new LocalDate(2012, 8, 1));
        controller = new ChildDetailController(context, caseId, allEligibleCouples, allBeneficiaries, allAlerts, allTimelineEvents);
    }

    @Test
    public void shouldGetChildDetailsAsJSON() throws Exception {
        TimelineEvent birthEvent = TimelineEvent.forChildBirthInChildProfile(caseId, "2011-10-21", null, null);
        TimelineEvent ancEvent = TimelineEvent.forMotherPNCVisit(caseId, "2", "2011-12-22", "bps 1", "bpd 1", "temp 1", "hb 1");
        TimelineEvent eventVeryCloseToCurrentDate = TimelineEvent.forMotherPNCVisit(caseId, "2", "2012-07-29", "bps 2", "bpd 2", "temp 2", "hb 2");
        ProfileTodo todo = new ProfileTodo(new Alert("Case X", "PNC", "PNC 1", normal, "2012-01-01", "2012-01-11"));
        ProfileTodo urgentTodo = new ProfileTodo(new Alert("Case X", "PNC", "TT 1", urgent, "2012-02-02", "2012-02-11"));

        HashMap<String, String> details = new HashMap<String, String>();
        details.put("ashaName", "Shiwani");
        details.put("dateOfDelivery", "2012-07-28");
        details.put("isHighRisk", "yes");
        details.put("highRiskReason", "Anaemia");

        when(allBeneficiaries.findChild(caseId)).thenReturn(new Child(caseId, "Mother-Case-Id", "TC 1", "2012-07-28", "male", details).withPhotoPath("photo path"));
        when(allBeneficiaries.findMother("Mother-Case-Id")).thenReturn(new Mother(caseId, "EC CASE 1", "TC 1", "2011-10-22").withDetails(details));
        when(allEligibleCouples.findByCaseID("EC CASE 1")).thenReturn(new EligibleCouple("EC CASE 1", "Woman 1", "Husband 1", "EC Number 1", "Village 1", "Subcenter 1", new HashMap<String, String>()));
        when(allAlerts.fetchAllActiveAlertsForCase(caseId)).thenReturn(asList(asList(todo), asList(urgentTodo)));
        when(allTimelineEvents.forCase(caseId)).thenReturn(asList(birthEvent, ancEvent, eventVeryCloseToCurrentDate));

        ChildDetail expectedDetail = new ChildDetail(caseId, "TC 1",
                new CoupleDetails("Woman 1", "Husband 1", "EC Number 1", false),
                new LocationDetails("Village 1", "Subcenter 1"),
                new BirthDetails("2012-07-28", "4 days", "male"), "photo path")
                .addTimelineEvents(asList(eventFor(eventVeryCloseToCurrentDate, "3d ago"), eventFor(ancEvent, "7m 1w ago"), eventFor(birthEvent, "9m 2w ago")))
                .addTodos(asList(todo))
                .addUrgentTodos(asList(urgentTodo))
                .addExtraDetails(details);

        String actualJson = controller.get();
        ChildDetail actualDetail = new Gson().fromJson(actualJson, ChildDetail.class);

        assertEquals(expectedDetail, actualDetail);
    }

    private org.ei.drishti.view.contract.TimelineEvent eventFor(TimelineEvent pregnancyEvent, String expectedRelativeTime) {
        return new org.ei.drishti.view.contract.TimelineEvent(pregnancyEvent.type(), pregnancyEvent.title(), new String[]{pregnancyEvent.detail1(), pregnancyEvent.detail2()}, expectedRelativeTime);
    }
}
