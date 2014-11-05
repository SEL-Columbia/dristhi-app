package org.ei.drishti.view.viewHolder;

import android.view.ViewGroup;
import org.ei.drishti.R;
import org.ei.drishti.view.customControls.ClientProfileView;

public class NativeANCSmartRegisterViewHolder {
    private final ClientProfileView profileInfoLayout;

    public NativeANCSmartRegisterViewHolder(ViewGroup itemView) {
        profileInfoLayout = (ClientProfileView) itemView.findViewById(R.id.profile_info_layout);
        profileInfoLayout.initialize();

    }

    public ClientProfileView profileInfoLayout() {
        return profileInfoLayout;
    }

}