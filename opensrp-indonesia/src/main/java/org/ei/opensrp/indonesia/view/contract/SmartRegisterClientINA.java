package org.ei.opensrp.indonesia.view.contract;

import org.ei.opensrp.util.IntegerUtil;
import org.ei.opensrp.view.contract.SmartRegisterClient;

import java.util.Comparator;

/**
 * Created by Dimas Ciputra on 9/15/15.
 */
public interface SmartRegisterClientINA extends SmartRegisterClient {

    Comparator<SmartRegisterClient> EDD_COMPARATOR_KI = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            KartuIbuClient kartuIbuClient = (KartuIbuClient) client;
            KartuIbuClient anotherKartuIbuClient = (KartuIbuClient) anotherClient;
            if (kartuIbuClient.edd() == null && anotherKartuIbuClient.edd() == null) {
                return 0;
            }

            if (kartuIbuClient.edd() == null) {
                return 1;
            }

            if (anotherKartuIbuClient.edd() == null) {
                return -1;
            }

            if (kartuIbuClient.getDueEdd().equalsIgnoreCase("Sudah melahirkan")) {
                return 1;
            }

            if (anotherKartuIbuClient.getDueEdd().equalsIgnoreCase("Sudah melahirkan")) {
                return -1;
            }

            return ((KartuIbuClient) client).edd()
                    .compareTo(((KartuIbuClient) anotherClient).edd());
        }
    };

    Comparator<SmartRegisterClient> EDD_COMPARATOR_KI_ANC = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            KIANCClient kartuIbuClient = (KIANCClient) client;
            KIANCClient anotherKartuIbuClient = (KIANCClient) anotherClient;
            if (kartuIbuClient.edd() == null && anotherKartuIbuClient.edd() == null) {
                return 0;
            }

            if (kartuIbuClient.edd() == null) {
                return 1;
            }

            if (anotherKartuIbuClient.edd() == null) {
                return -1;
            }
            return ((KIANCClient) client).edd()
                    .compareTo(((KIANCClient) anotherClient).edd());
        }
    };

    Comparator<SmartRegisterClient> ALL_HIGH_RISK_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return IntegerUtil.compare(((BidanSmartRegisterClient) anotherClient).riskFlagsCount(), ((BidanSmartRegisterClient) client).riskFlagsCount());
        }
    };
}
