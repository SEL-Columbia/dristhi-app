package org.ei.opensrp.test.vaksinator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.test.R;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by user on 2/12/15.
 */
public class VaksinatorSmartClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;
    private Drawable iconPencilDrawable;
    protected CommonPersonObjectController controller;

    AlertService alertService;

    public VaksinatorSmartClientsProvider(Context context,
                                          View.OnClickListener onClickListener,
                                          CommonPersonObjectController controller, AlertService alertService) {
        this.onClickListener = onClickListener;
        this.controller = controller;
        this.context = context;
        this.alertService = alertService;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(org.ei.opensrp.R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(org.ei.opensrp.R.color.text_black);

    }

    @Override
    public View getView(SmartRegisterClient smartRegisterClient, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (convertView == null){
           convertView = (ViewGroup) inflater().inflate(R.layout.smart_register_jurim_client, null);
            viewHolder = new ViewHolder();
            viewHolder.profilelayout =  (LinearLayout)convertView.findViewById(R.id.profile_info_layout);

            viewHolder.name = (TextView)convertView.findViewById(R.id.name);
            viewHolder.motherName = (TextView)convertView.findViewById(R.id.motherName);
            viewHolder.village = (TextView)convertView.findViewById(R.id.village);
            viewHolder.age = (TextView)convertView.findViewById(R.id.age);
            viewHolder.gender = (TextView)convertView.findViewById(R.id.gender);

            viewHolder.bcg = (TextView)convertView.findViewById(R.id.bcg);
            viewHolder.pol1 = (TextView)convertView.findViewById(R.id.pol1);
            viewHolder.pol2 = (TextView)convertView.findViewById(R.id.bcg);
            viewHolder.pol3 = (TextView)convertView.findViewById(R.id.pol3);
            viewHolder.pol4 = (TextView)convertView.findViewById(R.id.pol4);
            viewHolder.ipv = (TextView)convertView.findViewById(R.id.ipv);

            viewHolder.profilepic =(ImageView)convertView.findViewById(R.id.profilepic);
           // viewHolder.follow_up = (ImageButton)convertView.findViewById(R.id.btn_edit);
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.household_profile_thumb));
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.household_profile_thumb));
        }

        //viewHolder.follow_up.setOnClickListener(onClickListener);
        //viewHolder.follow_up.setTag(smartRegisterClient);
        viewHolder.profilelayout.setOnClickListener(onClickListener);
        viewHolder.profilelayout.setTag(smartRegisterClient);
        CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;
        if (iconPencilDrawable == null) {
            iconPencilDrawable = context.getResources().getDrawable(R.drawable.ic_pencil);
        }
        //viewHolder.follow_up.setImageDrawable(iconPencilDrawable);
        //viewHolder.follow_up.setOnClickListener(onClickListener);


        //set default image for mother
        viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.drawable.woman_placeholder));

        viewHolder.name.setText(pc.getDetails().get("nama_bayi") != null ? pc.getDetails().get("nama_bayi") : "-");
        viewHolder.motherName.setText(pc.getDetails().get("nama_orang_tua")!=null?pc.getDetails().get("nama_orang_tua"):"-");
        viewHolder.village.setText(pc.getDetails().get("lokasi")!=null?pc.getDetails().get("lokasi"):"-");
        viewHolder.age.setText(pc.getDetails().get("tanggal_lahir")!=null?pc.getDetails().get("tanggal_lahir"):"-");
        viewHolder.gender.setText(pc.getDetails().get("jenis_kelamin")!=null?pc.getDetails().get("jenis_kelamin"):"-");

        convertView.setLayoutParams(clientViewLayoutParams);
        return convertView;
    }
    CommonPersonObjectController householdelcocontroller;





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

     class ViewHolder {
         LinearLayout profilelayout;
         ImageView profilepic;
         ImageButton follow_up;
         public TextView bcg;
         public TextView pol1;
         public TextView pol2;
         public TextView complete;
         public TextView name;
         public TextView motherName;
         public TextView village;
         public TextView age;
         public TextView pol3;
         public TextView pol4;
         public TextView ipv;
         public TextView gender;
     }

}

