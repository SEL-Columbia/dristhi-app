package org.ei.drishti.view.contract;


import org.ei.drishti.domain.ANCServiceType;

import java.util.Comparator;
import java.util.List;

import static org.joda.time.LocalDateTime.parse;

public interface ANCSmartRegisterClient extends SmartRegisterClient {

    Comparator<SmartRegisterClient> EDD_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return parse(((ANCSmartRegisterClient) client).edd())
                    .compareTo(parse(((ANCSmartRegisterClient) anotherClient).edd()));
        }
    };

    boolean isMilestoneServiceProvided(ServiceProvidedDTO ancServiceProvided, String milestoneServiceName);

    public String edd();

    public String pastDueInDays();

    public String weeksAfterLMP();

    public AlertDTO getAlert(ANCServiceType type);

    public boolean isVisitsDone();

    public boolean isTTDone();

    public boolean isIFADone();

    public String visitDoneDateWithVisitName();

    public String ttDoneDate();

    public String ifaDoneDate();

    public String visitDoneDate();

    public String thayiCardNumber();

    public String ancNumber();

    public String lmp();

    public String riskFactors();

    public ServiceProvidedDTO serviceProvidedToACategory(String category);

    public String getHyperTension(ServiceProvidedDTO ancServiceProvided);

    public ServiceProvidedDTO getServiceProvidedDTO(String serviceName);

    public List<ServiceProvidedDTO> servicesProvided();
}
