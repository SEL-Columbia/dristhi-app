package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.util.Cache;
import org.ei.drishti.view.contract.ECClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.util.EasyMap.create;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class ECSmartRegisterControllerTest {
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allBeneficiaries;

    private ECSmartRegisterController controller;
    private Map<String, String> emptyDetails;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        emptyDetails = Collections.emptyMap();
        controller = new ECSmartRegisterController(allEligibleCouples, allBeneficiaries, new Cache<String>());
    }

    @Test
    public void shouldSortECsByName() throws Exception {
        EligibleCouple ec2 = new EligibleCouple("entity id 2", "Woman B", "Husband B", "EC Number 2", "kavalu_hosur", "Bherya SC", emptyDetails);
        EligibleCouple ec3 = new EligibleCouple("entity id 3", "Woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", emptyDetails);
        EligibleCouple ec1 = new EligibleCouple("entity id 1", "Woman A", "Husband A", "EC Number 1", "Bherya", null, emptyDetails);
        when(allEligibleCouples.all()).thenReturn(asList(ec2, ec3, ec1));
        ECClient expectedClient1 = createECClient("entity id 1", "Woman A", "Husband A", "Bherya", "EC Number 1");
        ECClient expectedClient2 = createECClient("entity id 2", "Woman B", "Husband B", "kavalu_hosur", "EC Number 2");
        ECClient expectedClient3 = createECClient("entity id 3", "Woman C", "Husband C", "Bherya", "EC Number 3");

        String clients = controller.get();

        List<ECClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<ECClient>>() {
        }.getType());
        assertEquals(asList(expectedClient1, expectedClient2, expectedClient3), actualClients);
    }

    private ECClient createECClient(String entityId, String name, String husbandName, String village, String ecNumber) {
        return new ECClient(entityId, name, husbandName, village, ecNumber)
                .withPhotoPath("../../img/woman-placeholder.png")
                .withIsOutOfArea(false);
    }

    @Test
    public void shouldMapECToECClient() throws Exception {
        Map<String, String> details = create("wifeAge", "22")
                .put("womanDOB", "1984-01-01")
                .put("currentMethod", "condom")
                .put("familyPlanningMethodChangeDate", "2013-01-02")
                .put("numberOfPregnancies", "2")
                .put("parity", "2")
                .put("numberOfLivingChildren", "1")
                .put("numberOfStillBirths", "1")
                .put("numberOfAbortions", "0")
                .put("isHighPriority", Boolean.toString(false))
                .put("caste", "sc")
                .put("economicStatus", "bpl")
                .put("iudPlace", "iudPlace")
                .put("iudPerson", "iudPerson")
                .put("numberOfCondomsSupplied", "numberOfCondomsSupplied")
                .put("numberOfCentchromanPillsDelivered", "numberOfCentchromanPillsDelivered")
                .put("numberOfOCPDelivered", "numberOfOCPDelivered")
                .put("highPriorityReason", "high priority reason")
                .map();
        EligibleCouple ec = new EligibleCouple("entity id 1", "Woman A", "Husband A", "EC Number 1", "Bherya", "Bherya SC", details)
                .withPhotoPath("new photo path").asOutOfArea();
        when(allEligibleCouples.all()).thenReturn(asList(ec));
        ECClient expectedECClient = new ECClient("entity id 1", "Woman A", "Husband A", "Bherya", "EC Number 1")
                .withDateOfBirth("1984-01-01")
                .withFPMethod("condom")
                .withFamilyPlanningMethodChangeDate("2013-01-02")
                .withIUDPlace("iudPlace")
                .withIUDPerson("iudPerson")
                .withNumberOfCondomsSupplied("numberOfCondomsSupplied")
                .withNumberOfCentchromanPillsDelivered("numberOfCentchromanPillsDelivered")
                .withNumberOfOCPDelivered("numberOfOCPDelivered")
                .withCaste("sc")
                .withEconomicStatus("bpl")
                .withNumberOfPregnancies("2")
                .withParity("2")
                .withNumberOfLivingChildren("1")
                .withNumberOfStillBirths("1")
                .withNumberOfAbortions("0")
                .withIsHighPriority(false)
                .withPhotoPath("new photo path")
                .withHighPriorityReason("high priority reason")
                .withIsOutOfArea(ec.isOutOfArea());

        String clients = controller.get();

        List<ECClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<ECClient>>() {
        }.getType());
        assertEquals(asList(expectedECClient), actualClients);
    }
}
