package org.ei.telemedicine.provider;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import org.ei.telemedicine.R;
import org.ei.telemedicine.view.activity.SecuredActivity;
import org.ei.telemedicine.view.contract.FPSmartRegisterClient;
import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.contract.SmartRegisterClients;
import org.ei.telemedicine.view.controller.FPSmartRegisterController;
import org.ei.telemedicine.view.dialog.FPAllMethodsServiceMode;
import org.ei.telemedicine.view.dialog.FPMethodFilter;
import org.ei.telemedicine.view.dialog.FPPrioritizationMethodFilter;
import org.ei.telemedicine.view.dialog.FilterOption;
import org.ei.telemedicine.view.dialog.NameSort;
import org.ei.telemedicine.view.dialog.ServiceModeOption;
import org.ei.telemedicine.view.dialog.SortOption;
import org.ei.telemedicine.view.viewHolder.ECProfilePhotoLoader;
import org.ei.telemedicine.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.OnClickFormLauncher;
import org.ei.telemedicine.view.viewHolder.ProfilePhotoLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

public class FPSmartRegisterClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final SecuredActivity activity;
    private final View.OnClickListener onClickListener;
    private final ProfilePhotoLoader photoLoader;

    private ServiceModeOption currentServiceModeOption;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected FPSmartRegisterController controller;

    public FPSmartRegisterClientsProvider(SecuredActivity activity,
                                          View.OnClickListener onClickListener,
                                          FPSmartRegisterController controller) {
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
        NativeFPSmartRegisterViewHolder viewHolder;
        if (convertView == null) {
            itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_fp_client, null);
            viewHolder = new NativeFPSmartRegisterViewHolder(itemView);
            itemView.setTag(viewHolder);
        } else {
            itemView = (ViewGroup) convertView;
            viewHolder = (NativeFPSmartRegisterViewHolder) itemView.getTag();
        }

        FPSmartRegisterClient client = (FPSmartRegisterClient) smartRegisterClient;

        setupClientProfileView(client, viewHolder);
        setupEcNumberView(client, viewHolder);
        setupGPLSAView(client, viewHolder);

        viewHolder.hideAllServiceModeOptions();
        currentServiceModeOption.setupListView(client, viewHolder, onClickListener);

        itemView.setLayoutParams(clientViewLayoutParams);
        return itemView;
    }


    private void setupClientProfileView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder) {
        viewHolder.profileInfoLayout().bindData(client, photoLoader);
        viewHolder.profileInfoLayout().setOnClickListener(onClickListener);
        viewHolder.profileInfoLayout().setTag(client);
    }

    private void setupEcNumberView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder) {
        viewHolder.txtECNumberView().setText(String.valueOf(client.ecNumber()));
    }

    private void setupGPLSAView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder) {
        viewHolder.gplsaAndChildLayout().bindData(client);
    }


    @Override
    public SmartRegisterClients getClients() {
        NameSort nameSortOption = new NameSort();
        FilterOption filterOption = getFPFilterOptionBasedOnDialogTab(currentServiceModeOption);
        return controller.getClients("condom").applyFilterWithFP(currentServiceModeOption, nameSortOption, filterOption);
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                                              FilterOption searchFilter, SortOption sortOption) {
        return controller.getClients("condom").applyFilterWithFP(serviceModeOption, sortOption, villageFilter, searchFilter, getFPFilterOptionBasedOnDialogTab(serviceModeOption));
    }

    private FilterOption getFPFilterOptionBasedOnDialogTab(ServiceModeOption serviceModeOption) {
        if (serviceModeOption instanceof FPAllMethodsServiceMode) {
            return new FPMethodFilter(serviceModeOption.name());
        }
        return new FPPrioritizationMethodFilter(serviceModeOption.name());
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {
        currentServiceModeOption = serviceModeOption;
    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return new OnClickFormLauncher(activity, formName, entityId, metaData);
    }

    public LayoutInflater inflater() {
        return inflater;
    }
}
