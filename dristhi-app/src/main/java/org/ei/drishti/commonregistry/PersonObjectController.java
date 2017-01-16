package org.ei.drishti.commonregistry;

import com.google.gson.Gson;

import org.ei.drishti.person.AllPersons;
import org.ei.drishti.person.Person;
import org.ei.drishti.person.PersonClient;
import org.ei.drishti.person.PersonClients;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.contract.SmartRegisterClient;

import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

public class PersonObjectController {

    private  final String person_CLIENTS_LIST;

    private final AllCommonsRepository allpersonobjects;
    private final AllBeneficiaries allBeneficiaries;
    private final Cache<String> cache;
    private final Cache<PersonObjectClients> personObjectClientsCache;
    public final String nameString;


    public PersonObjectController(AllCommonsRepository allpersons,
                                  AllBeneficiaries allBeneficiaries, Cache<String> cache,
                                  Cache<PersonObjectClients> personClientsCache,String nameString,String bindtype) {
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
                List<PersonObject> p = allpersonobjects.all();
                PersonObjectClients pClients = new PersonObjectClients();

                for (PersonObject personinlist : p) {
                      PersonObjectClient pClient = new PersonObjectClient(personinlist.getCaseId(),personinlist.getDetails(),personinlist.getDetails().get(nameString));
//                    pClient.entityID = personinlist.getCaseId();
                    pClients.add(pClient);
                }
                sortByName(pClients);
                return new Gson().toJson(pClients);
            }
        });
    }

    //#TODO: Remove duplication
    public  PersonObjectClients getClients() {
        return personObjectClientsCache.get(person_CLIENTS_LIST, new CacheableData<PersonObjectClients>() {


                    @Override
                    public PersonObjectClients fetch() {
                        List<PersonObject> p = allpersonobjects.all();
                        PersonObjectClients pClients = new PersonObjectClients();

                        for (PersonObject personinlist : p) {
                            PersonObjectClient pClient = new PersonObjectClient(personinlist.getCaseId(),personinlist.getDetails(),personinlist.getDetails().get(nameString));


                            pClients.add(pClient);
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

                        return ((PersonObjectClient)personClient).getName().compareToIgnoreCase(((PersonObjectClient)personClient2).getName());
                    }
                });
            }

            //#TODO: Needs refactoring

        }
