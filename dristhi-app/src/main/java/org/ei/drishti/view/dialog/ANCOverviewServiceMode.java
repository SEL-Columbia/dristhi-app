package org.ei.drishti.view.dialog;

import android.view.View;
import android.widget.TextView;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.domain.ANCServiceType;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.*;
import org.ei.drishti.view.viewHolder.NativeANCSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.OnClickFormLauncher;

import static android.view.View.VISIBLE;
import static org.ei.drishti.AllConstants.FormNames.ANC_VISIT;
import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;
import static org.ei.drishti.view.contract.AlertDTO.emptyAlert;

public class ANCOverviewServiceMode extends ServiceModeOption {

    public ANCOverviewServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.anc_service_mode_overview);
    }

    @Override
    public ClientsHeaderProvider getHeaderProvider() {
        return new ClientsHeaderProvider() {
            @Override
            public int count() {
                return 6;
            }

            @Override
            public int weightSum() {
                return 1000;
            }

            @Override
            public int[] weights() {
                return new int[]{210, 84, 124, 126, 126, 210};
            }

            @Override
            public int[] headerTextResourceIds() {
                return new int[]{
                        R.string.header_name, R.string.header_id, R.string.header_anc_status,
                        R.string.header_risk_factors, R.string.header_visits, R.string.header_tt, R.string.header_ifa};
            }
        };
    }

    @Override
    public void setupListView(ChildSmartRegisterClient client, NativeChildSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(ANCSmartRegisterClient client,
                              NativeANCSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {
        viewHolder.serviceModeOverviewView().setVisibility(VISIBLE);

        viewHolder.txtRiskFactors().setText(client.riskFactors());
        setupANCVisitLayout(client, viewHolder);

    }

    @Override
    public void setupListView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

    public void setupANCVisitLayout(ANCSmartRegisterClient client,
                                    NativeANCSmartRegisterViewHolder viewHolder) {
        if (client.isVisitsDone()) {
            viewHolder.txtANCVisitDoneOn().setVisibility(VISIBLE);
            viewHolder.txtANCVisitDoneOn().setText(client.visitDoneDate());
        } else {
            viewHolder.txtANCVisitDoneOn().setVisibility(View.INVISIBLE);
        }

        AlertDTO ancVisitAlert = client.getAlert(ANCServiceType.ANC_1);
        if (ancVisitAlert != emptyAlert) {
            viewHolder.btnAncVisitView().setVisibility(View.INVISIBLE);
            viewHolder.layoutANCVisitAlert().setVisibility(VISIBLE);
            viewHolder.layoutANCVisitAlert().setOnClickListener(launchANCVisitForm(client, ancVisitAlert));
            setAlertLayout(viewHolder.layoutANCVisitAlert(),
                    viewHolder.txtANCVisitDueType(),
                    viewHolder.txtANCVisitAlertDueOn(),
                    ancVisitAlert);
        } else {
            viewHolder.layoutANCVisitAlert().setVisibility(View.INVISIBLE);
            viewHolder.btnAncVisitView().setVisibility(View.INVISIBLE);
            viewHolder.btnAncVisitView().setOnClickListener(launchANCVisitForm(client, ancVisitAlert));
        }
    }

    private OnClickFormLauncher launchANCVisitForm(ANCSmartRegisterClient client, AlertDTO alert) {
        return provider().newFormLauncher(ANC_VISIT, client.entityId(), "{\"entityId\":\"" + client.entityId() + "\",\"alertName\":\"" + alert.name() + "\"}");
    }

    private void setAlertLayout(View layout, TextView typeView,
                                TextView dateView, AlertDTO alert) {
        typeView.setText(alert.ancServiceType().shortName());
        dateView.setText("due " + alert.shortDate());

        final AlertStatus alertStatus = alert.alertStatus();
        layout.setBackgroundResource(alertStatus.backgroundColorResourceId());
        typeView.setTextColor(alertStatus.fontColor());
        dateView.setTextColor(alertStatus.fontColor());
    }
}
