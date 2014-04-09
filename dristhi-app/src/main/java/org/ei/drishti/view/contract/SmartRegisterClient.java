package org.ei.drishti.view.contract;

import java.util.Comparator;

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
            return client.isBPL() && anotherClient.isBPL()
                    ? client.wifeName().compareToIgnoreCase(anotherClient.wifeName())
                    : anotherClient.isBPL() ? 1 : -1;
        }
    };

    Comparator<SmartRegisterClient> SC_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return client.isSC() && anotherClient.isSC()
                    ? client.wifeName().compareToIgnoreCase(anotherClient.wifeName())
                    : anotherClient.isSC() ? 1 : -1;
        }
    };

    Comparator<SmartRegisterClient> ST_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return client.isST() && anotherClient.isST()
                    ? client.wifeName().compareToIgnoreCase(anotherClient.wifeName())
                    : anotherClient.isST() ? 1 : -1;
        }
    };

    public String village();

    public boolean satisfiesFilter(String filterCriterion);

    public String wifeName();

    public Integer ecNumber();

    public boolean isSC();

    public boolean isST();

    public boolean isHighPriority();

    public boolean isBPL();

    public String locationStatus();

    public String entityId();
}
