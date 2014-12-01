package org.ei.drishti.view.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.ei.drishti.AllConstants;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.*;
import org.ei.drishti.view.contract.pnc.PNCSmartRegisterClient;
import org.ei.drishti.view.viewHolder.*;

import static android.view.View.VISIBLE;
import static org.ei.drishti.AllConstants.HbTestFields.HB_LEVEL;
import static org.ei.drishti.Context.getInstance;
import static org.ei.drishti.R.string.*;
import static org.ei.drishti.domain.ANCServiceType.HB_TEST;
import static org.ei.drishti.util.IntegerUtil.tryParse;
import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;
import static org.ei.drishti.view.contract.AlertDTO.emptyAlert;
import static org.ei.drishti.view.contract.AlertStatus.COMPLETE;

public class HbIFAServiceMode extends ServiceModeOption {

    private LayoutInflater inflater;

    public HbIFAServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
        this.inflater = LayoutInflater.from(Context.getInstance().applicationContext());
    }

    @Override
    public String name() {
        return getInstance().getStringResource(anc_service_mode_hb_ifa);
    }

    @Override
    public ClientsHeaderProvider getHeaderProvider() {
        return new ClientsHeaderProvider() {
            @Override
            public int count() {
                return 5;
            }

            @Override
            public int weightSum() {
                return 100;
            }

            @Override
            public int[] weights() {
                return new int[]{21, 9, 12, 26, 26};
            }

            @Override
            public int[] headerTextResourceIds() {
                return new int[]{
                        header_name, header_id, header_anc_status,
                        header_hb, header_ifa};
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
        viewHolder.serviceModeHbIFAViewsHolder().setVisibility(VISIBLE);

        setupHbDetailsLayout(client, viewHolder);
        setupHbAlertLayout(client, viewHolder);
    }

    @Override
    public void setupListView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

    public void setupHbDetailsLayout(ANCSmartRegisterClient client,
                                     NativeANCSmartRegisterViewHolder viewHolder) {
        viewHolder.layoutHbDetailsViewHolder().removeAllViews();
        for (ServiceProvidedDTO serviceProvided : client.servicesProvided()) {
            if (serviceProvided.name().equalsIgnoreCase(HB_TEST.displayName())) {
                String hbLevel = serviceProvided.data().get(HB_LEVEL);
                ViewGroup hbDetailsViewGroup = (ViewGroup) inflater.inflate(R.layout.smart_register_anc_hb_details_layout, null);
                ((TextView) hbDetailsViewGroup.findViewById(R.id.txt_hb_date)).setText(serviceProvided.shortDate());
                ((TextView) hbDetailsViewGroup.findViewById(R.id.txt_hb_level)).setText(hbLevel + getInstance().getStringResource(anc_service_mode_hb_unit));
                hbDetailsViewGroup.findViewById(R.id.hb_level_indicator)
                        .setBackgroundColor(getHbColor(tryParse(hbLevel, 0)));
                viewHolder.layoutHbDetailsViewHolder().addView(hbDetailsViewGroup);
            }
        }
    }

    private int getHbColor(int hbLevel) {
        if (hbLevel < 7)
            return getInstance().getColorResource(R.color.hb_level_dangerous);
        else if (hbLevel >= 7 && hbLevel < 11) {
            return getInstance().getColorResource(R.color.hb_level_high);
        } else {
            return getInstance().getColorResource(R.color.hb_level_normal);
        }
    }

    public void setupHbAlertLayout(ANCSmartRegisterClient client,
                                   NativeANCSmartRegisterViewHolder viewHolder) {
        AlertDTO hbAlert = client.getAlert(HB_TEST);
        if (hbAlert != emptyAlert) {
            viewHolder.btnHbView().setVisibility(View.INVISIBLE);
            viewHolder.layoutHbAlert().setVisibility(VISIBLE);
            viewHolder.layoutHbAlert().setOnClickListener(launchForm(AllConstants.FormNames.HB_TEST,client, hbAlert));
            setAlertLayout(viewHolder.layoutHbAlert(),
                    viewHolder.txtHbDueType(),
                    viewHolder.txtHbDueOn(),
                    hbAlert);
        } else {
            viewHolder.layoutHbAlert().setVisibility(View.INVISIBLE);
            viewHolder.btnHbView().setVisibility(View.VISIBLE);
            viewHolder.btnAncVisitView().setOnClickListener(provider().newFormLauncher(AllConstants.FormNames.HB_TEST, client.entityId(), null));
        }
    }

    private OnClickFormLauncher launchForm(String formName, ANCSmartRegisterClient client, AlertDTO alert) {
        return provider().newFormLauncher(formName, client.entityId(), "{\"entityId\":\"" + client.entityId() + "\",\"alertName\":\"" + alert.name() + "\"}");
    }

    private void setAlertLayout(View layout, TextView typeView,
                                TextView dateView, AlertDTO alert) {
        setAlertDate(dateView, alert);
        typeView.setText(alert.ancServiceType().shortName());

        final AlertStatus alertStatus = alert.alertStatus();
        layout.setBackgroundResource(alertStatus.backgroundColorResourceId());
        typeView.setTextColor(alertStatus.fontColor());
        dateView.setTextColor(alertStatus.fontColor());
    }

    private void setAlertDate(TextView dateView, AlertDTO alert) {
        if (alert.status().equalsIgnoreCase(COMPLETE.name()))
            dateView.setText(alert.shortDate());
        else
            dateView.setText(getInstance().getStringResource(R.string.str_due) + alert.shortDate());
    }

}
