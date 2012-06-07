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

        String text = "";
        List<Beneficiary> beneficiaries = allBeneficiaries.findByECCaseId(caseId);
        for (int i = 0; i < beneficiaries.size(); i++) {
            Beneficiary beneficiary = beneficiaries.get(i);
            text += "  Pregnancy " + (i + 1) + ":\n";
            text += beneficiary.description();
            text += "\n\n";
        }
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(text);
    }
}
