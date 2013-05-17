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
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.dto.AlertPriority.normal;
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

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new FPSmartRegistryController(allEligibleCouples, allBeneficiaries, alertService, new Cache<String>());
    }

    @Test
    public void shouldSortECsByPriorityAndThenByName() throws Exception {
        EligibleCouple ecNormalPriority1 = new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", "Bherya SC",
                withDetails("22", "condom", "sideEffects 1", "2011-05-05", "2", "2", "1", "1", "0", false, "2013-01-02", "yes", "3", "sc", "bpl", "2013-03-04", null, null, null, null, null)).withPhotoPath("new photo path");
        EligibleCouple ecNormalPriority2 = new EligibleCouple("EC Case 2", "Woman B", "Husband B", "EC Number 2", "kavalu_hosur", "Bherya SC",
                withDetails("23", "iud", "sideEffects 2", "2011-05-07", "4", "1", "5", "0", "9", false, "2013-01-01", "no", "", "st", "apl", "2013-03-02", null, null, null, null, null));
        EligibleCouple ecNormalPriority3 = new EligibleCouple("EC Case 3", "Woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", normalPriority());
        EligibleCouple ecHighPriority1 = new EligibleCouple("EC Case 4", "Woman D", "Husband D", "EC Number 4", "Bherya", "Bherya SC", highPriority());
        EligibleCouple ecHighPriority2 = new EligibleCouple("EC Case 5", "Woman E", "Husband E", "EC Number 5", "kavalu_hosur", "Bherya SC", highPriority());
        EligibleCouple ecHighPriority3 = new EligibleCouple("EC Case 6", "Woman F", "Husband F", "EC Number 6", "Bherya", "Bherya SC", highPriority());
        Mother motherForNormalPriorityEC1 = new Mother("MOTHER Case 1", "EC Case 1", "12345", "2012-12-12");
        Mother motherForHighPriorityEC1 = new Mother("MOTHER Case 4", "EC Case 4", "4444", "2012-12-22");
        when(allEligibleCouples.all()).thenReturn(asList(ecHighPriority3, ecNormalPriority2, ecHighPriority1, ecNormalPriority3, ecNormalPriority1, ecHighPriority2));
        when(allBeneficiaries.findMotherByECCaseId("EC Case 1")).thenReturn(motherForNormalPriorityEC1);
        when(allBeneficiaries.findMotherByECCaseId("EC Case 4")).thenReturn(motherForHighPriorityEC1);
        FPClient expectedNormalPriorityClient1 = new FPClient("EC Case 1", "EC Number 1", "12345", "Woman A", "Husband A", "22", "Bherya", "condom", "2013-01-02", "sideEffects 1", "2011-05-05", null, null, null, null, null, "2013-03-04", "sc", "bpl", "2", "2", "1", "1", "0", true, "3", null, null, false, "new photo path", Collections.<AlertDTO>emptyList());
        FPClient expectedNormalPriorityClient2 = new FPClient("EC Case 2", "EC Number 2", "", "Woman B", "Husband B", "23", "kavalu_hosur", "iud", "2013-01-01", "sideEffects 2", "2011-05-07", null, null, null, null, null, "2013-03-02", "st", "apl", "4", "1", "5", "0", "9", false, "", null, null, false, "../../img/woman-placeholder.png", Collections.<AlertDTO>emptyList());
        FPClient expectedNormalPriorityClient3 = new FPClient("EC Case 3", "EC Number 3", "", "Woman C", "Husband C", null, "Bherya", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null, null, false, "../../img/woman-placeholder.png", Collections.<AlertDTO>emptyList());
        FPClient expectedHighPriorityClient1 = new FPClient("EC Case 4", "EC Number 4", "4444", "Woman D", "Husband D", null, "Bherya", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null, null, true, "../../img/woman-placeholder.png", Collections.<AlertDTO>emptyList());
        FPClient expectedHighPriorityClient2 = new FPClient("EC Case 5", "EC Number 5", "", "Woman E", "Husband E", null, "kavalu_hosur", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null, null, true, "../../img/woman-placeholder.png", Collections.<AlertDTO>emptyList());
        FPClient expectedHighPriorityClient3 = new FPClient("EC Case 6", "EC Number 6", "", "Woman F", "Husband F", null, "Bherya", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null, null, true, "../../img/woman-placeholder.png", Collections.<AlertDTO>emptyList());

        String clients = controller.get();

        List<FPClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<FPClient>>() {
        }.getType());
        assertEquals(asList(
                expectedNormalPriorityClient1,
                expectedNormalPriorityClient2,
                expectedNormalPriorityClient3,
                expectedHighPriorityClient1,
                expectedHighPriorityClient2,
                expectedHighPriorityClient3
        ),
                actualClients);
    }

    @Test
    public void shouldCreateFPClientByMappingAllRequiredFieldsFromEC() throws Exception {
        EligibleCouple ec = new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", "Bherya SC",
                withDetails("22", "condom", "sideEffects 1", "2011-05-05", "2", "2", "1", "1", "0", false, "2013-01-02", "yes",
                        "3", "sc", "bpl", "2013-03-04", "iudPlace", "iudPerson", "numberOfCondomsSupplied", "numberOfCentchromanPillsDelivered", "numberOfOCPDelivered"))
                .withPhotoPath("new photo path");
        Mother mother = new Mother("MOTHER Case 1", "EC Case 1", "12345", "2012-12-12");
        when(allEligibleCouples.all()).thenReturn(asList(ec));
        when(allBeneficiaries.findMotherByECCaseId("EC Case 1")).thenReturn(mother);
        FPClient expectedFPClient = new FPClient("EC Case 1", "EC Number 1", "12345", "Woman A", "Husband A", "22", "Bherya", "condom",
                "2013-01-02", "sideEffects 1", "2011-05-05", "iudPlace", "iudPerson", "numberOfCondomsSupplied", "numberOfCentchromanPillsDelivered", "numberOfOCPDelivered", "2013-03-04", "sc", "bpl", "2", "2", "1", "1", "0", true, "3", null, null, false, "new photo path",
                Collections.<AlertDTO>emptyList()
        );

        String clients = controller.get();

        List<FPClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<FPClient>>() {
        }.getType());
        assertEquals(asList(expectedFPClient), actualClients);

    }

    @Test
    public void shouldCreateFPClientsWithOCPRefillAlert() throws Exception {
        EligibleCouple ec = new EligibleCouple("entity id 1", "Woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", normalPriority());
        Alert ocpRefillAlert = new Alert("entity id 1", "woman 1", "husband 1", "village 1", "OCP Refill", "", normal, "2013-01-01", "2013-02-01", open);
        when(allEligibleCouples.all()).thenReturn(asList(ec));
        when(alertService.findByECIdAndAlertNames("entity id 1", EC_ALERTS)).thenReturn(asList(ocpRefillAlert));

        String clients = controller.get();

        List<FPClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<FPClient>>() {
        }.getType());
        verify(alertService).findByECIdAndAlertNames("entity id 1", EC_ALERTS);
        AlertDTO expectedAlertDto = new AlertDTO("OCP Refill", "normal", "2013-01-01");
        FPClient expectedEC = new FPClient("entity id 1", "EC Number 3", "", "Woman C", "Husband C", null,
                "Bherya", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null, null, false, "../../img/woman-placeholder.png",
                asList(expectedAlertDto));
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

    private Map<String, String> withDetails(String wifeAge, String currentMethod, String sideEffects, String complicationDate, String numberOfPregnancies,
                                            String parity, String numberOfLivingChildren, String numberOfStillBirths, String numberOfAbortions,
                                            boolean isHighPriority, String familyPlanningMethodChangeDate, String isYoungestChildUnderTwo,
                                            String youngestChildAge, String caste, String economicStatus, String fpFollowupDate,
                                            String iudPlace, String iudPerson, String numberOfCondomsSupplied, String numberOfCentchromanPillsDelivered,
                                            String numberOfOCPDelivered) {
        return create("wifeAge", wifeAge)
                .put("currentMethod", currentMethod)
                .put("familyPlanningMethodChangeDate", familyPlanningMethodChangeDate)
                .put("sideEffects", sideEffects)
                .put("numberOfPregnancies", numberOfPregnancies)
                .put("parity", parity)
                .put("numberOfLivingChildren", numberOfLivingChildren)
                .put("numberOfStillBirths", numberOfStillBirths)
                .put("numberOfAbortions", numberOfAbortions)
                .put("isHighPriority", Boolean.toString(isHighPriority))
                .put("isYoungestChildUnderTwo", isYoungestChildUnderTwo)
                .put("youngestChildAge", youngestChildAge)
                .put("complicationDate", complicationDate)
                .put("caste", caste)
                .put("economicStatus", economicStatus)
                .put("fpFollowupDate", fpFollowupDate)
                .put("iudPlace", iudPlace)
                .put("iudPerson", iudPerson)
                .put("numberOfCondomsSupplied", numberOfCondomsSupplied)
                .put("numberOfCentchromanPillsDelivered", numberOfCentchromanPillsDelivered)
                .put("numberOfOCPDelivered", numberOfOCPDelivered)
                .map();
    }

    private Map<String, String> normalPriority() {
        return mapOf("isHighPriority", "no");
    }

    private Map<String, String> highPriority() {
        return mapOf("isHighPriority", "yes");
    }
}
