package org.ei.opensrp.indonesia.view.controller;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ei.opensrp.indonesia.view.contract.AnakClient;
import org.ei.opensrp.indonesia.view.contract.KIANCClient;
import org.ei.opensrp.indonesia.view.contract.KartuIbuClient;
import org.ei.opensrp.indonesia.view.contract.KartuIbuClients;
import org.ei.opensrp.util.EasyMap;
import org.ei.opensrp.view.contract.SmartRegisterClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dimas Ciputra on 6/23/15.
 */
public class ReportingController {

    private final KartuIbuRegisterController kartuIbuRegisterController;
    private final KIANCRegisterController kartuIbuANCRegisterController;
    private final AnakRegisterController anakRegisterController;
    public static final String SUBJECT_REPORTS = "subject";
    public static final String VALUE_REPORTS = "value";

    public ReportingController(KartuIbuRegisterController kartuIbuRegisterController,
                               KIANCRegisterController kartuIbuANCRegisterController,
                               AnakRegisterController anakRegisterController) {
        this.kartuIbuRegisterController = kartuIbuRegisterController;
        this.kartuIbuANCRegisterController = kartuIbuANCRegisterController;
        this.anakRegisterController = anakRegisterController;
    }

    public List<Map<String, String>> getReports() {
        List<Map<String, String>> reports = new ArrayList<>();

        KartuIbuClients kiClients = kartuIbuRegisterController.getKartuIbuClients();

        List<KIANCClient> ancClients = new Gson().fromJson(kartuIbuANCRegisterController.get(), new TypeToken<List<KIANCClient>>() {
        }.getType());

        List<AnakClient> anakClients = new Gson().fromJson(anakRegisterController.get(), new TypeToken<List<AnakClient>>(){}.getType());

        // Anc visit 1, 2, 3, 4 and greater
        int[] sumAncVisits = {0,0,0,0};
        int komplikasi1 = 0;
        int komplikasi2 = 0;
        int kb = 0;
        int anemia = 0;
        int kek = 0;
        int hipertensi = 0;
        int diabetes = 0;

        // Anak
        int hb = 0;
        int bcg_pol1 = 0;
        int dpt_hb1_pol2 = 0;
        int dpt_hb2_pol3 = 0;
        int dpt_hb3_pol4 = 0;

        for (KIANCClient ancClient : ancClients) {
            if(!Strings.isNullOrEmpty(ancClient.getAncVisitNumber())) {
                int ancVisit = Integer.parseInt(ancClient.getAncVisitNumber());
                ancVisit = (ancVisit > 4) ? 4 : ancVisit;
                sumAncVisits[ancVisit-1]++;
            }

            if(!Strings.isNullOrEmpty(ancClient.getRiwayatKomplikasiKebidanan())) {
                komplikasi1++;
            }
        }

        for(SmartRegisterClient client : kiClients) {
            KartuIbuClient kartuIbuClient = (KartuIbuClient) client;
            if(kartuIbuClient.hasLaborComplication()) {
                komplikasi2++;
            }
            if(!kartuIbuClient.kbMethod().equalsIgnoreCase("-")) {
                kb++;
            }
            if(kartuIbuClient.isPregnant() && (kartuIbuClient.isAnemia() || kartuIbuClient.isSevereAnemia())) {
                anemia++;
            }
            if(kartuIbuClient.isPregnant() && kartuIbuClient.isProteinEnergyMalnutrition()) {
                kek++;
            }
            if(kartuIbuClient.isPregnant() && kartuIbuClient.isHypertension()) {
                hipertensi++;
            }
            if(kartuIbuClient.isPregnant() && kartuIbuClient.isDiabetes()) {
                diabetes++;
            }
        }

        for(AnakClient client: anakClients) {

            hb = client.getHb07().equalsIgnoreCase("-") ? hb : hb+1;
            bcg_pol1 = client.getBcgPol1().equalsIgnoreCase("-") ? bcg_pol1 : bcg_pol1+1;
            dpt_hb1_pol2 = client.getDptHb1Pol2().equalsIgnoreCase("-") ? dpt_hb1_pol2 : dpt_hb1_pol2+1;
            dpt_hb2_pol3 = client.getDptHb2Pol3().equalsIgnoreCase("-") ? dpt_hb2_pol3 : dpt_hb2_pol3+1;
            dpt_hb3_pol4 = client.getDptHb3Pol4().equalsIgnoreCase("-") ? dpt_hb3_pol4 : dpt_hb3_pol4+1;

        }

        for(int i = 0; i < sumAncVisits.length; i++) {
            reports.add(createReport("Cakupan Pelayanan KIA K" + (i + 1), sumAncVisits[i] + ""));
        }

        reports.add(createReport("Deteksi Faktor Resiko dan Komplikasi", komplikasi1 + ""));
        reports.add(createReport("Pelayanan Komplikasi Maternal Ditemukan", komplikasi2 + ""));
        reports.add(createReport("Keluarga Berencana Aktif", kb + ""));
        reports.add(createReport("Bumil Anemia", anemia + ""));
        reports.add(createReport("Bumil KEK", kek + ""));
        reports.add(createReport("Bumil Diabetes", diabetes + ""));
        reports.add(createReport("Bumil Hipertensi", hipertensi + ""));

        reports.add(createReport("Cakupan Hb Anak", hb+""));
        reports.add(createReport("Cakupan BCG/Pol1 Anak", bcg_pol1+""));
        reports.add(createReport("Cakupan DPT/Hb1/Pol2 Anak", dpt_hb1_pol2+""));
        reports.add(createReport("Cakupan DPT/Hb2/Pol3 Anak", dpt_hb2_pol3+""));
        reports.add(createReport("Cakupan DPT/Hb3/Pol4 Anak", dpt_hb3_pol4+""));

        return reports;
    }

    private Map<String, String> createReport(String subject, String value) {
        return EasyMap.create(SUBJECT_REPORTS, subject).put(VALUE_REPORTS, value).map();
    }
}