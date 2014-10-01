package org.ei.drishti.view.contract;


import org.ei.drishti.domain.FPMethod;

import java.util.Comparator;

public interface FPSmartRegisterClient extends SmartRegisterClient {

    Comparator<SmartRegisterClient> EC_NUMBER_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return ((FPSmartRegisterClient) client).ecNumber()
                    .compareTo(((FPSmartRegisterClient) anotherClient).ecNumber());
        }
    };

    public String numberOfPregnancies();

    public String parity();

    public String numberOfLivingChildren();

    public String numberOfStillbirths();

    public String numberOfAbortions();

    public String familyPlanningMethodChangeDate();

    public String numberOfOCPDelivered();

    public String numberOfCondomsSupplied();

    public String numberOfCentchromanPillsDelivered();

    public String iudPerson();

    public String iudPlace();

    public Integer ecNumber();

    public FPMethod fpMethod();

    public String youngestChildAge();

}
