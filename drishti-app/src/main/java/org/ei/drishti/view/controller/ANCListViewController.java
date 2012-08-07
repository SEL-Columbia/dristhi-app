package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.view.activity.ANCDetailActivity;
import org.ei.drishti.view.contract.ANC;

import java.util.ArrayList;
import java.util.List;

public class ANCListViewController {
    private final AllBeneficiaries allBeneficiaries;
    private AllEligibleCouples allEligibleCouples;
    private final Context context;

    public ANCListViewController(AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples, Context context) {
        this.allBeneficiaries = allBeneficiaries;
        this.allEligibleCouples = allEligibleCouples;
        this.context = context;
    }

    public String get() {
        List<Beneficiary> beneficiaries = allBeneficiaries.allANCs();
        List<ANC> ancs = new ArrayList<ANC>();
        for (Beneficiary beneficiary : beneficiaries) {
            EligibleCouple ec = allEligibleCouples.findByCaseID(beneficiary.ecCaseId());
            ancs.add(new ANC(beneficiary.caseId(), ec.wifeName(), ec.village(), beneficiary.thaayiCardNumber(), false));
        }
        return new Gson().toJson(ancs);
    }

    public void startANC(String caseId) {
        Intent intent = new Intent(context.getApplicationContext(), ANCDetailActivity.class);
        intent.putExtra("caseId", caseId);
        context.startActivity(intent);
    }
}
