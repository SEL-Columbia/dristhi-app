package org.ei.opensrp.vaccinator.child;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.ei.opensrp.Context;
import org.ei.opensrp.adapter.SmartRegisterPaginatedAdapter;
import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonObjectFilterOption;
import org.ei.opensrp.commonregistry.CommonObjectSort;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.domain.form.FieldOverrides;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.util.StringUtil;
import org.ei.opensrp.vaccinator.R;
import org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity;
import org.ei.opensrp.view.contract.ECClient;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.controller.FormController;
import org.ei.opensrp.view.controller.VillageController;
import org.ei.opensrp.view.dialog.AllClientsFilter;
import org.ei.opensrp.view.dialog.ChildAgeSort;
import org.ei.opensrp.view.dialog.DialogOption;
import org.ei.opensrp.view.dialog.DialogOptionMapper;
import org.ei.opensrp.view.dialog.DialogOptionModel;
import org.ei.opensrp.view.dialog.EditOption;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.LocationSelectorDialogFragment;
import org.ei.opensrp.view.dialog.OpenFormOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SmartRegisterDialogFragment;
import org.ei.opensrp.view.dialog.SortOption;
import org.json.JSONObject;
import org.opensrp.api.domain.Location;
import org.opensrp.api.util.EntityUtils;
import org.opensrp.api.util.LocationTree;
import org.opensrp.api.util.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.ClientlessOpenFormOption;
import util.barcode.Barcode;
import util.barcode.BarcodeIntentResult;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by Ahmed on 13-Oct-15.
 */
public class ChildSmartRegisterActivity extends SecuredNativeSmartRegisterActivity {

    private DefaultOptionsProvider defaultOptionProvider;
    private NavBarOptionsProvider navBarOptionsProvider;

    private SmartRegisterClientsProvider clientProvider = null;
    private CommonPersonObjectController controller;
    private VillageController villageController;
    private DialogOptionMapper dialogOptionMapper;
    private  HashMap<String,String> overrides;
    private final ClientActionHandler clientActionHandler = new ClientActionHandler();

    @Override
    protected SmartRegisterPaginatedAdapter adapter() {
        return new SmartRegisterPaginatedAdapter(clientsProvider());
    }

    @Override
    protected DefaultOptionsProvider getDefaultOptionsProvider() {
        return new DefaultOptionsProvider() {

            @Override
            public ServiceModeOption serviceMode() {
                return new ChildServiceModeOption(clientsProvider());
            }

            @Override
            public FilterOption villageFilter() {
                return new AllClientsFilter();
            }

            @Override
            public SortOption sortOption() {
                return new ChildDateSort(ChildDateSort.ByColumnAndByDetails.byDetails,"first_name");

            }

            @Override
            public String nameInShortFormForTitle() {
                return Context.getInstance().getStringResource(R.string.child_title);
            }
        };
    }

    @Override
    protected NavBarOptionsProvider getNavBarOptionsProvider() {
        return new NavBarOptionsProvider() {

            @Override
            public DialogOption[] filterOptions() {

                return new DialogOption[]{};
            }

            @Override
            public DialogOption[] serviceModeOptions() {
                return new DialogOption[]{};
            }

            @Override
            public DialogOption[] sortingOptions() {
                return new DialogOption[]{

                        new CommonObjectSort(CommonObjectSort.ByColumnAndByDetails.byDetails,false,"first_name",getResources().getString(R.string.child_alphabetical_sort)),
                        new CommonObjectSort(CommonObjectSort.ByColumnAndByDetails.byDetails,true,"program_client_id",getResources().getString(R.string.child_id_sort))
                };
            }



            @Override
            public String searchHint() {
                return getResources().getString(R.string.child_search_hint);
            }
        };
    }

    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        if (clientProvider == null) {
            clientProvider = new ChildSmartClientsProvider(
                    this,clientActionHandler , controller,context.alertService());
        }
        return clientProvider;
    }


    @Override
    protected void onCreation() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreation();
        setContentView(R.layout.smart_register_activity_customized);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        onInitialization();

        defaultOptionProvider = getDefaultOptionsProvider();
        navBarOptionsProvider = getNavBarOptionsProvider();

        setupViews();
    }


    @Override
    protected void onInitialization() {

       controller= new CommonPersonObjectController(context.allCommonsRepositoryobjects("pkchild"),
                context.allBeneficiaries(), context.listCache(),
                context.personObjectClientsCache(), "first_name", "pkchild", "child_reg_date",
                CommonPersonObjectController.ByColumnAndByDetails.byDetails );

        String locationjson = context.anmLocationController().get();


        context.formSubmissionRouter().getHandlerMap().put("child_followup_form",new ChildFollowupHandler(new ChildService(context.allBeneficiaries(),context.allTimelineEvents(), context.allCommonsRepositoryobjects("pkchild"),context.alertService())));
        dialogOptionMapper = new DialogOptionMapper();

    }

    @Override
    protected void startRegistration() {
        Intent intent = new Intent(Barcode.BARCODE_INTENT);
        intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
        startActivityForResult(intent, Barcode.BARCODE_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
            if(resultCode==RESULT_OK)
            {

                Bundle extras =data.getExtras();
                String qrcode= (String)    extras.get(Barcode.SCAN_RESULT);
                org.ei.opensrp.util.Log.logDebug("ANM DETAILS"+context.anmController().get());
                String locationjson = context.anmLocationController().get();
                LocationTree locationTree = EntityUtils.fromJson(locationjson, LocationTree.class);

                Map<String,TreeNode<String, Location>> locationMap =
                        locationTree.getLocationsHierarchy();

                addChildToList(locationMap,"Country");
                addChildToList(locationMap,"province");
                addChildToList(locationMap,"city");
                addChildToList(locationMap,"town");
                addChildToList(locationMap,"uc");
                if(getfilteredClients(qrcode)<= 0){

                    HashMap<String , String> map=new HashMap<String,String>();
                    map.put("provider_uc",locations.get("uc"));
                    map.put("provider_town",locations.get("town"));
                    map.put("provider_city",locations.get("city"));
                    map.put("provider_province",locations.get("province"));
                    map.put("existing_program_client_id",qrcode);
                    map.put("provider_location_id",locations.get("uc"));
                    map.put("gender","female");
                    map.put("provider_location_name", locations.get("uc"));
                    map.put("provider_id",context.anmService().fetchDetails().name());                   // map.put("e_bcg",)

                    setOverrides(map);
                    showFragmentDialog(new EditDialogOptionModel(map),null);
                }else {
                 getSearchView().setText(qrcode);
   // startFormActivity();
             }


                     //          controller.getClients();



            }


    }





    private DialogOption[] getEditOptions( HashMap<String,String> overridemap ) {
     /*//  = new HashMap<String,String>();
        overridemap.put("existing_MWRA","MWRA");
        overridemap.put("existing_location", "existing_location");*/
        return new DialogOption[]{

                new ClientlessOpenFormOption("Enrollment", "child_enrollment_form", formController,overridemap, ClientlessOpenFormOption.ByColumnAndByDetails.bydefault)
            //    new ClientlessOpenFormOption("Followup", "child_followup_fake_form", formController,overridemap, ClientlessOpenFormOption.ByColumnAndByDetails.byDetails)
        };
    }




    

    private class EditDialogOptionModel implements DialogOptionModel {
        private  HashMap<String,String> overrides1;

        public EditDialogOptionModel(HashMap<String,String> overrides1) {
        this.overrides1=overrides1;
        }

        @Override
        public DialogOption[] getDialogOptions() {
            return getEditOptions(this.overrides1);
        }

        @Override
        public void onDialogOptionSelection(DialogOption option, Object tag) {


            onEditSelection((EditOption) option, (SmartRegisterClient) tag);
        }
    }

    public HashMap<String,String> getOverrides() {
        return overrides;
    }
    public void setOverrides(HashMap<String,String>  overrides ){

        this.overrides=overrides;
    }




    @Override
    protected void onResumption() {
        super.onResumption();
        getDefaultOptionsProvider();
        updateSearchView();
    }

    private class ClientActionHandler implements View.OnClickListener {

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.child_profilepic:

                    ChildDetailActivity.childclient=(CommonPersonObjectClient)view.getTag();
                    Intent intent =new Intent(ChildSmartRegisterActivity.this,ChildDetailActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.child_next_visit_holder:
                    CommonPersonObjectClient client=(CommonPersonObjectClient)view.getTag();
                    org.ei.opensrp.util.Log.logDebug("ANM DETAILS"+context.anmController().get());
                    String locationjson = context.anmLocationController().get();
                    LocationTree locationTree = EntityUtils.fromJson(locationjson, LocationTree.class);

                    Map<String,TreeNode<String, Location>> locationMap =
                            locationTree.getLocationsHierarchy();

                    addChildToList(locationMap,"Country");
                    addChildToList(locationMap,"province");
                    addChildToList(locationMap,"city");
                    addChildToList(locationMap, "town");
                    addChildToList(locationMap, "uc");


                        HashMap<String , String> preloadmap=new HashMap<String,String>();
                        preloadmap.put("provider_uc",locations.get("uc"));
                        preloadmap.put("provider_town",locations.get("town"));
                        preloadmap.put("provider_city",locations.get("city"));
                        preloadmap.put("provider_province",locations.get("province"));
                    preloadmap.put("provider_id",context.anmService().fetchDetails().name());
                    preloadmap.put("existing_program_client_id",client.getDetails().get("existing_program_client_id")!=null?client.getDetails().get("existing_program_client_id"):"");
                    preloadmap.put("provider_location_id",locations.get("uc"));
                    preloadmap.put("gender",client.getDetails().get("gender"));
                    preloadmap.put("provider_location_name", locations.get("uc"));


                    preloadmap.put("existing_house_number",client.getDetails().get("house_number")!=null?client.getDetails().get("house_number"):"");
                    preloadmap.put("existing_street",client.getDetails().get("street")!=null?client.getDetails().get("street"):"");
                    preloadmap.put("existing_union_council",client.getDetails().get("union_council")!=null?client.getDetails().get("union_council"):"");
                    preloadmap.put("existing_town",client.getDetails().get("town")!=null?client.getDetails().get("town"):"");
                    preloadmap.put("existing_city_village",client.getDetails().get("city_village")!=null?client.getDetails().get("city_village"):"");
                    preloadmap.put("existing_province",client.getDetails().get("province")!=null?client.getDetails().get("province"):"");
                    preloadmap.put("existing_landmark",client.getDetails().get("landmark")!=null?client.getDetails().get("landmark"):"");
                    preloadmap.put("existing_gender",client.getDetails().get("gender")!=null?client.getDetails().get("gender"):"");

                    preloadmap.put("existing_first_name",client.getDetails().get("first_name")!=null?client.getDetails().get("first_name"):"");
                    preloadmap.put("existing_second_name",client.getDetails().get("last_name")!=null?client.getDetails().get("last_name"):"");
                    preloadmap.put("existing_father_name",client.getDetails().get("father_name")!=null?client.getDetails().get("father_name"):"");
                    preloadmap.put("existing_mother_name",client.getDetails().get("mother_name")!=null?client.getDetails().get("mother_name"):"");

                    preloadmap.put("existing_chid_dob",client.getDetails().get("chid_dob_confirm")!=null?client.getDetails().get("chid_dob_confirm"):"");
                    preloadmap.put("existing_ethnicity",client.getDetails().get("ethnicity")!=null?client.getDetails().get("ethnicity"):"");
                    preloadmap.put("existing_child_reg_date",client.getDetails().get("child_reg_date")!=null?client.getDetails().get("child_reg_date"):"");
                    preloadmap.put("existing_card_number",client.getDetails().get("epi_card_number")!=null?client.getDetails().get("epi_card_number"):"");
                    preloadmap.put("existing_child_was_suffering_from_a_disease_at_birth",client.getDetails().get("child_was_suffering_from_a_disease_at_birth")!=null?client.getDetails().get("child_was_suffering_from_a_disease_at_birth"):"");

                        preloadmap.putAll(getPreloadVaccinesData(client));
                    setOverrides(preloadmap);


                    startFollowupForms("child_followup_form", (SmartRegisterClient) view.getTag(), preloadmap, ByColumnAndByDetails.bydefault);
                    break;
            }
        }

    }




    @Override
    public void setupViews() {
        getDefaultOptionsProvider();

        super.setupViews();

        setServiceModeViewDrawableRight(null);
        updateSearchView();


    }



    public void updateSearchView(){
        getSearchView().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(final CharSequence cs, int start, int before, int count) {
                (new AsyncTask() {
                    SmartRegisterClients filteredClients;

                    @Override
                    protected Object doInBackground(Object[] params) {
//                        currentSearchFilter =
                        setCurrentSearchFilter(new ChildSearchOption(cs.toString()));
                        filteredClients = getClientsAdapter().getListItemProvider()
                                .updateClients(getCurrentVillageFilter(), getCurrentServiceModeOption(),
                                        getCurrentSearchFilter(), getCurrentSortOption());


                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
//                        clientsAdapter
//                                .refreshList(currentVillageFilter, currentServiceModeOption,
//                                        currentSearchFilter, currentSortOption);
                        getClientsAdapter().refreshClients(filteredClients);
                        getClientsAdapter().notifyDataSetChanged();
                        getSearchCancelView().setVisibility(isEmpty(cs) ? INVISIBLE : VISIBLE);
                        super.onPostExecute(o);
                    }
                }).execute();
//                currentSearchFilter = new HHSearchOption(cs.toString());
//                clientsAdapter
//                        .refreshList(currentVillageFilter, currentServiceModeOption,
//                                currentSearchFilter, currentSortOption);
//
//                searchCancelView.setVisibility(isEmpty(cs) ? INVISIBLE : VISIBLE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private Map<String,String> locations =new HashMap<String,String>();


    public void addChildToList(Map<String,TreeNode<String, Location>> locationMap, String locationTag){
        for(Map.Entry<String, TreeNode<String, Location>> entry : locationMap.entrySet()) {
            boolean tagFound=false;
            if(entry.getValue() != null) {
                Location l= entry.getValue().getNode();

                for(String s:l.getTags()){
                    if(s.equalsIgnoreCase(locationTag))
                    {
                        locations.put(locationTag,l.getName());
                        tagFound=true;
                        return;
                    }
                }



            }
            if(!tagFound){
                if(entry.getValue().getChildren()!=null) {
                    addChildToList(entry.getValue().getChildren(), locationTag);
                }
            }
        }
    }


    private int getfilteredClients(String filterString){
    int i=0;
        setCurrentSearchFilter(new ChildSearchOption(filterString));
        SmartRegisterClients  filteredClients = getClientsAdapter().getListItemProvider()
                .updateClients(getCurrentVillageFilter(), getCurrentServiceModeOption(),
                        getCurrentSearchFilter(), getCurrentSortOption());
        i=filteredClients.size();

    return i;
    }


    public enum ByColumnAndByDetails{
        byColumn,byDetails,bydefault;
    }
    private void startFollowupForms(String formName,SmartRegisterClient client ,HashMap<String , String> overrideStringmap , ByColumnAndByDetails byColumnAndByDetails) {


        if(overrideStringmap == null) {
            org.ei.opensrp.util.Log.logDebug("overrides data is null");
            formController.startFormActivity(formName, client.entityId(), null);
        }else{
            JSONObject overridejsonobject = new JSONObject();
            try {
                for (Map.Entry<String, String> entry : overrideStringmap.entrySet()) {
                    switch (byColumnAndByDetails){
                        case byDetails:
                            overridejsonobject.put(entry.getKey() , ((CommonPersonObjectClient)client).getDetails().get(entry.getValue()));
                            break;
                        case byColumn:
                            overridejsonobject.put(entry.getKey() , ((CommonPersonObjectClient)client).getColumnmaps().get(entry.getValue()));
                            break;
                        case bydefault:
                            overridejsonobject.put(entry.getKey() ,entry.getValue());
                            break;
                    }
                }
//                overridejsonobject.put("existing_MWRA", );
            }catch (Exception e){
                e.printStackTrace();
            }
            // org.ei.opensrp.util.Log.logDebug("overrides data is : " + overrideStringmap);
            FieldOverrides fieldOverrides = new FieldOverrides(overridejsonobject.toString());
            org.ei.opensrp.util.Log.logDebug("fieldOverrides data is : " + fieldOverrides.getJSONString());
            formController.startFormActivity(formName, client.entityId(), fieldOverrides.getJSONString());
        }
    }

    //use to get columns and get date of vaccines submitted
    private HashMap getPreloadVaccinesData(CommonPersonObjectClient client){
        HashMap<String , String > map=new HashMap<String ,String>();
        for(String s :client.getColumnmaps().keySet()){
            if(s.contains("bcg"))
            {
                if(client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_bcg", client.getColumnmaps().get(s));
                }else{
                    map.put("e_bcg","");

                }
            }else   if(s.contains("opv_0"))
            {
                if(client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_opv0", client.getColumnmaps().get(s));
                }else{
                    map.put("e_opv0","");

                }
            }
            else   if(s.contains("opv_1"))
            {
                if(client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_opv1", client.getColumnmaps().get(s));
                }else{
                    map.put("e_opv1","");

                }
            }
            else   if(s.contains("opv_2"))
            {
                if( client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_opv2", client.getColumnmaps().get(s));
                }else{
                    map.put("e_opv2","");

                }
            }
            else   if(s.contains("opv_3"))
            {
                if( client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_opv3", client.getColumnmaps().get(s));
                }else{
                    map.put("e_opv3","");

                }
            }
            else   if(s.contains("pcv_1"))
            {
                if(client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_pcv1", client.getColumnmaps().get(s));
                }else{
                    map.put("e_pcv1","");

                }
            }
            else   if(s.contains("pcv_2"))
            {
                if(client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_pcv2", client.getColumnmaps().get(s));
                }else{
                    map.put("e_pcv2","");

                }
            }
            else   if(s.contains("pcv_3"))
            {
                if(client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_pcv3", client.getColumnmaps().get(s));
                }else{
                    map.put("e_pcv3","");

                }
            }
            else   if(s.contains("pentavalent_1"))
            {
                if(client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_penta1", client.getColumnmaps().get(s));
                }else{
                    map.put("e_penta1","");

                }
            }
            else   if(s.contains("pentavalent_2"))
            {
                if(client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_penta2", client.getColumnmaps().get(s));
                }else{
                    map.put("e_penta2","");

                }
            }
            else   if(s.contains("pentavalent_3"))
            {
                if(client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_penta3", client.getColumnmaps().get(s));
                }else{
                    map.put("e_penta3","");

                }
            }
            else   if(s.contains("measles_1"))
            {
                if(client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_measles1", client.getColumnmaps().get(s));
                }else{
                    map.put("e_measles1","");

                }
            }
            else   if(s.contains("measles_2"))
            {
                if(client.getColumnmaps().get(s)!=null && client.getColumnmaps().get(s).toString()!=null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("e_measles2", client.getColumnmaps().get(s));
                }else{
                    map.put("e_measles2","");

                }
            }
        }
        return map;

    }
}
