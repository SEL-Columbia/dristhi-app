package org.ei.drishti.view.viewHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.view.customControls.ClientIdDetailsView;
import org.ei.drishti.view.customControls.ClientProfileView;

public class NativeChildSmartRegisterViewHolder {
    private final ClientProfileView profileInfoLayout;
    private final ClientIdDetailsView idDetailsView;
    private final ImageButton editButton;
    private final TextView txtDobView;

    private final ViewGroup serviceModeViewsHolder;
    private final ViewGroup serviceModeOverviewView;
    private final ViewGroup serviceModeImmunization0to9View;
    private final ViewGroup serviceModeImmunization9PlusView;

    public NativeChildSmartRegisterViewHolder(ViewGroup itemView) {
        this.profileInfoLayout = (ClientProfileView) itemView.findViewById(R.id.profile_info_layout);
        this.profileInfoLayout.initialize();

        this.idDetailsView = (ClientIdDetailsView) itemView.findViewById(R.id.client_id_details_layout);
        this.idDetailsView.initialize();

        this.serviceModeViewsHolder = (ViewGroup) itemView.findViewById(R.id.child_register_service_mode_options_view);
        serviceModeOverviewView = (ViewGroup) serviceModeViewsHolder.findViewById(R.id.overview_service_mode_views);
        serviceModeImmunization0to9View = (ViewGroup) serviceModeViewsHolder.findViewById(R.id.immunization0to9_service_mode_views);
        serviceModeImmunization9PlusView = (ViewGroup) serviceModeViewsHolder.findViewById(R.id.immunization9plus_service_mode_views);

        this.txtDobView = (TextView) itemView.findViewById(R.id.child_register_dob);
        this.editButton = (ImageButton) itemView.findViewById(R.id.btn_edit);
    }

    public ClientProfileView profileInfoLayout() {
        return profileInfoLayout;
    }

    public ClientIdDetailsView idDetailsView() {
        return idDetailsView;
    }

    public TextView dobView() {
        return txtDobView;
    }

    public ImageButton editButton() {
        return editButton;
    }

    public ViewGroup serviceModeViewsHolder() {
        return serviceModeViewsHolder;
    }

    public ViewGroup serviceModeImmunization9PlusView() {
        return serviceModeImmunization9PlusView;
    }

    public ViewGroup serviceModeImmunization0to9View() {
        return serviceModeImmunization0to9View;
    }

    public ViewGroup serviceModeOverviewView() {
        return serviceModeOverviewView;
    }

    public void hideAllServiceModeOptions() {
        serviceModeOverviewView().setVisibility(View.GONE);
        serviceModeImmunization0to9View().setVisibility(View.GONE);
        serviceModeImmunization9PlusView().setVisibility(View.GONE);
    }
}