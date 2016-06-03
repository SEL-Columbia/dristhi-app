package org.ei.opensrp.indonesia.view.customControls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.view.contract.AnakClient;

/**
 * Created by Dimas Ciputra on 4/10/15.
 */
public class ClientAnakBirthStatusView extends RelativeLayout {
    private TextView txtVisitDate;
    private TextView txtCurrentWeight;

    @SuppressWarnings("UnusedDeclaration")
    public ClientAnakBirthStatusView(Context context) {
        this(context, null, 0);
    }

    @SuppressWarnings("UnusedDeclaration")
    public ClientAnakBirthStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClientAnakBirthStatusView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize() {
        txtVisitDate = (TextView) findViewById(R.id.txt_visit_date);
        txtCurrentWeight = (TextView) findViewById(R.id.txt_current_weight);
    }

    public void bindData(AnakClient client) {
        String birthWeight = client.currentWeight();
        String visitDate = client.getVisitDate();

        txtVisitDate.setText(visitDate);
        txtCurrentWeight.setText(birthWeight);
    }
}
