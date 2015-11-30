package org.ei.telemedicine.test.view.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.tuple.Pair;
import org.ei.telemedicine.domain.Alert;
import org.ei.telemedicine.domain.EligibleCouple;
import org.ei.telemedicine.domain.Mother;
import org.ei.telemedicine.domain.ServiceProvided;
import org.ei.telemedicine.repository.AllBeneficiaries;
import org.ei.telemedicine.repository.AllEligibleCouples;
import org.ei.telemedicine.service.AlertService;
import org.ei.telemedicine.service.ServiceProvidedService;
import org.ei.telemedicine.util.Cache;
import org.ei.telemedicine.view.contract.ANCClient;
import org.ei.telemedicine.view.contract.ANCClients;
import org.ei.telemedicine.view.contract.AlertDTO;
import org.ei.telemedicine.view.contract.ServiceProvidedDTO;
import org.ei.telemedicine.view.controller.ANCSmartRegisterController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.telemedicine.dto.AlertStatus.normal;
import static org.ei.telemedicine.util.EasyMap.create;
import static org.ei.telemedicine.util.EasyMap.mapOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ANCSmartRegisterControllerTest {
    public static final String[] ANC_ALERTS = new String[]{
            "ANC 1",
            "ANC 2",
            "ANC 3",
            "ANC 4",
            "IFA 1",
            "IFA 2",
            "IFA 3",
            "REMINDER",
            "TT 1",
            "TT 2",
            "Hb Test 1",
            "Hb Followup Test",
            "Hb Test 2",
            "Delivery Plan"
    };
    public static final String[] ANC_SERVICES = new String[]{
            "IFA",
            "TT 1",
            "TT 2",
            "TT Booster",
            "Hb Test",
            "ANC 1",
            "ANC 2",
            "ANC 3",
            "ANC 4",
            "Delivery Plan"
    };
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private AlertService alertService;
    @Mock
    private ServiceProvidedService sericeProvidedService;

    private ANCSmartRegisterController controller;
    private Map<String, String> emptyMap;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        emptyMap = Collections.emptyMap();
        controller = new ANCSmartRegisterController(sericeProvidedService, alertService, allBeneficiaries, new Cache<String>(), new Cache<ANCClients>());
    }

    /*@Test
    public void shouldSortANCsByWifeName() throws Exception {
        Map<String, String> details = mapOf("edd", "Tue, 25 Feb 2014 00:00:00 GMT");
        EligibleCouple ec2 = new EligibleCouple("EC Case 2", "Woman B", "Husband B", "EC Number 2", "kavalu_hosur", "Bherya SC", emptyMap);
        EligibleCouple ec3 = new EligibleCouple("EC Case 3", "Woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", emptyMap);
        EligibleCouple ec1 = new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", null, emptyMap);
        Mother m1 = new Mother("Entity X", "EC Case 2", "thayi 1", "2013-05-25").withDetails(details);
        Mother m2 = new Mother("Entity Y", "EC Case 3", "thayi 2", "2013-05-25").withDetails(details);
        Mother m3 = new Mother("Entity Z", "EC Case 1", "thayi 3", "2013-05-25").withDetails(details);
        ANCClient expectedClient1 = createANCClient("Entity Z", "Woman A", "Bherya", "thayi 3", "Tue, 25 Feb 2014 00:00:00 GMT", "2013-05-25").withECNumber("EC Number 1").withHusbandName("Husband A").withEntityIdToSavePhoto("EC Case 1").withHighRiskReason("");
        ANCClient expectedClient2 = createANCClient("Entity X", "Woman B", "kavalu_hosur", "thayi 1", "Tue, 25 Feb 2014 00:00:00 GMT", "2013-05-25").withECNumber("EC Number 2").withHusbandName("Husband B").withEntityIdToSavePhoto("EC Case 2").withHighRiskReason("");
        ANCClient expectedClient3 = createANCClient("Entity Y", "Woman C", "Bherya", "thayi 2", "Tue, 25 Feb 2014 00:00:00 GMT", "2013-05-25").withECNumber("EC Number 3").withHusbandName("Husband C").withEntityIdToSavePhoto("EC Case 3").withHighRiskReason("");
        when(allBeneficiaries.allANCsWithEC()).thenReturn(asList(Pair.of(m1, ec2), Pair.of(m2, ec3), Pair.of(m3, ec1)));

        String clients = controller.get();

        List<ANCClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<ANCClient>>() {
        }.getType());
        assertEquals(asList(expectedClient1, expectedClient2, expectedClient3), actualClients);
    }*/

    @Test
    public void shouldGetFPClientsListWithSortedANCsByWifeName() throws Exception {
        Map<String, String> details = mapOf("edd", "Tue, 25 Feb 2014 00:00:00 GMT");
        EligibleCouple ec2 = new EligibleCouple("EC Case 2", "Woman B", "Husband B", "EC Number 2", "kavalu_hosur", "Bherya SC", emptyMap);
        EligibleCouple ec3 = new EligibleCouple("EC Case 3", "Woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", emptyMap);
        EligibleCouple ec1 = new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", null, emptyMap);
        Mother m1 = new Mother("Entity X", "EC Case 2", "thayi 1", "2013-05-25").withDetails(details);
        Mother m2 = new Mother("Entity Y", "EC Case 3", "thayi 2", "2013-05-25").withDetails(details);
        Mother m3 = new Mother("Entity Z", "EC Case 1", "thayi 3", "2013-05-25").withDetails(details);
        ANCClient expectedClient1 = createANCClient("Entity Z", "Woman A", "Bherya", "thayi 3", "Tue, 25 Feb 2014 00:00:00 GMT", "2013-05-25").withECNumber("EC Number 1").withHusbandName("Husband A").withEntityIdToSavePhoto("EC Case 1").withHighRiskReason("");
        ANCClient expectedClient2 = createANCClient("Entity X", "Woman B", "kavalu_hosur", "thayi 1", "Tue, 25 Feb 2014 00:00:00 GMT", "2013-05-25").withECNumber("EC Number 2").withHusbandName("Husband B").withEntityIdToSavePhoto("EC Case 2").withHighRiskReason("");
        ANCClient expectedClient3 = createANCClient("Entity Y", "Woman C", "Bherya", "thayi 2", "Tue, 25 Feb 2014 00:00:00 GMT", "2013-05-25").withECNumber("EC Number 3").withHusbandName("Husband C").withEntityIdToSavePhoto("EC Case 3").withHighRiskReason("");
        when(allBeneficiaries.allANCsWithEC()).thenReturn(asList(Pair.of(m1, ec2), Pair.of(m2, ec3), Pair.of(m3, ec1)));

        ANCClients actualClients = controller.getClients();


        assertEquals(asList(expectedClient1, expectedClient2, expectedClient3), actualClients);
    }

    /*@Test
    public void shouldMapANCToANCClient() throws Exception {
        Map<String, String> details =
                create("edd", "Tue, 25 Feb 2014 00:00:00 GMT")
                        .put("isHighRisk", "yes")
                        .put("ancNumber", "ANC X")
                        .put("highRiskReason", "Headache")
                        .put("ashaPhoneNumber", "Asha phone number 1")
                        .map();
        EligibleCouple eligibleCouple = new EligibleCouple("ec id 1", "Woman A", "Husband A", "EC Number 1", "Bherya", null,
                create("wifeAge", "23")
                        .put("isHighPriority", Boolean.toString(false))
                        .put("caste", "other")
                        .put("economicStatus", "bpl")
                        .map()
        ).asOutOfArea();
        Mother mother = new Mother("Entity X", "ec id 1", "thayi 1", "2013-05-25").withDetails(details);
        when(allBeneficiaries.allANCsWithEC()).thenReturn(asList(Pair.of(mother, eligibleCouple)));
        ANCClient expectedANCClient = new ANCClient("Entity X", "Bherya", "Woman A", "thayi 1", "Tue, 25 Feb 2014 00:00:00 GMT", "2013-05-25")
                .withECNumber("EC Number 1")
                .withIsHighPriority(false)
                .withAge("23")
                .withHusbandName("Husband A")
                .withIsOutOfArea(true)
                .withIsHighRisk(true)
                .withCaste("other")
                .withANCNumber("ANC X")
                .withHighRiskReason("Headache")
                .withPhotoPath("../../img/woman-placeholder.png")
                .withEconomicStatus("bpl")
                .withEntityIdToSavePhoto("ec id 1")
                .withAlerts(Collections.<AlertDTO>emptyList())
                .withAshaPhoneNumber("Asha phone number 1")
                .withServicesProvided(Collections.<ServiceProvidedDTO>emptyList());

        String clients = controller.get();

        List<ANCClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<ANCClient>>() {
        }.getType());
        assertEquals(asList(expectedANCClient), actualClients);
    }

    @Test
    public void shouldCreateANCClientsWithANC1AlertAndDeliveryPlanAlert() throws Exception {
        Map<String, String> details = mapOf("edd", "Tue, 25 Feb 2014 00:00:00 GMT");
        EligibleCouple ec = new EligibleCouple("entity id 1", "Woman C", "Husband C", "EC Number 1", "Bherya", "Bherya SC", emptyMap);
        Mother mother = new Mother("Entity X", "entity id 1", "thayi 1", "2012-10-25").withDetails(details);
        Alert anc1Alert = new Alert("entity id 1", "ANC", "ANC 1", normal, "2013-01-01", "2013-02-01");
        Alert deliveryPlanAlert = new Alert("entity id 1", "Delivery Plan", "Delivery Plan", normal, "2012-10-25", "2013-08-25");
        when(allBeneficiaries.allANCsWithEC()).thenReturn(asList(Pair.of(mother, ec)));
        when(alertService.findByEntityIdAndAlertNames("Entity X", ANC_ALERTS)).thenReturn(asList(anc1Alert, deliveryPlanAlert));

        String clients = controller.get();

        List<ANCClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<ANCClient>>() {
        }.getType());
        verify(alertService).findByEntityIdAndAlertNames("Entity X", ANC_ALERTS);
        AlertDTO expectedANC1AlertDto = new AlertDTO("ANC 1", "normal", "2013-01-01");
        AlertDTO expectedDeliveryPlanAlertDto = new AlertDTO("Delivery Plan", "normal", "2012-10-25");
        ANCClient expectedEC = createANCClient("Entity X", "Woman C", "Bherya", "thayi 1", "Tue, 25 Feb 2014 00:00:00 GMT", "2012-10-25")
                .withECNumber("EC Number 1")
                .withHusbandName("Husband C")
                .withEntityIdToSavePhoto("entity id 1")
                .withAlerts(asList(expectedANC1AlertDto, expectedDeliveryPlanAlertDto))
                .withHighRiskReason("");
        assertEquals(asList(expectedEC), actualClients);
    }

    @Test
    public void shouldCreateANCClientsWithServicesProvided() throws Exception {
        Map<String, String> details = mapOf("edd", "Tue, 25 Feb 2014 00:00:00 GMT");
        EligibleCouple ec = new EligibleCouple("entity id 1", "Woman C", "Husband C", "EC Number 1", "Bherya", "Bherya SC", emptyMap);
        Mother mother = new Mother("Entity X", "entity id 1", "thayi 1", "2013-05-25").withDetails(details);
        when(allBeneficiaries.allANCsWithEC()).thenReturn(asList(Pair.of(mother, ec)));
        when(alertService.findByEntityIdAndAlertNames("Entity X", ANC_ALERTS)).thenReturn(Collections.<Alert>emptyList());
        when(sericeProvidedService.findByEntityIdAndServiceNames("Entity X", ANC_SERVICES))
                .thenReturn(asList(new ServiceProvided("entity id 1", "IFA", "2013-01-01", mapOf("dose", "100")),
                        new ServiceProvided("entity id 1", "TT 1", "2013-02-01", emptyMap),
                        new ServiceProvided("entity id 1", "Delivery Plan", "2013-02-01", emptyMap)
                ));

        String clients = controller.get();

        List<ANCClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<ANCClient>>() {
        }.getType());
        verify(alertService).findByEntityIdAndAlertNames("Entity X", ANC_ALERTS);
        verify(sericeProvidedService).findByEntityIdAndServiceNames("Entity X", ANC_SERVICES);
        List<ServiceProvidedDTO> expectedServicesProvided = asList(new ServiceProvidedDTO("IFA", "2013-01-01", mapOf("dose", "100")),
                new ServiceProvidedDTO("TT 1", "2013-02-01", emptyMap), new ServiceProvidedDTO("Delivery Plan", "2013-02-01", emptyMap));
        ANCClient expectedEC = createANCClient("Entity X", "Woman C", "Bherya", "thayi 1", "Tue, 25 Feb 2014 00:00:00 GMT", "2013-05-25")
                .withECNumber("EC Number 1")
                .withHusbandName("Husband C")
                .withEntityIdToSavePhoto("entity id 1")
                .withServicesProvided(expectedServicesProvided)
                .withHighRiskReason("");
        assertEquals(asList(expectedEC), actualClients);
    }*/

    private ANCClient createANCClient(String entityId, String name, String village, String thayi, String edd, String lmp) {
        return new ANCClient(entityId, village, name, thayi, edd, lmp)
                .withPhotoPath("../../img/woman-placeholder.png")
                .withIsOutOfArea(false)
                .withAlerts(Collections.<AlertDTO>emptyList())
                .withServicesProvided(Collections.<ServiceProvidedDTO>emptyList());
    }
}
