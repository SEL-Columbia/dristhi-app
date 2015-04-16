package org.ei.drishti.commonregistry;

import com.google.gson.Gson;

import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.contract.SmartRegisterClient;

import java.util.Comparator;
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

    public String get() {
        return cache.get(person_CLIENTS_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<CommonPersonObject> p = allpersonobjects.all();
                CommonPersonObjectClients pClients = new CommonPersonObjectClients();

                for (CommonPersonObject personinlist : p) {
                      CommonPersonObjectClient pClient = new CommonPersonObjectClient(personinlist.getCaseId(),personinlist.getDetails(),personinlist.getDetails().get(nameString));
//                    pClient.entityID = personinlist.getCaseId();
                    pClient.setColumnmaps(personinlist.getColumnmaps());
                    pClients.add(pClient);
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

                        for (CommonPersonObject personinlist : p) {
                            try {
                                CommonPersonObjectClient pClient = new CommonPersonObjectClient(personinlist.getCaseId(), personinlist.getDetails(), personinlist.getDetails().get(nameString));

                                pClient.setColumnmaps(personinlist.getColumnmaps());
                                pClients.add(pClient);
                            }catch (Exception e){}
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
