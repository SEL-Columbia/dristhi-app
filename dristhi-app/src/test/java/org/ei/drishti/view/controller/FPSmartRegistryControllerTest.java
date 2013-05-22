package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.service.AlertService;
import org.ei.drishti.util.Cache;
import org.ei.drishti.view.contract.AlertDTO;
import org.ei.drishti.view.contract.FPClient;
import org.ei.drishti.view.contract.Village;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.dto.AlertStatus.normal;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class FPSmartRegistryControllerTest {
    public static final List<String> EC_ALERTS = asList("OCP Refill",
            "Condom Refill",
            "DMPA Injectable Refill",
            "Female sterilization Followup 1",
            "Female sterilization Followup 2",
            "Female sterilization Followup 3",
            "Male sterilization Followup 1",
            "Male sterilization Followup 2",
            "IUD Followup 1",
            "IUD Followup 2",
            "FP Followup",
            "FP Referral Followup");
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private AlertService alertService;

    private FPSmartRegistryController controller;
    private Map<String, String> emptyDetails;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        emptyDetails = Collections.emptyMap();
        controller = new FPSmartRegistryController(allEligibleCouples, allBeneficiaries, alertService, new Cache<String>());
    }

    @Test
    public void shouldSortECsByName() throws Exception {
        EligibleCouple ec2 = new EligibleCouple("EC Case 2", "Woman B", "Husband B", "EC Number 2", "kavalu_hosur", "Bherya SC", emptyDetails);
        EligibleCouple ec3 = new EligibleCouple("EC Case 3", "Woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", emptyDetails);
        EligibleCouple ec1 = new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", null, emptyDetails);
        when(allEligibleCouples.all()).thenReturn(asList(ec2, ec3, ec1));
        FPClient expectedClient1 = createFPClient("EC Case 1", "Woman A", "Husband A", "Bherya", "EC Number 1");
        FPClient expectedClient2 = createFPClient("EC Case 2", "Woman B", "Husband B", "kavalu_hosur", "EC Number 2");
        FPClient expectedClient3 = createFPClient("EC Case 3", "Woman C", "Husband C", "Bherya", "EC Number 3");

        String clients = controller.get();

        List<FPClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<FPClient>>() {
        }.getType());
        assertEquals(asList(expectedClient1, expectedClient2, expectedClient3), actualClients);
    }

    private FPClient createFPClient(String entityId, String name, String husbandName, String village, String ecNumber) {
        return new FPClient(entityId, name, husbandName, village, ecNumber).withThayi("").withPhotoPath("../../img/woman-placeholder.png").withAlerts(Collections.<AlertDTO>emptyList());
    }

    @Test
    public void shouldMapECToFPClient() throws Exception {
        Map<String, String> details = create("wifeAge", "22")
                .put("currentMethod", "condom")
                .put("familyPlanningMethodChangeDate", "2013-01-02")
                .put("sideEffects", "sideEffects 1")
                .put("numberOfPregnancies", "2")
                .put("parity", "2")
                .put("numberOfLivingChildren", "1")
                .put("numberOfStillBirths", "1")
                .put("numberOfAbortions", "0")
                .put("isHighPriority", Boolean.toString(false))
                .put("isYoungestChildUnderTwo", "yes")
                .put("youngestChildAge", "3")
                .put("complicationDate", "2011-05-05")
                .put("caste", "sc")
                .put("economicStatus", "bpl")
                .put("fpFollowupDate", "2013-03-04")
                .put("iudPlace", "iudPlace")
                .put("iudPerson", "iudPerson")
                .put("numberOfCondomsSupplied", "numberOfCondomsSupplied")
                .put("numberOfCentchromanPillsDelivered", "numberOfCentchromanPillsDelivered")
                .put("numberOfOCPDelivered", "numberOfOCPDelivered")
                .put("condomSideEffect", "condom side effect")
                .put("iudSidEffect", "iud side effect")
                .put("ocpSideEffect", "ocp side effect")
                .put("sterilizationSideEffect", "sterilization side effect")
                .put("injectableSideEffect", "injectable side effect")
                .put("otherSideEffect", "other side effect")
                .map();
        EligibleCouple ec = new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", "Bherya SC", details)
                .withPhotoPath("new photo path");
        Mother mother = new Mother("MOTHER Case 1", "EC Case 1", "12345", "2012-12-12");
        when(allEligibleCouples.all()).thenReturn(asList(ec));
        when(allBeneficiaries.findMotherByECCaseId("EC Case 1")).thenReturn(mother);
        FPClient expectedFPClient = new FPClient("EC Case 1", "Woman A", "Husband A", "Bherya", "EC Number 1")
                .withThayi("12345")
                .withAge("22")
                .withFPMethod("condom")
                .withFamilyPlanningMethodChangeDate("2013-01-02")
                .withSideEffects("sideEffects 1")
                .withComplicationDate("2011-05-05")
                .withIUDPlace("iudPlace")
                .withIUDPerson("iudPerson")
                .withNumberOfCondomsSupplied("numberOfCondomsSupplied")
                .withNumberOfCentchromanPillsDelivered("numberOfCentchromanPillsDelivered")
                .withNumberOfOCPDelivered("numberOfOCPDelivered").withFPMethodFollowupDate("2013-03-04")
                .withCaste("sc")
                .withEconomicStatus("bpl")
                .withNumberOfPregnancies("2")
                .withParity("2")
                .withNumberOfLivingChildren("1")
                .withNumberOfStillBirths("1")
                .withNumberOfAbortions("0")
                .withIsYoungestChildUnderTwo(true)
                .withYoungestChildAge("3")
                .withDaysDue(null)
                .withDueMessage(null)
                .withIsHighPriority(false)
                .withPhotoPath("new photo path")
                .withCondomSideEffect("condom side effect")
                .withIUDSidEffect("iud side effect")
                .withOCPSideEffect("ocp side effect")
                .withSterilizationSideEffect("sterilization side effect")
                .withInjectableSideEffect("injectable side effect")
                .withOtherSideEffect("other side effect")
                .withAlerts(Collections.<AlertDTO>emptyList());

        String clients = controller.get();

        List<FPClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<FPClient>>() {
        }.getType());
        assertEquals(asList(expectedFPClient), actualClients);
    }

    @Test
    public void shouldCreateFPClientsWithOCPRefillAlert() throws Exception {
        EligibleCouple ec = new EligibleCouple("entity id 1", "Woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", emptyDetails);
        Alert ocpRefillAlert = new Alert("entity id 1", "OCP Refill", "OCP Refill", normal, "2013-01-01", "2013-02-01");
        when(allEligibleCouples.all()).thenReturn(asList(ec));
        when(alertService.findByEntityIdAndAlertNames("entity id 1", EC_ALERTS)).thenReturn(asList(ocpRefillAlert));

        String clients = controller.get();

        List<FPClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<FPClient>>() {
        }.getType());
        verify(alertService).findByEntityIdAndAlertNames("entity id 1", EC_ALERTS);
        AlertDTO expectedAlertDto = new AlertDTO("OCP Refill", "normal", "2013-01-01");
        FPClient expectedEC = createFPClient("entity id 1", "Woman C", "Husband C", "Bherya", "EC Number 3").withAlerts(asList(expectedAlertDto));
        assertEquals(asList(expectedEC), actualClients);
    }

    @Test
    public void shouldLoadVillages() throws Exception {
        List<Village> expectedVillages = asList(new Village("village1"), new Village("village2"));
        when(allEligibleCouples.villages()).thenReturn(asList("village1", "village2"));

        String villages = controller.villages();
        List<Village> actualVillages = new Gson().fromJson(villages, new TypeToken<List<Village>>() {
        }.getType());
        assertEquals(actualVillages, expectedVillages);
    }

    private Map<String, String> normalPriority() {
        return mapOf("isHighPriority", "no");
    }
}
