package org.ei.telemedicine.view.dialog;

import static android.view.View.VISIBLE;
import static org.ei.telemedicine.Context.getInstance;

import org.ei.telemedicine.R;
import org.ei.telemedicine.provider.SmartRegisterClientsProvider;
import org.ei.telemedicine.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;
import org.ei.telemedicine.view.contract.ANCSmartRegisterClient;
import org.ei.telemedicine.view.contract.ChildSmartRegisterClient;
import org.ei.telemedicine.view.contract.FPSmartRegisterClient;
import org.ei.telemedicine.view.contract.pnc.PNCSmartRegisterClient;
import org.ei.telemedicine.view.viewHolder.NativeANCSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativePNCSmartRegisterViewHolder;

import android.view.View;

public class FPPrioritizationAllECServiceMode extends ServiceModeOption {

    public FPPrioritizationAllECServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return getInstance().getStringResource(R.string.fp_prioritization_all_ec_service_mode);
    }

    @Override
    public ClientsHeaderProvider getHeaderProvider() {
        return new ClientsHeaderProvider() {
            @Override
            public int count() {
                return 4;
            }

            @Override
            public int weightSum() {
                return 100;
            }

            @Override
            public int[] weights() {
                return new int[]{24, 6, 11, 59};
            }

            @Override
            public int[] headerTextResourceIds() {
                return new int[]{
                        R.string.header_name, R.string.header_ec_no, R.string.header_gplsa,
                        R.string.header_fp_prioritization_risks};
            }
        };
    }

    @Override
    public void setupListView(FPSmartRegisterClient client,
                              NativeFPSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {
        viewHolder.serviceModeFPPrioritization().setVisibility(VISIBLE);
        setupPrioritizationRisksView(client, viewHolder);
        setupAddFPView(client, clientSectionClickListener, viewHolder);
//        viewHolder.lytFPVideosView().setOnClickListener(clientSectionClickListener);
    }

    private void setupPrioritizationRisksView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder) {
        viewHolder.txtPrioritizationRiskView().setText(client.highPriorityReason());
    }

    private void setupAddFPView(FPSmartRegisterClient client, View.OnClickListener clientSectionClickListener, NativeFPSmartRegisterViewHolder viewHolder) {
        viewHolder.lytAddFPView().setOnClickListener(clientSectionClickListener);
        viewHolder.lytAddFPView().setTag(client);
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
    public void setupListView(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }
}
