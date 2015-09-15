package org.ei.opensrp.indonesia.provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.view.contract.AnakClient;
import org.ei.opensrp.indonesia.view.controller.AnakRegisterController;
import org.ei.opensrp.indonesia.view.dialog.BidanServiceModeOption;
import org.ei.opensrp.indonesia.view.viewHolder.AnakRegisterProfilePhotoLoader;
import org.ei.opensrp.indonesia.view.viewHolder.NativeAnakRegisterViewHolder;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.view.activity.SecuredActivity;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;
import org.ei.opensrp.view.viewHolder.ProfilePhotoLoader;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class AnakRegisterClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final SecuredActivity activity;
    private final View.OnClickListener onClickListener;
    private final ProfilePhotoLoader photoLoader;

    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected AnakRegisterController controller;

    private BidanServiceModeOption currentServiceModeOption;

    public AnakRegisterClientsProvider(SecuredActivity activity,
                                       View.OnClickListener onClickListener,
                                       AnakRegisterController controller) {
        this.onClickListener = onClickListener;
        this.controller = controller;
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        photoLoader = new AnakRegisterProfilePhotoLoader(
                activity.getResources().getDrawable(R.drawable.child_boy_infant),
                activity.getResources().getDrawable(R.drawable.child_girl_infant));

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) activity.getResources().getDimension(R.dimen.list_item_height));
    }

    @Override
    public View getView(SmartRegisterClient smartRegisterClient, View convertView, ViewGroup viewGroup) {
        ViewGroup itemView;
        NativeAnakRegisterViewHolder viewHolder;
        if (convertView == null) {
            itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_anak_client, null);
            viewHolder = new NativeAnakRegisterViewHolder(itemView);
            itemView.setTag(viewHolder);
        } else {
            itemView = (ViewGroup) convertView;
            viewHolder = (NativeAnakRegisterViewHolder) itemView.getTag();
        }

        AnakClient client = (AnakClient)smartRegisterClient;
        setupClientProfileView(client, viewHolder);
        setupIdDetailsView(client, viewHolder);

        viewHolder.hideAllServiceModeOptions();
        currentServiceModeOption.setupListView(client, viewHolder, onClickListener);

        itemView.setLayoutParams(clientViewLayoutParams);
        return itemView;
    }

    private void setupClientProfileView(AnakClient client, NativeAnakRegisterViewHolder viewHolder) {
        viewHolder.getProfileViewLayout().bindData(client, photoLoader);
        viewHolder.getProfileViewLayout().setOnClickListener(onClickListener);
        viewHolder.getProfileViewLayout().setTag(client);
    }

    private void setupIdDetailsView(AnakClient client, NativeAnakRegisterViewHolder viewHolder) {
        viewHolder.getTxtIbuKiNo().setText(client.getBabyNo());
    }

    @Override
    public SmartRegisterClients getClients() {
        return controller.getClient();
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                                              FilterOption searchFilter, SortOption sortOption) {
        return getClients().applyFilter(villageFilter, serviceModeOption, searchFilter, sortOption);
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {
        currentServiceModeOption = (BidanServiceModeOption) serviceModeOption;
    }

    public LayoutInflater inflater() {
        return inflater;
    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return new OnClickFormLauncher(activity, formName, entityId, metaData);
    }

}
