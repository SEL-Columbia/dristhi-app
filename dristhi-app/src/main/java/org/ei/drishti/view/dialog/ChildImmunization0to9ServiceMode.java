package org.ei.drishti.view.dialog;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.domain.ChildServiceType;
import org.ei.drishti.dto.AlertStatus;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.AlertDTO;
import org.ei.drishti.view.contract.ChildClient;
import org.ei.drishti.view.contract.ChildSmartRegisterClient;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;

import static org.ei.drishti.dto.AlertStatus.*;
import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public class ChildImmunization0to9ServiceMode extends ServiceModeOption {

    public ChildImmunization0to9ServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.child_service_mode_immunization_0_to_9);
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
                return 100;
            }

            @Override
            public int[] weights() {
                return new int[]{26, 14, 15, 15, 15, 15};
            }

            @Override
            public int[] headerTextResourceIds() {
                return new int[]{
                        R.string.header_name, R.string.header_id_no, R.string.header_bcg,
                        R.string.header_hep_b_birth, R.string.header_opv, R.string.header_pentavalent};
            }
        };

    }

    @Override
    public void setupListView(ChildSmartRegisterClient client,
                              NativeChildSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {
        viewHolder.serviceModeImmunization0to9View().setVisibility(View.VISIBLE);

        setupBcgLayout(client, viewHolder, clientSectionClickListener);
        setupHepBLayout(client, viewHolder, clientSectionClickListener);
        setupOpvLayout(client, viewHolder, clientSectionClickListener);
        setupPentavLayout(client, viewHolder, clientSectionClickListener);
    }

    private void setupBcgLayout(ChildSmartRegisterClient client,
                                NativeChildSmartRegisterViewHolder viewHolder,
                                View.OnClickListener clientSectionClickListener) {
        if (client.isBcgDone()) {
            viewHolder.bcgDoneLayout().setVisibility(View.VISIBLE);
            viewHolder.bcgDoneOnView().setText("On " + client.bcgDoneDate());
            viewHolder.bcgPendingView().setVisibility(View.INVISIBLE);
        } else {
            viewHolder.bcgDoneLayout().setVisibility(View.INVISIBLE);
            viewHolder.bcgPendingView().setVisibility(View.VISIBLE);
            viewHolder.bcgPendingView().setOnClickListener(clientSectionClickListener);
        }
    }

    public void setupOpvLayout(ChildSmartRegisterClient client,
                               NativeChildSmartRegisterViewHolder viewHolder,
                               View.OnClickListener clientSectionClickListener) {
        if (client.isOpvDone()) {
            viewHolder.opvDoneOnView().setVisibility(View.VISIBLE);
            viewHolder.opvDoneOnView().setText(client.opvDoneDate());
        } else {
            viewHolder.opvDoneOnView().setVisibility(View.INVISIBLE);
        }

        AlertDTO opvAlert = ((ChildClient) client).getOpvAlert();
        if (opvAlert != null) {
            viewHolder.addOpvView().setVisibility(View.INVISIBLE);
            viewHolder.layoutOpvAlertView().setVisibility(View.VISIBLE);
            setAlertLayout(viewHolder.layoutOpvAlertView(),
                    viewHolder.opvAlertDueTypeView(),
                    viewHolder.opvAlertDueOnView(),
                    opvAlert);
        } else {
            viewHolder.layoutOpvAlertView().setVisibility(View.INVISIBLE);
            viewHolder.addOpvView().setVisibility(View.VISIBLE);
            viewHolder.addOpvView().setOnClickListener(clientSectionClickListener);
        }
    }

    public void setupHepBLayout(ChildSmartRegisterClient client,
                                NativeChildSmartRegisterViewHolder viewHolder,
                                View.OnClickListener clientSectionClickListener) {
        if (client.isHepBDone()) {
            viewHolder.hepBDoneOnView().setVisibility(View.VISIBLE);
            viewHolder.hepBDoneOnView().setText(client.hepBDoneDate());
        } else {
            viewHolder.hepBDoneOnView().setVisibility(View.INVISIBLE);
        }

        AlertDTO hepBAlert = ((ChildClient) client).getAlert(ChildServiceType.HEPB_0);
        if (hepBAlert != null) {
            viewHolder.addHepBView().setVisibility(View.INVISIBLE);
            viewHolder.layoutHepBAlertView().setVisibility(View.VISIBLE);
            setAlertLayout(viewHolder.layoutHepBAlertView(),
                    viewHolder.hepBAlertDueTypeView(),
                    viewHolder.hepBAlertDueOnView(),
                    hepBAlert);
        } else {
            viewHolder.layoutHepBAlertView().setVisibility(View.INVISIBLE);
            viewHolder.addHepBView().setVisibility(View.VISIBLE);
            viewHolder.addHepBView().setOnClickListener(clientSectionClickListener);
        }
    }

    public void setupPentavLayout(ChildSmartRegisterClient client,
                                  NativeChildSmartRegisterViewHolder viewHolder,
                                  View.OnClickListener clientSectionClickListener) {
        if (client.isPentavDone()) {
            viewHolder.pentavDoneOnView().setVisibility(View.VISIBLE);
            viewHolder.pentavDoneOnView().setText(client.pentavDoneDate());
        } else {
            viewHolder.pentavDoneOnView().setVisibility(View.INVISIBLE);
        }

        AlertDTO pentavAlert = ((ChildClient) client).getPentavAlert();
        if (pentavAlert != null) {
            viewHolder.addPentavView().setVisibility(View.INVISIBLE);
            viewHolder.layoutPentavAlertView().setVisibility(View.VISIBLE);
            setAlertLayout(viewHolder.layoutPentavAlertView(),
                    viewHolder.pentavAlertDueTypeView(),
                    viewHolder.pentavAlertDueOnView(),
                    pentavAlert);
        } else {
            viewHolder.layoutPentavAlertView().setVisibility(View.INVISIBLE);
            viewHolder.addPentavView().setVisibility(View.VISIBLE);
            viewHolder.addPentavView().setOnClickListener(clientSectionClickListener);
        }
    }

    private void setAlertLayout(View layout, TextView typeView, TextView dateView, AlertDTO alert) {
        typeView.setText(alert.type().shortName());
        dateView.setText("due " + alert.shortDate());

        final AlertStatus alertStatus = alert.alertStatus();
        if (urgent.equals(alertStatus)) {
            layout.setBackgroundResource(R.color.alert_urgent_red);
            typeView.setTextColor(Color.WHITE);
            dateView.setTextColor(Color.WHITE);
        } else if (upcoming.equals(alertStatus)) {
            layout.setBackgroundResource(R.color.alert_upcoming_light_blue);
            typeView.setTextColor(Color.BLACK);
            dateView.setTextColor(Color.BLACK);
        } else if (inProcess.equals(alertStatus)) {
            layout.setBackgroundResource(android.R.color.holo_orange_light);
            typeView.setTextColor(Color.WHITE);
            dateView.setTextColor(Color.WHITE);
        } else if (complete.equals(alertStatus)) {
            layout.setBackgroundResource(R.color.alert_complete_green);
            typeView.setTextColor(Color.WHITE);
            dateView.setTextColor(Color.WHITE);
        } else if (AlertStatus.normal.equals(alertStatus)) {
            layout.setBackgroundResource(R.color.alert_in_progress_blue);
            typeView.setTextColor(Color.WHITE);
            dateView.setTextColor(Color.WHITE);
        } else {
            layout.setBackgroundResource(android.R.color.holo_purple);
        }
    }
}
