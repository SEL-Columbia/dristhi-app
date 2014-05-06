package org.ei.drishti.view.dialog;

import android.view.View;
import android.view.ViewGroup;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.ChildSmartRegisterClient;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;

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
                return new int[]{30, 15, 15, 15, 15, 10};
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
    public void setupListView(ViewGroup serviceModeOptionsView, ChildSmartRegisterClient client, NativeChildSmartRegisterViewHolder viewHolder) {
        serviceModeOptionsView.findViewById(R.id.overview_service_mode_views).setVisibility(View.GONE);
        serviceModeOptionsView.findViewById(R.id.immunization9plus_service_mode_views).setVisibility(View.GONE);
        serviceModeOptionsView.findViewById(R.id.immunization0to9_service_mode_views).setVisibility(View.VISIBLE);
    }
}
