package org.ei.opensrp.indonesia.view.dialog;

import android.view.View;

import org.ei.opensrp.indonesia.Context;
import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.view.contract.AnakClient;
import org.ei.opensrp.indonesia.view.contract.KIANCClient;
import org.ei.opensrp.indonesia.view.contract.KartuIbuClient;
import org.ei.opensrp.indonesia.view.viewHolder.NativeAnakRegisterViewHolder;
import org.ei.opensrp.indonesia.view.viewHolder.NativeKIANCRegisterViewHolder;
import org.ei.opensrp.indonesia.view.viewHolder.NativeKIRegisterViewHolder;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity;
import org.ei.opensrp.view.contract.ANCSmartRegisterClient;
import org.ei.opensrp.view.contract.ChildSmartRegisterClient;
import org.ei.opensrp.view.contract.FPSmartRegisterClient;
import org.ei.opensrp.view.contract.pnc.PNCSmartRegisterClient;
import org.ei.opensrp.view.viewHolder.NativeANCSmartRegisterViewHolder;
import org.ei.opensrp.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.opensrp.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.opensrp.view.viewHolder.NativePNCSmartRegisterViewHolder;

/**
 * Created by Dimas Ciputra on 3/4/15.
 */
public class KIANCOverviewServiceMode extends BidanServiceModeOption {

    public KIANCOverviewServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.anc_service_mode_overview);
    }

    @Override
    public SecuredNativeSmartRegisterActivity.ClientsHeaderProvider getHeaderProvider() {
        return new SecuredNativeSmartRegisterActivity.ClientsHeaderProvider() {
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
                return new int[]{244, 100, 110, 120, 190, 135, 95};
            }

            @Override
            public int[] headerTextResourceIds() {
                return new int[]{
                        R.string.header_name, R.string.header_id, R.string.header_status,
                        R.string.header_pemeriksaan, R.string.header_resiko,
                        R.string.header_kunjungan, R.string.header_edit};
            }
        };
    }

    @Override
    public void setupListView(ChildSmartRegisterClient client, NativeChildSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

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

    @Override
    public void setupListView(KartuIbuClient client, NativeKIRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(KIANCClient client, NativeKIANCRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(AnakClient client, NativeAnakRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }
}
