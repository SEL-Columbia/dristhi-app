package org.ei.opensrp.vaksinator.imunisasiTT;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.cursoradapter.SmartRegisterCLientsProviderForCursorAdapter;
import org.ei.opensrp.repository.DetailsRepository;
import org.ei.opensrp.vaksinator.R;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

import java.text.SimpleDateFormat;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.joda.time.LocalDateTime.parse;

/**
 * Created by Dimas Ciputra on 2/16/15.
 */
public class TTSmartClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {
    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;
    private Drawable iconPencilDrawable;
    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected CommonPersonObjectController controller;

    AlertService alertService;
    public TTSmartClientsProvider(Context context,
                                     View.OnClickListener onClickListener,
                                     AlertService alertService) {
        this.onClickListener = onClickListener;
//        this.controller = controller;
        this.context = context;
        this.alertService = alertService;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(org.ei.opensrp.R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(org.ei.opensrp.R.color.text_black);

    }

    @Override
    public void getView(SmartRegisterClient smartRegisterClient, View convertView) {
        ViewHolder viewHolder;

        if(convertView.getTag() == null || !(convertView.getTag() instanceof  ViewHolder)){
            viewHolder = new ViewHolder();
            viewHolder.profilelayout =  (LinearLayout)convertView.findViewById(R.id.profile_info_layout);
            //----------Child Basic Information
            viewHolder.namaIbu = (TextView)convertView.findViewById(R.id.ttNamaIbu);
            viewHolder.namaSuami = (TextView)convertView.findViewById(R.id.ttNamaSuami);
            viewHolder.dusun = (TextView)convertView.findViewById(R.id.ttDusun);
            viewHolder.tanggalLahir = (TextView)convertView.findViewById(R.id.ttTanggalLahir);

            viewHolder.tt1Logo = (ImageView)convertView.findViewById(R.id.tt1Logo);
            viewHolder.tt2Logo = (ImageView)convertView.findViewById(R.id.tt2Logo);
            viewHolder.tt3Logo = (ImageView)convertView.findViewById(R.id.tt3Logo);
            viewHolder.tt4Logo = (ImageView)convertView.findViewById(R.id.tt4Logo);
            viewHolder.tt5Logo = (ImageView)convertView.findViewById(R.id.tt5Logo);

            viewHolder.tt1Tanggal = (TextView)convertView.findViewById(R.id.tanggalTT1);
            viewHolder.tt2Tanggal = (TextView)convertView.findViewById(R.id.tanggalTT2);
            viewHolder.tt3Tanggal = (TextView)convertView.findViewById(R.id.tanggalTT3);
            viewHolder.tt4Tanggal = (TextView)convertView.findViewById(R.id.tanggalTT4);
            viewHolder.tt5Tanggal = (TextView)convertView.findViewById(R.id.tanggalTT5);


            viewHolder.profilepic =(ImageView)convertView.findViewById(R.id.profilepic);
            viewHolder.follow_up = (ImageButton)convertView.findViewById(R.id.btn_edit);
//            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.drawable.child_boy_infant));
            viewHolder.profilepic =(ImageView)convertView.findViewById(R.id.img_profile);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;
        /*viewHolder.follow_up.setOnClickListener(onClickListener);
        viewHolder.follow_up.setTag(smartRegisterClient);
        viewHolder.profilelayout.setOnClickListener(onClickListener);
        viewHolder.profilelayout.setTag(smartRegisterClient);

        if (iconPencilDrawable == null) {
            iconPencilDrawable = context.getResources().getDrawable(R.drawable.ic_pencil);
        }
        viewHolder.follow_up.setImageDrawable(iconPencilDrawable);
        viewHolder.follow_up.setOnClickListener(onClickListener);*/

                                                                                                                            // IMPORTANT : data has 2 type: columnMaps and details

        AllCommonsRepository allancRepository =  org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("ec_ibu");// get all data from ec_ibu table
        CommonPersonObject ancobject = allancRepository.findByCaseID(pc.entityId());                                        // get all data related to entity id and transform it into CommonPersonObject

        DetailsRepository detailsRepository = org.ei.opensrp.Context.getInstance().detailsRepository();                     // gather all details from repository
        Map<String, String> details =  detailsRepository.getAllDetailsForClient(pc.entityId());                             // get specific detail from all details based on entity id
        details.putAll(ancobject.getColumnmaps());                                                                          // combine all columnMaps and details into 1 object.

        if(pc.getDetails() != null) {                                                                                       // used to update client details with details built on previous step
            pc.getDetails().putAll(details);
        }else{
            pc.setDetails(details);
        }
        viewHolder.namaIbu.setText(pc.getColumnmaps().get("namalengkap")!=null?pc.getColumnmaps().get("namalengkap"):"");
        viewHolder.namaSuami.setText(pc.getColumnmaps().get("namaSuami")!=null?pc.getColumnmaps().get("namaSuami"):"");
        viewHolder.dusun.setText(pc.getDetails().get("address1")!=null?pc.getDetails().get("address1"):"");
        viewHolder.tanggalLahir.setText(pc.getDetails().get("umur")!=null?pc.getDetails().get("umur"):"");

        String AncDate = "";
        boolean tt = false;
        if( pc.getDetails().get("statusImunisasitt") != null) {
            if (pc.getDetails().get("statusImunisasitt").equals("tt_ke_0")) {
                AncDate = pc.getDetails().get("ancDate") != null ? pc.getDetails().get("ancDate") : "-";
                tt = true;
            }
        }

        /*DetailsRepository detailsRepository = org.ei.opensrp.Context.getInstance().detailsRepository();
        detailsRepository.updateDetails(pc);

        String ages = pc.getColumnmaps().get("tanggalLahirAnak").substring(0, pc.getColumnmaps().get("tanggalLahirAnak").indexOf("T"));

        int umur = pc.getColumnmaps().get("tanggalLahirAnak") != null ? age(ages) : 0;

        viewHolder.namaIbu.setText(pc.getColumnmaps().get("namaBayi") != null ? pc.getColumnmaps().get("namaBayi") : " ");

        AllCommonsRepository childRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("ec_anak");
        CommonPersonObject childobject = childRepository.findByCaseID(pc.entityId());

        AllCommonsRepository kirep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("ec_kartu_ibu");
        final CommonPersonObject kiparent = kirep.findByCaseID(childobject.getColumnmaps().get("relational_id"));

        if(kiparent != null) {
            detailsRepository.updateDetails(kiparent);
            String namaayah = kiparent.getDetails().get("namaSuami") != null ? kiparent.getDetails().get("namaSuami") : "";
            String namaibu = kiparent.getColumnmaps().get("namalengkap") != null ? kiparent.getColumnmaps().get("namalengkap") : "";

            viewHolder.namaIbu.setText(namaibu);
            viewHolder.namaSuami.setText(namaayah);
            // viewHolder.no_ibu.setText(kiparent.getDetails().get("noBayi") != null ? kiparent.getDetails().get("noBayi") : "");
        }

        viewHolder.tanggalLahir.setText(kiparent.getColumnmaps().get("tanggalLahir")!=null
                ?     Integer.toString(age(ages)/12)+" "+ context.getResources().getString(R.string.year_short)
                + ", "+Integer.toString(age(ages)%12)+" "+ context.getResources().getString(R.string.month_short)
                : " ");*/

        viewHolder.tt1Tanggal.setText(AncDate);
        viewHolder.tt2Tanggal.setText("0000-00-00");
        viewHolder.tt3Tanggal.setText("0000-00-00");
        viewHolder.tt4Tanggal.setText("0000-00-00");
        viewHolder.tt5Tanggal.setText("0000-00-00");

        setIcon(viewHolder.tt1Logo, tt);
        setIcon(viewHolder.tt2Logo,true);
        setIcon(viewHolder.tt3Logo,true);
        setIcon(viewHolder.tt4Logo,true);
        setIcon(viewHolder.tt5Logo,true);

        convertView.setLayoutParams(clientViewLayoutParams);
        //  return convertView;
    }
    CommonPersonObjectController householdelcocontroller;

    private String latestDate(String[]dates){
        String max = dates[0]!=null
                ? dates[0].length()==10
                ? dates[0]
                : "0000-00-00"
                :"0000-00-00";
        for(int i=1;i<dates.length;i++){
            if(dates[i]==null)
                continue;
            if(dates[i].length()<10)
                continue;
            max =   (((Integer.parseInt(max.substring(0,4))-Integer.parseInt(dates[i].substring(0,4)))*360)
                    +((Integer.parseInt(max.substring(5,7))-Integer.parseInt(dates[i].substring(5,7)))*30)
                    +(Integer.parseInt(max.substring(8,10))-Integer.parseInt(dates[i].substring(8,10)))
            )<0 ? dates[i]:max;
        }
        return max.equals("0000-00-00")? "" : max;
    }

    //  updating icon
    private void setIcon(ImageView image, boolean isGiven) {
        image.setImageResource(isGiven ? R.drawable.ic_yes_large : R.drawable.abc_list_divider_mtrl_alpha);
    }

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
    //    @Override
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

    @Override
    public View inflatelayoutForCursorAdapter() {
        View View = inflater().inflate(R.layout.smart_register_tt_client, null);
        return View;
    }

    class ViewHolder {
        LinearLayout profilelayout;
        ImageView profilepic;
        ImageButton follow_up;
        public TextView namaIbu;
        public TextView namaSuami;
        public TextView dusun;
        public TextView tanggalLahir;
        public ImageView tt1Logo;
        public ImageView tt2Logo;
        public ImageView tt3Logo;
        public ImageView tt4Logo;
        public ImageView tt5Logo;
        public TextView tt1Tanggal;
        public TextView tt2Tanggal;
        public TextView tt3Tanggal;
        public TextView tt4Tanggal;
        public TextView tt5Tanggal;


    }


}