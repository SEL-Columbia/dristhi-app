package org.ei.telemedicine.provider;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


import org.ei.telemedicine.R;
import org.ei.telemedicine.view.activity.SecuredActivity;
import org.ei.telemedicine.view.contract.ANCSmartRegisterClient;
import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.contract.SmartRegisterClients;
import org.ei.telemedicine.view.controller.ANCSmartRegisterController;
import org.ei.telemedicine.view.dialog.FilterOption;
import org.ei.telemedicine.view.dialog.ServiceModeOption;
import org.ei.telemedicine.view.dialog.SortOption;
import org.ei.telemedicine.view.viewHolder.ECProfilePhotoLoader;
import org.ei.telemedicine.view.viewHolder.NativeANCSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.OnClickFormLauncher;
import org.ei.telemedicine.view.viewHolder.ProfilePhotoLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

public class ANCSmartRegisterClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final SecuredActivity activity;
    private final View.OnClickListener onClickListener;
    private final ProfilePhotoLoader photoLoader;

    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected ANCSmartRegisterController controller;

    private ServiceModeOption currentServiceModeOption;

    public ANCSmartRegisterClientsProvider(SecuredActivity activity,
                                           View.OnClickListener onClickListener,
                                           ANCSmartRegisterController controller) {
        this.onClickListener = onClickListener;
        this.controller = controller;
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        photoLoader = new ECProfilePhotoLoader(activity.getResources(),
                activity.getResources().getDrawable(R.drawable.woman_placeholder));

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) activity.getResources().getDimension(R.dimen.list_item_height));
    }

    @Override
    public View getView(SmartRegisterClient smartRegisterClient, View convertView, ViewGroup viewGroup) {
        ViewGroup itemView;

        NativeANCSmartRegisterViewHolder viewHolder;
        if (convertView == null) {
            itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_anc_client, null);
            viewHolder = new NativeANCSmartRegisterViewHolder(itemView);
            itemView.setTag(viewHolder);
        } else {
            itemView = (ViewGroup) convertView;
            viewHolder = (NativeANCSmartRegisterViewHolder) itemView.getTag();
        }

        ANCSmartRegisterClient client = (ANCSmartRegisterClient) smartRegisterClient;
        setupClientProfileView(client, viewHolder);
        setupClientIdDetailsView(viewHolder, client);
        setupANCStatusView(viewHolder, client);

        viewHolder.hideAllServiceModeOptions();
        currentServiceModeOption.setupListView(client, viewHolder, onClickListener);

        itemView.setLayoutParams(clientViewLayoutParams);
        return itemView;
    }

    private void setupANCStatusView(NativeANCSmartRegisterViewHolder viewHolder, ANCSmartRegisterClient client) {
        viewHolder.ancStatusView().bindData(client);
    }

    private void setupClientIdDetailsView(NativeANCSmartRegisterViewHolder viewHolder, ANCSmartRegisterClient client) {
        viewHolder.ancClientIdDetailsView().bindData(client);
    }

    private void setupClientProfileView(SmartRegisterClient client, NativeANCSmartRegisterViewHolder viewHolder) {
        viewHolder.profileInfoLayout().bindData(client, photoLoader);
        viewHolder.profileInfoLayout().setOnClickListener(onClickListener);
        viewHolder.profileInfoLayout().setTag(client);
    }

    @Override
    public SmartRegisterClients getClients() {
        return controller.getClients();
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                                              FilterOption searchFilter, SortOption sortOption) {
        return getClients().applyFilter(villageFilter, serviceModeOption, searchFilter, sortOption);
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {
        currentServiceModeOption = serviceModeOption;
    }

    public LayoutInflater inflater() {
        return inflater;
    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return new OnClickFormLauncher(activity, formName, entityId, metaData);
    }

}
