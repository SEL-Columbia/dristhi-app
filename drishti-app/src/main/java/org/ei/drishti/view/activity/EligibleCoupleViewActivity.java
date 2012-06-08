package org.ei.drishti.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.repository.AllBeneficiaries;

import java.util.List;

public class EligibleCoupleViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ec_view);

        Context context = Context.getInstance().updateApplicationContext(this.getApplicationContext());
        AllBeneficiaries allBeneficiaries = context.allBeneficiaries();

        String caseId = (String) getIntent().getExtras().get("caseId");
        String wifeName = (String) getIntent().getExtras().get("wifeName");

        setTitle(wifeName);

        List<Beneficiary> beneficiaries = allBeneficiaries.findByECCaseId(caseId);
        String text = "No pregnancy information found for this eligible couple.";
        if (beneficiaries.size() > 0) {
            text = pregnancyInfo(beneficiaries);
        }
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(text);
    }

    private String pregnancyInfo(List<Beneficiary> beneficiaries) {
        String text = "";
        for (int i = 0; i < beneficiaries.size(); i++) {
            Beneficiary beneficiary = beneficiaries.get(i);
            text += "Pregnancy " + (i + 1) + ":\n";
            text += beneficiary.description();
            text += "\n\n";
        }
        return text;
    }
}
