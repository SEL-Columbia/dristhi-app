package org.ei.drishti.provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.FPSmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClients;
import org.ei.drishti.view.controller.FPSmartRegisterController;
import org.ei.drishti.view.dialog.FilterOption;
import org.ei.drishti.view.dialog.ServiceModeOption;
import org.ei.drishti.view.dialog.SortOption;
import org.ei.drishti.view.viewHolder.ECProfilePhotoLoader;
import org.ei.drishti.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.OnClickFormLauncher;
import org.ei.drishti.view.viewHolder.ProfilePhotoLoader;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class FPSmartRegisterClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;
    private final ProfilePhotoLoader photoLoader;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected FPSmartRegisterController controller;

    public FPSmartRegisterClientsProvider(Context context,
                                          View.OnClickListener onClickListener,
                                          FPSmartRegisterController controller) {
        this.onClickListener = onClickListener;
        this.controller = controller;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        photoLoader = new ECProfilePhotoLoader(context.getResources(),
                context.getResources().getDrawable(R.drawable.woman_placeholder));

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(R.color.text_black);
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
        setupFPMethodView(client, viewHolder);
        setupUpdateButtonView(client, viewHolder);

        itemView.setLayoutParams(clientViewLayoutParams);
        return itemView;
    }

    private void setupUpdateButtonView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder) {
        viewHolder.btnUpdateView().setOnClickListener(onClickListener);
        viewHolder.btnUpdateView().setTag(client);
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

    private void setupFPMethodView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder) {
        viewHolder.fpMethodView().bindData(client, txtColorBlack);
        viewHolder.fpMethodView().setTag(client);
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
        // do nothing.
    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return null;
    }

    public LayoutInflater inflater() {
        return inflater;
    }
}
