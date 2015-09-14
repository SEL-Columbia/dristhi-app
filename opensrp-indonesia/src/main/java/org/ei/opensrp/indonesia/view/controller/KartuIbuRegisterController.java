package org.ei.opensrp.indonesia.view.controller;

import com.google.common.collect.Iterables;

import org.ei.opensrp.indonesia.AllConstantsINA;
import org.ei.opensrp.indonesia.domain.Anak;
import org.ei.opensrp.indonesia.domain.Ibu;
import org.ei.opensrp.indonesia.domain.KartuIbu;
import org.ei.opensrp.indonesia.repository.AllKartuIbus;
import org.ei.opensrp.indonesia.repository.AllKohort;
import org.ei.opensrp.indonesia.view.contract.KIChildClient;
import org.ei.opensrp.indonesia.view.contract.KartuIbuClient;
import org.ei.opensrp.indonesia.view.contract.KartuIbuClients;
import org.ei.opensrp.util.Cache;
import org.ei.opensrp.util.CacheableData;
import org.ei.opensrp.util.EasyMap;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.joda.time.LocalDate;

import java.util.Comparator;
import java.util.List;
import static java.util.Collections.sort;

import static org.ei.opensrp.indonesia.AllConstantsINA.KeluargaBerencanaFields.*;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.*;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuANCFields.*;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuPNCFields.*;

/**
 * Created by Dimas Ciputra on 2/18/15.
 */
public class KartuIbuRegisterController  extends CommonController{
    private static final String KI_CLIENTS_LIST = "KIClientsList";
    public static final String STATUS_DATE_FIELD = "date";
    public static final String ANC_STATUS = "anc";
    public static final String PNC_STATUS = "pnc";
    public static final String STATUS_TYPE_FIELD = "type";

    private final AllKartuIbus allKartuIbus;
    private final Cache<String> cache;
    private final Cache<KartuIbuClients> kartuIbuClientsCache;
    private final AllKohort allKohort;

    public KartuIbuRegisterController(AllKartuIbus allKartuIbus, Cache<String> cache,
                                      Cache<KartuIbuClients> kartuIbuClientsCache,
                                      AllKohort allKohort) {
        this.allKartuIbus = allKartuIbus;
        this.cache = cache;
        this.kartuIbuClientsCache = kartuIbuClientsCache;
        this.allKohort = allKohort;
    }

    public KartuIbuClients getKartuIbuClients() {
        return kartuIbuClientsCache.get(KI_CLIENTS_LIST, new CacheableData<KartuIbuClients>() {
            @Override
            public KartuIbuClients fetch() {
                List<KartuIbu> kartuIbus = allKartuIbus.all();
                KartuIbuClients kartuIbuClients = new KartuIbuClients();

                for (KartuIbu kartuIbu : kartuIbus) {
                    KartuIbuClient kartuIbuClient = new KartuIbuClient(kartuIbu.getCaseId(),
                            kartuIbu.getDetail(PUSKESMAS_NAME), kartuIbu.getDetail(PROPINSI),
                            kartuIbu.getDetail(KABUPATEN), kartuIbu.getDetail(POSYANDU_NAME),
                            kartuIbu.getDetail(MOTHER_ADDRESS), kartuIbu.getDetail(MOTHER_NUMBER),
                            kartuIbu.getDetail(MOTHER_NAME), kartuIbu.getDetail(MOTHER_AGE),
                            kartuIbu.getDetail(MOTHER_BLOOD_TYPE),
                            kartuIbu.getDetail(HUSBAND_NAME),
                            kartuIbu.dusun())
                            .withIsHighRiskPregnancy(kartuIbu.getDetail(IS_HIGH_RISK_PREGNANCY))
                            .withDateOfBirth(kartuIbu.getDetail(MOTHER_DOB))
                            .withParity(kartuIbu.getDetail(NUMBER_PARTUS))
                            .withNumberOfAbortions(kartuIbu.getDetail(NUMBER_ABORTIONS))
                            .withNumberOfPregnancies(kartuIbu.getDetail(NUMBER_OF_PREGNANCIES))
                            .withNumberOfLivingChildren(kartuIbu.getDetail(NUMBER_OF_LIVING_CHILDREN))
                            .withHighPriority(kartuIbu.getDetail(IS_HIGH_PRIORITY))
                            .withIsHighRisk(kartuIbu.getDetail(IS_HIGH_RISK))
                            .withEdd(kartuIbu.getDetail(EDD))
                            .withHighRiskLabour(kartuIbu.getDetail(IS_HIGH_RISK_LABOUR));

                    updateStatusInformation(kartuIbu, kartuIbuClient);
                    updateChildrenInformation(kartuIbuClient);
                    kartuIbuClients.add(kartuIbuClient);
                }
                sortByName(kartuIbuClients);
                return kartuIbuClients;
            }
        });
    }

    private void sortByName(List<?extends SmartRegisterClient> kiClients) {
        sort(kiClients, new Comparator<SmartRegisterClient>() {
            @Override
            public int compare(SmartRegisterClient o1, SmartRegisterClient o2) {
                return o1.wifeName().compareToIgnoreCase(o2.wifeName());
            }
        });
    }

    private void updateChildrenInformation(KartuIbuClient kartuIbuClient) {
        List<Anak> children = allKohort.findAllAnakByKIId(kartuIbuClient.entityId());
        sortByDateOfBirth(children);
        Iterable<Anak> youngestTwoChildren = Iterables.skip(children, children.size() < 2 ? 0 : children.size() - 2);
        for (Anak child : youngestTwoChildren) {
            kartuIbuClient.addChild(new KIChildClient(child.getCaseId(), child.getGender(), child.getDateOfBirth()));
        }
    }

    private void sortByDateOfBirth(List<Anak> children) {
        sort(children, new Comparator<Anak>() {
            @Override
            public int compare(Anak child, Anak anotherChild) {
                return LocalDate.parse(child.getDateOfBirth()).compareTo(LocalDate.parse(anotherChild.getDateOfBirth()));
            }
        });
    }

    //#TODO: Needs refactoring
    private void updateStatusInformation(KartuIbu kartuIbu, KartuIbuClient kartuIbuClient) {
        Ibu ibu = allKohort.findIbuWithOpenStatusByKIId(kartuIbu.getCaseId());

        kartuIbuClient.withKbMethod(kartuIbu.getDetail(CONTRACEPTION_METHOD));

        if(ibu == null) {
            kartuIbuClient.setIsInPNCorANC(false);
            kartuIbuClient.setIsPregnant(false);
        }

        if( ibu == null && kartuIbu.hasKBMethod()) {
            kartuIbuClient.withStatus(EasyMap.create(STATUS_TYPE_FIELD, KELUARGA_BERENCANA)
                    .put(STATUS_DATE_FIELD, kartuIbu.getDetail(VISITS_DATE)).map());
            kartuIbuClient.withKbStart(kartuIbu.getDetail(VISITS_DATE));
            return;
        }

        if(ibu!=null) {
            kartuIbuClient.setIsInPNCorANC(true);

            kartuIbuClient.setChronicDisease(ibu.getDetail(CHRONIC_DISEASE));
            kartuIbuClient.setrLila(ibu.getDetail(LILA_CHECK_RESULT));
            kartuIbuClient.setrHbLevels(ibu.getDetail(HB_RESULT));
            kartuIbuClient.setrTdDiastolik(ibu.getDetail(VITAL_SIGNS_TD_DIASTOLIC));
            kartuIbuClient.setrTdSistolik(ibu.getDetail(VITAL_SIGNS_TD_SISTOLIC));
            kartuIbuClient.setrBloodSugar(ibu.getDetail(SUGAR_BLOOD_LEVEL));
            kartuIbuClient.setrAbortus(kartuIbu.getDetail(NUMBER_ABORTIONS));
            kartuIbuClient.setrPartus(kartuIbu.getDetail(NUMBER_PARTUS));
            kartuIbuClient.setrPregnancyComplications(ibu.getDetail(COMPLICATION_HISTORY));
            kartuIbuClient.setrFetusNumber(ibu.getDetail(FETUS_NUMBER));
            kartuIbuClient.setrFetusSize(ibu.getDetail(FETUS_SIZE));
            kartuIbuClient.setrFetusPosition(ibu.getDetail(FETUS_POSITION));
            kartuIbuClient.setrPelvicDeformity(ibu.getDetail(PELVIC_DEFORMITY));
            kartuIbuClient.setrHeight(ibu.getDetail(HEIGHT));
            kartuIbuClient.setrDeliveryMethod(ibu.getDetail(DELIVERY_METHOD));
            kartuIbuClient.setLaborComplication(ibu.getDetail(COMPLICATION));

            if(ibu.isANC()) {
                kartuIbuClient
                        .withStatus(EasyMap.create(STATUS_TYPE_FIELD, ANC_STATUS)
                                .put(STATUS_DATE_FIELD, ibu.getReferenceDate()).map());
                kartuIbuClient.setIsPregnant(true);
            } else if(ibu.isPNC()) {
                kartuIbuClient.withStatus(EasyMap.create(STATUS_TYPE_FIELD, PNC_STATUS)
                        .put(STATUS_DATE_FIELD, ibu.getReferenceDate()).map());

                kartuIbuClient.setIsPregnant(false);
            }
            return;
        }
    }

    public boolean isMotherInANCorPNC(String entityId) {
        Ibu ibu = allKohort.findIbuWithOpenStatusByKIId(entityId);
        if(ibu==null) return false;
        return ibu.isANC() || ibu.isPNC();
    }

    public CharSequence[] getRandomNameChars(final SmartRegisterClient client) {
        return onRandomNameChars(client, getKartuIbuClients(), allKartuIbus.randomDummyName(), AllConstantsINA.DIALOG_DOUBLE_SELECTION_NUM);
    }

}
