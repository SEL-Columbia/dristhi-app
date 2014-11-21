package org.ei.drishti.view.contract;


import org.ei.drishti.domain.ANCServiceType;

import java.util.Comparator;

import static org.joda.time.LocalDateTime.parse;

public interface ANCSmartRegisterClient extends SmartRegisterClient {

    Comparator<SmartRegisterClient> EDD_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return parse(((ANCSmartRegisterClient) client).edd())
                    .compareTo(parse(((ANCSmartRegisterClient) anotherClient).edd()));
        }
    };

    public String edd();

    public String eddInDays();

    AlertDTO getAlert(ANCServiceType type);

    String ancVisitDoneDate();

    boolean isVisitsDone();

    String visitDoneDate();
}
