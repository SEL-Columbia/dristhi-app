package org.ei.opensrp.indonesia.view.controller;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.FluentIterable;
import com.google.gson.Gson;

import org.ei.opensrp.indonesia.domain.Ibu;
import org.ei.opensrp.indonesia.domain.KartuIbu;
import org.ei.opensrp.indonesia.repository.AllKartuIbus;
import org.ei.opensrp.indonesia.repository.AllKohort;
import org.ei.opensrp.indonesia.view.contract.KartuIbuClient;
import org.ei.opensrp.view.contract.SmartRegisterClient;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.gmariotti.cardslib.library.prototypes.CardWithList;

import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.EDD;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.HUSBAND_NAME;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.IS_HIGH_PRIORITY;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.IS_HIGH_RISK;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.IS_HIGH_RISK_LABOUR;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.IS_HIGH_RISK_PREGNANCY;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.KABUPATEN;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.MOTHER_ADDRESS;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.MOTHER_AGE;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.MOTHER_BLOOD_TYPE;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.MOTHER_DOB;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.MOTHER_NAME;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.MOTHER_NUMBER;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.NUMBER_ABORTIONS;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.NUMBER_OF_LIVING_CHILDREN;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.NUMBER_OF_PREGNANCIES;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.NUMBER_PARTUS;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.POSYANDU_NAME;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.PROPINSI;
import static org.ei.opensrp.indonesia.AllConstantsINA.KartuIbuFields.PUSKESMAS_NAME;

/**
 * Created by Dimas Ciputra on 9/21/15.
 */
public class ProfileDetailController {
    private final String caseId;
    private final KartuIbuRegisterController controller;
    private final AllKartuIbus allKartuIbus;
    private CharSequence[] filterKeys = {"isoutofarea", "high", "submission", "registration", "risk", "namalengkap", "hidden"};
    private CharSequence[] filterValues = {"invalid"};
    private final AllKohort allKohort;

    public ProfileDetailController(String caseId, KartuIbuRegisterController controller, AllKartuIbus allKartuIbus, AllKohort allKohort) {
        this.caseId = caseId;
        this.controller = controller;
        this.allKartuIbus = allKartuIbus;
        this.allKohort = allKohort;
    }

    public KartuIbuClient get() {
        return controller.readKartuIbu(allKartuIbus.findByCaseID(caseId));
    }

    public boolean containsCaseInsensitive(String s, CharSequence[] l) {
        for(int i=0;i<l.length;i++) {
            if(s.toLowerCase().contains(l[i])) {
                return true;
            }
        }
        return false;
    }

    public Map<String, String> detailMap() {

        KartuIbu kartuIbu = allKartuIbus.findByCaseID(caseId);
        Ibu ibuDetail = allKohort.findIbuByKartuIbuId(kartuIbu.getCaseId());

        Map<String, String> detail = kartuIbu.getDetails();

        if(ibuDetail != null) {
            detail.putAll(ibuDetail.getDetails());
        }

        Predicate<String> filterKey = new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return !containsCaseInsensitive(input, filterKeys);
            }
        };

        Predicate<String> filterValue = new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return !containsCaseInsensitive(input, filterValues);
            }
        };

        Map<String, String> dMap = new TreeMap<>(Maps.filterKeys(kartuIbu.getDetails(), filterKey));

        return dMap;
    }

    public List<CardWithList.ListObject> getListObject() {
        return null;
    }

    public String getClientJson() {
        return new Gson().toJson(get());
    }
}
