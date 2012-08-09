package org.ei.drishti.view.controller;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.view.activity.PNCDetailActivity;

import static android.widget.Toast.LENGTH_SHORT;

public class PNCDetailController {
    private final Context context;
    private final String caseId;
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private final AllTimelineEvents allTimelineEvents;

    public PNCDetailController(Context context, String caseId, AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, AllTimelineEvents allTimelineEvents) {
        this.context = context;
        this.caseId = caseId;
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.allTimelineEvents = allTimelineEvents;
    }

    public String get() {
        return "\"{\"womanName\":\"PNC 1\",\"caseId\":\"1234\",\"thaayiCardNumber\":\"TC Number 1\",\"location\":{\"villageName\":\"Village 1\",\"subcenter\":\"SubCenter 1\"},\"pncDetails\":{\"isHighRisk\":true,\"riskDetail\":\"Anaemia (active): 21 months\",\"daysPostpartum\":\"23\",\"dateOfDelivery\":\"24/03/12\",\"deliveryComplications\":[\"Prolonged Labor\"]},\"alerts\":[{\"message\":\"Alert 1\",\"formToOpen\":\"PNC\"},{\"message\":\"Alert 2\",\"formToOpen\":\"PNC\"}],\"todos\":[{\"message\":\"PNC Task #2\",\"formToOpen\":\"PNC\"},{\"message\":\"PNC Visit #3\",\"formToOpen\":\"PNC\"}],\"timelineEvents\":[{\"title\":\"Event 1\",\"details\":[\"Detail 1\",\"Detail 2\"],\"date\":\"1y 2m ago\"},{\"title\":\"Event 2\",\"details\":[\"Detail 3\",\"Detail 4\"],\"date\":\"2m 3d ago\"}]}\"";
    }

    public void startCommCare(String caseId, String formId) {
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
