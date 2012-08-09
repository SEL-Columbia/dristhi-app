package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.view.activity.ANCDetailActivity;
import org.ei.drishti.view.activity.PNCDetailActivity;
import org.ei.drishti.view.activity.PNCListActivity;

public class PNCListViewController {
    private final Context context;
    private final AllBeneficiaries allBeneficiaries;
    private final AllEligibleCouples allEligibleCouples;

    public PNCListViewController(Context context, AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples) {
        this.context = context;
        this.allBeneficiaries = allBeneficiaries;
        this.allEligibleCouples = allEligibleCouples;
    }

    public String get() {
        return "[{\"caseId\":\"12345\",\"womanName\":\"PNC 1\",\"thaayiCardNumber\":\"TC Number 1\",\"villageName\":\"Village 1\",\"isHighRisk\":true},{\"caseId\":\"11111\",\"womanName\":\"PNC 2\",\"thaayiCardNumber\":\"TC Number 2\",\"villageName\":\"Village 2\",\"isHighRisk\":false}]";
    }

    public void startPNC(String caseId) {
        Intent intent = new Intent(context.getApplicationContext(), PNCDetailActivity.class);
        intent.putExtra("caseId", caseId);
        context.startActivity(intent);
    }
}
