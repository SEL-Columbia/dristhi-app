package org.ei.telemedicine.view.dialog;

import android.view.View;

import org.ei.telemedicine.R;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.provider.SmartRegisterClientsProvider;
import org.ei.telemedicine.view.contract.ANCSmartRegisterClient;
import org.ei.telemedicine.view.contract.ChildSmartRegisterClient;
import org.ei.telemedicine.view.contract.FPSmartRegisterClient;
import org.ei.telemedicine.view.contract.pnc.PNCSmartRegisterClient;
import org.ei.telemedicine.view.viewHolder.NativeANCSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativePNCSmartRegisterViewHolder;

import static org.ei.telemedicine.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public class AllEligibleCoupleServiceMode extends ServiceModeOption {

    public AllEligibleCoupleServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.couple_selection);
    }

    @Override
    public ClientsHeaderProvider getHeaderProvider() {
        return new ClientsHeaderProvider() {
            @Override
            public int count() {
                return 7;
            }

            @Override
            public int weightSum() {
                return 1000;
            }

            @Override
            public int[] weights() {
                return new int[]{239, 73, 103, 107, 158, 221, 87};
            }

            @Override
            public int[] headerTextResourceIds() {
                return new int[]{
                        R.string.header_name, R.string.header_ec_no, R.string.header_gplsa,
                        R.string.header_fp, R.string.header_children, R.string.header_status,
                        R.string.header_edit};
            }
        };
    }

    @Override
    public void setupListView(ChildSmartRegisterClient client,
                              NativeChildSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(ANCSmartRegisterClient client, NativeANCSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }
}
