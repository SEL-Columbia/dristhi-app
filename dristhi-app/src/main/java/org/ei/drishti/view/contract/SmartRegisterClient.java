package org.ei.drishti.view.contract;

import java.util.Comparator;

public interface SmartRegisterClient {
    Comparator<SmartRegisterClient> NAME_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return client.name().compareToIgnoreCase(anotherClient.name());
        }
    };

    Comparator<SmartRegisterClient> HIGH_PRIORITY_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return client.isHighPriority() && anotherClient.isHighPriority()
                    ? client.name().compareToIgnoreCase(anotherClient.name())
                    : anotherClient.isHighPriority() ? 1 : -1;
        }
    };

    Comparator<SmartRegisterClient> BPL_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            if ((client.isBPL() && anotherClient.isBPL())
                    || (!client.isBPL() && !anotherClient.isBPL())) {
                return client.name().compareToIgnoreCase(anotherClient.name());
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
                return client.name().compareToIgnoreCase(anotherClient.name());
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
                return client.name().compareToIgnoreCase(anotherClient.name());
            } else {
                return anotherClient.isST() ? 1 : -1;
            }
        }
    };

    public String village();

    public String wifeName();

    public String name();

    public String husbandName();

    public int age();

    public boolean isSC();

    public boolean isST();

    public boolean isHighRisk();

    public boolean isHighPriority();

    public boolean isBPL();

    public String entityId();

    public String profilePhotoPath();

    public boolean satisfiesFilter(String filterCriterion);

    String locationStatus();

    public String ageInString();

    public int ageInDays();

    public String displayName();
}
