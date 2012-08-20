package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.view.contract.ANCDetail;
import org.ei.drishti.view.contract.FacilityDetails;
import org.ei.drishti.view.contract.LocationDetails;
import org.ei.drishti.view.contract.PregnancyDetails;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
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

    private String caseId = "1234-5678-1234";
    private ANCDetailController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        DateUtil.fakeIt(new LocalDate(2012, 5, 22));
        controller = new ANCDetailController(context, caseId, allEligibleCouples, allBeneficiaries, allTimelineEvents, commCareClientService);
    }

    @Test
    public void shouldGetANCDetailsAsJSON() {
        when(allBeneficiaries.findMother(caseId)).thenReturn(new Mother(caseId, "EC CASE 1", "TC 1", "2011-10-22").withExtraDetails(true, "District Hospital"));
        when(allEligibleCouples.findByCaseID("EC CASE 1")).thenReturn(new EligibleCouple("EC CASE 1", "Woman 1", "Husband 1", "EC Number 1", "IUD", "Village 1", "Subcenter 1", ""));

        TimelineEvent pregnancyEvent = TimelineEvent.forStartOfPregnancy(caseId, "2011-10-22");
        when(allTimelineEvents.forCase(caseId)).thenReturn(asList(pregnancyEvent));

        ANCDetail expectedDetail = new ANCDetail(caseId, "TC 1", "Woman 1",
                new LocationDetails("Village 1", "Subcenter 1"),
                new PregnancyDetails(true, "Anaemic", "7", "2012-07-28"),
                new FacilityDetails("District Hospital", "----", "Shiwani"))
                .addTimelineEvents(asList(new org.ei.drishti.view.contract.TimelineEvent(pregnancyEvent.type(), pregnancyEvent.title(), new String[]{pregnancyEvent.detail1(), pregnancyEvent.detail2()}, "6m 1m ago")));

        String actualJson = controller.get();
        ANCDetail actualDetail = new Gson().fromJson(actualJson, ANCDetail.class);

        assertEquals(expectedDetail, actualDetail);
    }
}
