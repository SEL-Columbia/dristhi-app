package org.ei.opensrp.commonregistry;

import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.SortOption;

import java.util.Collections;
import java.util.Comparator;
/**
 * Created by Raihan Ahmed on 3/22/15.
 */
public class CommonObjectSort implements SortOption {


    String field;
    ByColumnAndByDetails byColumnAndByDetails;
    boolean isInteger;
    public enum ByColumnAndByDetails{
        byColumn,byDetails;
    }

    public CommonObjectSort(ByColumnAndByDetails byColumnAndByDetails, boolean isinteger, String field) {
        this.byColumnAndByDetails = byColumnAndByDetails;
        this.isInteger = isinteger;
        this.field = field;
    }

    @Override
    public String name() {
        return field;
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, commoncomparator);
        return allClients;
    }

    Comparator<SmartRegisterClient> commoncomparator = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient oneClient, SmartRegisterClient anotherClient2) {
            CommonPersonObjectClient commonPersonObjectClient = (CommonPersonObjectClient)oneClient;
            CommonPersonObjectClient commonPersonObjectClient2 = (CommonPersonObjectClient)anotherClient2;
            switch (byColumnAndByDetails){
                case byColumn:
                    if(!isInteger){
                        return commonPersonObjectClient.getColumnmaps().get(field).compareTo(commonPersonObjectClient2.getColumnmaps().get(field));

                    }else{
                        return (new Integer(commonPersonObjectClient.getColumnmaps().get(field))).compareTo(new Integer(commonPersonObjectClient2.getColumnmaps().get(field)));

                    }
                case byDetails:
                    if(!isInteger){
                        return commonPersonObjectClient.getDetails().get(field).compareTo(commonPersonObjectClient2.getDetails().get(field));
                    }else{
                        return (new Integer(commonPersonObjectClient.getDetails().get(field))).compareTo(new Integer(commonPersonObjectClient2.getDetails().get(field)));
                    }
            }
            return 0;
        }
    };
}
