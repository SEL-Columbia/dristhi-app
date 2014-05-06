package org.ei.drishti.view.viewHolder;

import android.view.ViewGroup;
import android.widget.ImageButton;
import org.ei.drishti.R;
import org.ei.drishti.view.customControls.*;

public class NativeChildSmartRegisterViewHolder {
    private final ClientProfileView profileInfoLayout;
    private final ClientIdDetailsView idDetailsView;
    private final ImageButton editButton;


    public NativeChildSmartRegisterViewHolder(ViewGroup itemView) {
        this.profileInfoLayout = (ClientProfileView) itemView.findViewById(R.id.profile_info_layout);
        this.profileInfoLayout.initialize();

        this.idDetailsView = (ClientIdDetailsView) itemView.findViewById(R.id.client_id_details_layout);
        this.idDetailsView.initialize();

        this.editButton = (ImageButton) itemView.findViewById(R.id.btn_edit);
    }

    public ClientProfileView profileInfoLayout() {
        return profileInfoLayout;
    }

    public ClientIdDetailsView idDetailsView() {
        return idDetailsView;
    }

    public ImageButton editButton() {
        return editButton;
    }
}