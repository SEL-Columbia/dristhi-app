package org.ei.opensrp.indonesia.provider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.view.contract.KIPNCClient;
import org.ei.opensrp.indonesia.view.controller.KIPNCRegisterController;
import org.ei.opensrp.indonesia.view.viewHolder.NativeKIPNCRegisterViewHolder;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.view.activity.SecuredActivity;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.ECProfilePhotoLoader;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;
import org.ei.opensrp.view.viewHolder.ProfilePhotoLoader;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by Dimas Ciputra on 3/4/15.
 */
public class KIPNCClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final SecuredActivity activity;
    private final View.OnClickListener onClickListener;
    private final ProfilePhotoLoader photoLoader;

    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected KIPNCRegisterController controller;

    private Drawable iconPencilDrawable;

    public KIPNCClientsProvider(SecuredActivity activity, View.OnClickListener onClickListener, KIPNCRegisterController controller) {
        this.activity = activity;
        this.onClickListener = onClickListener;
        this.controller = controller;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        photoLoader = new ECProfilePhotoLoader(activity.getResources(),
                activity.getResources().getDrawable(R.drawable.woman_placeholder));

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) activity.getResources().getDimension(R.dimen.list_item_height));

    }

    @Override
    public View getView(SmartRegisterClient client, View convertView, ViewGroup viewGroup) {
        ViewGroup itemView;
        NativeKIPNCRegisterViewHolder viewHolder;
        if (convertView == null) {
            itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_ki_pnc_client, null);
            viewHolder = new NativeKIPNCRegisterViewHolder(itemView);
            itemView.setTag(viewHolder);
        } else {
            itemView = (ViewGroup) convertView;
            viewHolder = (NativeKIPNCRegisterViewHolder) itemView.getTag();
        }

        KIPNCClient kartuIbuClient = (KIPNCClient) client;
        setupClientProfileView(kartuIbuClient, viewHolder);
        setupIdDetailsView(kartuIbuClient, viewHolder);
        setupPNCPlan(kartuIbuClient, viewHolder);
        setupKomplikasiView(kartuIbuClient, viewHolder);
        setupMetodeKontrasepsiView(kartuIbuClient, viewHolder);
        setupTandaVitalView(kartuIbuClient, viewHolder);
        setupEditView(kartuIbuClient, viewHolder);

        itemView.setLayoutParams(clientViewLayoutParams);
        return itemView;
    }

    private void setupTandaVitalView(KIPNCClient client, NativeKIPNCRegisterViewHolder viewHolder) {
        viewHolder.getTdDiastolik().setText(client.tdDiastolik());
        viewHolder.getTdSistolik().setText(client.tdSistolik());
        viewHolder.getTdSuhu().setText(client.tdSuhu());
    }

    private void setupMetodeKontrasepsiView(KIPNCClient client, NativeKIPNCRegisterViewHolder viewHolder) {
        viewHolder.kondisiIbu().setText(client.motherCondition());
        String birthCondition1 = "-";
        String birthCondition2 = "-";
        if(client.getLastChild()!=null) {
            birthCondition1 = client.getLastChild().getBirthCondition();
            birthCondition2 = client.getLastChild().gender() + " , " + client.getLastChild().birthWeight();
        }
        viewHolder.kondisiAnak1().setText(birthCondition1);
        viewHolder.kondisiAnak2().setText(birthCondition2);
    }

    private void setupKomplikasiView(KIPNCClient client, NativeKIPNCRegisterViewHolder viewHolder) {
        viewHolder.getKomplikasi().setText(client.komplikasi() + " " + client.otherKomplikasi());
    }


    private void setupEditView(KIPNCClient client, NativeKIPNCRegisterViewHolder viewHolder) {
        if (iconPencilDrawable == null) {
            iconPencilDrawable = activity.getApplicationContext().getResources().getDrawable(R.drawable.ic_pencil);
        }
        viewHolder.editButton().setImageDrawable(iconPencilDrawable);
        viewHolder.editButton().setOnClickListener(onClickListener);
        viewHolder.editButton().setTag(client);
    }


    private void setupHighlightColor(ViewGroup itemView, int index) {
        if(index%2==0) {
            itemView.setBackgroundColor(Color.parseColor("#E0F5FF"));
        } else {
            itemView.setBackgroundColor(Color.WHITE);
        }
    }

    private void setupClientProfileView(KIPNCClient client, NativeKIPNCRegisterViewHolder viewHolder) {
        viewHolder.profileInfoLayout().bindData(client, photoLoader);
        viewHolder.profileInfoLayout().setOnClickListener(onClickListener);
        viewHolder.profileInfoLayout().setTag(client);
    }

    private void setupIdDetailsView(KIPNCClient client, NativeKIPNCRegisterViewHolder viewHolder) {
        viewHolder.pncId().setText(client.kiNumber()==null?"-":client.kiNumber());
    }

    private void setupPNCPlan(KIPNCClient client, NativeKIPNCRegisterViewHolder viewHolder) {
        viewHolder.dokTempat().setText(client.tempatPersalinan());
        viewHolder.dokTipe().setText(client.tipePersalinan());
        viewHolder.dokTanggalBersalin().setText(client.getLastBirth());
    }

    @Override
    public SmartRegisterClients getClients() {
        return controller.getKartuIbuPNCClients();
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption, FilterOption searchFilter, SortOption sortOption) {
        return getClients().applyFilter(villageFilter, serviceModeOption, searchFilter, sortOption);
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {

    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return new OnClickFormLauncher(activity, formName, entityId, metaData);
    }

    public LayoutInflater inflater() {
        return inflater;
    }
}
