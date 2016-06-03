package org.ei.opensrp.vaccinator.field;

import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.SortOption;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by muhammad.ahmed@ihsinformatics.com on 16-Nov-15.
 */
public class FieldSort implements SortOption {

    String field;
    ByMonthANDByDAILY byMonthlyAndByDaily;

    public enum ByMonthANDByDAILY {ByMonth, ByDaily;}

    public FieldSort(ByMonthANDByDAILY byMonthlyAndByDaily, String field) {
        this.field = field;
        this.byMonthlyAndByDaily = byMonthlyAndByDaily;

    }


    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, commoncomparator);
        return allClients;
    }

    @Override
    public String name() {
        return null;
    }


    Comparator<SmartRegisterClient> commoncomparator = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient oneClient, SmartRegisterClient anotherClient2) {
            CommonPersonObjectClient commonPersonObjectClient = (CommonPersonObjectClient) oneClient;
            CommonPersonObjectClient commonPersonObjectClient2 = (CommonPersonObjectClient) anotherClient2;
            switch (byMonthlyAndByDaily) {
                case ByMonth:
                    commonPersonObjectClient.getDetails().get(field).equalsIgnoreCase("monthly");
                    commonPersonObjectClient2.getDetails().get(field).equalsIgnoreCase("monthly");
                    return 0;// commonPersonObjectClient.getDetails().get(field).equalsIgnoreCase("monthly");

                case ByDaily:
                    commonPersonObjectClient.getDetails().get(field).equalsIgnoreCase("daily");
                    return 1;


            }
            return 0;
        }
    };
}
