package org.ei.drishti.view.dialog;

import android.view.View;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.ANCSmartRegisterClient;
import org.ei.drishti.view.contract.ChildSmartRegisterClient;
import org.ei.drishti.view.contract.FPSmartRegisterClient;
import org.ei.drishti.view.viewHolder.NativeANCSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.NativeFPSmartRegisterViewHolder;

import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

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


    }

    @Override
    public void setupListView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }


}
