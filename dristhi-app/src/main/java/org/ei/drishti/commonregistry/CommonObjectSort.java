package org.ei.drishti.commonregistry;

import org.ei.drishti.view.contract.SmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClients;
import org.ei.drishti.view.dialog.SortOption;

import java.util.Collections;
import java.util.Comparator;

public class CommonObjectSort implements SortOption {
    boolean byColumn;
    boolean byDetails;
    boolean isInteger;
    String field;

    public CommonObjectSort(boolean byColumn, boolean byDetails, boolean isInteger, String field) {
        this.byColumn = byColumn;
        this.byDetails = byDetails;
        this.isInteger = isInteger;
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
            if(!isInteger && !byColumn && byDetails) {
                return commonPersonObjectClient.getDetails().get(field).compareTo(commonPersonObjectClient2.getDetails().get(field));
            }else if(isInteger && !byColumn && byDetails){
                return (new Integer(commonPersonObjectClient.getDetails().get(field))).compareTo(new Integer(commonPersonObjectClient2.getDetails().get(field)));
            }else if(!isInteger && byColumn && !byDetails){
                return commonPersonObjectClient.getColumnmaps().get(field).compareTo(commonPersonObjectClient2.getColumnmaps().get(field));

            }else{

            }   return (new Integer(commonPersonObjectClient.getColumnmaps().get(field))).compareTo(new Integer(commonPersonObjectClient2.getColumnmaps().get(field)));

        }
    };
}
