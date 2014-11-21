package org.ei.drishti.view.dialog;

import android.view.View;
import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.view.contract.*;
import org.ei.drishti.view.viewHolder.*;

import static android.view.View.VISIBLE;
import static org.ei.drishti.Context.getInstance;
import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public class PNCVisitsServiceMode extends ServiceModeOption {

    public PNCVisitsServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
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
                return new int[]{24, 10, 7, 15, 22, 22};
            }

            @Override
            public int[] headerTextResourceIds() {
                return new int[]{
                        R.string.header_name, R.string.header_thayi_number, R.string.header_days_pp,
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
    }

    private void setupDaysPPView(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder) {
        viewHolder.txtNumberOfVisits().setText(String.valueOf(DateUtil.dayDifference(client.deliveryDate(), DateUtil.today())));
        viewHolder.txtDOB().setText(client.deliveryShortDate());
    }

    private void setupComplicationsView(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder) {
        viewHolder.txtVisitComplicationsView().setText(client.deliveryComplications());
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
