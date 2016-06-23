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
        if (convertView == null){
           convertView = (ViewGroup) inflater().inflate(R.layout.smart_register_jurim_client, null);
            viewHolder = new ViewHolder();
            viewHolder.profilelayout =  (LinearLayout)convertView.findViewById(R.id.profile_info_layout);

            viewHolder.name = (TextView)convertView.findViewById(R.id.name);
            viewHolder.motherName = (TextView)convertView.findViewById(R.id.motherName);
            viewHolder.village = (TextView)convertView.findViewById(R.id.village);
            viewHolder.age = (TextView)convertView.findViewById(R.id.age);
            viewHolder.gender = (TextView)convertView.findViewById(R.id.gender);

            viewHolder.hb1 = (TextView)convertView.findViewById(R.id.hb1);
            viewHolder.pol1 = (TextView)convertView.findViewById(R.id.pol1);
            viewHolder.pol2 = (TextView)convertView.findViewById(R.id.pol2);
            viewHolder.pol3 = (TextView)convertView.findViewById(R.id.pol3);
            viewHolder.pol4 = (TextView)convertView.findViewById(R.id.pol4);
            viewHolder.ipv = (TextView)convertView.findViewById(R.id.ipv);

            // checklist logo
            viewHolder.hbLogo = (ImageView)convertView.findViewById(R.id.hbLogo);
            viewHolder.pol1Logo = (ImageView)convertView.findViewById(R.id.pol1Logo);
            viewHolder.pol2Logo = (ImageView)convertView.findViewById(R.id.pol2Logo);
            viewHolder.pol3Logo = (ImageView)convertView.findViewById(R.id.pol3Logo);
            viewHolder.pol4Logo = (ImageView)convertView.findViewById(R.id.pol4Logo);
            viewHolder.ipvLogo = (ImageView)convertView.findViewById(R.id.measlesLogo);

            // alert logo
//            viewHolder.hbAlert = (ImageView) convertView.findViewById(R.id.hbAlert);
//            viewHolder.pol1Alert = (ImageView) convertView.findViewById(R.id.pol1Alert);
//            viewHolder.pol2Alert = (ImageView) convertView.findViewById(R.id.pol2Alert);
//            viewHolder.pol3Alert = (ImageView) convertView.findViewById(R.id.pol3Alert);
//            viewHolder.pol4Alert = (ImageView) convertView.findViewById(R.id.pol4Alert);
//            viewHolder.measlesAlert = (ImageView) convertView.findViewById(R.id.measlesAlert);

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
        CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;
        if (iconPencilDrawable == null) {
            iconPencilDrawable = context.getResources().getDrawable(R.drawable.ic_pencil);
        }
        viewHolder.follow_up.setImageDrawable(iconPencilDrawable);
        viewHolder.follow_up.setOnClickListener(onClickListener);

        int umur = pc.getDetails().get("tanggal_lahir") != null ? age(pc.getDetails().get("tanggal_lahir")) : 0;
        //set default image for mother
        viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(pc.getDetails().get("jenis_kelamin").contains("k")? R.drawable.child_boy_infant : R.drawable.child_girl_infant));

        viewHolder.name.setText(pc.getDetails().get("nama_bayi") != null ? pc.getDetails().get("nama_bayi") : "-");
        viewHolder.motherName.setText(pc.getDetails().get("nama_orang_tua")!=null?pc.getDetails().get("nama_orang_tua"):"-");
        viewHolder.village.setText(pc.getDetails().get("village")!=null?pc.getDetails().get("village"):"-");
        viewHolder.age.setText(pc.getDetails().get("tanggal_lahir")!=null?pc.getDetails().get("tanggal_lahir"):"-");
        viewHolder.gender.setText(pc.getDetails().get("jenis_kelamin") != null ? pc.getDetails().get("jenis_kelamin") : "-");

        viewHolder.hb1.setText(pc.getDetails().get("hb1_kurang_7_hari")!=null && pc.getDetails().get("hb1_kurang_7_hari").length()==10
                ? pc.getDetails().get("hb1_kurang_7_hari")
                : pc.getDetails().get("hb1_lebih_7_hari")!=null && pc.getDetails().get("hb1_lebih_7_hari").length()==10
                    ? pc.getDetails().get("hb1_lebih_7_hari"):"-");

        viewHolder.pol1.setText(pc.getDetails().get("bcg_pol_1")!=null ? pc.getDetails().get("bcg_pol_1"):"-");
        viewHolder.pol2.setText(pc.getDetails().get("dpt_1_pol_2")!=null ? pc.getDetails().get("dpt_1_pol_2"):"-");
        viewHolder.pol3.setText(pc.getDetails().get("dpt_2_pol_3")!=null ? pc.getDetails().get("dpt_2_pol_3"):"-");
        viewHolder.pol4.setText(pc.getDetails().get("dpt_3_pol_4_ipv") != null ? pc.getDetails().get("dpt_3_pol_4_ipv") : "-");
        viewHolder.ipv.setText(pc.getDetails().get("imunisasi_campak") != null ? pc.getDetails().get("imunisasi_campak") : "-");

        // logo visibility, sometimes the variable contains blank string that count as not null, so we must check both the availability and content
        boolean a = pc.getDetails().get("hb1_kurang_7_hari") != null ? pc.getDetails().get("hb1_kurang_7_hari").length() == 10 ? true : false : false;
        boolean b = a || (pc.getDetails().get("hb1_lebih_7_hari") != null ? pc.getDetails().get("hb1_lebih_7_hari").length() == 10 ? true : false : false);

        viewHolder.hbLogo.setImageResource(b ? R.drawable.ic_yes_large : umur > 0 ? R.drawable.ic_no : R.drawable.abc_list_divider_mtrl_alpha);
        setIcon(viewHolder.pol1Logo,"bcg_pol_1",umur,1,pc);
        setIcon(viewHolder.pol2Logo,"dpt_1_pol_2",umur,2,pc);
        setIcon(viewHolder.pol3Logo,"dpt_2_pol_3",umur,3,pc);
        setIcon(viewHolder.pol4Logo,"dpt_3_pol_4_ipv",umur,4,pc);
        setIcon(viewHolder.ipvLogo,"imunisasi_campak",umur,9,pc);

//        viewHolder.pol1Logo.setImageResource(pc.getDetails().get("bcg_pol_1")!=null ? pc.getDetails().get("bcg_pol_1").length()==10
//                ? R.drawable.ic_yes_large : umur > 1 ? R.drawable.ic_no : R.drawable.abc_list_divider_mtrl_alpha : R.drawable.abc_list_divider_mtrl_alpha);
//        viewHolder.pol2Logo.setImageResource(pc.getDetails().get("dpt_1_pol_2") != null ? pc.getDetails().get("dpt_1_pol_2").length() == 10
//                ? R.drawable.ic_yes_large : umur > 2 ? R.drawable.ic_no : R.drawable.abc_list_divider_mtrl_alpha : R.drawable.abc_list_divider_mtrl_alpha);
//        viewHolder.pol3Logo.setImageResource(pc.getDetails().get("dpt_2_pol_3")!=null ? pc.getDetails().get("dpt_2_pol_3").length()==10
//                ? R.drawable.ic_yes_large : umur > 3 ? R.drawable.ic_no : R.drawable.abc_list_divider_mtrl_alpha : R.drawable.abc_list_divider_mtrl_alpha);
//        viewHolder.pol4Logo.setImageResource(pc.getDetails().get("dpt_3_pol_4_ipv") != null ? pc.getDetails().get("dpt_3_pol_4_ipv").length() == 10
//                ? R.drawable.ic_yes_large : umur > 4 ? R.drawable.ic_no : R.drawable.abc_list_divider_mtrl_alpha : R.drawable.abc_list_divider_mtrl_alpha);
//        viewHolder.ipvLogo.setImageResource(pc.getDetails().get("imunisasi_campak")!=null ? pc.getDetails().get("imunisasi_campak").length()>4
//                ? R.drawable.ic_yes_large : umur > 9 ? R.drawable.ic_no : R.drawable.abc_list_divider_mtrl_alpha : R.drawable.abc_list_divider_mtrl_alpha);

        convertView.setLayoutParams(clientViewLayoutParams);
        return convertView;
    }

    //  updating icon
    private void setIcon(ImageView image, String detailID,int value, int indicator, CommonPersonObjectClient pc) {
        image.setImageResource(pc.getDetails().get(detailID) != null ? pc.getDetails().get(detailID).length() == 10
                ? R.drawable.ic_yes_large : value > indicator ? R.drawable.ic_no : R.drawable.abc_list_divider_mtrl_alpha : R.drawable.abc_list_divider_mtrl_alpha);
    }

    //  month age calculation
    private int age(String date){
        String []dateOfBirth = date.split("-");
        String []currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()).split("-");

        int tahun = Integer.parseInt(currentDate[0]) - Integer.parseInt(dateOfBirth[0]);
        int bulan = Integer.parseInt(currentDate[1]) - Integer.parseInt(dateOfBirth[1]);
        int hari = Integer.parseInt(currentDate[2]) - Integer.parseInt(dateOfBirth[2]);

        int result = (tahun*360 + bulan*30 + hari)/30;
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
         public TextView hb1;
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
     }

}

