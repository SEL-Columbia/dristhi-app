package org.ei.drishti.view.dialog;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.ChildSmartRegisterClient;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;

import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public class ChildOverviewServiceMode extends ServiceModeOption {

    public ChildOverviewServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.child_service_mode_overview);
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
                        R.string.header_name, R.string.header_id_no, R.string.header_dob,
                        R.string.header_last_service, R.string.header_sick_status, R.string.header_edit};
            }
        };
    }

    @Override
    public void setupListView(ViewGroup serviceModeOptionsView, ChildSmartRegisterClient client, NativeChildSmartRegisterViewHolder viewHolder) {
        serviceModeOptionsView.findViewById(R.id.overview_service_mode_views).setVisibility(View.VISIBLE);
        serviceModeOptionsView.findViewById(R.id.immunization0to9_service_mode_views).setVisibility(View.GONE);
        serviceModeOptionsView.findViewById(R.id.immunization9plus_service_mode_views).setVisibility(View.GONE);

        setupDobView(client, viewHolder);
        setupEditView(client, viewHolder);
    }

    private void setupDobView(ChildSmartRegisterClient client, NativeChildSmartRegisterViewHolder viewHolder) {
        viewHolder.dobView().setText(client.dateOfBirth());
    }

    private void setupEditView(ChildSmartRegisterClient client, NativeChildSmartRegisterViewHolder viewHolder) {
        Drawable iconPencilDrawable = Context.getInstance().applicationContext().getResources().getDrawable(R.drawable.ic_pencil);
        viewHolder.editButton().setImageDrawable(iconPencilDrawable);
        viewHolder.editButton().setTag(client);
    }
}
