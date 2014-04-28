package org.ei.drishti.view.contract;

import org.ei.drishti.domain.FPMethod;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface SmartRegisterClient {
    //#TODO: Write unit test
    Comparator<SmartRegisterClient> NAME_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return client.wifeName().compareToIgnoreCase(anotherClient.wifeName());
        }
    };

    //#TODO: Write unit test
    Comparator<SmartRegisterClient> EC_NUMBER_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return client.ecNumber().compareTo(anotherClient.ecNumber());
        }
    };

    Comparator<SmartRegisterClient> HIGH_PRIORITY_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return client.isHighPriority() && anotherClient.isHighPriority()
                    ? client.wifeName().compareToIgnoreCase(anotherClient.wifeName())
                    : anotherClient.isHighPriority() ? 1 : -1;
        }
    };

    Comparator<SmartRegisterClient> BPL_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            if ((client.isBPL() && anotherClient.isBPL())
                    || (!client.isBPL() && !anotherClient.isBPL())) {
                return client.wifeName().compareToIgnoreCase(anotherClient.wifeName());
            } else {
                return anotherClient.isBPL() ? 1 : -1;
            }
        }
    };

    Comparator<SmartRegisterClient> SC_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            if ((client.isSC() && anotherClient.isSC())
                    || (!client.isSC() && !anotherClient.isSC())) {
                return client.wifeName().compareToIgnoreCase(anotherClient.wifeName());
            } else {
                return anotherClient.isSC() ? 1 : -1;
            }
        }
    };

    Comparator<SmartRegisterClient> ST_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            if ((client.isST() && anotherClient.isST())
                    || (!client.isST() && !anotherClient.isST())) {
                return client.wifeName().compareToIgnoreCase(anotherClient.wifeName());
            } else {
                return anotherClient.isST() ? 1 : -1;
            }
        }
    };

    public String village();

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

    public boolean satisfiesFilter(String filterCriterion);

    public String wifeName();

    public String name();

    public String husbandName();

    public int age();

    public Integer ecNumber();

    public boolean isSC();

    public boolean isST();

    public boolean isHighPriority();

    public boolean isBPL();

    public String locationStatus();

    public FPMethod fpMethod();

    public List<ECChildClient> children();

    public Map<String, String> status();

    public String entityId();

    public String profilePhotoPath();
}
