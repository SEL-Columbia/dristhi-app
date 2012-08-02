package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.view.contract.ANC;

import java.util.ArrayList;
import java.util.List;

public class ANCListViewController {
    private final AllBeneficiaries allBeneficiaries;
    private final Context context;

    public ANCListViewController(AllBeneficiaries allBeneficiaries, Context context) {
        this.allBeneficiaries = allBeneficiaries;
        this.context = context;
    }

    public String get() {
        List<Beneficiary> beneficiaries = allBeneficiaries.allANCs();
        List<ANC> ancs = new ArrayList<ANC>();
        for (Beneficiary beneficiary : beneficiaries) {
            ancs.add(new ANC(beneficiary.caseId(), "TODO", "TODO", beneficiary.thaayiCardNumber(), false));
        }
        return new Gson().toJson(ancs);
    }

    public void startANC(String caseId) {

    }
}
