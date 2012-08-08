package org.ei.drishti.view.controller;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.view.contract.ANCDetail;
import org.ei.drishti.view.contract.FacilityDetails;
import org.ei.drishti.view.contract.LocationDetails;
import org.ei.drishti.view.contract.PregnancyDetails;

import static android.widget.Toast.LENGTH_SHORT;

public class ANCDetailController {
    private final Context context;
    private final String caseId;
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private final AllTimelineEvents allTimelineEvents;

    public ANCDetailController(Context context, String caseId, AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, AllTimelineEvents allTimelineEvents) {
        this.context = context;
        this.caseId = caseId;
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.allTimelineEvents = allTimelineEvents;
    }

    public String get() {
        Mother mother = allBeneficiaries.findMother(caseId);
        EligibleCouple couple = allEligibleCouples.findByCaseID(mother.ecCaseId());

        ANCDetail detail = new ANCDetail(caseId, mother.thaayiCardNumber(), couple.wifeName(),
                new LocationDetails(couple.village(), couple.subCenter()),
                new PregnancyDetails(mother.isHighRisk(), "Anaemic", "7", "24/8/12"),
                new FacilityDetails("Broadway", "----", "Shiwani"));

        return new Gson().toJson(detail);
    }

    public void startCommCare() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(ComponentName.unflattenFromString("org.commcare.dalvik/.activities.CommCareHomeActivity"));
        intent.addCategory("android.intent.category.Launcher");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context.getApplicationContext(), "CommCare ODK is not installed.", LENGTH_SHORT).show();
        }
    }
}
