package org.ei.drishti.view.dialog;

import android.view.View;
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
                return new int[]{26, 14, 15, 15, 15, 15};
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
    public void setupListView(ChildSmartRegisterClient client,
                              NativeChildSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {
        viewHolder.serviceModeImmunization0to9View().setVisibility(View.VISIBLE);

        if (client.isBcgDone()) {
            viewHolder.bcgDoneLayout().setVisibility(View.VISIBLE);
            viewHolder.bcgDoneOnView().setText("On " + client.bcgDoneDate());
            viewHolder.bcgPendingView().setVisibility(View.INVISIBLE);
        } else {
            viewHolder.bcgDoneLayout().setVisibility(View.INVISIBLE);
            viewHolder.bcgPendingView().setVisibility(View.VISIBLE);
        }

        if (client.isOpvDone()) {
            viewHolder.opvDoneOnView().setVisibility(View.VISIBLE);
            viewHolder.opvDoneOnView().setText(client.opvDoneDate());
        } else {
            viewHolder.opvDoneOnView().setVisibility(View.INVISIBLE);
        }

        if (client.isHepBDone()) {
            viewHolder.hepBDoneOnView().setVisibility(View.VISIBLE);
            viewHolder.hepBDoneOnView().setText(client.hepBDoneDate());
        } else {
            viewHolder.hepBDoneOnView().setVisibility(View.INVISIBLE);
        }

        if (client.isPentavDone()) {
            viewHolder.pentavDoneOnView().setVisibility(View.VISIBLE);
            viewHolder.pentavDoneOnView().setText(client.pentavDoneDate());
        } else {
            viewHolder.pentavDoneOnView().setVisibility(View.INVISIBLE);
        }
    }
}
