package org.ei.drishti.person;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;

import org.ei.drishti.AllConstants;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.util.EasyMap;
import org.ei.drishti.util.IntegerUtil;
import org.ei.drishti.view.contract.ECChildClient;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.ECClients;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.AllConstants.ANCRegistrationFields.EDD;
import static org.ei.drishti.AllConstants.DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH;
import static org.ei.drishti.AllConstants.ECRegistrationFields.CASTE;
import static org.ei.drishti.AllConstants.ECRegistrationFields.CURRENT_FP_METHOD;
import static org.ei.drishti.AllConstants.ECRegistrationFields.ECONOMIC_STATUS;
import static org.ei.drishti.AllConstants.ECRegistrationFields.FAMILY_PLANNING_METHOD_CHANGE_DATE;
import static org.ei.drishti.AllConstants.ECRegistrationFields.HIGH_PRIORITY_REASON;
import static org.ei.drishti.AllConstants.ECRegistrationFields.IUD_PERSON;
import static org.ei.drishti.AllConstants.ECRegistrationFields.IUD_PLACE;
import static org.ei.drishti.AllConstants.ECRegistrationFields.NUMBER_OF_ABORTIONS;
import static org.ei.drishti.AllConstants.ECRegistrationFields.NUMBER_OF_CENTCHROMAN_PILLS_DELIVERED;
import static org.ei.drishti.AllConstants.ECRegistrationFields.NUMBER_OF_CONDOMS_SUPPLIED;
import static org.ei.drishti.AllConstants.ECRegistrationFields.NUMBER_OF_LIVING_CHILDREN;
import static org.ei.drishti.AllConstants.ECRegistrationFields.NUMBER_OF_OCP_DELIVERED;
import static org.ei.drishti.AllConstants.ECRegistrationFields.NUMBER_OF_PREGNANCIES;
import static org.ei.drishti.AllConstants.ECRegistrationFields.NUMBER_OF_STILL_BIRTHS;
import static org.ei.drishti.AllConstants.ECRegistrationFields.PARITY;
import static org.ei.drishti.AllConstants.ECRegistrationFields.REGISTRATION_DATE;
import static org.ei.drishti.AllConstants.ECRegistrationFields.WOMAN_DOB;

public class PersonController {

    private static final String person_CLIENTS_LIST = "personClientsList";

    private final AllPersons allpersons;
    private final AllBeneficiaries allBeneficiaries;
    private final Cache<String> cache;
    private final Cache<PersonClients> personClientsCache;

    public PersonController(AllPersons allpersons,
                            AllBeneficiaries allBeneficiaries, Cache<String> cache,
                            Cache<PersonClients> personClientsCache) {
        this.allpersons = allpersons;
        this.allBeneficiaries = allBeneficiaries;
        this.cache = cache;
        this.personClientsCache = personClientsCache;
    }

    public String get() {
        return cache.get(person_CLIENTS_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<Person> p = allpersons.all();
                List<PersonClient> pClients = new ArrayList<PersonClient>();

                for (Person personinlist : p) {
                      PersonClient pClient = new PersonClient(personinlist.getCaseId(),personinlist.getDetails().get("case_id"),personinlist.getDetails().get("name")
                              , personinlist.getDetails().get("age"), personinlist.getDetails().get("sex"),
                              personinlist.getDetails().get("address"), personinlist.getDetails().get("resistance_type"),
                              personinlist.getDetails().get("patient_type"), personinlist.getDetails().get("risk_factors"),
                              personinlist.getDetails().get("risk_factors_other"), personinlist.getDetails().get("drug_regimen_start"),
                              personinlist.getDetails().get("drug_regimen"), personinlist.getDetails().get("current_drug_regimen_start"),
                              personinlist.getDetails().get("bmi"), personinlist.getDetails().get("current_bmi"),
                              personinlist.getDetails().get("resistance_drugs"), personinlist.getDetails().get("current_resistance_drugs"), personinlist.getDetails().get("smear"), personinlist.getDetails().get("current_smear"));
//                    pClient.entityID = personinlist.getCaseId();
                    pClients.add(pClient);
                }
//                sortByName(pClients);
                return new Gson().toJson(pClients);
            }
        });
    }

    //#TODO: Remove duplication
    public  PersonClients getClients() {
        return personClientsCache.get(person_CLIENTS_LIST, new CacheableData<PersonClients>() {


                    @Override
                    public PersonClients fetch() {
                        List<Person> p = allpersons.all();
                        PersonClients pClients = new PersonClients();

                        for (Person personinlist : p) {
                            PersonClient pClient = new PersonClient(personinlist.getCaseId(),personinlist.getDetails().get("case_id"), personinlist.getDetails().get("name")
                                    , personinlist.getDetails().get("age"), personinlist.getDetails().get("sex"),
                                    personinlist.getDetails().get("address"), personinlist.getDetails().get("resistance_type"),
                                    personinlist.getDetails().get("patient_type"), personinlist.getDetails().get("risk_factors"),
                                    personinlist.getDetails().get("risk_factors_other"), personinlist.getDetails().get("drug_regimen_start"),
                                    personinlist.getDetails().get("drug_regimen"), personinlist.getDetails().get("current_drug_regimen_start"),
                                    personinlist.getDetails().get("bmi"), personinlist.getDetails().get("current_bmi"),
                                    personinlist.getDetails().get("resistance_drugs"), personinlist.getDetails().get("current_resistance_drugs"), personinlist.getDetails().get("smear"), personinlist.getDetails().get("current_smear"));


                            pClients.add(pClient);
                        }
//                        sortByName(pClients);
                        return pClients;
                    }
                });
            }


//            private void sortByName(PersonClients personClients) {
//                sort(personClients, new Comparator<PersonClient>() {
//
//
//                    @Override
//                    public int compare(PersonClient personClient, PersonClient personClient2) {
//                        return personClient.getName().compareToIgnoreCase(personClient2.getName());
//                    }
//                });
//            }

            //#TODO: Needs refactoring

        }
