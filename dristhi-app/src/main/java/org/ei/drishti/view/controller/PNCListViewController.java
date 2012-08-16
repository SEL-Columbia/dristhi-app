package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.view.activity.PNCDetailActivity;
import org.ei.drishti.view.contract.PNC;

import java.util.ArrayList;
import java.util.List;

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
        List<PNC> pncs = new ArrayList<PNC>();
        List<Mother> mothers = allBeneficiaries.allPNCs();
        for (Mother mother : mothers) {
            EligibleCouple couple = allEligibleCouples.findByCaseID(mother.ecCaseId());
            pncs.add(new PNC(mother.caseId(), couple.wifeName(), mother.thaayiCardNumber(), couple.village(), mother.isHighRisk()));
        }
        return new Gson().toJson(pncs);
    }

    public void startPNC(String caseId) {
        Intent intent = new Intent(context.getApplicationContext(), PNCDetailActivity.class);
        intent.putExtra("caseId", caseId);
        context.startActivity(intent);
    }
}
