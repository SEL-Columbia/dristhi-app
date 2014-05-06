package org.ei.drishti.provider;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.ChildSmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClients;
import org.ei.drishti.view.controller.ChildSmartRegisterController;
import org.ei.drishti.view.dialog.FilterOption;
import org.ei.drishti.view.dialog.ServiceModeOption;
import org.ei.drishti.view.dialog.SortOption;
import org.ei.drishti.view.viewHolder.ChildRegisterProfilePhotoLoader;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.ProfilePhotoLoader;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class ChildSmartRegisterClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;
    private final ProfilePhotoLoader photoLoader;

    private final String wifeAgeFormatString;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected ChildSmartRegisterController controller;

    private Drawable iconPencilDrawable;
    private Drawable maleInfantDrawable;
    private Drawable femaleInfantDrawable;
    private ServiceModeOption currentServiceModeOption;

    public ChildSmartRegisterClientsProvider(Context context,
                                             View.OnClickListener onClickListener,
                                             ChildSmartRegisterController controller) {
        this.onClickListener = onClickListener;
        this.controller = controller;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        maleInfantDrawable = context.getResources().getDrawable(R.drawable.child_boy_infant);
        femaleInfantDrawable = context.getResources().getDrawable(R.drawable.child_girl_infant);

        photoLoader = new ChildRegisterProfilePhotoLoader(maleInfantDrawable, femaleInfantDrawable);

        wifeAgeFormatString = context.getResources().getString(R.string.ec_register_wife_age);

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(R.dimen.list_item_height));
    }

    @Override
    public View getView(SmartRegisterClient smartRegisterClient, View convertView, ViewGroup viewGroup) {
        ViewGroup itemView;
        NativeChildSmartRegisterViewHolder viewHolder;
        if (convertView == null) {
            itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_child_client, null);
            viewHolder = new NativeChildSmartRegisterViewHolder(itemView);
            itemView.setTag(viewHolder);
        } else {
            itemView = (ViewGroup) convertView;
            viewHolder = (NativeChildSmartRegisterViewHolder) itemView.getTag();
        }

        ChildSmartRegisterClient client = (ChildSmartRegisterClient)smartRegisterClient;
        setupClientProfileView(client, viewHolder);
        setupIdDetailsView(client, viewHolder);

        viewHolder.hideAllServiceModeOptions();
        currentServiceModeOption.setupListView(viewHolder.serviceModeViewsHolder(), client, viewHolder);

        itemView.setLayoutParams(clientViewLayoutParams);
        return itemView;
    }

    private void setupClientProfileView(SmartRegisterClient client, NativeChildSmartRegisterViewHolder viewHolder) {
        viewHolder.profileInfoLayout().bindData(client, photoLoader, wifeAgeFormatString);
        viewHolder.profileInfoLayout().setOnClickListener(onClickListener);
        viewHolder.profileInfoLayout().setTag(client);
    }

    private void setupIdDetailsView(ChildSmartRegisterClient client, NativeChildSmartRegisterViewHolder viewHolder) {
        viewHolder.idDetailsView().bindData(client);
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
}
