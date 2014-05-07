package org.ei.drishti.view.dialog;

import android.view.View;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.ChildSmartRegisterClient;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;

import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public class ChildImmunization9PlusServiceMode extends ServiceModeOption {
    public ChildImmunization9PlusServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.child_service_mode_immunization_9_plus);
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
                        R.string.header_name, R.string.header_id_no, R.string.header_measles,
                        R.string.header_opv_booster, R.string.header_dpt_booster, R.string.header_vitamin_a};
            }
        };

    }

    @Override
    public void setupListView(ChildSmartRegisterClient client,
                              NativeChildSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {
        viewHolder.serviceModeImmunization9PlusView().setVisibility(View.VISIBLE);

        if (client.isMeaslesDone()) {
            viewHolder.measlesDoneOnView().setVisibility(View.VISIBLE);
            viewHolder.measlesDoneOnView().setText(client.measlesDoneDate());
        } else {
            viewHolder.measlesDoneOnView().setVisibility(View.INVISIBLE);
        }

        if (client.isOpvBoosterDone()) {
            viewHolder.opvBoosterDoneOnView().setVisibility(View.VISIBLE);
            viewHolder.opvBoosterDoneOnView().setText(client.opvBoosterDoneDate());
        } else {
            viewHolder.opvBoosterDoneOnView().setVisibility(View.INVISIBLE);
        }

        if (client.isDptBoosterDone()) {
            viewHolder.dptBoosterDoneOnView().setVisibility(View.VISIBLE);
            viewHolder.dptBoosterDoneOnView().setText(client.dptBoosterDoneDate());
        } else {
            viewHolder.dptBoosterDoneOnView().setVisibility(View.INVISIBLE);
        }

        if (client.isVitaminADone()) {
            viewHolder.vitaminADoneOnView().setVisibility(View.VISIBLE);
            viewHolder.vitaminADoneOnView().setText(client.vitaminADoneDate());
        } else {
            viewHolder.vitaminADoneOnView().setVisibility(View.INVISIBLE);
        }
    }
}
