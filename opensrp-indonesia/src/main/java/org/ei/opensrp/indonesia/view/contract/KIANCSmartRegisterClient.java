package org.ei.opensrp.indonesia.view.contract;

import org.ei.opensrp.domain.ANCServiceType;
import org.ei.opensrp.view.contract.AlertDTO;
import org.ei.opensrp.view.contract.ServiceProvidedDTO;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.joda.time.LocalDateTime;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Dimas Ciputra on 3/4/15.
 */
public interface KIANCSmartRegisterClient {

    Comparator<SmartRegisterClient> EDD_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return ((KIANCSmartRegisterClient) client).edd()
                    .compareTo(((KIANCSmartRegisterClient) anotherClient).edd());
        }
    };

    public String eddForDisplay();

    LocalDateTime edd();

    public String pastDueInDays();

    public AlertDTO getAlert(ANCServiceType type);

    public boolean isVisitsDone();

    public boolean isTTDone();

    public String visitDoneDateWithVisitName();

    public String ttDoneDate();

    public String ifaDoneDate();

    public String ancNumber();

    public String riskFactors();

    public ServiceProvidedDTO serviceProvidedToACategory(String category);

    public String getHyperTension(ServiceProvidedDTO ancServiceProvided);

    public ServiceProvidedDTO getServiceProvidedDTO(String serviceName);

    public List<ServiceProvidedDTO> allServicesProvidedForAServiceType(String serviceType);

}