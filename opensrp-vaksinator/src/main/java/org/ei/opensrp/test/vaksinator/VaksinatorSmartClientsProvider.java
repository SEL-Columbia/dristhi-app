package org.ei.opensrp.test.vaksinator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.CommonPersonObject;
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

import java.text.SimpleDateFormat;

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
        CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;
        if (convertView == null){
            convertView = (ViewGroup) inflater().inflate(R.layout.smart_register_jurim_client, null);
            viewHolder = new ViewHolder();
            viewHolder.profilelayout =  (LinearLayout)convertView.findViewById(R.id.profile_info_layout);

//----------Child Basic Information
            viewHolder.name = (TextView)convertView.findViewById(R.id.name);
            viewHolder.motherName = (TextView)convertView.findViewById(R.id.motherName);
            viewHolder.village = (TextView)convertView.findViewById(R.id.village);
            viewHolder.age = (TextView)convertView.findViewById(R.id.age);
            viewHolder.gender = (TextView)convertView.findViewById(R.id.gender);

//----------FrameLayout
            viewHolder.hb0Layout = (FrameLayout)convertView.findViewById(R.id.hb0Layout);
            viewHolder.bcgLayout = (FrameLayout)convertView.findViewById(R.id.bcgLayout);
            viewHolder.hb1Layout = (FrameLayout)convertView.findViewById(R.id.hb1Layout);
            viewHolder.hb2Layout = (FrameLayout)convertView.findViewById(R.id.hb2Layout);
            viewHolder.hb3Layout = (FrameLayout)convertView.findViewById(R.id.hb3Layout);
            viewHolder.campakLayout = (FrameLayout)convertView.findViewById(R.id.campakLayout);

//----------TextView to show immunization date
            viewHolder.hb0 = (TextView)convertView.findViewById(R.id.hb1);
            viewHolder.pol1 = (TextView)convertView.findViewById(R.id.pol1);
            viewHolder.pol2 = (TextView)convertView.findViewById(R.id.pol2);
            viewHolder.pol3 = (TextView)convertView.findViewById(R.id.pol3);
            viewHolder.pol4 = (TextView)convertView.findViewById(R.id.pol4);
            viewHolder.campak = (TextView)convertView.findViewById(R.id.ipv);

//----------Checklist logo
            viewHolder.hbLogo = (ImageView)convertView.findViewById(R.id.hbLogo);
            viewHolder.pol1Logo = (ImageView)convertView.findViewById(R.id.pol1Logo);
            viewHolder.pol2Logo = (ImageView)convertView.findViewById(R.id.pol2Logo);
            viewHolder.pol3Logo = (ImageView)convertView.findViewById(R.id.pol3Logo);
            viewHolder.pol4Logo = (ImageView)convertView.findViewById(R.id.pol4Logo);
            viewHolder.ipvLogo = (ImageView)convertView.findViewById(R.id.measlesLogo);

            viewHolder.profilepic =(ImageView)convertView.findViewById(R.id.profilepic);
            viewHolder.follow_up = (ImageButton)convertView.findViewById(R.id.btn_edit);
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.household_profile_thumb));

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.household_profile_thumb));
        }

        viewHolder.follow_up.setOnClickListener(onClickListener);
        viewHolder.follow_up.setTag(smartRegisterClient);
        viewHolder.profilelayout.setOnClickListener(onClickListener);
        viewHolder.profilelayout.setTag(smartRegisterClient);

        if (iconPencilDrawable == null) {
            iconPencilDrawable = context.getResources().getDrawable(R.drawable.ic_pencil);
        }
        viewHolder.follow_up.setImageDrawable(iconPencilDrawable);
        viewHolder.follow_up.setOnClickListener(onClickListener);

        int umur = pc.getDetails().get("tanggal_lahir") != null ? age(pc.getDetails().get("tanggal_lahir")) : 0;
        //set default image for mother
        viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(pc.getDetails().get("jenis_kelamin").contains("k")
                ? R.drawable.child_boy_infant
                : R.drawable.child_girl_infant));

        viewHolder.name.setText(pc.getDetails().get("nama_bayi") != null ? pc.getDetails().get("nama_bayi") : " ");
        viewHolder.motherName.setText(pc.getDetails().get("nama_orang_tua")!=null?pc.getDetails().get("nama_orang_tua"):" ");
        viewHolder.village.setText(pc.getDetails().get("village")!=null?pc.getDetails().get("village"):" ");
        viewHolder.age.setText(pc.getDetails().get("tanggal_lahir")!=null?pc.getDetails().get("tanggal_lahir"):" ");
        viewHolder.gender.setText(pc.getDetails().get("jenis_kelamin") != null ? pc.getDetails().get("jenis_kelamin") : " ");

        viewHolder.hb0.setText(hasDate(pc,"hb0")
                ? pc.getDetails().get("hb0")
                : hasDate(pc,"hb1_kurang_7_hari")
                    ? pc.getDetails().get("hb1_kurang_7_hari")
                    :   hasDate(pc,"hb1_lebih_7_hari")
                        ? pc.getDetails().get("hb1_lebih_7_hari")
                        :(" ")
        );

        viewHolder.pol1.setText(
            hasDate(pc,"bcg_pol_1")
                ? pc.getDetails().get("bcg_pol_1")
                : hasDate(pc,"bcg")//pc.getDetails().get("bcg")!=null && pc.getDetails().get("bcg").length()>6
                    ? pc.getDetails().get("bcg")
                    : hasDate(pc,"polio1")//pc.getDetails().get("polio1")!=null && pc.getDetails().get("polio1").length()>6
                        ? pc.getDetails().get("polio1")
                        : " "
        );

        viewHolder.pol2.setText(
            hasDate(pc,"dpt_1_pol_2")//pc.getDetails().get("dpt_1_pol_2")!=null && pc.getDetails().get("dpt_1_pol_2").length()>6
                ? pc.getDetails().get("dpt_1_pol_2")
                : hasDate(pc,"dpt_hb1")//pc.getDetails().get("dpt_hb1")!=null && pc.getDetails().get("dpt_hb1").length()>6
                    ? pc.getDetails().get("dpt_hb1")
                    : hasDate(pc,"polio2")//pc.getDetails().get("polio2")!=null && pc.getDetails().get("polio_2").length()>6
                        ? pc.getDetails().get("polio2")
                        : " "
        );

        viewHolder.pol3.setText(
             hasDate(pc,"dpt_2_pol_3")//pc.getDetails().get("dpt_2_pol_3")!=null && pc.getDetails().get("dpt_2_pol_3").length()>6
                 ? pc.getDetails().get("dpt_2_pol_3")
                 : hasDate(pc,"dpt_hb2")//pc.getDetails().get("dpt_hb2")!=null && pc.getDetails().get("dpt_hb_2").length()>6
                     ? pc.getDetails().get("dpt_hb2")
                     : hasDate(pc,"polio3")//pc.getDetails().get("polio3")!=null && pc.getDetails().get("polio3").length()>6
                        ? pc.getDetails().get("polio3")
                        : " "
        );

        viewHolder.pol4.setText(
             hasDate(pc,"dpt_3_pol_4_ipv")//pc.getDetails().get("dpt_3_pol_4_ipv")!=null && pc.getDetails().get("dpt_3_pol_4_ipv").length()>6
                ? pc.getDetails().get("dpt_3_pol_4_ipv")
                : hasDate(pc,"dpt_hb3")//pc.getDetails().get("dpt_hb3")!=null && pc.getDetails().get("dpt_hb3").length()>6
                    ? pc.getDetails().get("dpt_hb3")
                    : hasDate(pc,"polio4")//pc.getDetails().get("polio4")!=null && pc.getDetails().get("polio4").length()>6
                       ? pc.getDetails().get("polio4")
                       : hasDate(pc,"ipv")//pc.getDetails().get("ipv")!=null && pc.getDetails().get("ipv").length()>6
                           ? pc.getDetails().get("ipv")
                           : " "
        );

        viewHolder.campak.setText(pc.getDetails().get("imunisasi_campak") != null ? pc.getDetails().get("imunisasi_campak") : " ");

//----- logo visibility, sometimes the variable contains blank string that count as not null, so we must check both the availability and content
        boolean a = hasDate(pc,"hb0") || hasDate(pc,"hb1_kurang_7_hari") || hasDate(pc,"hb1_lebih_7_hari");
        viewHolder.hbLogo.setImageResource(a ? R.drawable.ic_yes_large : umur > 0 ? R.drawable.vacc_late : umur == 0 ? R.mipmap.vacc_due : R.drawable.abc_list_divider_mtrl_alpha);
        //if(!a)
           // viewHolder.hb0Layout.setBackgroundColor(context.getResources().getColor(R.color.vaccBlue));

        setIcon(viewHolder.bcgLayout,viewHolder.pol1Logo,"bcg_pol_1","bcg,polio1",umur,1,pc);
        setIcon(viewHolder.hb1Layout,viewHolder.pol2Logo,"dpt_1_pol_2","dpt_hb1,polio2",umur,2,pc);
        setIcon(viewHolder.hb2Layout,viewHolder.pol3Logo,"dpt_2_pol_3","dpt_hb2,polio3",umur,3,pc);
        setIcon(viewHolder.hb3Layout,viewHolder.pol4Logo,"dpt_3_pol_4_ipv","dpt_hb3,polio4,ipv",umur,4,pc);
        setIcon(viewHolder.campakLayout,viewHolder.ipvLogo,"imunisasi_campak","imnisasi_campak",umur,9,pc);

        convertView.setLayoutParams(clientViewLayoutParams);
        return convertView;
    }

    //  updating icon
    private void setIcon(FrameLayout frame,ImageView image, String oldCode, String detailID,int umur, int indicator, CommonPersonObjectClient pc) {
        if(hasDate(pc,oldCode)){
            image.setImageResource(pc.getDetails().get(oldCode).contains("-")
                ? R.drawable.ic_yes_large
                : umur > indicator
                    ? R.drawable.vacc_late
                    : R.drawable.abc_list_divider_mtrl_alpha
            );
            return;
        }

        frame.setBackgroundColor(context.getResources().getColor(R.color.abc_background_cache_hint_selector_material_light));

        String[]var = detailID.split(",");
        boolean complete = false;
        boolean someComplete = false;

        for(int i=0;i<var.length;i++){
            someComplete = someComplete || (pc.getDetails().get(var[i]) != null && pc.getDetails().get(var[i]).length()>6);
        }

        if(someComplete){
            complete=true;
            for(int i=0;i<var.length;i++){
                complete = complete && (pc.getDetails().get(var[i]) != null && pc.getDetails().get(var[i]).length()>6);
            }
        }

        image.setImageResource(complete
            ? R.drawable.ic_yes_large
            : someComplete
                ? R.drawable.vacc_due
                : umur > indicator
                    ? R.drawable.vacc_late
                    : R.drawable.abc_list_divider_mtrl_alpha
        );

        if((umur == indicator) && !(complete || someComplete))
            frame.setBackgroundColor(context.getResources().getColor(R.color.vaccBlue));
    }


    /*
    * Used to check if the variable contains a date (10 character which representing yyyy-MM-dd) or not
    * params:
    * CommonPersonObjectClient pc
    * String variable
    *
    * return:
    * true - if the variable contains date
    * false - if the variable null or less than 10 character length
    * */

    private boolean hasDate(CommonPersonObjectClient pc, String variable){
        return pc.getDetails().get(variable)!=null && pc.getDetails().get(variable).length()>6;
    }

    //  month age calculation
    private int age(String date){
        String []dateOfBirth = date.split("-");
        String []currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()).split("-");

        int tahun = Integer.parseInt(currentDate[0]) - Integer.parseInt(dateOfBirth[0]);
        int bulan = Integer.parseInt(currentDate[1]) - Integer.parseInt(dateOfBirth[1]);
        int hari = Integer.parseInt(currentDate[2]) - Integer.parseInt(dateOfBirth[2]);

        int result = ((tahun*360) + (bulan*30) + hari)/30;
        result = result < 0 ? 0 : result;

        return result;
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
         public TextView hb0;
         public TextView pol1;
         public TextView pol2;
         public TextView complete;
         public TextView name;
         public TextView motherName;
         public TextView village;
         public TextView age;
         public TextView pol3;
         public TextView pol4;
         public TextView campak;
         public TextView gender;
         public ImageView hbLogo;
         public ImageView pol1Logo;
         public ImageView pol2Logo;
         public ImageView pol3Logo;
         public ImageView pol4Logo;
         public ImageView ipvLogo;
         public ImageView hbAlert;
         public ImageView pol1Alert;
         public ImageView pol2Alert;
         public ImageView pol3Alert;
         public ImageView pol4Alert;
         public ImageView measlesAlert;
         public FrameLayout hb0Layout;
         public FrameLayout bcgLayout;
         public FrameLayout hb1Layout;
         public FrameLayout hb2Layout;
         public FrameLayout hb3Layout;
         public FrameLayout campakLayout;
     }
}

