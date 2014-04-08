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

    public String village();

    public boolean satisfiesFilter(String filterCriterion);

    public String wifeName();

    public Integer ecNumber();

    public String locationStatus();
}
