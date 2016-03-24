package org.ei.telemedicine.test.view.contract;

import junit.framework.Assert;

import org.ei.telemedicine.util.EasyMap;
import org.ei.telemedicine.view.contract.AlertDTO;
import org.ei.telemedicine.view.contract.ServiceProvidedDTO;
import org.ei.telemedicine.view.contract.Visits;
import org.ei.telemedicine.view.contract.pnc.PNCClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

@RunWith(MockitoJUnitRunner.class)
public class PNCClientTest {
    @Test
    public void shouldAddPNCAlertToServiceToVisitMap() throws Exception {
        AlertDTO pncAlert = new AlertDTO("PNC", "urgent", "2013-02-01");
        PNCClient pncClient = new PNCClient("entityId", "village", "name", "thayi","2013-01-30")
                .withAlerts(asList(pncAlert))
                .withServicesProvided(new ArrayList<ServiceProvidedDTO>());

        PNCClient preprocessedClients = pncClient.withPreProcess();

        Visits visits = new Visits();
        visits.toProvide = pncAlert;
        Map<String, Visits> serviceToVisitsMap = EasyMap.create("pnc", visits).map();

        Assert.assertEquals(preprocessedClients.serviceToVisitsMap(), serviceToVisitsMap);

    }

    @Test
    public void shouldAddPNCServiceProvidedToServiceToVisitMap() throws Exception {
        ServiceProvidedDTO servicesProvided = new ServiceProvidedDTO("PNC", "2013-02-01", new HashMap<String, String>());
        PNCClient pncClient = new PNCClient("entityId", "village", "name", "thayi","2013-01-30")
                .withAlerts(new ArrayList<AlertDTO>())
                .withServicesProvided(asList(servicesProvided));

        PNCClient preprocessedClients = pncClient.withPreProcess();

        Visits visits = new Visits();
        visits.provided = servicesProvided;
        Map<String, Visits> serviceToVisitsMap = EasyMap.create("pnc", visits).map();

        Assert.assertEquals(preprocessedClients.serviceToVisitsMap(), serviceToVisitsMap);
    }

}