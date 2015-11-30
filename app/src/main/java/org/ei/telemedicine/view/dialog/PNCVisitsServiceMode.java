package org.ei.telemedicine.view.dialog;

import static android.view.View.VISIBLE;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_VISIT;
import static org.ei.telemedicine.Context.getInstance;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.domain.ANCServiceType;
import org.ei.telemedicine.provider.SmartRegisterClientsProvider;
import org.ei.telemedicine.util.DateUtil;
import org.ei.telemedicine.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;
import org.ei.telemedicine.view.contract.ANCSmartRegisterClient;
import org.ei.telemedicine.view.contract.AlertDTO;
import org.ei.telemedicine.view.contract.AlertStatus;
import org.ei.telemedicine.view.contract.ChildSmartRegisterClient;
import org.ei.telemedicine.view.contract.FPSmartRegisterClient;
import org.ei.telemedicine.view.contract.ServiceProvidedDTO;
import org.ei.telemedicine.view.contract.pnc.PNCFirstSevenDaysVisits;
import org.ei.telemedicine.view.contract.pnc.PNCSmartRegisterClient;
import org.ei.telemedicine.view.viewHolder.NativeANCSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativePNCSmartRegisterViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

public class PNCVisitsServiceMode extends ServiceModeOption {
    public static final AlertDTO emptyAlert = new AlertDTO("", "", "");
    private final LayoutInflater inflater;

    public PNCVisitsServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
        this.inflater = LayoutInflater.from(Context.getInstance().applicationContext());
    }

    @Override
    public String name() {
        return getInstance().getStringResource(R.string.pnc_register_service_mode_pnc_visits);
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
                return new int[]{24, 8, 7, 15, 22, 24};
            }

            @Override
            public int[] headerTextResourceIds() {
                return new int[]{
                        R.string.header_name, R.string.pnc_no, R.string.header_days_pp,
                        R.string.header_delivery_complications, R.string.header_first_seven_days, R.string.header_pnc_visits};
            }
        };
    }

    @Override
    public void setupListView(PNCSmartRegisterClient client,
                              NativePNCSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {
        viewHolder.pncVisitsServiceModeView().setVisibility(VISIBLE);
        setupDaysPPView(client, viewHolder);
        setupComplicationsView(client, viewHolder);
        setUpPNCVisitsGraph(client, viewHolder);
        setUpPNCRecentVisits(client, viewHolder);
        setUpPNCVisitLayout(client, viewHolder);
    }

    private void setUpPNCVisitLayout(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder) {
        if (client.isVisitsDone()) {
            viewHolder.txtPNCVisitDoneOn().setVisibility(VISIBLE);
            viewHolder.txtPNCVisitDoneOn().setText(client.visitDoneDateWithVisitName());
        } else {
            viewHolder.txtPNCVisitDoneOn().setVisibility(View.INVISIBLE);
        }

        AlertDTO pncVisitAlert = client.getAlert(ANCServiceType.PNC);
        if (!pncVisitAlert.equals(emptyAlert)) {
            viewHolder.btnPncVisitView().setVisibility(View.VISIBLE);
            viewHolder.layoutPNCVisitAlert().setVisibility(VISIBLE);
            viewHolder.layoutPNCVisitAlert().setOnClickListener(launchForm(client, pncVisitAlert, PNC_VISIT));
            setAlertLayout(viewHolder.layoutPNCVisitAlert(),
                    viewHolder.txtPNCVisitDueType(),
                    viewHolder.txtPNCVisitAlertDueOn(),
                    pncVisitAlert);
        } else {
            viewHolder.btnPncVisitView().setVisibility(View.VISIBLE);
            viewHolder.layoutPNCVisitAlert().setVisibility(VISIBLE);
            viewHolder.layoutPNCVisitAlert().setOnClickListener(launchForm(client, pncVisitAlert, PNC_VISIT));
        }
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

    private View.OnClickListener launchForm(PNCSmartRegisterClient client, AlertDTO alert, String formName) {
        return provider().newFormLauncher(formName, client.entityId(), "{\"entityId\":\"" + client.entityId() + "\"}");
    }

    private void setUpPNCRecentVisits(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder) {
        viewHolder.recentPNCVisits().removeAllViews();
        for (ServiceProvidedDTO serviceProvided : client.recentlyProvidedServices()) {
            ViewGroup recentVisitsGroup = (ViewGroup) inflater.inflate(R.layout.smart_register_pnc_recent_visits_layout, null);
            ((TextView) recentVisitsGroup.findViewById(R.id.txt_recent_visit_day)).setText(String.valueOf(serviceProvided.day()));
            ((TextView) recentVisitsGroup.findViewById(R.id.txt_recent_visit_date)).setText(serviceProvided.dateForDisplay());
            viewHolder.recentPNCVisits().addView(recentVisitsGroup);
        }
    }

    private void setupDaysPPView(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder) {
        viewHolder.txtNumberOfVisits().setText(String.valueOf(DateUtil.dayDifference(client.deliveryDate(), DateUtil.today())));
        viewHolder.txtDOB().setText(client.deliveryShortDate());
    }

    private void setupComplicationsView(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder) {
        viewHolder.txtVisitComplicationsView().setText(client.pncComplications());
    }

    private void setUpPNCVisitsGraph(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder) {
        String jsonString = new Gson().toJson(client.firstSevenDaysVisits(), PNCFirstSevenDaysVisits.class);
        viewHolder.wbvPncVisitsGraph().loadUrl("javascript:drawSevenDayGraphic('" + jsonString + "')");
    }

    @Override
    public void setupListView(FPSmartRegisterClient client,
                              NativeFPSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(ChildSmartRegisterClient client,
                              NativeChildSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(ANCSmartRegisterClient client, NativeANCSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

}
