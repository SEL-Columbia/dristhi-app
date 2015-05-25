package org.ei.opensrp.commonregistry;

import com.google.gson.Gson;

import org.ei.opensrp.repository.AllBeneficiaries;
import org.ei.opensrp.util.Cache;
import org.ei.opensrp.util.CacheableData;
import org.ei.opensrp.view.contract.SmartRegisterClient;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.sort;
/**
 * Created by Raihan Ahmed on 4/15/15.
 */
public class CommonPersonObjectController {

    private  final String person_CLIENTS_LIST;

    private final AllCommonsRepository allpersonobjects;
    private final AllBeneficiaries allBeneficiaries;
    private final Cache<String> cache;
    private final Cache<CommonPersonObjectClients> personObjectClientsCache;
    public final String nameString;
    public  String filterkey = null;
    public String filtervalue = null;
    ByColumnAndByDetails byColumnAndByDetails;

    public enum ByColumnAndByDetails{
        byColumn,byDetails,byrelationalid;
    }


    public CommonPersonObjectController(AllCommonsRepository allpersons,
                                        AllBeneficiaries allBeneficiaries, Cache<String> cache,
                                        Cache<CommonPersonObjectClients> personClientsCache, String nameString, String bindtype) {
        this.allpersonobjects = allpersons;
        this.allBeneficiaries = allBeneficiaries;
        this.cache = cache;
        this.personObjectClientsCache = personClientsCache;
        this.person_CLIENTS_LIST = bindtype+"ClientsList";
        this.nameString = nameString;
    }
    public CommonPersonObjectController(AllCommonsRepository allpersons,
                                        AllBeneficiaries allBeneficiaries, Cache<String> cache,
                                        Cache<CommonPersonObjectClients> personClientsCache, String nameString, String bindtype,String filterkey,String filtervalue,ByColumnAndByDetails byColumnAndByDetails) {
        this.allpersonobjects = allpersons;
        this.allBeneficiaries = allBeneficiaries;
        this.cache = cache;
        this.personObjectClientsCache = personClientsCache;
        this.person_CLIENTS_LIST = bindtype+"_"+filterkey+"_"+filtervalue+"ClientsList";
        this.nameString = nameString;
        this.filterkey = filterkey;
        this.filtervalue = filtervalue;
        this.byColumnAndByDetails = byColumnAndByDetails;
    }


    public String get() {
        return cache.get(person_CLIENTS_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<CommonPersonObject> p = allpersonobjects.all();
                CommonPersonObjectClients pClients = new CommonPersonObjectClients();
                if(filterkey == null){
                    for (CommonPersonObject personinlist : p) {
                        CommonPersonObjectClient pClient = new CommonPersonObjectClient(personinlist.getCaseId(),personinlist.getDetails(),personinlist.getDetails().get(nameString));
//                    pClient.entityID = personinlist.getCaseId();
                        pClient.setColumnmaps(personinlist.getColumnmaps());
                        pClients.add(pClient);
                    }
                }else{
                    switch (byColumnAndByDetails){
                        case byColumn:
                            for (CommonPersonObject personinlist : p) {
                                if(personinlist.getColumnmaps().get(filterkey)!= null) {
                                    if(personinlist.getColumnmaps().get(filterkey).equalsIgnoreCase(filtervalue)) {
                                        CommonPersonObjectClient pClient = new CommonPersonObjectClient(personinlist.getCaseId(), personinlist.getDetails(), personinlist.getDetails().get(nameString));
                                        pClient.setColumnmaps(personinlist.getColumnmaps());

                                        pClients.add(pClient);
                                    }
                                }
                            }
                            break;
                        case byDetails:
                            for (CommonPersonObject personinlist : p) {
                                if(personinlist.getDetails().get(filterkey)!= null) {
                                    if(personinlist.getDetails().get(filterkey).equalsIgnoreCase(filtervalue)) {
                                        CommonPersonObjectClient pClient = new CommonPersonObjectClient(personinlist.getCaseId(), personinlist.getDetails(), personinlist.getDetails().get(nameString));
                                        pClient.setColumnmaps(personinlist.getColumnmaps());

                                        pClients.add(pClient);
                                    }
                                }
                            }
                            break;
                        case byrelationalid:
                            for (CommonPersonObject personinlist : p) {
                                if(personinlist.getRelationalId().equalsIgnoreCase(filtervalue)) {
                                    CommonPersonObjectClient pClient = new CommonPersonObjectClient(personinlist.getCaseId(), personinlist.getDetails(), personinlist.getDetails().get(nameString));
                                    pClient.setColumnmaps(personinlist.getColumnmaps());

                                    pClients.add(pClient);

                                }
                            }
                            break;
                    }
                }
                sortByName(pClients);
                return new Gson().toJson(pClients);
            }
        });
    }

    //#TODO: Remove duplication
    public CommonPersonObjectClients getClients() {
        return personObjectClientsCache.get(person_CLIENTS_LIST, new CacheableData<CommonPersonObjectClients>() {


            @Override
            public CommonPersonObjectClients fetch() {
                List<CommonPersonObject> p = allpersonobjects.all();
                CommonPersonObjectClients pClients = new CommonPersonObjectClients();
                if(filterkey == null){
                    for (CommonPersonObject personinlist : p) {
                        CommonPersonObjectClient pClient = new CommonPersonObjectClient(personinlist.getCaseId(),personinlist.getDetails(),personinlist.getDetails().get(nameString));
//                    pClient.entityID = personinlist.getCaseId();
                        pClient.setColumnmaps(personinlist.getColumnmaps());
                        pClients.add(pClient);
                    }
                }else{
                    switch (byColumnAndByDetails){
                        case byColumn:
                            for (CommonPersonObject personinlist : p) {
                                if(personinlist.getColumnmaps().get(filterkey)!= null) {
                                    if(personinlist.getColumnmaps().get(filterkey).equalsIgnoreCase(filtervalue)) {
                                        CommonPersonObjectClient pClient = new CommonPersonObjectClient(personinlist.getCaseId(), personinlist.getDetails(), personinlist.getDetails().get(nameString));
                                        pClient.setColumnmaps(personinlist.getColumnmaps());

                                        pClients.add(pClient);
                                    }
                                }
                            }
                            break;
                        case byDetails:
                            for (CommonPersonObject personinlist : p) {
                                if(personinlist.getDetails().get(filterkey)!= null) {
                                    if(personinlist.getDetails().get(filterkey).equalsIgnoreCase(filtervalue)) {
                                        CommonPersonObjectClient pClient = new CommonPersonObjectClient(personinlist.getCaseId(), personinlist.getDetails(), personinlist.getDetails().get(nameString));
                                        pClient.setColumnmaps(personinlist.getColumnmaps());

                                        pClients.add(pClient);
                                    }
                                }
                            }
                            break;
                        case byrelationalid:
                            for (CommonPersonObject personinlist : p) {
                                if(personinlist.getRelationalId().equalsIgnoreCase(filtervalue)) {
                                    CommonPersonObjectClient pClient = new CommonPersonObjectClient(personinlist.getCaseId(), personinlist.getDetails(), personinlist.getDetails().get(nameString));
                                    pClient.setColumnmaps(personinlist.getColumnmaps());

                                    pClients.add(pClient);

                                }
                            }
                            break;
                    }
                }
                sortByName(pClients);
                return pClients;
            }
        });
    }


    private void sortByName(List<? extends SmartRegisterClient> personClients) {
        sort(personClients, new Comparator<SmartRegisterClient>() {


            @Override
            public int compare(SmartRegisterClient personClient, SmartRegisterClient personClient2) {

                return ((CommonPersonObjectClient)personClient).getName().compareToIgnoreCase(((CommonPersonObjectClient)personClient2).getName());
            }
        });
    }

    //#TODO: Needs refactoring

}
