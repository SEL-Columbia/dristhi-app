package org.ei.opensrp.indonesia.view.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.tuple.Pair;
import org.ei.opensrp.indonesia.AllConstantsINA;
import org.ei.opensrp.indonesia.domain.Ibu;
import org.ei.opensrp.indonesia.domain.KartuIbu;
import org.ei.opensrp.indonesia.repository.AllKohort;
import org.ei.opensrp.indonesia.view.contract.KIANCClient;
import org.ei.opensrp.indonesia.view.contract.KIANCClients;
import org.ei.opensrp.util.Cache;
import org.ei.opensrp.util.CacheableData;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.Village;
import org.ei.opensrp.view.contract.Villages;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import static java.util.Collections.sort;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.*;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuANCFields.*;

/**
 * Created by Dimas Ciputra on 3/4/15.
 */
public class KIANCRegisterController extends CommonController {

    private static final String KI_ANC_CLIENTS_LIST = "KIANCClientsList";

    private final AllKohort allKohort;
    private final Cache<String> cache;
    private final Cache<KIANCClients> KIANCClientsCache;
    private final Cache<Villages> villagesCache;

    public KIANCRegisterController(AllKohort allKohort, Cache<String> cache, Cache<KIANCClients> KIANCClientsCache, Cache<Villages> villagesCache) {
        this.allKohort = allKohort;
        this.cache = cache;
        this.villagesCache = villagesCache;
        this.KIANCClientsCache = KIANCClientsCache;
    }

    public String get() {
        return cache.get(KI_ANC_CLIENTS_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                KIANCClients ancClients = new KIANCClients();
                List<Pair<Ibu, KartuIbu>> ancsWithKis = allKohort.allANCsWithKartuIbu();

                for (Pair<Ibu, KartuIbu> ancsWithKi : ancsWithKis) {
                    Ibu anc = ancsWithKi.getLeft();
                    KartuIbu ki = ancsWithKi.getRight();

                    KIANCClient kartuIbuClient = new KIANCClient(anc.getId(),
                            ki.dusun(),
                            ki.getDetail(PUSKESMAS_NAME),
                            ki.getDetail(MOTHER_NAME),
                            ki.getDetail(MOTHER_DOB));

                    kartuIbuClient.setIsInPNCorANC(true);
                    kartuIbuClient.setIsPregnant(true);

                    kartuIbuClient.setAncVisitNumber(ki.getDetail(ANC_VISIT_NUMBER));
                    kartuIbuClient.setRiwayatKomplikasiKebidanan(anc.getDetail(COMPLICATION_HISTORY));
                    kartuIbuClient.setLaborComplication(anc.getDetail(AllConstantsINA.KartuPNCFields.COMPLICATION));

                    ancClients.add(kartuIbuClient);
                }
                return new Gson().toJson(ancClients);
            }
        });
    }

    public KIANCClients getKIANCClients() {
        return KIANCClientsCache.get(KI_ANC_CLIENTS_LIST, new CacheableData<KIANCClients>() {
            @Override
            public KIANCClients fetch() {
                KIANCClients ancClients = new KIANCClients();
                List<Pair<Ibu, KartuIbu>> ancsWithKis = allKohort.allANCsWithKartuIbu();

                for (Pair<Ibu, KartuIbu> ancsWithKi : ancsWithKis) {
                    Ibu anc = ancsWithKi.getLeft();
                    KartuIbu ki = ancsWithKi.getRight();

                    KIANCClient kartuIbuClient = new KIANCClient(anc.getId(),
                            ki.dusun(),
                            ki.getDetail(PUSKESMAS_NAME),
                            ki.getDetail(MOTHER_NAME),
                            ki.getDetail(MOTHER_DOB))
                            .withHusband(ki.getDetail(HUSBAND_NAME))
                            .withKINumber(ki.getDetail(MOTHER_NUMBER))
                            .withEDD(ki.getDetail(EDD))
                            .withANCStatus(anc.getDetail(MOTHER_NUTRITION_STATUS))
                            .withKunjunganData(anc.getDetail(TRIMESTER))
                            .withTTImunisasiData(anc.getDetail(IMMUNIZATION_TT_STATUS))
                            .withTanggalHPHT(anc.getDetail(HPHT_DATE))
                            .withUniqueId(ki.getDetail(UNIQUE_ID));

                    kartuIbuClient.setKartuIbuCaseId(anc.getKartuIbuId());
                    kartuIbuClient.setBB(anc.getDetail(WEIGHT_BEFORE));
                    kartuIbuClient.setTB(anc.getDetail(HEIGHT));
                    kartuIbuClient.setLILA(anc.getDetail(LILA_CHECK_RESULT));
                    kartuIbuClient.setBeratBadan(anc.getDetail(WEIGHT_CHECK_RESULT));
                    kartuIbuClient.setPenyakitKronis(anc.getDetail(CHRONIC_DISEASE));
                    kartuIbuClient.setAlergi(anc.getDetail(ALLERGY));
                    kartuIbuClient.setHighRiskLabour(ki.getDetail(IS_HIGH_RISK_LABOUR));
                    kartuIbuClient.setHigRiskPregnancyReason(ki.getDetail(HIGH_RISK_PREGNANCY_REASON));
                    kartuIbuClient.setRiwayatKomplikasiKebidanan(anc.getDetail(COMPLICATION_HISTORY));

                    kartuIbuClient.setIsInPNCorANC(true);
                    kartuIbuClient.setIsPregnant(true);

                    kartuIbuClient.setChronicDisease(anc.getDetail(CHRONIC_DISEASE));
                    kartuIbuClient.setrLila(anc.getDetail(AllConstantsINA.KartuANCFields.LILA_CHECK_RESULT));
                    kartuIbuClient.setrHbLevels(anc.getDetail(AllConstantsINA.KartuANCFields.HB_RESULT));
                    kartuIbuClient.setrTdDiastolik(anc.getDetail(AllConstantsINA.KartuPNCFields.VITAL_SIGNS_TD_DIASTOLIC));
                    kartuIbuClient.setrTdSistolik(anc.getDetail(AllConstantsINA.KartuPNCFields.VITAL_SIGNS_TD_SISTOLIC));
                    kartuIbuClient.setrBloodSugar(anc.getDetail(AllConstantsINA.KartuANCFields.SUGAR_BLOOD_LEVEL));
                    kartuIbuClient.setrAbortus(ki.getDetail(NUMBER_ABORTIONS));
                    kartuIbuClient.setrPartus(ki.getDetail(NUMBER_PARTUS));
                    kartuIbuClient.setrPregnancyComplications(anc.getDetail(AllConstantsINA.KartuANCFields.COMPLICATION_HISTORY));
                    kartuIbuClient.setrFetusNumber(anc.getDetail(AllConstantsINA.KartuANCFields.FETUS_NUMBER));
                    kartuIbuClient.setrFetusSize(anc.getDetail(AllConstantsINA.KartuANCFields.FETUS_SIZE));
                    kartuIbuClient.setrFetusPosition(anc.getDetail(AllConstantsINA.KartuANCFields.FETUS_POSITION));
                    kartuIbuClient.setrPelvicDeformity(anc.getDetail(AllConstantsINA.KartuANCFields.PELVIC_DEFORMITY));
                    kartuIbuClient.setrHeight(anc.getDetail(AllConstantsINA.KartuANCFields.HEIGHT));
                    kartuIbuClient.setrDeliveryMethod(anc.getDetail(AllConstantsINA.KartuPNCFields.DELIVERY_METHOD));
                    kartuIbuClient.setLaborComplication(anc.getDetail(AllConstantsINA.KartuPNCFields.COMPLICATION));

                    ancClients.add(kartuIbuClient);
                }
                sortByName(ancClients);
                return ancClients;
            }
        });
    }

    private void sortByName(List<? extends SmartRegisterClient> kiClients) {
        sort(kiClients, new Comparator<SmartRegisterClient>() {
            @Override
            public int compare(SmartRegisterClient o1, SmartRegisterClient o2) {
                return o1.name().compareToIgnoreCase(o2.name());
            }
        });
    }

    public Villages villages() {
        return villagesCache.get(KI_ANC_CLIENTS_LIST, new CacheableData<Villages>() {
            @Override
            public Villages fetch() {
                List<SmartRegisterClient> clients = new Gson().fromJson(get(), new TypeToken<List<KIANCClient>>() {
                }.getType());
                List<String> villageNameList = new ArrayList<>();
                Villages villagesList = new Villages();

                for(SmartRegisterClient client : clients) {
                    villageNameList.add(client.village());
                }

                villageNameList = new ArrayList<>(new LinkedHashSet<>(villageNameList));
                for(String name : villageNameList) {
                    Village village = new Village(name);
                    villagesList.add(new Village(name));
                }
                return villagesList;
            }
        });
    }

    public CharSequence[] getRandomNameChars(final SmartRegisterClient client) {
        String clients = get();
        List<SmartRegisterClient> ancClients = new Gson().fromJson(clients, new TypeToken<List<KIANCClient>>() {
        }.getType());
        return onRandomNameChars(
                client,
                ancClients,
                allKohort.randomDummyANCName(),
                AllConstantsINA.DIALOG_DOUBLE_SELECTION_NUM);
    }

}
