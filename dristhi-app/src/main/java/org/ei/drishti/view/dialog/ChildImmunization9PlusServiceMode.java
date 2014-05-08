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
import org.ei.drishti.view.contract.ChildSmartRegisterClient;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static org.ei.drishti.dto.AlertStatus.*;
import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public class ChildImmunization9PlusServiceMode extends ServiceModeOption {
    public ChildImmunization9PlusServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.child_service_mode_immunization_9_plus);
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
                        R.string.header_name, R.string.header_id_no, R.string.header_measles,
                        R.string.header_opv_booster, R.string.header_dpt_booster, R.string.header_vitamin_a};
            }
        };

    }

    @Override
    public void setupListView(ChildSmartRegisterClient client,
                              NativeChildSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {
        viewHolder.serviceModeImmunization9PlusView().setVisibility(VISIBLE);

        setupMeaslesLayout(client, viewHolder, clientSectionClickListener);
        setupOpvBoosterLayout(client, viewHolder, clientSectionClickListener);
        setupDptBoosterLayout(client, viewHolder, clientSectionClickListener);
        setupVitaminALayout(client, viewHolder, clientSectionClickListener);
    }

    public void setupMeaslesLayout(ChildSmartRegisterClient client,
                                  NativeChildSmartRegisterViewHolder viewHolder,
                                  View.OnClickListener clientSectionClickListener) {
        if (client.isMeaslesDone()) {
            viewHolder.measlesDoneOnView().setVisibility(VISIBLE);
            viewHolder.measlesDoneOnView().setText(client.measlesDoneDate());
        } else {
            viewHolder.measlesDoneOnView().setVisibility(INVISIBLE);
        }

        AlertDTO measlesAlert = client.getAlert(ChildServiceType.MEASLES);
        if (measlesAlert != null) {
            viewHolder.addMeaslesView().setVisibility(INVISIBLE);
            viewHolder.layoutMeaslesAlertView().setVisibility(VISIBLE);
            setAlertLayout(viewHolder.layoutMeaslesAlertView(),
                    viewHolder.measlesAlertDueTypeView(),
                    viewHolder.measlesAlertDueOnView(),
                    measlesAlert);
        } else {
            viewHolder.layoutMeaslesAlertView().setVisibility(INVISIBLE);
            viewHolder.addMeaslesView().setVisibility(VISIBLE);
            viewHolder.addMeaslesView().setOnClickListener(clientSectionClickListener);
        }
    }

    public void setupOpvBoosterLayout(ChildSmartRegisterClient client,
                                   NativeChildSmartRegisterViewHolder viewHolder,
                                   View.OnClickListener clientSectionClickListener) {
        if (client.isOpvBoosterDone()) {
            viewHolder.opvBoosterDoneOnView().setVisibility(VISIBLE);
            viewHolder.opvBoosterDoneOnView().setText(client.opvBoosterDoneDate());
        } else {
            viewHolder.opvBoosterDoneOnView().setVisibility(INVISIBLE);
        }

        AlertDTO opvBoosterAlert = client.getAlert(ChildServiceType.OPV_BOOSTER);
        if (opvBoosterAlert != null) {
            viewHolder.addOpvBoosterView().setVisibility(INVISIBLE);
            viewHolder.layoutOpvBoosterAlertView().setVisibility(VISIBLE);
            setAlertLayout(viewHolder.layoutOpvBoosterAlertView(),
                    viewHolder.opvBoosterAlertDueTypeView(),
                    viewHolder.opvBoosterAlertDueOnView(),
                    opvBoosterAlert);
        } else {
            viewHolder.layoutOpvBoosterAlertView().setVisibility(INVISIBLE);
            viewHolder.addOpvBoosterView().setVisibility(VISIBLE);
            viewHolder.addOpvBoosterView().setOnClickListener(clientSectionClickListener);
        }
    }

    public void setupDptBoosterLayout(ChildSmartRegisterClient client,
                                      NativeChildSmartRegisterViewHolder viewHolder,
                                      View.OnClickListener clientSectionClickListener) {
        if (client.isDptBoosterDone()) {
            viewHolder.dptBoosterDoneOnView().setVisibility(VISIBLE);
            viewHolder.dptBoosterDoneOnView().setText(client.dptBoosterDoneDate());
        } else {
            viewHolder.dptBoosterDoneOnView().setVisibility(INVISIBLE);
        }

        AlertDTO dptBoosterAlert = client.getDptBoosterAlert();
        if (dptBoosterAlert != null) {
            viewHolder.addDptBoosterView().setVisibility(INVISIBLE);
            viewHolder.layoutDptBoosterAlertView().setVisibility(VISIBLE);
            setAlertLayout(viewHolder.layoutDptBoosterAlertView(),
                    viewHolder.dptBoosterAlertDueTypeView(),
                    viewHolder.dptBoosterAlertDueOnView(),
                    dptBoosterAlert);
        } else {
            viewHolder.layoutDptBoosterAlertView().setVisibility(INVISIBLE);
            viewHolder.addDptBoosterView().setVisibility(VISIBLE);
            viewHolder.addDptBoosterView().setOnClickListener(clientSectionClickListener);
        }
    }

    public void setupVitaminALayout(ChildSmartRegisterClient client,
                                      NativeChildSmartRegisterViewHolder viewHolder,
                                      View.OnClickListener clientSectionClickListener) {
        if (client.isVitaminADone()) {
            viewHolder.vitaminADoneOnView().setVisibility(VISIBLE);
            viewHolder.vitaminADoneOnView().setText(client.vitaminADoneDate());
        } else {
            viewHolder.vitaminADoneOnView().setVisibility(INVISIBLE);
        }

        AlertDTO vitaminAAlert = client.getAlert(ChildServiceType.VITAMIN_A);
        if (vitaminAAlert != null) {
            viewHolder.addVitaminAView().setVisibility(INVISIBLE);
            viewHolder.layoutVitaminAAlertView().setVisibility(VISIBLE);
            setAlertLayout(viewHolder.layoutVitaminAAlertView(),
                    viewHolder.vitaminAAlertDueTypeView(),
                    viewHolder.vitaminAAlertDueOnView(),
                    vitaminAAlert);
        } else {
            viewHolder.layoutVitaminAAlertView().setVisibility(INVISIBLE);
            viewHolder.addVitaminAView().setVisibility(VISIBLE);
            viewHolder.addVitaminAView().setOnClickListener(clientSectionClickListener);
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
