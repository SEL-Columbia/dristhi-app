package org.ei.opensrp.indonesia.kb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Strings;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.cursoradapter.SmartRegisterCLientsProviderForCursorAdapter;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.indonesia.R;

import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.view.contract.AlertDTO;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.ECProfilePhotoLoader;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;
import org.ei.opensrp.view.viewHolder.ProfilePhotoLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.ei.opensrp.util.StringUtil.humanize;
import static org.ei.opensrp.view.controller.ECSmartRegisterController.STATUS_DATE_FIELD;
import static org.ei.opensrp.view.controller.ECSmartRegisterController.STATUS_TYPE_FIELD;

/**
 * Created by Dimas Ciputra on 2/16/15.
 */
public class KBClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;
    static String bindobject = "kartu_ibu";
    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;
    private Drawable iconPencilDrawable;
    protected CommonPersonObjectController controller;

    AlertService alertService;

    public KBClientsProvider(Context context,
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
            convertView = (ViewGroup) inflater().inflate(R.layout.smart_register_kb_client, null);
            viewHolder = new ViewHolder();
            viewHolder.profilelayout =  (LinearLayout)convertView.findViewById(R.id.profile_info_layout);
            viewHolder.wife_name = (TextView)convertView.findViewById(R.id.wife_name);
            viewHolder.husband_name = (TextView)convertView.findViewById(R.id.txt_husband_name);
            viewHolder.village_name = (TextView)convertView.findViewById(R.id.txt_village_name);
            viewHolder.wife_age = (TextView)convertView.findViewById(R.id.wife_age);
            viewHolder.no_ibu = (TextView)convertView.findViewById(R.id.no_ibu);
         //   viewHolder.unique_id = (TextView)convertView.findViewById(R.id.unique_id);

            viewHolder.gravida = (TextView)convertView.findViewById(R.id.txt_gravida);
            viewHolder.parity = (TextView)convertView.findViewById(R.id.txt_parity);
            viewHolder.number_of_abortus = (TextView)convertView.findViewById(R.id.txt_number_of_abortus);
            viewHolder.number_of_alive = (TextView)convertView.findViewById(R.id.txt_number_of_alive);


            viewHolder.kb_method = (TextView)convertView.findViewById(R.id.kb_method);
            viewHolder.kb_mulai = (TextView)convertView.findViewById(R.id.kb_mulai);
            viewHolder.risk_HB = (TextView)convertView.findViewById(R.id.risk_HB);
            viewHolder.LILA =(TextView)convertView.findViewById(R.id.risk_LILA);

       //     viewHolder.risk_PenyakitKronis = (TextView)convertView.findViewById(R.id.txt_edd);
        //    viewHolder.risk_IMS = (TextView)convertView.findViewById(R.id.txt_edd_due);

      //      viewHolder.follow_up_due = (TextView)convertView.findViewById(R.id.txt_children_age_left);

            viewHolder.profilepic =(ImageView)convertView.findViewById(R.id.img_profile);
            viewHolder.follow_up = (ImageButton)convertView.findViewById(R.id.btn_edit);
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.woman_placeholder));
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.woman_placeholder));
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
        //set image
        //  final ImageView childview = (ImageView)convertView.findViewById(R.id.profilepic);

        viewHolder.wife_name.setText(pc.getDetails().get("namalengkap")!=null?pc.getDetails().get("namalengkap"):"");
        viewHolder.husband_name.setText(pc.getDetails().get("namaSuami")!=null?pc.getDetails().get("namaSuami"):"");
        viewHolder.village_name.setText(pc.getDetails().get("desa")!=null?pc.getDetails().get("desa"):"");
        viewHolder.wife_age.setText(pc.getDetails().get("umur")!=null?pc.getDetails().get("umur"):"");
        viewHolder.no_ibu.setText(pc.getDetails().get("noIbu")!=null?pc.getDetails().get("noIbu"):"");
     //   viewHolder.unique_id.setText(pc.getDetails().get("unique_id")!=null?pc.getDetails().get("unique_id"):"");

        viewHolder.gravida.setText(pc.getDetails().get("gravida")!=null?pc.getDetails().get("gravida"):"-");
        viewHolder.parity.setText(pc.getDetails().get("partus")!=null?pc.getDetails().get("partus"):"-");
        viewHolder.number_of_abortus.setText(pc.getDetails().get("abortus")!=null?pc.getDetails().get("abortus"):"-");
        viewHolder.number_of_alive.setText(pc.getDetails().get("hidup")!=null?pc.getDetails().get("hidup"):"-");

        viewHolder.kb_method.setText(pc.getDetails().get("jenisKontrasepsi")!=null?pc.getDetails().get("jenisKontrasepsi"):"");
        viewHolder.kb_mulai.setText(pc.getDetails().get("tanggalkunjungan")!=null?pc.getDetails().get("tanggalkunjungan"):"");
        viewHolder.risk_HB.setText(pc.getDetails().get("laboratoriumPeriksaHbHasil")!=null?pc.getDetails().get("laboratoriumPeriksaHbHasil"):"-");
        viewHolder.LILA.setText(pc.getDetails().get("alkilila")!=null?pc.getDetails().get("alkilila"):"");
//        viewHolder.risk_IMS.setText(pc.getDetails().get("htp")!=null?pc.getDetails().get("htp"):"");
//        viewHolder.follow_up_due.setText(pc.getDetails().get("tanggalLahirAnak")!=null?pc.getDetails().get("tanggalLahirAnak"):"");
//        viewHolder.risk_PenyakitKronis.setText(pc.getDetails().get("penyakitKronis")!=null?pc.getDetails().get("penyakitKronis"):"");


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

        TextView wife_name ;
        TextView husband_name ;
        TextView village_name;
        TextView wife_age;
        LinearLayout profilelayout;
        ImageView profilepic;
        TextView gravida;
        Button warnbutton;
        ImageButton follow_up;
        TextView parity;
        TextView number_of_abortus;
        TextView number_of_alive;
        TextView no_ibu;
        TextView unique_id;
        TextView kb_method;
        TextView risk_HB;
        TextView LILA;
        TextView risk_PenyakitKronis;
        TextView risk_IMS;
        TextView follow_up_due;
        TextView kb_mulai;
    }


}