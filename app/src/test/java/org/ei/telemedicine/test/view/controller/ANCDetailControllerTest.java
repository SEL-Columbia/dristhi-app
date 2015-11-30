package org.ei.telemedicine.test.view.controller;

import android.content.Context;
import com.google.gson.Gson;

import org.ei.telemedicine.view.controller.ANCDetailController;
import org.mockito.runners.MockitoJUnitRunner;
import org.ei.telemedicine.domain.EligibleCouple;
import org.ei.telemedicine.domain.Mother;
import org.ei.telemedicine.domain.TimelineEvent;
import org.ei.telemedicine.repository.AllBeneficiaries;
import org.ei.telemedicine.repository.AllEligibleCouples;
import org.ei.telemedicine.repository.AllTimelineEvents;
import org.ei.telemedicine.util.DateUtil;
import org.ei.telemedicine.view.contract.ANCDetail;
import org.ei.telemedicine.view.contract.CoupleDetails;
import org.ei.telemedicine.view.contract.LocationDetails;
import org.ei.telemedicine.view.contract.PregnancyDetails;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.ei.telemedicine.util.EasyMap.mapOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ANCDetailControllerTest {
    @Mock
    Context context;
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private AllTimelineEvents allTimelineEvents;

    private String caseId = "1234-5678-1234";
    private ANCDetailController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        DateUtil.fakeIt(new LocalDate(2012, 8, 1));
        controller = new ANCDetailController(context, caseId, allEligibleCouples, allBeneficiaries, allTimelineEvents);
    }

    @Test
    public void shouldGetANCDetailsAsJSON() {
        TimelineEvent pregnancyEvent = TimelineEvent.forStartOfPregnancy(caseId, "2011-10-21", "2011-10-21");
        TimelineEvent ancEvent = TimelineEvent.forANCCareProvided(caseId, "2", "2011-12-22", new HashMap<String, String>());
        TimelineEvent eventVeryCloseToCurrentDate = TimelineEvent.forANCCareProvided(caseId, "2", "2012-07-29", new HashMap<String, String>());

        HashMap<String, String> details = new HashMap<String, String>();
        details.put("ashaName", "Shiwani");

        when(allBeneficiaries.findMotherWithOpenStatus(caseId)).thenReturn(new Mother(caseId, "EC CASE 1", "TC 1", "2011-10-22").withDetails(details));
        Map<String, String> ecDetails = mapOf("caste", "st");
        ecDetails.put("economicStatus", "bpl");
        when(allEligibleCouples.findByCaseID("EC CASE 1")).thenReturn(new EligibleCouple("EC CASE 1", "Woman 1", "Husband 1", "EC Number 1", "Village 1", "Subcenter 1", ecDetails).withPhotoPath("photo path"));
        when(allTimelineEvents.forCase(caseId)).thenReturn(asList(pregnancyEvent, ancEvent, eventVeryCloseToCurrentDate));

        ANCDetail expectedDetail = new ANCDetail(caseId, "TC 1",
                new CoupleDetails("Woman 1", "Husband 1", "EC Number 1", false)
                        .withCaste("st")
                        .withEconomicStatus("bpl")
                        .withPhotoPath("photo path"),
                new LocationDetails("Village 1", "Subcenter 1"),
                new PregnancyDetails("9", "2012-07-28", 4))
                .addTimelineEvents(asList(eventFor(eventVeryCloseToCurrentDate, "29-07-2012"), eventFor(ancEvent, "22-12-2011"), eventFor(pregnancyEvent, "21-10-2011")))
                .addExtraDetails(details);

        String actualJson = controller.get();
        ANCDetail actualDetail = new Gson().fromJson(actualJson, ANCDetail.class);

        assertEquals(expectedDetail, actualDetail);
    }

    private org.ei.telemedicine.view.contract.TimelineEvent eventFor(TimelineEvent pregnancyEvent, String expectedRelativeTime) {
        return new org.ei.telemedicine.view.contract.TimelineEvent(pregnancyEvent.type(), pregnancyEvent.title(), new String[]{pregnancyEvent.detail1(), pregnancyEvent.detail2()}, expectedRelativeTime);
    }
}
